package cn.itcast.store.dao.impl;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

    @Override
    public void saveOrder(Connection connection, Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner();
        Object[] params = new Object[]{order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
                order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid()};
        runner.update(connection, sql, params);
    }

    @Override
    public void saveOrderItem(Connection connection, OrderItem orderItem) throws SQLException {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        QueryRunner runner = new QueryRunner();
        Object[] params = new Object[]{orderItem.getItemid(), orderItem.getQuantity(), orderItem.getTotal(),
                orderItem.getProduct().getPid(), orderItem.getOrder().getOid()};
        runner.update(connection, sql, params);
    }

    @Override
    public int getUserOrderTotalCount(User user) throws SQLException {
        String sql = "select count(*) from orders where uid =?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Long count = (Long) runner.query(sql, new ScalarHandler(), user.getUid());
        return count.intValue();
    }

    @Override
    public List<Order> getUserDaoWithPage(User user, Integer startIndex, Integer pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select * from orders where uid = ? limit ?,?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex,
                pageSize);
        for (Order order : list) {
            String sql2 = "select * from orderitem o ,product p where o.pid = p.pid and o.oid =?";
            List<Map<String, Object>> mapList = runner.query(sql2, new MapListHandler(), order.getOid());

            for (Map<String, Object> map : mapList) {
                //注册转换器
                DateConverter dc = new DateConverter();
                dc.setPattern("yyyy-MM-dd");
                ConvertUtils.register(dc, Date.class);

                OrderItem item = new OrderItem();
                Product product = new Product();
                BeanUtils.populate(item,map );
                BeanUtils.populate(product,map );

                item.setProduct(product);//关联商品
                item.setOrder(order);//关联订单
                order.getList().add(item);
            }
        }
        return list;
    }

    @Override
    public Order getOrderByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select * from orders where oid = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Order order = runner.query(sql, new BeanHandler<Order>(Order.class), oid);
        String sql2 = "select * from orderitem o ,product p where o.pid = p.pid and o.oid =?";
        List<Map<String, Object>> mapList = runner.query(sql2, new MapListHandler(), order.getOid());

        for (Map<String, Object> map : mapList) {
            //注册转换器
            DateConverter dc = new DateConverter();
            dc.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dc, Date.class);

            OrderItem item = new OrderItem();
            Product product = new Product();
            BeanUtils.populate(item,map );
            BeanUtils.populate(product,map );

            item.setProduct(product);//关联商品
            item.setOrder(order);//关联订单
            order.getList().add(item);
        }
        return order;
    }
}
