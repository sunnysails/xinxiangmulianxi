package com.kaishengit.service.impl;

import com.google.common.collect.Lists;
import com.kaishengit.dto.FinanceDto;
import com.kaishengit.dto.FinanceMoneyDto;
import com.kaishengit.mapper.FinanceMapper;
import com.kaishengit.pojo.Finance;
import com.kaishengit.service.FinanceService;
import com.kaishengit.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/23.
 */
@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public List<Finance> findByQueryParam(Map<String, Object> queryParam) {
        return financeMapper.findByQueryParam(queryParam);
    }

    @Override
    public Long count() {
        return count("");
    }

    @Override
    public Long count(String day) {
        int i = 0;
        if (day.length() != 10 && !day.isEmpty()) {
            day += "%";
            i++;
        }
        return financeMapper.count(day, i);
    }

    @Override
    public void confirmById(String serialNumber) {
        Finance finance = financeMapper.findByserialNumber(serialNumber);
        if (finance != null) {
            finance.setState(Finance.FINANCE_FINISH);
            finance.setConfirmUser(ShiroUtil.getCurrentUserName());
            finance.setConfirmDate(DateTime.now().toString("yyyy-MM-dd"));
            financeMapper.update(finance);
        }
    }

    /**
     * 日报表中显示饼图数据
     *
     * @param today 确认收（付款）日期
     * @param type  “收入”    “支出”
     * @return
     */
    @Override
    public List<FinanceDto> findPieDataByDay(String today, String type) {
        return getFinanceDtosByDateType(today, type);
    }

    /**
     * 根据创建时间查找
     *
     * @param today
     * @return
     */
    @Override
    public List<Finance> findByCreateDate(String today) {
        return financeMapper.findByCreateDate(today);
    }

    @Override
    public List<FinanceMoneyDto> findByMonth(String month) {
        return getFinanceMoneyDtos(month);
    }

    @Override
    public List<FinanceMoneyDto> findByYear(String year) {
        return getFinanceMoneyDtos(year);
    }

    /**
     * 根据时间模糊查找
     *
     * @param data
     * @return
     */
    public List<FinanceMoneyDto> getFinanceMoneyDtos(String data) {
        String d = data + "%";
        List<FinanceDto> inF = getFinanceDtosByDateType(d, Finance.FINANCE_IN);
        List<FinanceDto> outF = getFinanceDtosByDateType(d, Finance.FINANCE_OUT);
        return getFinanceMoneyDtosByInOut(inF, outF);
    }

    /**
     * @param financeDtos
     * @return
     */
    public List<FinanceDto> setFinanceNameToYear(List<FinanceDto> financeDtos) {
        if (!financeDtos.isEmpty()) {
            for (FinanceDto f :
                    financeDtos) {
                f.setName(f.getName().substring(0, 7));
            }
        }
        return financeDtos;
    }

    /**
     * 通过收入及支出List 获取相同时间内的收入及支出统计。
     *
     * @param inF
     * @param outF
     * @return
     */
    private List<FinanceMoneyDto> getFinanceMoneyDtosByInOut(List<FinanceDto> inF, List<FinanceDto> outF) {
        List<FinanceMoneyDto> financeMoneyDtos = Lists.newArrayList();
        if (inF.size() == 0 && outF.size() == 0) {
            return financeMoneyDtos;
        } else if (inF.size() == 0) {
            for (FinanceDto out :
                    outF) {
                FinanceMoneyDto financeMoneyDto = new FinanceMoneyDto();
                financeMoneyDto.setConfirmDate(out.getName());
                financeMoneyDto.setInMoney(0F);
                financeMoneyDto.setOutMoney((Float) out.getValue());
                financeMoneyDtos.add(financeMoneyDto);
            }
        } else if (outF.size() == 0) {
            for (FinanceDto in :
                    inF) {
                FinanceMoneyDto financeMoneyDto = new FinanceMoneyDto();
                financeMoneyDto.setConfirmDate(in.getName());
                financeMoneyDto.setInMoney((Float) in.getValue());
                financeMoneyDto.setOutMoney(0F);
                financeMoneyDtos.add(financeMoneyDto);
            }
        } else {
            for (FinanceDto in :
                    inF) {
                for (FinanceDto out :
                        outF) {
                    if (in.getName().equals(out.getName())) {
                        FinanceMoneyDto financeMoneyDto = new FinanceMoneyDto();
                        financeMoneyDto.setConfirmDate(in.getName());
                        financeMoneyDto.setInMoney((Float) in.getValue());
                        financeMoneyDto.setOutMoney((Float) out.getValue());
                        financeMoneyDtos.add(financeMoneyDto);
                    }
                }
            }
        }
        return financeMoneyDtos;
    }

    /**
     * 根据时间及“收入”或“支出”查询。
     *
     * @param date
     * @param type
     * @return
     */
    private List<FinanceDto> getFinanceDtosByDateType(String date, String type) {
        List<FinanceDto> financeDtoList = null;
        if (date.length() == 10) {
            financeDtoList = financeMapper.findDtoByDataType(date, type, 0);
        } else {
            financeDtoList = financeMapper.findDtoByDataType(date, type, 1);
        }
        if (financeDtoList.isEmpty()) {
            return financeDtoList;
        } else {
            if (date.length() < 7) {
                financeDtoList = setFinanceNameToYear(financeDtoList);
            }
            return addValue(financeDtoList, Lists.newArrayList());
        }
    }

    /**
     * 计算相同时间中相同类型的收入的总和，
     *
     * @param financeDtoList
     * @param financeDtos
     * @return
     */
    private List<FinanceDto> addValue(List<FinanceDto> financeDtoList, List<FinanceDto> financeDtos) {
        while (!financeDtoList.isEmpty()){
            FinanceDto financeDto = new FinanceDto();
            Float v = 0F;
            String name = financeDtoList.get(0).getName();
            for (int i = 0; i < financeDtoList.size(); ) {
                //比较取出name相同的value值，相加并放入新集合，同时删除该元素
                if (financeDtoList.get(i).getName().equals(name)) {
                    v += (Float) financeDtoList.get(i).getValue();
                    financeDtoList.remove(i);
                } else {
                    i++;
                }
            }
            financeDto.setName(name);
            financeDto.setValue(v);
            financeDtos.add(financeDto);
        }
        return financeDtos;
    }
}