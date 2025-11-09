package co.kr.muldum.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ItemRequest {

    private final Long requestId;
    private final Long userId;
    private final UUID teamId;
    private final String itemName;
    private final Integer quantity;
    private final String description;

    private ItemRequest(Long requestId, Long userId, UUID teamId, String itemName, Integer quantity, String description) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (teamId == null) {
            throw new IllegalArgumentException("Team ID cannot be null");
        }
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.requestId = requestId;
        this.userId = userId;
        this.teamId = teamId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.description = description;
    }

    public static ItemRequest create(Long userId, UUID teamId, String itemName, Integer quantity, String description) {
        return new ItemRequest(null, userId, teamId, itemName, quantity, description);
    }

    public static ItemRequest of(Long requestId, Long userId, UUID teamId, String itemName, Integer quantity, String description) {
        return new ItemRequest(requestId, userId, teamId, itemName, quantity, description);
    }

    public ItemRequest withId(Long requestId) {
        return new ItemRequest(requestId, this.userId, this.teamId, this.itemName, this.quantity, this.description);
    }
}