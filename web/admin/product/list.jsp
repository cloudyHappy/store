<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
    <script type="text/javascript">
        function addProduct() {
            window.location.href = "${pageContext.request.contextPath}/AdminProductServlet?method=addProductUI";
        }
    </script>


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
</HEAD>
<body>
<br>
<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
    <table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
        <TBODY>
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3">
                <strong>商品列表</strong>
            </TD>
        </tr>
        <tr>
            <td class="ta_01" align="right">
                <button type="button" id="add" name="add" value="添加" class="button_add" onclick="addProduct()">
                    &#28155;&#21152;
                </button>

            </td>
        </tr>
        <tr>
            <td class="ta_01" align="center" bgColor="#f5fafe">
                <table cellspacing="0" cellpadding="1" rules="all"
                       bordercolor="gray" border="1" id="DataGrid1"
                       style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
                    <tr
                            style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

                        <td align="center" width="5%">
                            序号
                        </td>
                        <td align="center" width="17%">
                            商品图片
                        </td>
                        <td align="center" width="17%">
                            商品名称
                        </td>
                        <td align="center" width="17%">
                            商品价格
                        </td>
                        <td align="center" width="17%">
                            上架时间
                        </td>
                        <td align="center" width="5%">
                            是否热门
                        </td>
                        <td width="7%" align="center">
                            编辑
                        </td>
                        <td width="7%" align="center">
                            下架
                        </td>
                    </tr>
                    <c:forEach items="${requestScope.pageModel.data}" var="product" varStatus="status">
                        <tr onmouseover="this.style.backgroundColor = 'white'"
                            onmouseout="this.style.backgroundColor = '#F5FAFE';">
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="5%">
                                    ${ status.count }
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                <img width="40" height="45"
                                     src="${ pageContext.request.contextPath }/${product.pimage}">
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                    ${ product.pname }
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                    ${ product.shop_price }
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                    ${ product.pdate}
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="5%">
                                <c:if test="${product.is_hot==1}">
                                    <span style="color: #17c694">热门</span>
                                </c:if>
                                <c:if test="${product.is_hot==0}">
                                    <span style="color: red">否</span>
                                </c:if>

                            </td>
                            <td align="center" style="HEIGHT: 22px">
                                <a href="${pageContext.request.contextPath}/AdminProductServlet?method=editUI&pid=${product.pid}">
                                    <img src="${pageContext.request.contextPath}/img/admin/i_edit.gif" border="0"
                                         style="CURSOR: hand">
                                </a>
                            </td>

                            <td align="center" style="HEIGHT: 22px">
                                    <%--下架 pushdown --%>
                                <a href="${pageContext.request.contextPath}/AdminProductServlet?method=lowerShelf&pid=${product.pid}">
                                    <img src="${pageContext.request.contextPath}/img/admin/i_del.gif" width="16"
                                         height="16"
                                         border="0" style="CURSOR: hand">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </td>
        </tr>

        <%--<%@include file="" %>--%>


        <%--       <tr align="center">
                   <td colspan="7">
                       第${ pageBean.currPage }/${ pageBean.totalPage }页 &nbsp; &nbsp; &nbsp;
                       总记录数:${ pageBean.totalCount } &nbsp; 每页显示:${ pageBean.pageSize }
                       <c:if test="${ pageBean.currPage != 1 }">
                           <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findByPage&currPage=1">首页</a>|
                           <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findByPage&currPage=${ pageBean.currPage - 1}">上一页</a>|
                       </c:if>
                       &nbsp; &nbsp;

                       <c:forEach var="i" begin="1" end="${ pageBean.totalPage }">
                           <c:if test="${ pageBean.currPage == i }">
                               [${ i }]
                           </c:if>
                           <c:if test="${ pageBean.currPage != i }">
                               <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findByPage&currPage=${ i}">[${ i }]</a>
                           </c:if>
                       </c:forEach>

                       &nbsp; &nbsp;
                       <c:if test="${ pageBean.currPage != pageBean.totalPage }">
                           <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findByPage&currPage=${ pageBean.currPage + 1}">下一页</a>|
                           <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findByPage&currPage=${ pageBean.totalPage}">尾页</a>|
                       </c:if>
                   </td>
               </tr>--%>
        </TBODY>
    </table>
    <div style="margin-left: 40%">
        <jsp:include page="../../jsp/page.jsp"/>

    </div>
</form>
</body>
</HTML>

