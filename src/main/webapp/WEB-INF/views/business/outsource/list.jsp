<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/21
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>外包流水查询</title>
    <!-- Tell tde browser to be responsive to screen widtd -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../../include/css.jsp" %>

    <link rel="stylesheet" href="/static/css/bootstrap-table-expandable.css">
    <link rel="stylesheet" href="/static/css/style.css">
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
                <li><a href="#">设备租赁</a></li>
                <li class="active">业务流水</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">设备租赁流水</h3>
                    <div class="box-tools pull-right">
                        <div class="box-tools pull-right">
                            <a href="/business/outsource/new" class="btn"><i class="fa fa-plus"></i></a>
                        </div>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">
                        <div id="filtrate-box" class="screen-condition scd01">
                            <!-- 筛选开始 -->
                            <form action="" class="form-inline">
                                <div class="form-group form-marginR">
                                    <label for="exampleInputName2">流水号:</label>
                                    <input type="text" class="form-control form-angle input-sm" id="exampleInputName2"
                                           placeholder="">
                                </div>
                                <div class="form-group form-marginR">
                                    <label for="exampleInputEmail2">用人单位:</label>
                                    <input type="text" class="form-control form-angle input-sm" id="exampleInputName2"
                                           placeholder="">
                                </div>
                                <div class="form-group form-marginR">
                                    <label for="exampleInputName2">状态:</label>
                                    <!-- <div class="input-group"> -->
                                    <select class="form-control form-angle input-sm" id="select_Type">
                                        <option value="1">完成</option>
                                        <option value="2">租用中</option>
                                    </select>
                                    <input type="hidden" name="workFlowType" id="workFlowType">
                                    <!-- </div> -->
                                </div>
                                <div class="form-group form-marginR">
                                    <label for="exampleInputName2">起止时间:</label>
                                    <div class="input-group">
                                        <div class="input-group-addon form-angle input-sm"><i
                                                class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control form-angle form_datetime input-sm"
                                               name="createDate" id="exampleInputName2">
                                    </div>
                                    -
                                    <div class="input-group">
                                        <div class="input-group-addon form-angle input-sm"><i
                                                class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control form-angle form_datetime input-sm"
                                               name="createDate" id="exampleInputName2">
                                    </div>
                                </div>
                                <a type="submit" class="btn btn-default btn-sm">查询</a>
                            </form>
                        </div>
                        <!-- 筛选结束 -->

                        <div class="box-body">
                            <c:if test="${not empty message}">
                                <div class="alert alert-success">
                                        ${message}
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                </div>
                            </c:if>
                            <table class="table table-bordered table-hover table-expandable">
                                <thead align="center">
                                <tr>
                                    <th>流水号</th>
                                    <th>租赁公司</th>
                                    <th>公司电话</th>
                                    <th>法人代表</th>
                                    <th>法人电话</th>
                                    <th>法人身份证号</th>
                                    <th>总租金</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>合同</th>
                                    <%--<th>#</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${outsourceList}" var="out">
                                    <tr align="center">
                                        <td>${out.outAccount}</td>
                                        <td>${out.outCompany}</td>
                                        <td>${out.outCompanyPhone}</td>
                                        <td>${out.outLegal}</td>
                                        <td>${out.outLegalPhone}</td>
                                        <td>${out.outLegalIdCard}</td>
                                        <td>${out.outAmount}</td>
                                        <td>${out.outState == 1 ? "完成":"租用中"}</td>
                                        <td class="time">${out.outCreateTime}</td>
                                        <td><a href="#">下载</a></td>
                                            <%--<td><a href="/business/lease/${lease.id}/detail">详情</a></td>--%>
                                    </tr>
                                    <tr>
                                        <td colspan="12">
                                            <c:forEach items="${out.leaseWorkerList}" var="leaseWorker">
                                                <div class="col-md-6">
                                                    <div class="box box-primary">
                                                        <div class="box-header with-border">
                                                            <h3 class="box-title">
                                                                外包“${leaseWorker.worker.workerViewName}”${leaseWorker.outNum}${leaseWorker.worker.workerUnit}
                                                            </h3>
                                                        </div>
                                                        <!-- /.box-header -->
                                                        <div class="box-body" style="font-size: medium">
                                                            <ul>
                                                                <li><span>租赁起始时间：</span><span class="time">${leaseWorker.workerCreateTime}</span></li>
                                                                <li><span>租赁单位数量（${leaseWorker.worker.workerUnit}）：</span>${leaseWorker.outNum}</li>
                                                                <li><span>租赁确定单价（元）：</span>${leaseWorker.workerCreatePrice}</li>
                                                            </ul>
                                                        </div>
                                                        <!-- /.box-body -->
                                                    </div>
                                                    <!-- /.box -->
                                                </div>
                                            </c:forEach>
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
    <%@ include file="../../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="../../include/js.jsp" %>
<%@ include file="../../include/moment.jsp" %>
<script src="/static/js/bootstrap-table-expandable.js"></script>
</body>
</html>
