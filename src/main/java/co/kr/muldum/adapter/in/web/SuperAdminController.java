package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.application.port.in.GetTeamSpaceLogsUseCase;
import co.kr.muldum.application.port.in.response.TeamSpaceLogResponse;
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

@Tag(name = "SuperAdmin", description = "슈퍼 관리자 API")
@RestController
@RequestMapping("/sup/teamspace")
public class SuperAdminController {

    private final GetTeamSpaceLogsUseCase getTeamSpaceLogsUseCase;

    public SuperAdminController(GetTeamSpaceLogsUseCase getTeamSpaceLogsUseCase) {
        this.getTeamSpaceLogsUseCase = getTeamSpaceLogsUseCase;
    }

    @Operation(
            summary = "팀스페이스 로그 조회",
            description = "팀스페이스 관련 기능의 모든 로그를 조회합니다. type 파라미터로 특정 메소드의 로그만 필터링할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = TeamSpaceLogResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효하지 않은 메소드 타입)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/logs")
    public ResponseEntity<List<TeamSpaceLogResponse>> getTeamSpaceLogs(
            @Parameter(description = "로그 메소드 타입 (CREATE_TEAM, UPDATE_TEAM, DELETE_TEAM, INVITE_MEMBER, REMOVE_MEMBER, UPDATE_TEAM_PAGE, UPDATE_BANNER, UPDATE_ICON)", required = false)
            @RequestParam(required = false) String type
    ) {
        List<TeamSpaceLogResponse> logs = getTeamSpaceLogsUseCase.getTeamSpaceLogs(type);
        return ResponseEntity.ok(logs);
    }
}