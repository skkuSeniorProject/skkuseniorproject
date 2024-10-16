package com.skkuwhere.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class BuildingDTO {
    int code;
    String name;
    String floor;
}
