package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.*;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.impl.OrderServiceImpl;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    /**
     * 保存订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String saveOrder (HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        //确认用户登录状态
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            request.setAttribute("message","请登录之后再下单" );
            return "/jsp/info.jsp";
        }else{
            Cart_map cart = (Cart_map) request.getSession().getAttribute("cart_map");
            //创建订单对象
            Order order = new Order();
            order.setOid(UUIDUtils.getId());
            order.setOrdertime(new Date());
            order.setTotal(cart.getTotalMoney());
            order.setState(1);
            order.setUser(user);
            //创建订单项

            for (CartItem cartItem : cart.getMap().values()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setItemid(UUIDUtils.getId());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getNum());
                orderItem.setTotal(cartItem.getSubTotal());
                orderItem.setOrder(order);
                order.getList().add(orderItem);
            }
            //保存订单
            OrderService orderService = new OrderServiceImpl();
            try {
                orderService.saveOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //清空购物车
            cart.clearCart();
            //将订单存放至request
            request.setAttribute("order",order );
            //转发===
            return "/jsp/order_info.jsp";
        }
    }

    /**
     * get user order
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getUserOrderByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
            OrderService orderService = new OrderServiceImpl();
            PageModel<Order> pageModel  = null;
            try {
                pageModel = orderService.getUserOrderByPage(user,currentPage);
                pageModel.setUrl("OrderServlet?method=getUserOrderByPage");
                request.setAttribute("pageModel",pageModel);
                return "jsp/order_list.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message",e.getMessage());
                return "/jsp/info.jsp";
            }
        }else{
            request.setAttribute("message"," login before checking the order,please" );
            return "/jsp/info.jsp";
        }
    }

    /**
     * get order by oid
     * @param request
     *@param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid =request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        Order order = null;
        try {
            order = orderService.getOrderByOid(oid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("order", order);
        return "/jsp/order_info.jsp";
    }
}
