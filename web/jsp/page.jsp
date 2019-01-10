<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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