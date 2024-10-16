package com.skkuwhere.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CongestionVO {
    private int r_people;
    private int r_seat;
}
