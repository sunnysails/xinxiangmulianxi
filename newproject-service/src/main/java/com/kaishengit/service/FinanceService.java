package com.kaishengit.service;

import com.kaishengit.dto.FinanceDto;
import com.kaishengit.dto.FinanceMoneyDto;
import com.kaishengit.pojo.Finance;

import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/23.
 */
public interface FinanceService {

    List<Finance> findByQueryParam(Map<String, Object> queryParam);

    Long count();

    Long count(String  day);

    void confirmById(String serialNumber);

    List<FinanceDto> findPieDataByDay(String today, String type);

    List<Finance> findByCreateDate(String today);

    List<FinanceMoneyDto> findByMonth(String month);

    List<FinanceMoneyDto> findByYear(String year);
}
