<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/17
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>设备信息编辑</title>
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
                    <h3 class="box-title">设备修改</h3>
                </div>
                <div class="box-body">
                    <form method="post">
                        <input type="hidden" name="id" value="${device.id}"/>
                        <div class="form-group">
                            <label>设备号（设备号不可修改）</label>
                            <input type="text" disabled="disabled" name="deviceAccount" value="${device.deviceAccount}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>设备名称</label>
                            <input type="text" name="deviceName" value="${device.deviceName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>总数量</label>
                            <input type="text" name="deviceAllNum" value="${device.deviceAllNum}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>现有库存</label>
                            <input type="text" name="deviceNowNum" value="${device.deviceNowNum}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>单位</label>
                            <input type="text" name="deviceUnit" value="${device.deviceUnit}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>租赁单价（元/（${device.deviceUnit}/天））</label>
                            <input type="text" name="deviceUnitPrice" value="${device.deviceUnitPrice}" class="form-control">
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