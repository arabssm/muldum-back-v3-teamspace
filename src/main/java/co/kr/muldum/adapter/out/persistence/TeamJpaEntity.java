package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.TeamType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "team")
public class TeamJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID teamId;

    @Column(nullable = false, length = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TeamType teamType;

    @Column(columnDefinition = "text")
    private String readme;

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    protected TeamJpaEntity() {
    }

    public TeamJpaEntity(UUID teamId, String name, TeamType teamType, String readme, String iconUrl) {
        this.teamId = teamId;
        this.name = name;
        this.teamType = teamType;
        this.readme = readme;
        this.iconUrl = iconUrl;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public String getReadme() {
        return readme;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void updateReadme(String readme) {
        this.readme = readme;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
