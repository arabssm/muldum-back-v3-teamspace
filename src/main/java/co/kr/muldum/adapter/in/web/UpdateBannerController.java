package co.kr.muldum.adapter.in.web;

import co.kr.muldum.adapter.in.web.dto.ErrorResponse;
import co.kr.muldum.adapter.in.web.dto.MessageResponse;
import co.kr.muldum.adapter.in.web.dto.UpdateBannerRequest;
import co.kr.muldum.application.port.in.UpdateBannerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TeamSpace Banner", description = "팀스페이스 배너 관련 API")
@RestController
@RequestMapping("/std/teamspace")
public class UpdateBannerController {

    private final UpdateBannerUseCase updateBannerUseCase;

    public UpdateBannerController(UpdateBannerUseCase updateBannerUseCase) {
        this.updateBannerUseCase = updateBannerUseCase;
    }

    @Operation(
            summary = "팀스페이스 배너 이미지 수정",
            description = "팀스페이스의 배너 이미지를 수정합니다. type 파라미터는 팀 타입(major, network, autonomous, greaduation)을 의미하며, teamid로 팀을 식별합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "배너 이미지 수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = MessageResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효하지 않은 파라미터)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "INVALID_PARAMETER",
                                              "message": "유효하지 않은 팀 ID 값입니다: 0"
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
                    description = "접근 권한 없음 (다른 팀의 배너 수정 시도)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "ACCESS_DENIED",
                                              "message": "해당 팀의 배너를 수정할 권한이 없습니다."
                                            }
                                            """
                            )
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
                                              "message": "팀을 찾을 수 없습니다. (팀 ID: 1)"
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
                    description = "서버 내부 오류",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "errorCode": "INTERNAL_SERVER_ERROR",
                                              "message": "서버 내부 오류가 발생했습니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/banner")
    public ResponseEntity<MessageResponse> updateBanner(
            @Parameter(description = "팀 ID", example = "1", required = true)
            @RequestParam Long teamid,
            @Parameter(description = "팀 타입 (major: 전공동아리, network: 네트워크, autonomous: 자율동아리, greaduation: 졸업작품)", example = "major", required = true)
            @RequestParam String type,
            @Valid @RequestBody UpdateBannerRequest request) {

        updateBannerUseCase.updateBanner(teamid, type, request.getUrl());

        return ResponseEntity.ok(MessageResponse.of("파일 업로드 성공"));
    }
}
