package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamSpaceByTypeUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceContentListResponse;
import co.kr.muldum.application.port.in.response.TeamSpaceContentListResponse.MemberInfo;
import co.kr.muldum.application.port.in.response.TeamSpaceContentListResponse.TeamContentInfo;
import co.kr.muldum.domain.exception.TeamSpaceLoadException;
import co.kr.muldum.domain.model.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamSpaceByTypeService implements GetTeamSpaceByTypeUseCase {

    private static final Logger log = LoggerFactory.getLogger(TeamSpaceByTypeService.class);

    @Override
    public TeamSpaceContentListResponse getTeamSpaceByType(String type) {
        try {
            // type 파라미터를 TeamType enum으로 변환
            TeamType teamType = TeamType.fromCode(type);

            // TODO: 실제 DB에서 팀 타입별로 데이터를 조회하도록 수정 필요
            // 현재는 더미 데이터를 반환합니다.

            List<MemberInfo> members1 = List.of(
                    MemberInfo.of(1001L, "2312이효준"),
                    MemberInfo.of(1002L, "2301김예빈")
            );

            List<MemberInfo> members2 = List.of(
                    MemberInfo.of(1003L, "2111심현진"),
                    MemberInfo.of(1004L, "2212이하은"),
                    MemberInfo.of(1005L, "2205김현우")
            );

            TeamContentInfo team1 = TeamContentInfo.of(1L, "올망졸망", "MAJOR_CLUB", members1);
            TeamContentInfo team2 = TeamContentInfo.of(2L, "네트워킹", "NETWORK", members2);

            // type에 따라 필터링
            List<TeamContentInfo> allTeams = List.of(team1, team2);
            List<TeamContentInfo> content = allTeams.stream()
                    .filter(team -> team.getTeamType().equals(teamType.getValue()))
                    .toList();

            log.info("Successfully loaded {} team(s) of type {} from teamspace", content.size(), teamType);
            return TeamSpaceContentListResponse.of(content);

        } catch (IllegalArgumentException e) {
            log.error("Invalid team type: {}", type, e);
            throw new TeamSpaceLoadException("잘못된 팀 타입: " + type, e);
        } catch (Exception e) {
            log.error("Failed to load teamspace data by type", e);
            throw new TeamSpaceLoadException("팀 타입별 데이터 조회 실패", e);
        }
    }
}