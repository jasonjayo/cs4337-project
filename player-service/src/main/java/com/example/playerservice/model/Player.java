package com.example.playerservice.model;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(name="email")
    private String email;

    public String getName() {
        return playerName;
    }

//    @ElementCollection
//    @CollectionTable(name = "player_team", joinColumns = @JoinColumn(name = "player_id"))
//    @Column(name = "team_id")
//    private Set<Long> teamIds;
}
