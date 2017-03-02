<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/22
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>网盘系统</title>
    <%@include file="../include/css.jsp" %>
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <style>
        #picker {
            float: left;
            margin-right: 20px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="pan"/>
    </jsp:include>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title">网盘系统</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" id="change" title="">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" title="">
                            <i class="fa fa-times"></i></button>
                    </div>

                </div>
                <div class="box-body">
                    <div id="picker">上传文件</div>
                    <button class="btn btn-primary" id="newDir">新建文件夹</button>
                    <table id="list" class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>名称</th>
                            <th>大小</th>
                            <th>创建时间</th>
                            <th>创建人</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty skyDriveList}">
                            <tr>
                                <td colspan="6">暂无资源</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${skyDriveList}" var="skyDriver">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${skyDriver.fileType == 'dir'}">
                                            <i class="fa fa-folder-o"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-file-o"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${skyDriver.fileType == 'dir'}">
                                            <a href="/skydrive?path=${skyDriver.id}">${skyDriver.sourceName}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/skydrive/download?id=${skyDriver.id}">${skyDriver.sourceName}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${skyDriver.fileSize}</td>
                                <td>${skyDriver.createTime}</td>
                                <td>${skyDriver.createUser}</td>
                                <td>
                                    <a href="javascript:;" class="remove" rel="${skyDriver.id}"><i
                                            class="fa fa-trash text-danger"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <ul id="Icon" hidden class="table table-bordered list-inline">
                        <c:forEach items="${skyDriveList}" var="skyDrive">
                            <li style="width: 100px">
                                <c:choose>
                                    <c:when test="${skyDrive.fileType=='dir'}">
                                        <a href="/skydrive?path=${skyDrive.id}">
                                            <div>
                                                <div class="${skyDrive.fileType}"></div>
                                                <br>
                                                <span>${skyDrive.sourceName}</span>
                                            </div>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/skydrive/download?id=${skyDrive.id}">
                                            <div>
                                                <div class="${skyDrive.fileType}"></div>
                                                <br>
                                                <span>${skyDrive.sourceName}</span>
                                            </div>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@include file="../include/footer.jsp" %>
</div>

<%@include file="../include/js.jsp" %>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script>
    $(function () {
        $("#change").click(function () {
            $("#list").toggle();
            $("#Icon").toggle();
        });


        var uploder = WebUploader.create({
            swf: "/static/js/uploader/Uploader.swf",
            server: "/skydrive/upload",
            pick: "#picker",
            auto: true,
            fileVal: 'file',
            formData: {"fid":${fid}}
        });

        uploder.on("uploadSuccess", function (file, resp) {
            if (resp.status == 'success') {
                window.history.go(0);
            } else {
                layer.msg(resp.message);
            }
        });
        uploder.on("uploadError", function () {
            layer.msg("服务器异常");
        });


        $("#newDir").click(function () {
            var fid = ${fid};
            layer.prompt({title: "请输入文件夹名称"}, function (text, index) {
                layer.close(index);
                $.post("/skydrive/newdir", {"fid": fid, "sourceName": text}).done(function (resp) {
                    if (resp.status == 'success') {
                        window.history.go(0);
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });
        });

        $(".remove").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗?", function (index) {
                layer.close(index);

                $.get("/skydrive/del/" + id).done(function (resp) {
                    if (resp.status == 'success') {
                        layer.msg("删除成功");
                        window.history.go(0);
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍后");
                })
            });
        });
    });
</script>
</body>
</html>
