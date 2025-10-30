package co.kr.muldum.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "배너 이미지 수정 요청")
@Getter
public class UpdateBannerRequest {

    @Schema(description = "배너 이미지 URL", example = "/uploads/notice/98e01e0e-25a7-4b94-b546-74ad7933580d.JPG", required = true)
    @NotBlank(message = "배너 이미지 URL은 필수입니다.")
    private String url;
}
