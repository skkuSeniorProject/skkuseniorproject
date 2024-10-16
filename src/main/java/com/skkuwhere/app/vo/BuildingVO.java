package com.skkuwhere.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class BuildingVO {
    private int code;
    private String name;
}
