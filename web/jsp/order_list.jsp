<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<div class="container">
    <div class="row">

        <div style="margin:0 auto; margin-top:10px;width:950px;">
            <strong>我的订单</strong>
            <c:forEach items="${pageModel.data}" var="order">
            <table class="table table-bordered">
                <tbody>
                <tr class="success">
                    <th colspan="5">订单编号:${order.oid} 订单时间:${order.ordertime} 总金额:${order.total}
                        订单状态:
                        <c:if test="${order.state==1}"><a
                                href="${pageContext.request.contextPath}/OrderServlet?method=getOrder&oid=${order.oid}">付款</a></c:if>
                        <c:if test="${order.state==2}"><a href="javascript:void(0)">待发货</a></c:if>
                        <c:if test="${order.state==3}"><a href="javascript:void(0)">待签收</a></c:if>
                        <c:if test="${order.state==4}"><a href="javascript:void(0)">已完成</a></c:if>

                    </th>
                </tr>
                <tr class="warning">
                    <th>图片</th>
                    <th>商品</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>小计</th>
                </tr>
                <c:forEach items="${order.list}" var="item">

                    <tr class="active">
                        <td width="60" width="40%">
                            <input type="hidden" name="id" value="22">
                            <img src="${pageContext.request.contextPath}/${item.product.pimage}"
                                 width="70" height="60">
                        </td>
                        <td width="30%">
                            <a target="_blank"
                               href="${pageContext.request.contextPath}/ProductServlet?method=getProductById&pid=${item.product.pid}">${item.product.pname}</a>
                        </td>
                        <td width="20%">
                            ￥${item.product.shop_price}
                        </td>
                        <td width="10%">
                                ${item.quantity}
                        </td>
                        <td width="15%">
                            <span class="subtotal">￥${item.total}</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                </c:forEach>

            </table>
        </div>
    </div>
    <div style="text-align: center;">
        <jsp:include page="page.jsp"/>
    </div>
</div>
<%@include file="footer.jsp" %>