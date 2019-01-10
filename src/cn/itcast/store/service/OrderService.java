package cn.itcast.store.service;

import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface OrderService {
    /**
     * 添加订单
     * @param order
     * @throws Exception
     */
    void saveOrder(Order order)throws Exception;

    /**
     * 获取用户的订单
     * @param user
     * @param currentPage
     * @return
     */
    PageModel<Order> getUserOrderByPage(User user, Integer currentPage) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据订单号查询订单
     * @param oid
     * @return
     */
    Order getOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;
}
