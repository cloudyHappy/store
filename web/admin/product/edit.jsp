<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
    <style>
        .myselfStyle {
            border: 1px dashed #2aabd2;
        }
    </style>
</HEAD>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<body>
<!--  -->
<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/AdminProductServlet?method=updateProduct" method="post"
      enctype="multipart/form-data">
    <%--<input type="hidden" name="pid" value="<s:property value="model.pid"/>">
    <input type="hidden" name="image" value="<s:property value="model.image"/>">--%>

    <table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
        <input type="hidden" name="pid" value="${product.pid}">
        <input type="hidden" name="pimage" value="${product.pimage}">
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
                height="26">
                <strong><STRONG>编辑商品</STRONG>
                </strong>
            </td>
        </tr>

        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品名称：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="pname" value="${product.pname}"
                       class="myselfStyle"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01" class="myselfStyle">
                是否热门：
            </td>
            <td class="ta_01" bgColor="#ffffff">

                <select name="is_hot" class="myselfStyle">


                    <option style="color: #17c694" value="1" <c:if test="${product.is_hot==1}">selected
                    </c:if>><span style="color:#17c694;">热门</span>
                    </option>
                    <option style="color: red" value="0" <c:if test="${product.is_hot==0}">selected
                    </c:if>><span style="color:green">否</span>
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                市场价格：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="market_price" class="myselfStyle" value="${product.market_price}"
                       class="bg"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商城价格：
            </td>
            <td class="ta_01">
                <input type="text" name="shop_price" class="myselfStyle" value="${product.shop_price}"
                       class="bg"/>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品图片：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <img src="${pageContext.request.contextPath}/${product.pimage}">
                <input type="file" name="upload"/>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                所属的分类：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <select name="cid" id="category_select" class="myselfStyle">

                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品描述：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <textarea name="pdesc" rows="5" cols="30">${product.pdesc}</textarea>
            </td>
        </tr>
        <tr>
            <td class="ta_01" style="WIDTH: 100%" align="center"
                bgColor="#f5fafe" colSpan="4">
                <button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok" onclick="updateProduct">
                    &#30830;&#23450;
                </button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <button type="reset" value="重置" class="button_cancel" onclick="reset">&#37325;&#32622;</button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
                <span id="Label1"></span>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    function updateProduct() {
        $("#userAction_save_do").submit();
    }

    function reset() {
        window.location.href = "${pageContext.request.contextPath}/AdminProductServlet?method=editUI&pid=${product.pid}";
    }

    $(function () {
        var url = "${pageContext.request.contextPath}/CategoryServlet";
        var parameter = {"method": "getAllCategory"};
        $.post(url, parameter, function (result) {

            $(result).each(function (i, e) {
                var option = "<option value=" + e.cid + ">" + e.cname + "</option>";
                if ("${product.category.cid}" == e.cid
                ) {
                    option = "<option value='" + e.cid + "' selected>" + e.cname + "</option>";
                }
                $("#category_select").append(option);
            });
        }, "json");
    })
    /*$(document).ready(function () {
        alert("z");

    });*/
</script>
</body>

</HTML>