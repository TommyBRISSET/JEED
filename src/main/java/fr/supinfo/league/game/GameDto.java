package fr.supinfo.league.game;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record GameDto(UUID id, String description, @NotNull UUID matchDayId, @NotNull UUID homeTeamId,
                      @NotNull UUID visitorTeamId,
                      @NotNull LocalTime startTime, LocalTime endTime, Boolean isPostponed,
                      String postponementReason, Boolean isSuspended, String suspensionReason,
                      List<String> comments, List<String> events){
}