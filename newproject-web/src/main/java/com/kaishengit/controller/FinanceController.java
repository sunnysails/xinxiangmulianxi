package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.FinanceDto;
import com.kaishengit.pojo.Finance;
import com.kaishengit.service.FinanceService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by sunny on 2017/2/9.
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping("/day")
    public String day() {
        return "/finance/day";
    }

    @GetMapping("/day/load")
    @ResponseBody
    public DataTablesResult dayData(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String day = request.getParameter("day");

        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("start", start);
        queryParam.put("length", length);
        queryParam.put("day", day);

        List<Finance> financeList = financeService.findByQueryParam(queryParam);
        Long count = financeService.count();
        Long filterCount = financeService.count(day);
        return new DataTablesResult(draw, count, filterCount, financeList);
    }

    /**
     * 财务流水确认
     */
    @PostMapping("/confirm/{serialNumber}")
    @ResponseBody
    public AjaxResult confirmFinance(@PathVariable String serialNumber) {
        financeService.confirmById(serialNumber);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    @GetMapping("/day/{today}/data.xls")
    public void exportExcel(@PathVariable String today, HttpServletResponse response) throws IOException {
        List<Finance> financeList = financeService.findByCreateDate(today);
        //写响应头及输入框地址
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + today + ".xls\"");

        //1.创建工作表
        Workbook workbook = new HSSFWorkbook();

        //2.创建sheet页
        Sheet sheet = workbook.createSheet(today + "财务流水");


        //单元格样式（可选）
        /*CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.*/

        //3.创建行 从0开始
        Row row = sheet.createRow(0);
        Cell c1 = row.createCell(0);
        c1.setCellValue("财务流水号");
        row.createCell(1).setCellValue("财务流水名称");
        row.createCell(2).setCellValue("创建日期");
        row.createCell(3).setCellValue("类型");
        row.createCell(4).setCellValue("金额");
        row.createCell(5).setCellValue("业务模块");
        row.createCell(6).setCellValue("业务流水号");
        row.createCell(7).setCellValue("状态");
        row.createCell(8).setCellValue("创建人");
        row.createCell(9).setCellValue("确认人");
        row.createCell(10).setCellValue("确认日期");

        for (int i = 0; i < financeList.size(); i++) {
            Finance finance = financeList.get(i);

            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(finance.getSerialNumber());
            dataRow.createCell(1).setCellValue(finance.getFinanceName());
            dataRow.createCell(2).setCellValue(finance.getCreateDate());
            dataRow.createCell(3).setCellValue(finance.getType());
            dataRow.createCell(4).setCellValue(finance.getMoney());
            dataRow.createCell(5).setCellValue(finance.getModel());
            dataRow.createCell(6).setCellValue(finance.getRentSerial());
            dataRow.createCell(7).setCellValue(finance.getState());
            dataRow.createCell(8).setCellValue(finance.getCreateUser());
            dataRow.createCell(9).setCellValue(finance.getConfirmUser());
            dataRow.createCell(10).setCellValue(finance.getConfirmDate());
        }


        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(10);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 按天加载饼图数据
     *
     * @param type  in收入 out支出
     * @param today
     * @return
     */
    @GetMapping("/day/{type}/{today}/pie")
    @ResponseBody
    public AjaxResult dayPieData(@PathVariable String type, @PathVariable String today) {
        type = "in".equals(type) ? Finance.FINANCE_IN : Finance.FINANCE_OUT;
        List<FinanceDto> financeDtoList = financeService.findPieDataByDay(today, type);
        if (financeDtoList.isEmpty()){
            return new AjaxResult(AjaxResult.SUCCESS);
        }
        return new AjaxResult(financeDtoList);
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public String month() {
        return "/finance/month";
    }

    @GetMapping("/month/{month}/axis")
    @ResponseBody
    public AjaxResult monthAxis(@PathVariable String month) {
        return new AjaxResult(financeService.findByMonth(month));
    }

    @RequestMapping(value = "year", method = RequestMethod.GET)
    public String year() {
        return "/finance/year";
    }

    @GetMapping("/year/{year}/axis")
    @ResponseBody
    public AjaxResult yearAxis(@PathVariable String year) {
        return new AjaxResult(financeService.findByYear(year));
    }
}
