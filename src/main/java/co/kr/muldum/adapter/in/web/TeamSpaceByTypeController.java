package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.adapter.in.web.dto.TeamContentResponse;
import co.kr.muldum.adapter.in.web.dto.TeamMemberResponse;
import co.kr.muldum.adapter.in.web.dto.TeamSpaceContentResponse;
import co.kr.muldum.application.port.in.GetTeamSpaceByTypeUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceContentListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TeamSpace", description = "팀스페이스 관련 API")
@RestController
@RequestMapping("/ara")
public class TeamSpaceByTypeController {

    private final GetTeamSpaceByTypeUseCase getTeamSpaceByTypeUseCase;

    public TeamSpaceByTypeController(GetTeamSpaceByTypeUseCase getTeamSpaceByTypeUseCase) {
        this.getTeamSpaceByTypeUseCase = getTeamSpaceByTypeUseCase;
    }

    @Operation(
            summary = "팀 종류별 팀스페이스 조회",
            description = "팀 스페이스 페이지에서 팀의 종류(전공동아리, 네트워크 등)별로 조회합니다. 각 팀의 정보와 타입, 팀원 목록을 포함합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "팀 목록 조회 성공 (팀이 없는 경우 빈 배열 반환)",
                    content = @Content(
                            schema = @Schema(implementation = TeamSpaceContentResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효하지 않은 type 파라미터)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "INVALID_TEAM_TYPE",
                                              "message": "잘못된 팀 타입입니다. 허용된 값: major, network, autonomous, greaduation"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 누락 또는 만료)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "UNAUTHORIZED",
                                              "message": "인증이 필요합니다. 토큰이 누락되었거나 만료되었습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "접근 권한 없음 (조직 외 팀 접근 시도)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "ACCESS_DENIED",
                                              "message": "해당 팀스페이스에 접근할 권한이 없습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "429",
                    description = "요청 제한 초과 (Rate Limit)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "TOO_MANY_REQUESTS",
                                              "message": "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요."
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
                                              "message": "팀스페이스 데이터를 불러오는 중 오류가 발생했습니다: 팀 타입별 데이터 조회 실패"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/teamspaces")
    public ResponseEntity<TeamSpaceContentResponse> getTeamSpaceByType(
            @Parameter(description = "팀 종류 (major: 전공동아리, network: 네트워크, autonomous: 자율동아리, greaduation: 졸업작품 )", example = "major", required = true)
            @RequestParam String type) {
        TeamSpaceContentListResponse response = getTeamSpaceByTypeUseCase.getTeamSpaceByType(type);

        List<TeamContentResponse> content = response.getContent().stream()
                .map(team -> {
                    List<TeamMemberResponse> members = team.getMembers().stream()
                            .map(member -> TeamMemberResponse.of(member.getUserId(), member.getStudentName()))
                            .toList();
                    return TeamContentResponse.of(team.getTeamId(), team.getTeamName(), members);
                })
                .toList();

        return ResponseEntity.ok(TeamSpaceContentResponse.of(content));
    }
}