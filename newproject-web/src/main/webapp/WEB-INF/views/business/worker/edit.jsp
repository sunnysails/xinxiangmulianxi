<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/19
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>工种编辑</title>
    <%@include file="../../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp" %>
    <%@include file="../../include/sider.jsp" %>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">工种编辑</h3>
                </div>
                <div class="box-body">
                    <form method="post">
                        <input type="hidden" name="id" value="${worker.id}"/>
                        <div class="form-group">
                            <label>工种保存名，注：创建后不允许修改）</label>
                            <input type="text" disabled="disabled" name="workerName" value="${worker.workerName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>工种名称</label>
                            <input type="text" name="workerViewName" value="${worker.workerViewName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>工种总数量</label>
                            <input type="text" name="workerAllNum" value="${worker.workerAllNum}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>现有该工种数量</label>
                            <input type="text" name="workerNowNum" value="${worker.workerNowNum}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>工种单位（请谨慎修改！）</label>
                            <input type="text" name="workerUnit" value="${worker.workerUnit}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>工种外包单价（元/（单位/天））</label>
                            <input type="text" name="workerUnitPrice" value="${worker.workerUnitPrice}" class="form-control">
                        </div>
                        <div class="form-group">
                            <button class="btn btn-success">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<%@include file="../../include/js.jsp" %>
</body>
</html>
