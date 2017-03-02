<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/17
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>设备新增</title>
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
                    <h3 class="box-title">新增设备</h3>
                </div>
                <div class="box-body">
                    <form method="post">
                        <div class="form-group">
                            <label>设备号（系统自动生成）</label>
                            <input type="text" disabled="disabled" name="deviceAccount" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>设备名称</label>
                            <input type="text" name="deviceName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>总数量</label>
                            <input type="text" name="deviceAllNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>现有库存（默认等于总数量）</label>
                            <input type="text" disabled="disabled" name="deviceNowNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>单位</label>
                            <input type="text" name="deviceUnit" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>租赁单价（元/（单位/天））</label>
                            <input type="text" name="deviceUnitPrice" class="form-control">
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