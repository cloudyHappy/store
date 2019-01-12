package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.impl.ProductServiceImpl;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/ProductServlet")
public class ProductServlet extends BaseServlet {
    /**
     * 根据商品id获取商品
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        Product product = null;
        try {
            product = productService.getProductById(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("product",product );
        return "jsp/product_info.jsp";
    }

    /**
     * 根据分类,分页获取商品列表
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getProductByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        ProductService productService = new ProductServiceImpl();
        Integer pageSize = 9;
        PageModel<Product> pageModel = null;
        try {
            pageModel = productService.getProductByCidWithPage(cid,currentPage,pageSize);
            String url = request.getContextPath()+"/ProductServlet?method=getProductByCidWithPage&cid="+cid;
            pageModel.setUrl(url);
            request.setAttribute("pageModel",pageModel);
        } catch (Exception e) {
            e.printStackTrace();
                request.setAttribute("message","对不起,该分类下暂无商品!" );
        }finally {
            return "jsp/product_list.jsp";
        }


    }
}
