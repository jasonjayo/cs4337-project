package com.example.playerservice.model;

import com.example.shared.dto.TeamDTO;
import com.example.shared.feign.TeamClient;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "google_id")
    private String googleId;

    // list of the IDs for the teams player is a member of
    @ElementCollection
    @CollectionTable(name = "player_team", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "team_id")
    private Set<Long> teamIds;

    // list of attendance records for this player
    @ElementCollection
    @CollectionTable(name = "attendance", joinColumns = @JoinColumn(name = "player_id"))
    @MapKeyColumn(name = "event_id")
    @Column(name = "status")
    private Map<Long, Boolean> eventAttendances;

    @Transient
    private List<TeamDTO> teams;

    public String getName() {
        return playerName;
    }


}
