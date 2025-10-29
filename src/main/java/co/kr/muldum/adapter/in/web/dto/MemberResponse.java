package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "팀 멤버 정보")
@Getter
public class MemberResponse {

    @Schema(description = "사용자 ID", example = "1001")
    private final Long userId;

    @Schema(description = "학생 이름 (학번 포함)", example = "2312이효준")
    private final String studentName;

    private MemberResponse(Long userId, String studentName) {
        this.userId = userId;
        this.studentName = studentName;
    }

    public static MemberResponse of(Long userId, String studentName) {
        return new MemberResponse(userId, studentName);
    }
}