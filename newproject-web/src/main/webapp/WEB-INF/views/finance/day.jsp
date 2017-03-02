<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/2/9
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>财务报表-日报</title>
    <%@include file="../include/css.jsp" %>
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
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
                    <h3 class="box-title">财务日报</h3>
                    <div class="box-tools pull-right">
                        <a href="javascript:;" id="exportCsv" class="btn btn-default"><i class="fa fa-file-o"></i>导出Excel</a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box-body">
                        <table class="table table-bordered" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>财务流水号</th>
                                <th>财务流水名称</th>
                                <th>业务模块</th>
                                <th>业务流水号</th>
                                <th>金额</th>
                                <th>创建人</th>
                                <th>创建时间</th>
                                <th>状态</th>
                                <th>完成时间</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                    <div style="font-size:18px;font-weight:100"><span id="day"></span>：收入<span class="alert-success"
                                                                                               ID="inMoney"></span>元，支出<span
                            class="alert-error" id="outMoney"></span>元
                    </div>
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
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
<%@include file="../include/moment.jsp" %>
<!-- echarts.js -->
<%@include file="../include/dateTables.jsp" %>
<script src="/static/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/js/echarts.js"></script>

<script>
    $(function () {
        if (${param.get("_")!=null}) {
            $("#date").val('${param.get("_")}');
            loadPie();
        } else {
            $("#date").val(moment().format("YYYY-MM-DD"));
            $("#day").text(moment().format("YYYY年MM月DD日"));
        }

        $("#date").datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            endDate: moment().format("YYYY-MM-DD")
        }).on("changeDate", function (e) {
//            var today = e.format(0, 'YYYY-MM-DD');
//            table.ajax.reload(false, null);参数是为了保证在不是第一页操作后刷新页面使页面回到刷新前页码
            table.ajax.reload();
            loadPie();
            $("#day").text(e.format('yyyy年mm月dd日'));
        });

        var table = $(".table").DataTable({
            "lengthChange": false,
            /*"lengthMenu": [10,50,100],*/
            "pageLength": 25,
            "serverSide": true,
            "ajax": {
                "url": "/finance/day/load",
                "type": "get",
                "data": function (obj) {
                    obj.day = $("#date").val()
                }
            },
            "searching": false,//不使用自带的搜索
//            "order": [[0, 'desc']],//默认排序方式,
            "ordering": false,
            "columns": [
                {"data": "id", "name": "id"},
                {"data": "serialNumber"},
                {"data": "financeName"},
                {"data": "model"},
                {
                    "data": function (data) {
                        if (data.model == '设备租赁') {
                            return "<a href='/business/rent/" + data.rentSerial + "'>" + data.rentSerial + "</a>";
                        } else {
                            return "<a href='/business/out/" + data.rentSerial + "'>" + data.rentSerial + "</a>";
                        }
                    }
                },
                {"data": "money"},
                {"data": "createUser"},
                {"data": "createDate"},
                {
                    "data": function (data) {
                        if (data.state == '未完成') {
                            return "<a href='javascript:;' rel='" + data.serialNumber + "' class='confirm_btn btn btn-xs btn-default'> <i class='fa fa-check'></i>完成</a>";
                        } else {
                            return "已完成";
                        }
                    }
                },
                {"data": "confirmDate"}
            ],
            "columnDefs": [
                {targets: [0], visible: false}
            ],
            "language": { //定义中文
                "search": "搜索:",
                "zeroRecords": "没有匹配的数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "显示从 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoFiltered": "(从 _MAX_ 条数据中过滤得来)",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "Showing": "筛选出",
                "paginate": {
                    "first": "首页",
                    "last": "末页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            }
        });

        //更改财务流水状态
        $(document).delegate(".confirm_btn", "click", function () {
            var serialNumber = $(this).attr("rel");
            layer.confirm("确认已收(付)款?", function (index) {
                layer.close(index);
                $.post("/finance/confirm/" + serialNumber).done(function (result) {
                    if (result.status == "success") {
                        layer.msg("确认成功");
                        table.ajax.reload(false, null);
                    } else {
                        layer.msg(message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });
        });
        //导出Excel文件
        $("#exportCsv").click(function () {
            var day = $("#date").val();
            window.location.href = "/finance/day/" + day + "/data.xls";
        })

        // 基于准备好的dom，初始化echarts实例
        var inChart = echarts.init(document.getElementById('in'));
        var outChart = echarts.init($("#out")[0]);

        function loadPie() {
            //收入统计
            $.get("/finance/day/in/" + $("#date").val() + "/pie").done(function (result) {
                if (result.status == 'success') {
                    var nameArray = [];
                    for (var i = 0; i < result.data.length; i++) {
                        var obj = result.data[i];
                        nameArray.push(obj.name);
                    }
                    var option = {
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
                            data: nameArray
                        },
                        series: [{
//                            name: '收入',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: result.data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }]
                    };
                    inChart.setOption(option);
                    $("#inMoney").text(money(result.data));
                } else {
                    layer.msg(result.message);
                }
            }).error(function () {
                layer.msg("加载饼图异常");
            });
            //支出统计
            $.get("/finance/day/out/" + $("#date").val() + "/pie").done(function (result) {
                if (result.status == 'success') {
                    var nameArray = [];
                    for (var i = 0; i < result.data.length; i++) {
                        var obj = result.data[i];
                        nameArray.push(obj.name);
                    }
                    var option = {
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
                            data: nameArray
                        },
                        series: [{
//                            name: '支出',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: result.data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }]
                    };
                    outChart.setOption(option);
                    $("#outMoney").text(money(result.data));
                } else {
                    layer.msg(result.message);
                }
            }).error(function () {
                layer.msg("加载饼图异常");
            });
        }

        loadPie();

        function money(data) {
            var v = 0;
            for (var i = 0; i < data.length; i++) {
                v += data[i].value;
            }
            return v;
        }
    });
</script>
</body>
</html>