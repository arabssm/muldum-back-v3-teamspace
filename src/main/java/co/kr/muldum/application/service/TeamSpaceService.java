package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamSpaceUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse.MemberInfo;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse.TeamInfo;
import co.kr.muldum.domain.exception.TeamSpaceLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamSpaceService implements GetTeamSpaceUseCase {

    private static final Logger log = LoggerFactory.getLogger(TeamSpaceService.class);

    @Override
    public TeamSpaceListResponse getTeamSpace() {
        try {
            // TODO: 실제 DB에서 데이터를 조회하도록 수정 필요
            // 현재는 더미 데이터를 반환합니다.

            List<MemberInfo> members = List.of(
                    MemberInfo.of(1001L, "2312이효준"),
                    MemberInfo.of(1002L, "2301김예빈"),
                    MemberInfo.of(1003L, "2111심현진"),
                    MemberInfo.of(1004L, "2212이하은"),
                    MemberInfo.of(1005L, "2205김현우")
            );

            TeamInfo team = TeamInfo.of(1L, "아라", members);
            List<TeamInfo> teams = List.of(team);

            // 팀 목록이 비어있어도 빈 배열을 반환 (RESTful 설계)
            log.info("Successfully loaded {} team(s) from teamspace", teams.size());
            return TeamSpaceListResponse.of(teams);

        } catch (Exception e) {
            log.error("Failed to load teamspace data", e);
            throw new TeamSpaceLoadException("데이터 조회 실패", e);
        }
    }
}