<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2017/1/23
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="//cdn.bootcss.com/moment.js/2.17.1/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.17.1/locale/zh-cn.js"></script>
<script>
    $(document).ready(function () {
        $(".time").text(function () {
            var time = $(this).text();
            return moment(time).format("YYYY年MM月DD日");
        });
    });
</script>