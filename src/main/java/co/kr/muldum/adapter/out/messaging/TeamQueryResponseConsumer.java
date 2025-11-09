package co.kr.muldum.adapter.out.messaging;

import co.kr.muldum.adapter.out.messaging.dto.TeamQueryResponse;
import co.kr.muldum.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamQueryResponseConsumer {

    private final TeamQueryProducer teamQueryProducer;

    @RabbitListener(queues = RabbitMQConfig.TEAM_QUERY_RESPONSE_QUEUE)
    public void handleTeamQueryResponse(TeamQueryResponse response) {
        log.info("Received team query response for userId: {} with correlationId: {}",
                response.getUserId(), response.getCorrelationId());

        teamQueryProducer.completeRequest(response.getCorrelationId(), response);
    }
}