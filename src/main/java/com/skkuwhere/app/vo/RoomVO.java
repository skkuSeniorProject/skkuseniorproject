package com.skkuwhere.app.vo;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomVO {
    private int r_building_code;
    private int r_seat;
    private int r_people;
}
