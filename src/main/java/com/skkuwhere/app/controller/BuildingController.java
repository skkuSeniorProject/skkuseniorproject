package com.skkuwhere.app.controller;


import com.skkuwhere.app.service.BuildingService;
import com.skkuwhere.app.vo.RoomLogVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping("/code")
    public ResponseEntity<Object> searchBuildingCoDe(@RequestParam("code") String code ){
        return buildingService.searchBuildingCode(code);
    }

    @GetMapping("/sc-room/up")
    public ResponseEntity<Object> countUp(){
        return buildingService.countUp();
    }

    @GetMapping("/sc-room/down")
    public ResponseEntity<Object> countDown(){
        return buildingService.countDown();
    }

    @GetMapping("/congestion")
    public ResponseEntity<Object> getCongestion(@RequestParam("code") int code){
        return buildingService.getCongestion(code);
    }

    @GetMapping("/aggregation/view")
    public Map<Integer, List<RoomLogVO>> getAggregation(){
        return buildingService.getAggregation();
    }

}