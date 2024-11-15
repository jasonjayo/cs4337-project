package com.example.playerservice.model;

import com.example.shared.dto.TeamDTO;
import com.example.shared.feign.TeamClient;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    @Column(name="player_name")
    private String playerName;

    @Column(name="google_id")
    private String googleId;

    public String getName() {
        return playerName;
    }

    @ElementCollection
    @CollectionTable(name = "player_team", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "team_id")
    private Set<Long> teamIds;

    @Transient
    private List<TeamDTO> teams;

}
