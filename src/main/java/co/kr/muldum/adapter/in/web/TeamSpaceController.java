package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.adapter.in.web.dto.MemberResponse;
import co.kr.muldum.adapter.in.web.dto.TeamResponse;
import co.kr.muldum.adapter.in.web.dto.TeamSpaceResponse;
import co.kr.muldum.application.port.in.GetTeamSpaceUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TeamSpace", description = "팀스페이스 관련 API")
@RestController
@RequestMapping("/ara/major")
public class TeamSpaceController {

    private final GetTeamSpaceUseCase getTeamSpaceUseCase;

    public TeamSpaceController(GetTeamSpaceUseCase getTeamSpaceUseCase) {
        this.getTeamSpaceUseCase = getTeamSpaceUseCase;
    }

    @Operation(
            summary = "팀스페이스 목록 조회",
            description = "팀스페이스 페이지에 표시되는 모든 팀의 목록을 조회합니다. 각 팀의 정보와 팀원 목록을 포함합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "팀 목록 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = TeamSpaceResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "팀을 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "TEAM_NOT_FOUND",
                                              "message": "팀 정보를 찾을 수 없습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류 (팀스페이스 데이터 로드 실패)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "TEAMSPACE_LOAD_FAILED",
                                              "message": "팀스페이스 데이터를 불러오는 중 오류가 발생했습니다: 데이터 조회 실패"
                                            }
                                            """
                            )
                    )
            )
    })
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