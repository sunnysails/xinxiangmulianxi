<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/2/9
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>财务报表-年报</title>
    <%@include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp" %>
    <%@include file="../include/sider.jsp" %>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>

                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 财务报表</a></li>
                <li class="active"><a href="#">年报</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">财务年报</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">

                        <div class="box-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th>月份</th>
                                    <th>当月收入</th>
                                    <th>当月支出</th>
                                    <th>备注</th>
                                    <th>操作</th>

                                </tr>
                                <tr>
                                    <td>2016-02</td>
                                    <td>150000</td>
                                    <td>20000</td>
                                    <td>无</td>
                                    <td><a href="monthReport.html">详情</a></td>
                                </tr>
                                <tr>
                                    <td>2016-02</td>
                                    <td>150000</td>
                                    <td>20000</td>
                                    <td>无</td>
                                    <td><a href="monthReport.html">详情</a></td>
                                </tr>
                                <tr>
                                <tr>
                                    <td>2016-02</td>
                                    <td>150000</td>
                                    <td>20000</td>
                                    <td>无</td>
                                    <td><a href="monthReport.html">详情</a></td>
                                </tr>
                                <tr>
                                <tr>
                                    <td>2016-02</td>
                                    <td>150000</td>
                                    <td>20000</td>
                                    <td>无</td>
                                    <td><a href="monthReport.html">详情</a></td>
                                </tr>
                                <tr>
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
                    <div style="font-size:18px;font-weight:100" >2016年收入<span class="alert-success">1000000.00</span>元，支出<span class="alert-error">500000.00</span>元，盈利<span class="alert-warning">500000.00</span>元</div>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="year" style="width:auto;height:800px"></div>

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
    var yearChart = echarts.init(document.getElementById('year'));


    option = {
        title : {
            text: '10月收入支出统计图',
            subtext: ''
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['收入','支出']
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ['1','2','3','4','5','6','7','8','9','10','11','12']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'收入',
                type:'bar',
                data:[2230, 4119, 7110, 2312, 2516, 7617, 1356, 1622, 326, 2000, 6400, 3030 ],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'支出',
                type:'bar',
                data:[2600, 5900, 9000, 2640, 2870, 7017, 1756, 1822, 4870, 1880, 9000,13240],
                markPoint : {
                    data : [
                        {name : '年最高', value : 13240, xAxis: 11, yAxis: 13240},
                        {name : '年最低', value : 2600, xAxis: 0, yAxis: 2600}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    yearChart.setOption(option);
</script>
</body>
</html>
