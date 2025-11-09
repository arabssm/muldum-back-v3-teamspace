package co.kr.muldum.adapter.in.messaging;

import co.kr.muldum.adapter.out.messaging.dto.TeamQueryRequest;
import co.kr.muldum.adapter.out.messaging.dto.TeamQueryResponse;
import co.kr.muldum.adapter.out.persistence.MemberJpaEntity;
import co.kr.muldum.adapter.out.persistence.MemberJpaRepository;
import co.kr.muldum.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamQueryConsumer {

    private final MemberJpaRepository memberJpaRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.TEAM_QUERY_QUEUE)
    public void handleTeamQuery(TeamQueryRequest request) {
        log.info("Received team query request for userId: {} with correlationId: {}",
                request.getUserId(), request.getCorrelationId());

        try {
            Optional<MemberJpaEntity> memberOpt = memberJpaRepository.findByUserId(request.getUserId());

            TeamQueryResponse response;
            if (memberOpt.isPresent()) {
                UUID teamId = memberOpt.get().getTeam().getTeamId();
                response = TeamQueryResponse.success(request.getUserId(), teamId, request.getCorrelationId());
                log.info("Found teamId: {} for userId: {}", teamId, request.getUserId());
            } else {
                response = TeamQueryResponse.failure(
                        request.getUserId(),
                        request.getCorrelationId(),
                        "No team found for userId: " + request.getUserId()
                );
                log.warn("No team found for userId: {}", request.getUserId());
            }

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TEAM_QUERY_EXCHANGE,
                    RabbitMQConfig.TEAM_QUERY_RESPONSE_ROUTING_KEY,
                    response
            );

        } catch (Exception e) {
            log.error("Error processing team query for userId: {}", request.getUserId(), e);
            TeamQueryResponse errorResponse = TeamQueryResponse.failure(
                    request.getUserId(),
                    request.getCorrelationId(),
                    "Error: " + e.getMessage()
            );
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TEAM_QUERY_EXCHANGE,
                    RabbitMQConfig.TEAM_QUERY_RESPONSE_ROUTING_KEY,
                    errorResponse
            );
        }
    }
}
