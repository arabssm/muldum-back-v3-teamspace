package co.kr.muldum.application.port.in;

import java.util.UUID;

public interface UpdateBannerUseCase {
    void updateBanner(UUID teamId, String type, String url);
}
