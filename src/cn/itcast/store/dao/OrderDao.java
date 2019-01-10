package cn.itcast.store.dao;

import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void saveOrder(Connection connection, Order order) throws SQLException;

    void saveOrderItem(Connection connection, OrderItem orderItem) throws SQLException;

    /**
     * 获取指定用户下的订单总数
     * @param user
     * @return
     */
    int getUserOrderTotalCount(User user) throws SQLException;

    /**
     * 分页获取用户下的订单
     * @param user
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Order> getUserDaoWithPage(User user, Integer startIndex, Integer pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据oid获取订单对象
     * @param oid
     * @return
     */
    Order getOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;
}
