package co.kr.muldum.adapter.out.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamQueryResponse {
    private Long userId;
    private UUID teamId;
    private String correlationId;
    private boolean success;
    private String errorMessage;

    public static TeamQueryResponse success(Long userId, UUID teamId, String correlationId) {
        return new TeamQueryResponse(userId, teamId, correlationId, true, null);
    }

    public static TeamQueryResponse failure(Long userId, String correlationId, String errorMessage) {
        return new TeamQueryResponse(userId, null, correlationId, false, errorMessage);
    }
}