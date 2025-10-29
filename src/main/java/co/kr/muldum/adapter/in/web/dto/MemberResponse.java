package co.kr.muldum.adapter.in.web.dto;

import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long userId;
    private final String studentName;

    private MemberResponse(Long userId, String studentName) {
        this.userId = userId;
        this.studentName = studentName;
    }

    public static MemberResponse of(Long userId, String studentName) {
        return new MemberResponse(userId, studentName);
    }
}