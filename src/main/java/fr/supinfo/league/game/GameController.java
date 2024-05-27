package fr.supinfo.league.game;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/games")
@RestController
public class GameController {

    private final GameServices gameServices;

    @GetMapping
    public List<GameDto> getGames(@RequestParam(required = false, name = "date") LocalDate date) {
        return this.gameServices.getGames(date);
    }

    // configure security roles using annotations https://www.baeldung.com/spring-security-method-security
    @RolesAllowed({"ADMIN"})
    @PostMapping
    public GameDto createGame(@RequestBody GameDto game) {
        return this.gameServices.createGame(game);
    }

    // configure security roles using annotations https://www.baeldung.com/spring-security-method-security
    @RolesAllowed({"JOURNALIST"})
    @PutMapping("/{gameId}/time")
    public void setGameTime(@PathVariable UUID gameId, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        this.gameServices.setGameTime(gameId, startTime, endTime);
    }

    @RolesAllowed({"JOURNALIST"})
    @PostMapping("/{gameId}/comments")
    public void addComment(@PathVariable UUID gameId, @RequestBody String comment) {
        this.gameServices.addComment(gameId, comment);
    }

    @RolesAllowed({"JOURNALIST"})
    @GetMapping("/{gameId}/comments")
    public List<String> getComments(@PathVariable UUID gameId) {
        return this.gameServices.getComments(gameId);
    }

    @RolesAllowed({"JOURNALIST"})
    @PostMapping("/{gameId}/events")
    public void addEvent(@PathVariable UUID gameId, @RequestBody String event) {
        this.gameServices.addEvent(gameId, event);
    }

    @RolesAllowed({"MEMBER-LEAGUE"})
    @PutMapping("/{gameId}/postpone")
    public void postponeGame(@PathVariable UUID gameId, @RequestBody String reason) {
        this.gameServices.postponeGame(gameId, reason);
    }

    @RolesAllowed({"MEMBER-LEAGUE"})
    @PutMapping("/{gameId}/suspend")
    public void suspendGame(@PathVariable UUID gameId, @RequestBody String reason) {
        this.gameServices.suspendGame(gameId, reason);
    }
}
