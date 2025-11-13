package co.kr.muldum.adapter.out.persistence;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pages")
public class PageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "team_id", nullable = false, columnDefinition = "uuid")
    private UUID teamId;

    @Column(columnDefinition = "TEXT")
    private String banner;

    @Column(columnDefinition = "TEXT")
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamJpaEntity team;

    protected PageJpaEntity() {
    }

    public PageJpaEntity(Long id, String content, UUID teamId, String banner, String icon) {
        this.id = id;
        this.content = content;
        this.teamId = teamId;
        this.banner = banner;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public String getBanner() {
        return banner;
    }

    public String getIcon() {
        return icon;
    }

    public TeamJpaEntity getTeam() {
        return team;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateBanner(String banner) {
        this.banner = banner;
    }

    public void updateIcon(String icon) {
        this.icon = icon;
    }
}
