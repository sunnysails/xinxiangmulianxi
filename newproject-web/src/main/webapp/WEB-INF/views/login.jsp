<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/16
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>系统登录</title>
    <%@include file="include/css.jsp" %>
    <!-- iCheck -->
    <link rel="stylesheet" href="/static/plugins/iCheck/square/blue.css">

    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body class="hold-transition login-page" style="background-image: url(/static/img/bg.jpg);">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><h2>功成企业管理系统登录</h2></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body" style="background-color: #ffe;">
        <p class="login-box-msg">请输入帐号密码</p>

        <form method="post">
            <div class="form-group has-feedback">
                <input type="text" name="userName" class="form-control" placeholder="帐号/邮箱/手机号码">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <c:if test="${not empty message}">
                <div class="alert alert-info">${message}</div>
            </c:if>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label style="margin-left:20px"><input type="checkbox">记住我</label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="form-group col-xs-4">
                    <button class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>
        <!-- /.social-auth-links -->
        <a href="#">忘记密码</a><br>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<%@include file="include/js.jsp" %>
<script>
    $(function () {

    });
</script>
</body>
</html>
