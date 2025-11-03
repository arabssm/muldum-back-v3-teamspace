package co.kr.muldum.adapter.out.external;

import co.kr.muldum.application.port.out.TeamSheetFetchPort;
import co.kr.muldum.domain.exception.TeamInvitationException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class TeamSheetFetchAdapter implements TeamSheetFetchPort {

    private final RestTemplate restTemplate;

    public TeamSheetFetchAdapter(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String fetchSheet(String sheetSource) {
        if (sheetSource.startsWith("mock://")) {
            return loadMockSheet(sheetSource.substring("mock://".length()));
        }

        try {
            String response = restTemplate.getForObject(sheetSource, String.class);
            if (response == null) {
                throw new TeamInvitationException("Google Sheet 데이터를 불러오지 못했습니다.");
            }
            return response;
        } catch (RestClientException e) {
            throw new TeamInvitationException("Google Sheet 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    private String loadMockSheet(String mockKey) {
        String resourceName = mockKey.isBlank() ? "team_invite_sample" : mockKey;
        ClassPathResource resource = new ClassPathResource("mock/" + resourceName + ".csv");

        if (!resource.exists()) {
            throw new TeamInvitationException("모의 데이터 파일을 찾을 수 없습니다: " + resourceName);
        }

        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TeamInvitationException("모의 데이터 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
