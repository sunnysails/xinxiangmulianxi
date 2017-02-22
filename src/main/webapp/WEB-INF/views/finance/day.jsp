<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/2/9
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>财务报表-日报</title>
    <%@include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp" %>
    <%@include file="../include/sider.jsp" %>
    <!-- =============================================== -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 财务报表</a></li>
                <li class="active"><a href="#">日报</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">财务日报</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">

                        <div class="box-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th>财务流水号</th>
                                    <th>财务流水名称</th>
                                    <th>业务模块</th>
                                    <th>业务流水号</th>
                                    <th>金额</th>
                                    <th>状态EZ</th>
                                    <th>创建时间</th>
                                    <th>备注</th>
                                </tr>
                                <tr>
                                    <td>123451215</td>
                                    <td>XXX公司租赁挖掘机预付款</td>
                                    <td>设备租赁</td>
                                    <th><a href="#">1001</a></th>
                                    <th>11000.00</th>
                                    <th>已完成</th>
                                    <th>2016-10-10</th>
                                    <th>无</th>
                                </tr>

                                <tr>
                                    <td>123451215</td>
                                    <td>XXX公司租赁挖掘机预付款</td>
                                    <td>设备租赁</td>
                                    <th><a href="#">1001</a></th>
                                    <th>11000.00</th>
                                    <th>已完成</th>
                                    <th>2016-10-10</th>
                                    <th>无</th>
                                </tr>
                                <tr>
                                    <td>123451215</td>
                                    <td>XXX公司租赁挖掘机预付款</td>
                                    <td>设备租赁</td>
                                    <th><a href="#">1001</a></th>
                                    <th>11000.00</th>
                                    <th>已完成</th>
                                    <th>2016-10-10</th>
                                    <th>无</th>
                                </tr>

                                <tr>
                                    <td>123451215</td>
                                    <td>XXX公司租赁挖掘机预付款</td>
                                    <td>设备租赁</td>
                                    <th><a href="#">1001</a></th>
                                    <th>11000.00</th>
                                    <th>已完成</th>
                                    <th>2016-10-10</th>
                                    <th>无</th>
                                </tr>
                            </table>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer clearfix">
                            <ul class="pagination pagination-sm no-margin pull-right">
                                <li><a href="#">&laquo;</a></li>
                                <li><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">&raquo;</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /.box -->
                    <div style="font-size:18px;font-weight:100">2016年10月1日：收入<span class="alert-success">1000.00</span>元，支出<span
                            class="alert-error">2000.00</span>元
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div class="form-group row">

                <div class="col-lg-6">
                    <div id="in" style="padding-left: 20px;width: 600px;height:400px;"></div>
                </div>
                <div class="col-lg-6">
                    <div id="out" style="padding-left: 20px;width: 600px;height:400px;"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<!-- jQuery 2.2.0 -->
<%@include file="../include/js.jsp" %>
<!-- echarts.js -->
<script src="/static/js/echarts.js"></script>

<script>
    // 基于准备好的dom，初始化echarts实例
    var inChart = echarts.init(document.getElementById('in'));
    option = {
        title: {
            text: '当日收入饼状图',
            subtext: '只统计收入',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['设备租赁', '劳务外包', '金融借贷', '其他']
        },
        series: [
            {
                name: '收入',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 33500, name: '设备租赁'},
                    {value: 12340, name: '劳务外包'},
                    {value: 23400, name: '金融借贷'},
                    {value: 13500, name: '其他'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    inChart.setOption(option);


    // 基于准备好的dom，初始化echarts实例
    var outChart = echarts.init(document.getElementById('out'));
    outoption = {
        title: {
            text: '当日支出饼状图',
            subtext: '只统计支出',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['工资', '日常开销', '设备维修', '设备更新', '其他']
        },
        series: [
            {
                name: '支出',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 0, name: '工资'},
                    {value: 400, name: '日常开销'},
                    {value: 23400, name: '设备维修'},
                    {value: 35000, name: '设备更新'},
                    {value: 3500, name: '其他'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    outChart.setOption(outoption);
</script>
</body>
</html>