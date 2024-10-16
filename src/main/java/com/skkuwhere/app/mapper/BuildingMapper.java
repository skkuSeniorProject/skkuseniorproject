package com.skkuwhere.app.mapper;

import com.skkuwhere.app.vo.BuildingVO;
import com.skkuwhere.app.vo.ScRoomVO;
import com.skkuwhere.app.vo.CongestionVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuildingMapper {
    BuildingVO searchByCode(int codenum);

    int countUp();

    int countDown();

    ScRoomVO selectPeopleCnt();

    int updatePeopleCntToZero();

    CongestionVO getPeopleCntByCode(int code);
}
