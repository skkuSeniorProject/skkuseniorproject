package com.skkuwhere.app.mapper;

import com.skkuwhere.app.vo.BuildingVO;
import com.skkuwhere.app.vo.ScRoomVO;
import com.skkuwhere.app.vo.CongestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BuildingMapper {
    BuildingVO searchByCode(int codenum);

    int countUp();

    int countDown();


    int updatePeopleCntToZero();

    CongestionVO getPeopleCntByCode(int code);
    int updatePeopleByRoomType(@Param("roomType") String roomType, @Param("people") int people);
    ScRoomVO getScRoomCnt();
}
