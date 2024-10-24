package com.skkuwhere.app.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomLogVO {
    private int rl_building_code;
    private int rl_people;
    private String rl_log_time;
}
