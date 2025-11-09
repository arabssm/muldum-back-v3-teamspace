package co.kr.muldum.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Team {

    private final UUID teamId;
    private final String name;
    private final TeamType teamType;
    private final String readme;
    private final String iconUrl;

    private Team(UUID teamId, String name, TeamType teamType, String readme, String iconUrl) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        this.teamId = teamId;
        this.name = name;
        this.teamType = teamType;
        this.readme = readme;
        this.iconUrl = iconUrl;
    }

    public static Team create(String name, TeamType teamType) {
        return new Team(null, name, teamType, null, null);
    }

    public static Team of(UUID teamId, String name, TeamType teamType, String readme, String iconUrl) {
        return new Team(teamId, name, teamType, readme, iconUrl);
    }

    public Team withReadme(String readme) {
        return new Team(this.teamId, this.name, this.teamType, readme, this.iconUrl);
    }

    public Team withIcon(String iconUrl) {
        return new Team(this.teamId, this.name, this.teamType, this.readme, iconUrl);
    }

    public Team withId(UUID teamId) {
        return new Team(teamId, this.name, this.teamType, this.readme, this.iconUrl);
    }
}
