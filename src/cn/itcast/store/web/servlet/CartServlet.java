package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Cart_map;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.impl.ProductServiceImpl;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/CartServlet")
public class CartServlet extends BaseServlet {
    public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pid = request.getParameter("pid");
        Integer num = Integer.valueOf(request.getParameter("num"));
        Cart_map cart = (Cart_map) request.getSession().getAttribute("cart_map");
        if (cart == null) {
            cart = new Cart_map();
            request.getSession().setAttribute("cart_map", cart);
        }
        ProductService productService = new ProductServiceImpl();
        try {
            Product product = productService.getProductById(pid);
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setNum(num);
            System.out.println(cartItem.getSubTotal());
            cart.addCartItemToCar(cartItem);
            response.sendRedirect("jsp/cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 移除购物车中指定的项
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pid = request.getParameter("pid");
        Cart_map cart_map = (Cart_map) request.getSession().getAttribute("cart_map");
        if (cart_map != null) {
            cart_map.removeCartItemByPid(pid);
        }
        response.sendRedirect("jsp/cart.jsp");
        return null;
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Cart_map cart_map = (Cart_map) request.getSession().getAttribute("cart_map");
        if (cart_map != null) {
            cart_map.clearCart();
        }
        response.sendRedirect("jsp/cart.jsp");
        return null;

    }

}
