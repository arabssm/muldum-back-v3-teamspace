package co.kr.muldum.domain.model;

import lombok.Getter;

@Getter
public class Team {

    private final Long teamId;
    private final String name;
    private final TeamType teamType;
    private final String readme;

    private Team(Long teamId, String name, TeamType teamType, String readme) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        this.teamId = teamId;
        this.name = name;
        this.teamType = teamType;
        this.readme = readme;
    }

    public static Team create(String name, TeamType teamType) {
        return new Team(null, name, teamType, null);
    }

    public static Team of(Long teamId, String name, TeamType teamType, String readme) {
        return new Team(teamId, name, teamType, readme);
    }

    public Team withReadme(String readme) {
        return new Team(this.teamId, this.name, this.teamType, readme);
    }

    public Team withId(Long teamId) {
        return new Team(teamId, this.name, this.teamType, this.readme);
    }
}
