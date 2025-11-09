package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.adapter.in.web.dto.MessageResponse;
import co.kr.muldum.adapter.in.web.dto.TeamInviteRequest;
import co.kr.muldum.application.port.in.InviteTeamUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Teacher Team", description = "교사용 팀 관리 API")
@RestController
@RequestMapping("/tch/teamspace")
public class TeamInviteController {

    private final InviteTeamUseCase inviteTeamUseCase;

    public TeamInviteController(InviteTeamUseCase inviteTeamUseCase) {
        this.inviteTeamUseCase = inviteTeamUseCase;
    }

    @Operation(
            summary = "Google Sheet를 활용한 팀 초대",
            description = "Google Sheet 링크와 팀 타입을 입력 받아 팀원 명단을 검증합니다. 시트에는 팀명, 학번(studentId), 이름, 역할(LEADER 또는 공백)을 포함해야 하며, 역할이 비어 있으면 자동으로 MEMBER로 처리됩니다.\n\n"
                    + "Swagger에서 테스트할 경우 `googleSheetUrl`에 `mock://team_invite_sample` 값을 사용하면 예시 CSV가 적용되어 동작을 확인할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "팀 초대 요청 처리 성공",
                    content = @Content(
                            schema = @Schema(implementation = MessageResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 요청 또는 Google Sheet 데이터",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "등록되지 않은 사용자 포함",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Google Sheet 데이터를 처리하는 중 오류 발생",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteTeam(@Valid @RequestBody TeamInviteRequest request) {
        inviteTeamUseCase.inviteTeam(request.getGoogleSheetUrl(), request.getTeamType());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("success"));
    }
}
