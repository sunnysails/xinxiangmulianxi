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
    <title>财务报表-月报</title>
    <%@include file="../include/css.jsp" %>
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
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
                <li class="active">月报</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">

            <div class="box box-solid box-primary">
                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" class="form-control" id="date">
                    </form>
                </div>
            </div>
            <!-- Default box -->
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">财务月报</h3>

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
                                <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>当日收入</th>
                                    <th>当日支出</th>
                                    <%--   <th>备注</th>
                                       <th>操作</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                    <div style="font-size:18px;font-weight:100"><span id="month1"></span>：收入<span class="alert-success"
                                                                                                  id="inMoney"></span>元，支出<span
                            class="alert-error" id="outMoney"></span>元，盈利<span class="alert-warning"
                                                                               id="last">50000.00</span>元
                    </div>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="month" style="width:auto;height:800px"></div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<!-- jQuery 2.2.0 -->
<%@include file="../include/js.jsp" %>
<%@include file="../include/moment.jsp" %>
<%@include file="../include/dateTables.jsp" %>
<script src="/static/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<!-- echarts.js -->
<script src="/static/js/echarts.js"></script>

<script>

    $(function () {
        if (${param.get("_")!=null}) {
            $("#date").val('${param.get("_")}');
            loadPie();
        } else {
            $("#date").val(moment().format("YYYY-MM"));
            $("#month1").text(moment().format("YYYY年MM月"));

        }

        $("#date").datepicker({
            language: "zh-CN",
            autoclose: true,
            format: "yyyy-mm",
            minViewMode: 1,
            endDate: moment().format("yyyy-MM")
        }).on("changeDate", function (e) {
            var month = e.format(0, 'YYYY-MM');
//            table.ajax.reload(false, null);参数是为了保证在不是第一页操作后刷新页面使页面回到刷新前页码
//            table.ajax.reload();
            loadPie();
            $("#month1").text(e.format('yyyy年mm月'));
        });


        // 基于准备好的dom，初始化echarts实例
        var monthChart = echarts.init(document.getElementById('month'));

        function loadPie() {
            $.get("/finance/month/" + $("#date").val() + "/axis").done(function (result) {
                if (result.status == 'success') {
                    var tData = [];
                    var monthArray = [];
                    var inArray = [];
                    var outArray = [];
                    for (var i = 0; i < result.data.length; i++) {
                        var obj = result.data[i];
                        monthArray.push(obj.confirmDate);
                        inArray.push(obj.inMoney);
                        outArray.push(obj.outMoney);
                        var d = [];
                        d.push("<a href=/finance/day?_=" + obj.confirmDate + ">" + obj.confirmDate + "</a>", obj.inMoney, obj.outMoney);
                        tData.push(d);
                    }

                    var table = $(".table").DataTable()
                    table.destroy();//销毁表
                    $(".table").DataTable({
                        "lengthChange": true,
                        "paging": false,//分页开关
                        "info": false,
                        "data": tData,
                        "searching": false,//不使用自带的搜索
                        /*                  "order": [0, 'desc'],//默认排序方式,*/
                        "ordering": false,
                        "language": { //定义中文
                            "zeroRecords": "没有匹配的数据",
                        }
                    });
//                    table.clear().draw();
//                    table.rows.add(tData).draw();

                    var option = {
                        title: {
                            text: '10月收入支出统计图',
                            subtext: ''
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['月份', '收入', '支出']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [{
                            type: 'category',
                            data: monthArray
                        }],
                        yAxis: [{type: 'value'}],
                        series: [{
                            name: '收入',
                            type: 'bar',
                            data: inArray,
                            markPoint: {
                                data: [{type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}]
                            },
                            markLine: {data: [{type: 'average', name: '平均值'}]}
                        }, {
                            name: '支出',
                            type: 'bar',
                            data: outArray,
                            markPoint: {
                                data: [
                                    {name: '年最高', value: 9000, xAxis: 2, yAxis: 9000},
                                    {name: '年最低', value: 230, xAxis: 21, yAxis: 230}]
                            },
                            markLine: {
                                data: [
                                    {type: 'average', name: '平均值'}]
                            }
                        }]
                    };
                    monthChart.setOption(option);
                    var i = money(inArray);
                    var o = money(outArray);
                    $("#inMoney").text(i);
                    $("#outMoney").text(o);
                    $("#last").text(i - o);
                }
            }).error(function () {
                layer.msg("加载图异常");
            });
        }

        loadPie();
        // 使用刚指定的配置项和数据显示图表。
        function money(data) {
            var v = 0;
            for (var i = 0; i < data.length; i++) {
                v += data[i];
            }
            return v;
        }
    });

</script>
</body>
</html>