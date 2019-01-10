<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>

<div class="row" style="width:1210px;margin:0 auto;">
    <div class="col-md-12">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
        </ol>
    </div>
    <c:if test="${message !=null}">
        <h1>${message}</h1>
    </c:if>
    <c:forEach items="${pageModel.data}" var="p">
        <div class="col-md-2">
            <a href="${pageContext.request.contextPath}/ProductServlet?method=getProductById&pid=${p.pid}">
                <img src="${pageContext.request.contextPath}/${p.pimage}"
                     width="170" height="170" style="display: inline-block;">
            </a>
            <p><a href="${pageContext.request.contextPath}/ProductServlet?method=getProductById&pid=${p.pid}"
                  style='color:green'>${p.pname}</a></p>
            <p><font color="#FF0000">商城价：&yen;${p.shop_price}</font></p>
        </div>
    </c:forEach>


</div>

<!--分页 -->
<div style="width:380px;margin:0 auto;margin-top:50px;">
    <c:if test="${pageModel.data!=null}">
        <ul class="pagination" style="text-align:center; margin-top:10px;" id="pagination">
                <%--是否遍历首页--%>
            <c:if test="${pageModel.currentPage != 1}">
                    <li><a href="${pageModel.url}&currentPage=1">首页</a>
                    </li>
                </c:if>
                    <c:if test="${pageModel.currentPage == 1}">
                        <li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span
                                aria-hidden="true">首页</span></a></li>
                    </c:if>
                        <%--输出当前页前面的两页--%>
                    <c:if test="${pageModel.currentPage - 2 >0}">
                        <li><a href="${pageModel.url}&currentPage=${pageModel.currentPage -2}">${pageModel.currentPage -2}</a>
                        </li>
                    </c:if>
                    <c:if test="${pageModel.currentPage - 1 >0}">
                        <li><a
                                href="${pageModel.url}&currentPage=${pageModel.currentPage -1}">${pageModel.currentPage -1}</a>
                        </li>
                    </c:if>

                        <%--输出选中的页--%>
                    <li class="active"><a
                            href="${pageModel.url}&currentPage=${pageModel.currentPage}">${pageModel.currentPage}</a></li>
                        <%--输出后面两页--%>
                    <c:if test="${pageModel.currentPage+1<=pageModel.totalPage}">
                        <li><a href="${pageModel.url}&currentPage=${pageModel.currentPage +1}">${pageModel.currentPage +1}</a>
                        </li>
                    </c:if>
                    <c:if test="${pageModel.currentPage+2<=pageModel.totalPage}">
                        <li><a
                                href="${pageModel.url}&currentPage=${pageModel.currentPage +2}">${pageModel.currentPage +2}</a>
                        </li>
                    </c:if>

                        <%--是否遍历尾页--%>
                    <c:if test="${pageModel.currentPage != pageModel.totalPage}">
                        <li><a href="${pageModel.url}&currentPage=${pageModel.totalCount}">尾页</a>
                        </li>
                    </c:if>
                    <c:if test="${pageModel.currentPage == pageModel.totalPage}">
                        <li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span
                                aria-hidden="true">尾页</span></a></li>
                    </c:if>
        </ul>
    </c:if>
    <%--      <li class="disabled"><a href="#" aria-label="Previous"><span
                  aria-hidden="true">&laquo;</span></a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">6</a></li>
          <li><a href="#">7</a></li>
          <li><a href="#">8</a></li>
          <li><a href="#">9</a></li>
          <li>
              <a href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
              </a>
          </li>--%>

</div>

<!-- 分页结束======================= -->

<!--
商品浏览记录:
-->
<%--<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

    <h4 style="width:50%;float: left;font: 14px/30px 微软雅黑">浏览记录</h4>
    <div style="width: 50%;float: right;text-align: right;"><a href="">more</a>
    </div>
    <div style="clear: both;"></div>

    <div style="overflow: hidden;">

        <ul style="list-style: none;">
            <li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;">
                <img src="${pageContext.request.contextPath}/products/1/cs10001.jpg"
                     width="130px" height="130px"/></li>
        </ul>

    </div>
</div>--%>
<%@include file="footer.jsp" %>