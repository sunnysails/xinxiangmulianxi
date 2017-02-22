<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/22
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公司网盘</title>
    <!-- Tell tde browser to be responsive to screen widtd -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp" %>

    <!-- 文件上传 -->
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/dist/sweetalert.css">
    <link rel="stylesheet" href="/static/css/style.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp" %>

    <!-- Left side column. contains tde sidebar -->
    <%@include file="../include/sider.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>

                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 公司网盘</a></li>
                <li><a href="#">浏览网盘</a></li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header witd-border">
                    <h3 class="box-title">文件列表</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                                title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip"
                                title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">
                        <div class="box-header with-border" style="height: 70px">
                            <div class="box-tools pull-right">
                                <div class="box-tools pull-right">
                                    <a href="javascript:;" rel="${skyDriveList[0].relationId}"
                                       class="btn newFile">新建文件夹</a>
                                    <a class="btn" id="picker">上传文件</a>
                                </div>
                            </div>
                        </div>
                        <div class="box-body">
                            <ul class="table table-bordered list-inline">
                                <c:forEach items="${skyDriveList}" var="skyDrive">
                                    <li style="width: 100px">
                                        <c:choose>
                                            <c:when test="${skyDrive.fileType=='dic'}">
                                                <a href="/skydrive/${skyDrive.id}">
                                                    <div class="${skyDrive.fileType}"></div>
                                                    <br>
                                                    <span>${skyDrive.storeName}</span>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="${skyDrive.fileType}"></div>
                                                <br>
                                                <span>${skyDrive.storeName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <!-- /.box-body -->
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
    <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@include file="../include/js.jsp" %>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/dist/sweetalert.min.js"></script>
<script>
    $(function () {
        //文件上传
        var uploder = WebUploader.create({
            swf: "js/uploader/Uploader.swf",
            server: "#",
            pick: '#picker',
            auto: true,
            fileVal: 'file',
        });
        $(".newFile").click(function () {
            var relationId = $(this).attr("rel");
            swal({
                title: "新建文件夹!",
                text: "输入新文件夹名称",
                type: "input",
                showCancelButton: true,
                cancelButtonText: "取消",
                closeOnConfirm: false,
                animation: "slide-from-top",
                confirmButtonText: "确定",
                inputPlaceholder: "新建文件夹名称"
            }, function (inputValue) {
                if (inputValue === false)
                    return false;
                if (inputValue === "") {
                    swal.showInputError("文件夹名不能为空!");
                    return false
                }
                swal({
                    title: "新文件夹名称：" + inputValue,
                    text: "确定要建立或更改文件夹名称?",
                    showConfirmButton: true,
                    confirmButtonText: "确定",
                    showCancelButton: true,
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function () {
                    $.ajax({
                        url: "/skydrive/newdic",
                        method:"post",
                        data: {"inputValue": inputValue, "relationId": relationId},
                        success: function (data) {
                            console.log(data);
                            if (data.state == 'success') {
                                swal({
                                    title: "更新节点名称成功!",
                                    type: "success",
                                    timer: 1000,
                                    showConfirmButton: false,
                                    showCancelButton: false,
                                    closeOnConfirm: false
                                }, function () {
                                    window.history.go(0);
                                });
                            } else {
                                swal("更新失败", data.message, "error");
                            }
                        },
                        error: function () {
                            swal("服务器异常,更新失败!", "", "error");
                        }
                    });
                });
            });
        });
    });
</script>
</body>
</html>
