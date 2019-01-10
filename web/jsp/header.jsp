<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>关于我们</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css"
          type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
            type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css"
          type="text/css"/>
</head>

<body>
<div class="container-fluid">
    <div class="col-md-4">
        <img src="${pageContext.request.contextPath}/img/logo2.png"/>
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png"/>
    </div>
    <ol class="list-inline">

        <c:if test="${loginUser==null}">
            <li>
                <a href="${pageContext.request.contextPath}/UserServlet?method=loginUI">登录</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/UserServlet?method=registerUI">注册</a>
            </li>
        </c:if>
        <c:if test="${loginUser!=null}">
            <li>欢迎${loginUser.username}</li>
            <li>
                <a href="${pageContext.request.contextPath}/UserServlet?method=logout">退出</a>
            </li>
            <li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/OrderServlet?method=getUserOrderByPage&currentPage=1">我的订单
                </a>
            </li>
        </c:if>
    </ol>
    <div class="col-md-3" style="padding-top:20px">
    </div>
</div>
<!--
    描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="categoryUL">
                    <%--<li class="active"><a href="#">手机数码<span class="sr-only">(current)</span></a>
                    </li>--%>
                    <%--<c:forEach items="${allCategory}" var="category">
                        <li><a href="${category.cid}">${category.cname}</a></li>
                    </c:forEach>--%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control"
                               placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit
                    </button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<script type="text/javascript">

    $(document).ready(function () {
        var url = "CategoryServlet";
        $.post(url, {"method":"getAllCategory"},function (data) {
            $(data).each(function (i,e) {
                var li =
                    "<li><a href='${pageContext.request.contextPath}/ProductServlet?method=getProductByCidWithPage&currentPage=1&cid="+e.cid+"'>"+e.cname+"</a></li>";
                $("#categoryUL").append(li);
            });
        },"json");
    })



</script>