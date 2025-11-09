package co.kr.muldum.adapter.out.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamQueryRequest {
    private Long userId;
    private String correlationId;
}