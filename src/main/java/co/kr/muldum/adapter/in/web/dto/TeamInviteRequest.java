package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInviteRequest {

    @Schema(description = "팀 정보를 포함한 Google Sheet URL", example = "https://docs.google.com/spreadsheets/d/...")
    @NotBlank(message = "Google Sheet URL은 필수입니다.")
    private String googleSheetUrl;

    @Schema(description = "초대할 팀 타입", example = "major", allowableValues = {"major", "network", "autonomous", "greaduation"})
    @NotBlank(message = "팀 타입은 필수입니다.")
    private String teamType;

    public TeamInviteRequest(String googleSheetUrl, String teamType) {
        this.googleSheetUrl = googleSheetUrl;
        this.teamType = teamType;
    }
}
