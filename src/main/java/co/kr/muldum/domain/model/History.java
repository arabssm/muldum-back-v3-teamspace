package co.kr.muldum.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class History {
    private final Long historyId;
    private final String name;
    private final int generation;
    private final String description;
    private final String logoUrl;
    private final String slogan;
    private final String detailBackground;
    private final String detailFeatures;
    private final String detailResearch;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final List<HistoryAward> awards;
    private final List<HistoryContributor> contributors;

    private History(Long historyId, String name, int generation, String description,
                    String logoUrl, String slogan, String detailBackground,
                    String detailFeatures, String detailResearch,
                    OffsetDateTime createdAt, OffsetDateTime updatedAt,
                    List<HistoryAward> awards, List<HistoryContributor> contributors) {
        this.historyId = historyId;
        this.name = validateName(name);
        this.generation = validateGeneration(generation);
        this.description = description;
        this.logoUrl = logoUrl;
        this.slogan = slogan;
        this.detailBackground = detailBackground;
        this.detailFeatures = detailFeatures;
        this.detailResearch = detailResearch;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.awards = awards != null ? new ArrayList<>(awards) : new ArrayList<>();
        this.contributors = contributors != null ? new ArrayList<>(contributors) : new ArrayList<>();
    }

    public static History create(String name, int generation, String description,
                                  String logoUrl, String slogan, String detailBackground,
                                  String detailFeatures, String detailResearch) {
        return new History(null, name, generation, description, logoUrl, slogan,
                detailBackground, detailFeatures, detailResearch,
                OffsetDateTime.now(), null, new ArrayList<>(), new ArrayList<>());
    }

    public static History of(Long historyId, String name, int generation, String description,
                             String logoUrl, String slogan, String detailBackground,
                             String detailFeatures, String detailResearch,
                             OffsetDateTime createdAt, OffsetDateTime updatedAt,
                             List<HistoryAward> awards, List<HistoryContributor> contributors) {
        return new History(historyId, name, generation, description, logoUrl, slogan,
                detailBackground, detailFeatures, detailResearch,
                createdAt, updatedAt, awards, contributors);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("History name cannot be null or empty");
        }
        return name;
    }

    private int validateGeneration(int generation) {
        if (generation < 1) {
            throw new IllegalArgumentException("Generation must be at least 1");
        }
        return generation;
    }

    public History withId(Long historyId) {
        return new History(historyId, this.name, this.generation, this.description,
                this.logoUrl, this.slogan, this.detailBackground, this.detailFeatures,
                this.detailResearch, this.createdAt, this.updatedAt, this.awards, this.contributors);
    }

    public Long getHistoryId() {
        return historyId;
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

    public List<HistoryAward> getAwards() {
        return new ArrayList<>(awards);
    }

    public List<HistoryContributor> getContributors() {
        return new ArrayList<>(contributors);
    }
}
