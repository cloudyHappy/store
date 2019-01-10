package cn.itcast.store.service.impl;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.dao.impl.OrderDaoImpl;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.utils.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public void saveOrder(Order order) throws Exception {
        //保存订单和订单下所有的订单项(同时完成,或者失败)
       /*
       //方式1:
        JDBCUtils.startTransaction();
        try {
            OrderDao orderDao = new OrderDaoImpl();
            orderDao.saveOrder(order);
            for (OrderItem orderItem : order.getList()) {
                orderDao.saveOrderItem(orderItem);
            }
        } catch (Exception e) {
            JDBCUtils.rollbackAndClose();
            e.printStackTrace();
        }
        JDBCUtils.commitAndClose();*/
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);//设置不自动提交
            //保存订单以及订单项
            OrderDao orderDao = new OrderDaoImpl();
            orderDao.saveOrder(connection,order);
            for (OrderItem orderItem : order.getList()) {
                orderDao.saveOrderItem(connection,orderItem);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public PageModel<Order> getUserOrderByPage(User user, Integer currentPage) throws SQLException, InvocationTargetException, IllegalAccessException {
        OrderDao orderDao = new OrderDaoImpl();
        int totalCount = orderDao.getUserOrderTotalCount(user);
        PageModel<Order> pageModel = new PageModel<>(currentPage,totalCount , 3);
        List<Order> list = orderDao.getUserDaoWithPage(user,pageModel.getStartIndex(),pageModel.getPageSize());
        pageModel.setData(list);
        return pageModel;
    }

    @Override
    public Order getOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        OrderDao orderDao = new OrderDaoImpl();
        return orderDao.getOrderByOid(oid);
    }
}
