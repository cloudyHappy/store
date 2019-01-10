package cn.itcast.store.test;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.impl.ProductDaoImpl;
import cn.itcast.store.domain.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class Test_runProductDao {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoImpl();
        try {
            List<Product> hotProduct = productDao.getHotProduct();
            for (Product product : hotProduct) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
