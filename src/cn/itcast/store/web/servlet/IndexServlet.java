package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.impl.CategoryServiceImpl;
import cn.itcast.store.service.impl.ProductServiceImpl;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("indexServlet===");
        /*
            默认方法,获取最新商品, 最热商品发送到首页
         */
        /*List<Category> categoryList = null;
        try {
             categoryList = categoryService.getAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(categoryList);
        if (categoryList != null) {
            request.setAttribute("allCategory",categoryList );
        }*/

        ProductService productService = new ProductServiceImpl();
        try {
            List<Product> newProduct = productService.getNewProduct();
            List<Product> hotProduct = productService.getHotProduct();
            request.setAttribute("newProduct", newProduct);
            request.setAttribute("hotProduct", hotProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "jsp/index.jsp";
    }
}
