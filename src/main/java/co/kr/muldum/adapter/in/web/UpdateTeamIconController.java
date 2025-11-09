package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.adapter.in.web.dto.UpdateTeamIconRequest;
import co.kr.muldum.application.port.in.UpdateTeamIconUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TeamSpace Icon", description = "팀스페이스 아이콘 관련 API")
@RestController
@RequestMapping("/std/teamspace")
public class UpdateTeamIconController {

    private final UpdateTeamIconUseCase updateTeamIconUseCase;

    public UpdateTeamIconController(UpdateTeamIconUseCase updateTeamIconUseCase) {
        this.updateTeamIconUseCase = updateTeamIconUseCase;
    }

    @Operation(
            summary = "팀 아이콘 수정",
            description = "팀 타입과 팀 ID를 통해 해당 팀의 아이콘을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "팀 아이콘 수정 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "등록되지 않은 사용자 포함",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{teamId}/icon")
    public ResponseEntity<String> updateTeamIcon(
            @Parameter(description = "팀 타입 (major: 전공동아리, network: 네트워크, autonomous: 자율동아리, greaduation: 졸업작품)", example = "major", required = true)
            @RequestParam String type,
            @Parameter(description = "팀 ID", example = "1", required = true)
            @PathVariable Long teamId,
            @Valid @RequestBody UpdateTeamIconRequest request) {

        updateTeamIconUseCase.updateTeamIcon(teamId, type, request.getUrl());
        return ResponseEntity.ok("아이콘이 성공적으로 수정됐습니다.");
    }
}
