package com.example.shared.dto;

public class TeamDTO {
    private Long teamId;
    private String pin;
    private String teamName;
    private String manager;

    // Constructors, getters, setters
    public TeamDTO() {
    }

    public TeamDTO(Long id, String name, String manager) {
        this.teamId = id;
        this.teamName = name;
        this.manager = manager;
    }

    public String getPin() {
        return pin;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getManager() {
        return manager;
    }


//    public Long getId() {
//        return teamId;
//    }
//
//    public void setId(Long id) {
//        this.teamId = id;
//    }
//
//    public String getName() {
//        return teamName;
//    }
//
//    public void setName(String name) {
//        this.teamName = name;
//    }
}
