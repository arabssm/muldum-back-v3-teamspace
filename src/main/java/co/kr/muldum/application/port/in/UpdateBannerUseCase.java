package co.kr.muldum.application.port.in;

public interface UpdateBannerUseCase {
    void updateBanner(Long teamId, String type, String url);
}
