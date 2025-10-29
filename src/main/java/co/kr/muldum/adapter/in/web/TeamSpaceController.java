package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.MemberResponse;
import co.kr.muldum.adapter.in.web.dto.TeamResponse;
import co.kr.muldum.adapter.in.web.dto.TeamSpaceResponse;
import co.kr.muldum.application.port.in.GetTeamSpaceUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ara/major")
public class TeamSpaceController {

    private final GetTeamSpaceUseCase getTeamSpaceUseCase;

    public TeamSpaceController(GetTeamSpaceUseCase getTeamSpaceUseCase) {
        this.getTeamSpaceUseCase = getTeamSpaceUseCase;
    }

    @GetMapping("/teamspace")
    public ResponseEntity<TeamSpaceResponse> getTeamSpace() {
        TeamSpaceListResponse response = getTeamSpaceUseCase.getTeamSpace();

        List<TeamResponse> teams = response.getTeams().stream()
                .map(team -> {
                    List<MemberResponse> members = team.getMembers().stream()
                            .map(member -> MemberResponse.of(member.getUserId(), member.getStudentName()))
                            .toList();
                    return TeamResponse.of(team.getTeamId(), team.getTeamName(), members);
                })
                .toList();

        return ResponseEntity.ok(TeamSpaceResponse.of(teams));
    }
}