package com.core.miniproject.src.room.domain.dto;

import com.core.miniproject.src.room.domain.entity.Room;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInsertResponse {

    private Long roomId;
    private String roomName;
    private String roomInfo;
    private Integer roomCount;
    private Integer fixedNumber;
    private Integer maxedNumber;
    private String roomImage;
    private Integer price;

    public static RoomInsertResponse toClient(Room room) {
        return RoomInsertResponse.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .roomInfo(room.getRoomInfo())
                .roomCount(room.getRoomCount())
                .fixedNumber(room.getFixedMember())
                .maxedNumber(room.getMaxedMember())
                .roomImage(room.getRoomImage())
                .price(room.getPrice())
                .build();
    }




}
