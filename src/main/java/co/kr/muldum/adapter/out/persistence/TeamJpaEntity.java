package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.TeamType;
import jakarta.persistence.*;

@Entity
@Table(name = "team")
public class TeamJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long teamId;

    @Column(nullable = false, length = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TeamType teamType;

    @Column(columnDefinition = "text")
    private String readme;

    protected TeamJpaEntity() {
    }

    public TeamJpaEntity(Long teamId, String name, TeamType teamType, String readme) {
        this.teamId = teamId;
        this.name = name;
        this.teamType = teamType;
        this.readme = readme;
    }

    public Long getTeamId() {
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

    public void updateReadme(String readme) {
        this.readme = readme;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
