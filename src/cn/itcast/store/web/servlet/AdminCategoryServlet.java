package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.impl.CategoryServiceImpl;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        try {
            List<Category> categoryList = categoryService.getAllCategory();
            request.setAttribute("categoryList", categoryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/admin/category/list.jsp";
    }

    /**
     * 跳转至添加分类界面
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        return "/admin/category/add.jsp";
    }

    /**
     * 添加分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String cname = request.getParameter("cname");
        CategoryService categoryService = new CategoryServiceImpl();
        Category category  = new Category();
        category.setCid(UUIDUtils.getId());
        category.setCname(cname);
        try {
            categoryService.addCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("AdminCategoryServlet?method=findAllCategory");
        return null;
    }
//deleteCategory
public String deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException, SQLException {
        String cid = request.getParameter("cid");
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.deleteCategoryByCid(cid);
    response.sendRedirect("AdminCategoryServlet?method=findAllCategory");
    return null;
        }
}
