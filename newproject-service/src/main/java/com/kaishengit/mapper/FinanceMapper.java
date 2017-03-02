package com.kaishengit.mapper;

import com.kaishengit.dto.FinanceDto;
import com.kaishengit.dto.FinanceMoneyDto;
import com.kaishengit.pojo.Finance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/23.
 */
public interface FinanceMapper {
    void save(Finance finance);

    List<Finance> findByQueryParam(Map<String, Object> queryParam);

    Long count(@Param("date") String date, @Param("i") Integer i);

    Finance findByserialNumber(String serialNumber);

    void update(Finance finance);

    List<FinanceDto> findDtoByDataType(@Param("date") String date, @Param("type") String type, @Param("i") Integer i);

    List<Finance> findByCreateDate(String today);

}
