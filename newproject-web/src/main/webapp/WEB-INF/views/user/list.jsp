<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工清单</title>
    <%@include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="sys_accounts"/>
    </jsp:include>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">账户管理</h3>
                    <div class="box-tools pull-right">
                        <a href="/user/new" class="btn"><i class="fa fa-plus"></i></a>
                    </div>
                </div>
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">
                                ${message}
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        </div>
                    </c:if>
                    <table class="table">
                        <thead align="center">
                        <tr>
                            <th>姓名</th>
                            <th>帐号</th>
                            <th>角色</th>
                            <th>email</th>
                            <th>电话</th>
                            <th>状态</th>
                            <th width="80">#</th>
                        </tr>
                        </thead>
                        <tbody align="center">
                        <c:forEach items="${userList}" var="user">
                            <tr>
                                <td>${user.userName}</td>
                                <td>${user.userAccount}</td>
                                <td>${user.roleNames}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>
                                <c:choose>
                                    <c:when test="${user.state == 1}">
                                        <td>可用</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>禁用中</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <a href="/user/${user.id}/edit">编辑</a>
                                    <a href="/usr/${user.id}/del">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@include file="../include/footer.jsp" %>
</div>
<%@include file="../include/js.jsp" %>
</body>
</html>
