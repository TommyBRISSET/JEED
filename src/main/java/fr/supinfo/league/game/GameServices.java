package fr.supinfo.league.game;

import fr.supinfo.league.season.matchday.MatchDayServices;
import fr.supinfo.league.team.TeamServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GameServices {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    private final MatchDayServices matchDayServices;
    private final TeamServices teamServices;

    public List<GameDto> getGames(LocalDate date) {
        List<GameDto> result;
        if (Objects.isNull(date)) {
            result = this.gameMapper.entityToDto(this.gameRepository.findAll());
        } else {
            Optional<UUID> matchDayId = this.matchDayServices.retrieveMatchDayId(date);
            if (matchDayId.isPresent()) {
                result = this.gameMapper.entityToDto(this.gameRepository.findByMatchDayId(matchDayId.get()));
            } else {
                result = Collections.emptyList();
            }
        }
        return result;
    }

    public GameDto createGame(GameDto game) {
        // checks
        this.matchDayServices.checkMatchDayToCreateGame(game.matchDayId());
        this.teamServices.checkTeams(game.homeTeamId(), game.visitorTeamId());

        GameEntity gameEntity = this.gameMapper.dtoToEntity(game);
        GameEntity saved = this.gameRepository.save(gameEntity);
        return this.gameMapper.entityToDto(saved);
    }

    public void setGameTime(UUID gameId, LocalTime startTime, LocalTime endTime) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        gameEntity.setStartTime(startTime);
        gameEntity.setEndTime(endTime);
        this.gameRepository.save(gameEntity);
    }

    public void addComment(UUID gameId, String comment) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        gameEntity.getComments().add(comment);
        this.gameRepository.save(gameEntity);
    }

    public List<String> getComments(UUID gameId) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        return gameEntity.getComments();
    }

    public void addEvent(UUID gameId, String event) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        gameEntity.getEvents().add(event);
        this.gameRepository.save(gameEntity);
    }

    public void postponeGame(UUID gameId, String reason) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();

        if (gameEntity.getStartTime().isAfter(LocalTime.now())) {
            gameEntity.setIsPostponed(true);
            gameEntity.setPostponementReason(reason);
            this.gameRepository.save(gameEntity);
        } else {
            throw new IllegalStateException("Game has already started and cannot be postponed.");
        }
    }

    public void suspendGame(UUID gameId, String reason) {
        GameEntity gameEntity = this.gameRepository.findById(gameId).orElseThrow();

        if (gameEntity.getStartTime().isBefore(LocalTime.now())) {
            gameEntity.setIsSuspended(true);
            gameEntity.setSuspensionReason(reason);
            this.gameRepository.save(gameEntity);
        } else {
            throw new IllegalStateException("Game has not started and cannot be suspended.");
        }
    }
}
