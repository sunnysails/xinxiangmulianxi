<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/2/21
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>设备租赁合同显示</title>
    <%@include file="../../include/css.jsp"%>
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper" id="app">

    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="business_device_rent"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <h3 style="text-align: center" class="visible-print-block">凯盛软件外包合同清单</h3>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">租赁合同详情</h3>

                    <div class="box-tools pull-right hidden-print">
                        <button id="print" class="btn btn-default btn-sm"><i class="fa fa-print"></i>打印</button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <th>公司名称：${rent.companyName}</th>
                            <th>法人代表：${rent.linkMan}</th>
                            <th>身份证号：${rent.linkManCard}</th>
                        </tr>
                        <tr>
                            <th>联系电话：${rent.companyTel}</th>
                            <th>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：${rent.address}</th>
                            <th>传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：${rent.companyFax}</th>
                        </tr>
                        <tr>
                            <th>租赁日期：${rent.rentDate}</th>
                            <th>归还日期：${rent.backDate}</th>
                            <th>总&nbsp;&nbsp;天&nbsp;数：${rent.totalDay}（天）</th>
                        </tr>
                        <tr>
                            <th>总&nbsp;&nbsp;金&nbsp;额：${rent.totalPrice}（元）</th>
                            <th>预&nbsp;&nbsp;付&nbsp;款：${rent.preCost}（元）</th>
                            <th>尾&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款：${rent.lastCost}（元）</th>
                        </tr>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">设备列表</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>设备名称</th>
                            <th>单位</th>
                            <th>租赁单价</th>
                            <th>数量</th>
                            <th>总价</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${detailList}" var="d">
                            <tr>
                                <td>${d.workerName}</td>
                                <td>人</td>
                                <td>${d.workerPrice}（元）</td>
                                <td>${d.outNum}</td>
                                <td>${d.totalPrice}（元）</td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="box hidden-print">
                <div class="box-header">
                    <h3 class="box-title">合同扫描件</h3>
                    <div class="box-tools pull-right">
                        <a href="/business/rent/doc/zip?id=${rent.id}" class="btn btn-sm btn-default">
                            <i class="fa fa-file-zip"></i> 打包下载
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <ul id="fileList">
                        <c:forEach items="${docList}" var="doc">
                            <li><a href="/business/rent/doc?id=${doc.id}">${doc.sourceName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<%@include file="../../include/js.jsp"%>
<script>
    $(function () {
        $("#print").click(function () {
            window.print();
        });
    });
</script>
</body>
</html>

