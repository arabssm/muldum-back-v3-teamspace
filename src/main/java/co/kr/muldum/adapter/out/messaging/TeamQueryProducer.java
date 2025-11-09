package co.kr.muldum.adapter.out.messaging;

import co.kr.muldum.adapter.out.messaging.dto.TeamQueryRequest;
import co.kr.muldum.adapter.out.messaging.dto.TeamQueryResponse;
import co.kr.muldum.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamQueryProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<TeamQueryResponse>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<TeamQueryResponse> getTeamIdByUserId(Long userId) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<TeamQueryResponse> future = new CompletableFuture<>();

        pendingRequests.put(correlationId, future);

        TeamQueryRequest request = new TeamQueryRequest(userId, correlationId);

        log.info("Sending team query request for userId: {} with correlationId: {}", userId, correlationId);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TEAM_QUERY_EXCHANGE,
                RabbitMQConfig.TEAM_QUERY_ROUTING_KEY,
                request
        );

        // Timeout after 5 seconds
        future.orTimeout(5, TimeUnit.SECONDS)
                .whenComplete((result, throwable) -> {
                    pendingRequests.remove(correlationId);
                    if (throwable != null) {
                        log.error("Team query request timed out for userId: {}", userId);
                    }
                });

        return future;
    }

    public void completeRequest(String correlationId, TeamQueryResponse response) {
        CompletableFuture<TeamQueryResponse> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(response);
            log.info("Completed team query request with correlationId: {}", correlationId);
        }
    }
}