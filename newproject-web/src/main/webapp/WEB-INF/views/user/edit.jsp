<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>工号编辑</title>
    <%@include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <%@include file="../include/sider.jsp"%>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">更改账户</h3>
                </div>
                <div class="box-body">
                    <form method="post">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="userName" value="${user.userName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>帐号（帐号暂不支持修改</label>
                            <input disabled="disabled" type="text" name="userAccount" class="form-control" value="${user.userAccount}">
                        </div>
                        <div class="form-group">
                            <label>密码(若不修改请留空)</label>
                            <input type="password" name="password" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>email</label>
                            <input type="email" name="email" value="${user.email}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>电话</label>
                            <input type="text" name="phone" value="${user.phone}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>状态:</label>
                            <br>
                            <c:choose>
                                <c:when test="${user.state == 1}">
                                    <input type="radio" name="state" value="1" checked>正常
                                    <input type="radio" name="state" value="0">禁用
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" name="state" value="1">正常
                                    <input type="radio" name="state" value="0" checked>禁用
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group">
                            <label>角色:</label>
                            <div>
                                <c:forEach items="${roleList}" var="role">
                                    <c:set var="flag" value="true" scope="page"/>
                                    <c:forEach items="${user.roleList}" var="userRole">
                                        <c:if test="${role.id == userRole.id}">
                                            <label class="checkbox-inline">
                                                <input checked type="checkbox" name="roleIds" value="${role.id}"> ${role.viewName}
                                            </label>
                                            <c:set var="flag" value="false"/>
                                        </c:if>
                                    </c:forEach>
                                   <c:if test="${flag}">
                                       <label  class="checkbox-inline">
                                           <input type="checkbox" name="roleIds" value="${role.id}"> ${role.viewName}
                                       </label>
                                   </c:if>
                                </c:forEach>
                            </div>
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
    <%@include file="../include/footer.jsp" %>
</div>
<%@include file="../include/js.jsp"%>
</body>
</html>
