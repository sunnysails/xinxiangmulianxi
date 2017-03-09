<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/18
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>设备租赁新增</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../../include/css.jsp" %>
    <%--<link rel="stylesheet" href="/static/css/bootstrap-datepicker.min.css">--%>
    <!-- 文件上传 -->
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper" id="app">

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
                <li class="active">新增流水</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">新增租赁流水</h3>
                </div>
                <div class="box-body">
                    <%--公司 Lease--%>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">新租赁公司信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>公司名称</label>
                                        <input ttype="text" class="form-control" name="companyName"
                                               id="companyName">
                                    </div>
                                    <div class="form-group">
                                        <label>联系电话</label>
                                        <input type="text" class="form-control" name="companyTel"
                                               id="companyTel">
                                    </div>
                                    <div class="form-group">
                                        <label>租赁日期</label>
                                        <input type="text" class="form-control" id="rentDate" readonly>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>法人代表</label>
                                        <input type="text" class="form-control" name="linkMan" id="linkMan">
                                    </div>
                                    <div class="form-group">
                                        <label>地址</label>
                                        <input type="text" class="form-control" name="address"
                                               id="address">
                                    </div>
                                    <div class="form-group">
                                        <label>归还日期</label>
                                        <input type="text" class="form-control" id="backDate" name="backDate">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>身份证号</label>
                                        <input type="text" class="form-control" name="linkManCard"
                                               id="linkManCard">
                                    </div>
                                    <div class="form-group">
                                        <label>传真</label>
                                        <input type="text" class="form-control" id="companyFax">
                                    </div>
                                    <div class="form-group">
                                        <label>总天数</label>
                                        <input type="text" class="form-control" id="totalDay" name="totalDay" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-footer" style="text-align: right">
                            总租赁费 {{total}} 元 预付款 {{preCost}} 元 尾款 {{lastCost}} 元
                        </div>
                    </div>
                    <%--公司结束--%>

                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">设备列表</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">
                                    <i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>设备名称</th>
                                    <th>单位</th>
                                    <th>租赁单价</th>
                                    <th>数量</th>
                                    <th>总价</th>
                                    <th>#</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-if="deviceArray.length == 0">
                                    <td colspan="6">暂无数据</td>
                                </tr>
                                <tr v-for="device in deviceArray">
                                    <td>{{device.name}}</td>
                                    <td>{{device.unit}}</td>
                                    <td>{{device.price}}</td>
                                    <td>{{device.num}}</td>
                                    <td>{{device.total}}</td>
                                    <td><a href="javascript:;" @click="remove(device)"><i
                                            class="fa fa-trash text-danger"></i></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div>
                        <br/>
                    </div>
                    <div class="box" style="padding-left: 20px">
                        <div class="box-header">
                            <h4><span class="title"><i class="fa fa-user"></i> 合同上传</span></h4>
                        </div>

                        <p style="padding-left: 20px">注意事项</p>
                        <ul>
                            <li>上传合同扫描件要求清晰可见</li>
                            <li>合同必须公司法人签字盖章</li>
                        </ul>
                        <br>
                        <div class="form-actions">
                            <div id="picker">选择文件</div>
                            <ul id="fileList">
                            </ul>
                        </div>
                    </div>
                    <div class="pull-right box-footer">
                        <button type="button" class="btn btn-primary" @click="saveRent">保存合同</button>
                    </div>
                </div>
                <!-- /.box-body-->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择设备</h4>
                </div>
                <div class="modal-body">
                    <form action="">
                        <div class="form-group">
                            <input type="hidden" id="deviceName">
                            <label>设备名称</label>
                            <select id="deviceId" style="width: 300px;" class="form-control">
                                <option value="">选择设备</option>
                                <c:forEach items="${deviceList}" var="device">
                                    <option value="${device.id}">${device.deviceName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>库存数量</label>
                            <input type="text" class="form-control" id="currNum" readonly>
                        </div>
                        <div class="form-group">
                            <label>单位</label>
                            <input type="text" class="form-control" id="unit" readonly>
                        </div>
                        <div class="form-group">
                            <label>租赁单价</label>
                            <input type="text" class="form-control" id="rentPrice" readonly>
                        </div>
                        <div class="form-group">
                            <label>租赁数量</label>
                            <input type="text" class="form-control" id="rentNum">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">添加完成</button>
                    <button type="button" class="btn btn-primary" @click="addDevice">加入列表</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

    <%@ include file="../../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="../../include/js.jsp" %>
<%@ include file="../../include/moment.jsp" %>
<!-- datepicker -->
<script src="/static/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/plugins/select2/select2.full.min.js"></script>
<script src="/static/plugins/vue.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    var fileArray = [];
    $(function () {
        $("#rentDate").val(moment().format("YYYY-MM-DD"));

        $("#backDate").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            format: "yyyy-mm-dd",//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
            startDate: moment().add(1, "days").format("YYYY-MM-DD")
        }).on("changeDate", function (e) {
            var rentDay = moment();
            var backDay = moment(e.format(0, 'yyyy-mm-dd'));
            var days = backDay.diff(rentDay, 'days') + 1;
            $("#totalDay").val(days);
        });

        $("#deviceId").select2();
        $("#deviceId").change(function () {
            var id = $(this).val();
            if (id) {
                $.get("/business/rent/device.json", {'id': id}).done(function (resp) {
                    if (resp.status == 'success') {
                        var device = resp.data;
                        $("#deviceName").val(device.deviceName);
                        $("#currNum").val(device.deviceNowNum);
                        $("#unit").val(device.deviceUnit);
                        $("#rentPrice").val(device.deviceUnitPrice);
                    } else {
                        alert(resp.message);
                    }
                }).error(function () {
                    alert("服务器异常，请稍后再试");
                });
            }
        });

        //文件上传
        var uploder = WebUploader.create({
            swf: "/static/js/uploader/Uploader.swf",
            server: "/file/upload",
            pick: '#picker',
            auto: true,
            fileVal: 'file'
        });

        uploder.on("uploadSuccess", function (file, resp) {
            layer.msg("上传成功");
            var html = "<li>" + resp.data.sourceFileName + "</li>";
            $("#fileList").append(html);

            var json = {
                sourceName: resp.data.sourceFileName,
                newName: resp.data.newFileName,
            };
            fileArray.push(json);
        });
        uploder.on("uploadError", function () {
            layer.msg("服务器忙，强稍后再试");
        })
    });

    var app = new Vue({
        el: "#app",
        data: {
            deviceArray: []
        },
        methods: {
            addDevice: function () {
                var id = $("#deviceId").val();
                //判断数组中是否存在当前设备信息
                var flag = false;
                for (var i = 0; i < this.$data.deviceArray.length; i++) {
                    var item = this.$data.deviceArray[i];
                    if (item.id == id) {
                        item.num = parseFloat(item.num) + parseFloat($("#rentNum").val());
                        item.total = parseFloat(item.num) * parseFloat($("#rentPrice").val());
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    var json = {};
                    json.id = id;
                    json.name = $("#deviceName").val();
                    json.unit = $("#unit").val();
                    json.price = $("#rentPrice").val();
                    json.num = $("#rentNum").val();
                    json.total = parseFloat(json.price) * parseFloat(json.num);

                    this.$data.deviceArray.push(json);
                }
            },
            remove: function (device) {
                layer.confirm("确定要删除吗", function (index) {
                    app.$data.deviceArray.splice(app.$data.deviceArray.indexOf(device), 1);
                    layer.close(index);
                });
            },
            saveRent: function () {
                var json = {
                    deviceArray: app.$data.deviceArray,
                    fileArray: fileArray,
                    companyName: $("#companyName").val(),
                    companyTel: $("#companyTel").val(),
                    linkMan: $("#linkMan").val(),
                    linkManCard: $("#linkManCard").val(),
                    address: $("#address").val(),
                    companyFax: $("#companyFax").val(),
                    rentDate: $("#rentDate").val(),
                    backDate: $("#backDate").val(),
                    totalDate: $("#totalDays").val()
                };

                $.ajax({
                    url: "/business/rent/new",
                    type: "post",
                    data: JSON.stringify(json),
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.status == 'success') {
                            layer.confirm("保存成功", {btn: ['继续添加', '打印合同']}, function () {
                                window.history.go(0);
                            }, function () {
                                window.location.href="/business/rent/"+data.data;
                            });
                        }
                    },
                    error: function () {
                        layer.msg("服务器忙，请稍候");
                    }
                });
            }
        },
        computed: {
            total: function () {
                var result = 0;
                for (var i = 0; i < this.$data.deviceArray.length; i++) {
                    var item = this.$data.deviceArray[i];
                    result += item.total;
                }
                return result;
            },
            preCost: function () {
                return this.total * 0.3;
            },
            lastCost: function () {
                return this.total - this.preCost;
            }
        }
    });

</script>
</body>
</html>