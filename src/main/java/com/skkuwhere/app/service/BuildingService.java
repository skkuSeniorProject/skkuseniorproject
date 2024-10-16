package com.skkuwhere.app.service;

import com.skkuwhere.app.dto.BuildingDTO;
import com.skkuwhere.app.dto.CongestionDTO;
import com.skkuwhere.app.mapper.BuildingMapper;
import com.skkuwhere.app.vo.BuildingVO;
import com.skkuwhere.app.vo.CongestionVO;
import com.skkuwhere.app.vo.ScRoomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.naming.event.ObjectChangeListener;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingMapper buildingMapper;
    // 공간명 코드로 건물 검색 SiBBA GGU
    public ResponseEntity<Object> searchBuildingCode(String code) {
        // 입력 Validation ㅋㅋㅋ
        if (ObjectUtils.isEmpty(code)) {
            return new ResponseEntity<>("Code 값이 null이거나 빈 값입니다.", HttpStatus.BAD_REQUEST);
        }
        int codenum = Integer.parseInt(code.substring(1, 3));
        int floor = Integer.parseInt(code.substring(3, 4));

        BuildingVO vo = buildingMapper.searchByCode(codenum);

        if (ObjectUtils.isEmpty(vo)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BuildingDTO dto = BuildingDTO.builder().code(vo.getCode()).name(vo.getName()).floor(floor+"층").build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    // SC_ROOM DB의 People Column 카운트 증가
    public ResponseEntity<Object> countUp() {
        ScRoomVO scRoomVO = buildingMapper.selectPeopleCnt();

        if (scRoomVO.getPeople() > 15)
            return new ResponseEntity<>("정원을 초과했습니다.", HttpStatus.BAD_REQUEST);

        int updateCnt =buildingMapper.countUp();
        // Exeception 처리
        if (updateCnt == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("정상적으로 숫자가 증가했습니다.");
    }

    // SC_ROOM DB의 People Column 카운트 감소
    public ResponseEntity<Object> countDown() {
        ScRoomVO scRoomVO = buildingMapper.selectPeopleCnt();
        if (scRoomVO.getPeople() <= 0)
            return new ResponseEntity<>("0명 입니다.", HttpStatus.BAD_REQUEST);

        int updateCnt =buildingMapper.countDown();
        // Exeception 처리
        if (updateCnt == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("정상적으로 숫자가 감소했습니다.");
    }

    // SC-ROOM 의 혼잡도를 인구 기준으로 받아오기
    public ResponseEntity<Object> getCongestion() {
        ScRoomVO scRoomVO = buildingMapper.selectPeopleCnt();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @Scheduled(cron = "1 0 0 * * *")
    public ResponseEntity<Object> updatePeopleCntToZero(){
        Integer updateCnt = buildingMapper.updatePeopleCntToZero();

        log.info("MidNight Reset Count: {}",updateCnt);

        if (ObjectUtils.isEmpty(updateCnt)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("정상적으로 숫자가 초기화됐습니다.");
    }

    /*public ResponseEntity<Object> getCongetsionByBuildingCode(int code) {
        if (code == 0)
            return new ResponseEntity<>("Code 값이 null이거나 빈 값입니다.", HttpStatus.BAD_REQUEST);
        CongestionVO vo = buildingMapper.getPeopleCntByCode(code);

        String remainSeat = vo.getR_people() + " / " +vo.getR_seat();

        CongestionDTO dto = CongestionDTO.builder().remainSeat(remainSeat).congestionState().build();

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }*/
}
