package com.skkuwhere.app.mapper;

import com.skkuwhere.app.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuildingMapper {
    BuildingVO searchByCode(int codenum);

    int countUp();

    int countDown();


    int updatePeopleCntToZero();

    CongestionVO getPeopleCntByCode(int code);

    ScRoomVO getScRoomCnt();

    List<RoomVO> getBuildingCodeAndSeat();

    int updatePeopleByRandom(RoomVO accessvo);

    int updateLog(RoomVO accessvo);

    List<RoomLogVO> findRecentLogsForAllBuildings();
}
