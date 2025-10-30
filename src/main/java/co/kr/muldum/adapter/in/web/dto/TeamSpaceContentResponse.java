package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "팀스페이스 컨텐츠 응답")
@Getter
public class TeamSpaceContentResponse {

    @Schema(description = "팀 목록")
    private final List<TeamContentResponse> content;

    private TeamSpaceContentResponse(List<TeamContentResponse> content) {
        this.content = content;
    }

    public static TeamSpaceContentResponse of(List<TeamContentResponse> content) {
        return new TeamSpaceContentResponse(content);
    }
}