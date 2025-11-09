package co.kr.muldum.adapter.in.web.dto;

import co.kr.muldum.domain.model.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ItemRequestResponseDto {

    private Long requestId;
    private Long userId;
    private UUID teamId;
    private String itemName;
    private Integer quantity;
    private String description;

    public static ItemRequestResponseDto from(ItemRequest itemRequest) {
        return new ItemRequestResponseDto(
                itemRequest.getRequestId(),
                itemRequest.getUserId(),
                itemRequest.getTeamId(),
                itemRequest.getItemName(),
                itemRequest.getQuantity(),
                itemRequest.getDescription()
        );
    }
}