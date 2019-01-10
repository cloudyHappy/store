package cn.itcast.store.dao.impl;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.domain.Product;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {

    private QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());

    @Override
    public List<Product> getHotProduct() throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select p.pid,p.pname,p.market_price,p.shop_price,p.pimage,p.pdate,p.is_hot,p.pdesc,p.pflag,\n" +
                "c.cid,c.cname\n" +
                "from product p \n" +
                "inner join category c \n" +
                "on p.cid = c.cid\n" +
                "where pflag = 0 and is_hot = 1 \n" +
                "order by pdate desc \n" +
                "limit 9;";
        List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler());
        List<Product> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Product p = new Product();
            BeanUtils.populate(p, map);
            p.getCategory().setCid((String) map.get("cid"));
            p.getCategory().setCname((String) map.get("cname"));
            list.add(p);
        }
        return list;
    }

    @Override
    public List<Product> getNewProduct() throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select p.pid,p.pname,p.market_price,p.shop_price,p.pimage,p.pdate,p.is_hot,p.pdesc,p.pflag,\n" +
                "c.cid  ,c.cname \n" +
                "from product p \n" +
                "inner join category c \n" +
                "on p.cid = c.cid  \n" +
                "where p.pflag = 0 \n" +
                "order by p.pdate desc \n" +
                "limit 0,9;";
        List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler());
        List<Product> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Product p = new Product();
            BeanUtils.populate(p, map);
            p.getCategory().setCid((String) map.get("cid"));
            p.getCategory().setCname((String) map.get("cname"));
            list.add(p);
        }
//        List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class));
        return list;
    }

    @Override
    public Product getProductById(String pid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select p.pid,p.pname,p.market_price,p.shop_price,p.pimage,p.pdate,p.is_hot,p.pdesc,p.pflag,c" +
                ".cid,c.cname from product p inner join category c  on p.cid = c.cid where p.pid = ?";
        Map<String, Object> map = runner.query(sql, new MapHandler(), pid);
        Product product = new Product();
        BeanUtils.populate(product,map );
        product.getCategory().setCid((String)map.get("cid"));
        product.getCategory().setCname((String)map.get("cname"));
        return product;
    }

    public Integer getTotalRecordsByCid(String cid) throws SQLException {
        String sql = "select count(1) from product where cid = ?";
        Long totalRecords = (Long)runner.query(sql, new ScalarHandler(),cid);
        return totalRecords.intValue();
    }

    public List<Product> getProductByCidWithPage(String cid, Integer startIndex, Integer pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select * from product  where cid = ? order by pdate desc limit ?,?";
        List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), cid, startIndex, pageSize);
        List<Product> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Product p = new Product();
            BeanUtils.populate(p, map);
            p.getCategory().setCid((String) map.get("cid"));
            list.add(p);
        }
        return list;
    }

    @Override
    public int getTotalRecores() throws SQLException {
        String sql = "select count(*) from product";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        Long query = (Long) runner.query(sql, new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Product> getProductWithPage(Integer currentPage, Integer pageSize) throws SQLException {
        String sql = "select * from product order by pdate desc limit ?,?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), currentPage, pageSize);
        return list;
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{product.getPid(),product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc()
                ,product.getPflag(),product.getCid()};
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql,params );

    }
}
