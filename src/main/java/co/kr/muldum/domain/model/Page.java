package co.kr.muldum.domain.model;

import java.util.UUID;

public class Page {
    private final Long pageId;
    private final String content;
    private final UUID teamId;
    private final String banner;
    private final String icon;

    private Page(Long pageId, String content, UUID teamId, String banner, String icon) {
        this.pageId = pageId;
        this.content = content;
        this.teamId = validateTeamId(teamId);
        this.banner = banner;
        this.icon = icon;
    }

    public static Page create(String content, UUID teamId, String banner, String icon) {
        return new Page(null, content, teamId, banner, icon);
    }

    public static Page of(Long pageId, String content, UUID teamId, String banner, String icon) {
        return new Page(pageId, content, teamId, banner, icon);
    }

    private UUID validateTeamId(UUID teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("Team ID cannot be null");
        }
        return teamId;
    }

    public Page withId(Long pageId) {
        return new Page(pageId, this.content, this.teamId, this.banner, this.icon);
    }

    public Page withContent(String content) {
        return new Page(this.pageId, content, this.teamId, this.banner, this.icon);
    }

    public Page withBanner(String banner) {
        return new Page(this.pageId, this.content, this.teamId, banner, this.icon);
    }

    public Page withIcon(String icon) {
        return new Page(this.pageId, this.content, this.teamId, this.banner, icon);
    }

    public Long getPageId() {
        return pageId;
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
}
