package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GetTeamSpaceUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse.MemberInfo;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse.TeamInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamSpaceService implements GetTeamSpaceUseCase {

    @Override
    public TeamSpaceListResponse getTeamSpace() {
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

        return TeamSpaceListResponse.of(List.of(team));
    }
}