<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="container">
    <div class="row">
        <c:if test="${sessionScope.cart_map.map ==null}">
            <div class="col-md-12">
                <h1 style="color:red">开启剁手模式!</h1>
            </div>
        </c:if>
        <c:if test="${sessionScope.cart_map.map !=null}">
            <div style="margin:0 auto; margin-top:10px;width:950px;">
                <strong style="font-size:16px;margin:5px 0;">订单详情</strong>
                <table class="table table-bordered">
                    <tbody>
                    <tr class="warning">
                        <th>图片</th>
                        <th>商品</th>
                        <th>价格</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>


                    <c:forEach items="${sessionScope.cart_map.map}" var="c">
                        <tr class="active">
                            <td width="60" width="40%">
                                <input type="hidden" name="id" value="22">
                                <img src="${pageContext.request.contextPath}/${c.value.product.pimage}"
                                     width="70" height="60">
                            </td>
                            <td width="30%">
                                <a target="_blank">${c.value.product.pname}</a>
                            </td>
                            <td width="20%">
                                ￥${c.value.product.shop_price}
                            </td>
                            <td width="10%">
                                <input type="text" name="quantity" value="${c.value.num}"
                                       maxlength="4" size="10">
                            </td>
                            <td width="15%">
                                <span class="subtotal">￥${c.value.subTotal}</span>
                            </td>
                            <td>
                                <a href="javascript:void(0)" onclick="deleteCartItem(${c.value.product.pid})"
                                   class="delete">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div style="margin-right:130px;">
                <div style="text-align:right;">
                    <em style="color:#ff6600;">
                        登录后确认是否享有优惠&nbsp;&nbsp;
                    </em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong
                        style="color:#ff6600;">￥${sessionScope.cart_map.totalMoney}元</strong>
                </div>
                <div style="text-align:right;margin-top:10px;margin-bottom:10px;">
                    <a href="javascript:void(0)" onclick="clearCart()"
                       id="clear" class="clear">清空购物车</a>
                    <a href="${pageContext.request.contextPath}/OrderServlet?method=saveOrder">
                            <%--提交表单 --%>
                        <input type="submit" width="100" value="提交订单" name="submit"
                               border="0"
                               style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                                       height:35px;width:100px;color:white;">
                    </a>
                </div>
            </div>
        </c:if>
    </div>


</div>
<script type="text/javascript">
    function deleteCartItem(pid){
        if (confirm("确认从购物车中删除该项吗?")) {
            window.location.href = "${pageContext.request.contextPath}/CartServlet?method=removeCartItem&pid="+pid;
        }
    }
    function clearCart(){
        if (confirm("确认从购物车中删除该项吗?")) {
            window.location.href = "${pageContext.request.contextPath}/CartServlet?method=clearCart";
        }
    }
</script>
<%@include file="footer.jsp" %>