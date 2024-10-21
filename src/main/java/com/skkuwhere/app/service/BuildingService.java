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
import org.springframework.validation.ObjectError;

import javax.naming.event.ObjectChangeListener;
import java.util.Random;

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

    @Transactional
    // SC_ROOM DB의 People Column 카운트 증가
    public ResponseEntity<Object> countUp() {
        ScRoomVO scRoomVO = buildingMapper.getScRoomCnt();

        if (scRoomVO.getR_people() > 15)
            return new ResponseEntity<>("정원을 초과했습니다.", HttpStatus.BAD_REQUEST);

        int updateCnt =buildingMapper.countUp();
        // Exeception 처리
        if (updateCnt == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("정상적으로 숫자가 증가했습니다.");
    }
    @Transactional
    // SC_ROOM DB의 People Column 카운트 감소
    public ResponseEntity<Object> countDown() {
        ScRoomVO scRoomVO = buildingMapper.getScRoomCnt();
        if (scRoomVO.getR_people() <= 0)
            return new ResponseEntity<>("0명 입니다.", HttpStatus.BAD_REQUEST);

        int updateCnt =buildingMapper.countDown();
        // Exeception 처리
        if (updateCnt == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body("정상적으로 숫자가 감소했습니다.");
    }

    //  혼잡도를 인구 기준으로 받아오기
    public ResponseEntity<Object> getCongestion(int code) {
        CongestionVO congestionVO = buildingMapper.getPeopleCntByCode(code);
        // 예외 처리
        if (ObjectUtils.isEmpty(congestionVO))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // 사람수와 총 좌석수 추출
        int people  = congestionVO.getR_people();
        int totalSeat = congestionVO.getR_seat();
        String remainSeat = people + "/" + totalSeat;
        // 혼잡도 계산
        double usage = ((double) people / totalSeat) * 100;
        int useCategory = (int) (usage / 25);

        String state = switch(useCategory) {
            case 0 -> "여유";
            case 1 -> "원활";
            case 2 -> "혼잡";
            default -> "포화";
        };
        // 전송 객체 생성 후 전달
        CongestionDTO congestionDTO = CongestionDTO.builder()
                    .remainSeat(remainSeat)
                    .congestionState(state)
                    .build();

        return new ResponseEntity<>(congestionDTO,HttpStatus.OK);
    }

    // 3시간 마다 SC룸 이외의 공부방 사람 랜덤 생성후 UPDATE
    @Transactional
    @Scheduled(cron = "1 0 */3 * * *")
    public ResponseEntity<Object> generateRandomPeople(){
        Random random = new Random();

        int roomA = random.nextInt(21);
        int roomB = random.nextInt(16);
        int roomC = random.nextInt(31);

        int updateACnt = buildingMapper.updatePeopleByRoomType("A",roomA);
        int updateBCnt = buildingMapper.updatePeopleByRoomType("B",roomB);
        int updateCCnt = buildingMapper.updatePeopleByRoomType("C",roomC);

        if (updateCCnt == 0 || updateBCnt == 0 || updateACnt == 0) {
            log.info("!!!!!!!Fail To Generates Random People!!!!!!!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("!!!!!!!SUCCESS To Generates Random People!!!!!!!!");
        return ResponseEntity.ok().body("Random Success.");
    }

}
