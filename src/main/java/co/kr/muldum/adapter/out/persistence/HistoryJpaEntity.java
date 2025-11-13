package co.kr.muldum.adapter.out.persistence;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "histories")
public class HistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false)
    private int generation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", columnDefinition = "TEXT")
    private String logoUrl;

    @Column(columnDefinition = "TEXT")
    private String slogan;

    @Column(name = "detail_background", columnDefinition = "TEXT")
    private String detailBackground;

    @Column(name = "detail_features", columnDefinition = "TEXT")
    private String detailFeatures;

    @Column(name = "detail_research", columnDefinition = "TEXT")
    private String detailResearch;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryAwardJpaEntity> awards = new ArrayList<>();

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryContributorJpaEntity> contributors = new ArrayList<>();

    protected HistoryJpaEntity() {
    }

    public HistoryJpaEntity(Long id, String name, int generation, String description,
                            String logoUrl, String slogan, String detailBackground,
                            String detailFeatures, String detailResearch,
                            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.description = description;
        this.logoUrl = logoUrl;
        this.slogan = slogan;
        this.detailBackground = detailBackground;
        this.detailFeatures = detailFeatures;
        this.detailResearch = detailResearch;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGeneration() {
        return generation;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getDetailBackground() {
        return detailBackground;
    }

    public String getDetailFeatures() {
        return detailFeatures;
    }

    public String getDetailResearch() {
        return detailResearch;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<HistoryAwardJpaEntity> getAwards() {
        return awards;
    }

    public List<HistoryContributorJpaEntity> getContributors() {
        return contributors;
    }
}
