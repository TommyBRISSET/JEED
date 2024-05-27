package fr.supinfo.league.game;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class GameEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID matchDayId;
    private UUID homeTeamId;
    private UUID visitorTeamId;
    @ElementCollection
    private List<String> comments;
    @ElementCollection
    private List<String> events;
    private Boolean isPostponed;
    private String  postponementReason;
    private Boolean isSuspended;
    private String  suspensionReason;
}
