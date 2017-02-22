<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/23
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>设备入库</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../../include/css.jsp" %>

    <link rel="stylesheet" href="/static/css/bootstrap-datepicker.min.css">
    <!-- 文件上传 -->
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
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
                <li class="active">设备入库</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">设备入库</h3>
                </div>
                <div class="box-body">
                    <%--公司 Lease--%>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title"><span>租赁流水号：${lease.leaseAccount}。<br></span>租赁公司信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label>租赁公司：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseCompany"
                                           value="${lease.leaseCompany}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>地 &nbsp;&nbsp;&nbsp;&nbsp;址：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseAddress"
                                           value="${lease.leaseAddress}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>公司电话：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseCompanyPhone"
                                           value="${lease.leaseCompanyPhone}">
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label>法人代表：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseLegal"
                                           value="${lease.leaseLegal}">
                                </div>
                                <div class="form-group col-md-4">
                                    <label>电 &nbsp;&nbsp;&nbsp;&nbsp;话：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseLegalPhone"
                                           value="${lease.leaseLegalPhone}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>身份证号：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseLegalIdCard"
                                           value="${lease.leaseLegalIdCard}">
                                </div>
                            </div>
                            <!--金额 -->
                            <div class="row">

                                <div class="form-group col-md-4">
                                    <label>租金金额：</label>
                                    <input type="text" disabled="disabled" name="leaseAmount"
                                           value="${lease.leaseAmount}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>预&nbsp;付&nbsp;款：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leasePrepaid"
                                           value="${lease.leasePrepaid}">
                                </div>

                                <div class="form-group col-md-4">
                                    <label>尾&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="leaseUnpaid"
                                           value="${lease.leaseUnpaid}">
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <label>违约金额：&nbsp;</label>
                                    <input type="text" disabled="disabled" name="">
                                </div>
                                <div class="form-group col-md-4">
                                    <label>损丢赔付：</label>
                                    <input type="text" disabled="disabled" name="">
                                </div>
                                <div class="form-group col-md-4">
                                    <label>应付余款：</label>
                                    <input type="text" disabled="disabled" name="">
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${lease.leaseDeviceList}" var="leaseDevice">
                        <div class="box">
                            <div class="box-body">
                                <div class="box-header with-border">
                                    <h3 class="box-title">设备信息<br>创建时间：<span
                                            class="time">${leaseDevice.createTime}</span></h3>
                                </div>
                                <form id="settlementForm" method="post">
                                    <div class="box-body" class="form-group">
                                            <%--设备--%>
                                        <div class="row">
                                            <div class="col-lg-4">
                                                <div class="form-group">
                                                    <label>设备名称：&nbsp;</label>
                                                    <input type="text" value="${leaseDevice.device.deviceName}">
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div>
                                                    <label>应还数量：&nbsp;</label>
                                                    <input type="text" disabled="disabled"
                                                           value="${leaseDevice.leaseNum}">
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div>
                                                    <label for="backNums">实还数量：&nbsp;</label>
                                                    <input id="backNums" type="text" name="backNums">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-4">
                                                <label>应还日期：</label>
                                                <input type="text" disabled="disabled" value="${leaseDevice.backTime}"
                                                       class="time">
                                            </div>
                                            <div class="form-group col-md-4">
                                                <label>实还日期：</label>
                                                <input type="text" disabled="disabled" value="" class="time">
                                            </div>
                                        </div>
                                        <div><br/></div>
                                    </div>
                                </form>
                            </div>
                            <!-- /.box-body -->
                        </div>
                    </c:forEach>
                    <div class="row">
                        <div class="col-md-2"></div>

                        <div class="col-md-3 box-footer">
                            <button id="settlementBtn" class="btn btn-primary">结算</button>
                        </div>

                        <div class="col-md-3 box-footer">
                            <button type="submit" class="btn btn-primary">重置</button>
                        </div>
                        <div class="col-md-3 box-footer">
                            <button type="submit" class="btn btn-primary">提交数据</button>
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
<%@include file="../../include/moment.jsp" %>
<!-- datepicker -->
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script>
    $(function () {
        $("#datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            //clearBtn: true,//清除按钮
            //todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
//        $("#settlementBtn").click(function () {
//            $("#settlementForm").submit();
//        });
        //文件上传
        var uploder = WebUploader.create({
            swf: "js/uploader/Uploader.swf",
            server: "#",
            pick: '#picker',
            auto: true,
            fileVal: 'file',
            /*accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
             }*/
        });

        $("#settlementBtn").click(function () {
            $.ajax({
                url:"/businesslease/back",
                type: "post",

            })
        });
    });
</script>
</body>
</html>