<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/19
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>工种管理</title>
    <!-- Tell tde browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp" %>

    <%@include file="../../include/sider.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 业务</a></li>
                <li><a href="#">设备管理</a></li>
                <li class="active">设备库存</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">工种列表</h3>
                    <div class="box-tools pull-right">
                        <a href="/business/worker/new" class="btn"><i class="fa fa-plus"></i></a>
                    </div>
                </div>
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">
                                ${message}
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        </div>
                    </c:if>
                    <div class="box">
                        <div class="box-body">
                            <table class="table table-bordered">
                                <thead align="center">
                                <tr>
                                    <th>工种名称</th>
                                    <th>工种总数量</th>
                                    <th>工种现有数量</th>
                                    <th>工种单位</th>
                                    <th>工种租金（元/（单位/天））</th>
                                    <th>工种创建时间</th>
                                    <th>工种更新时间</th>
                                    <th width="80">#</th>
                                </tr>
                                </thead>
                                <tbody align="center">
                                <c:forEach items="${workerList}" var="worker">
                                    <tr>
                                        <td>${worker.workerViewName}</td>
                                        <td>${worker.workerAllNum}</td>
                                        <td>${worker.workerNowNum}</td>
                                        <td>${worker.workerUnit}</td>
                                        <td>${worker.workerUnitPrice}</td>
                                        <td class="time">${worker.workerCreateTime}</td>
                                        <td class="time">${worker.workerUpdateTime}</td>
                                        <td>
                                            <a href="/business/worker/${worker.id}/edit">编辑</a>
                                            <a href="/business/worker/${worker.id}/del">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
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
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@include file="../../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@include file="../../include/js.jsp" %>
<%@ include file="../../include/moment.jsp" %>
</body>
</html>