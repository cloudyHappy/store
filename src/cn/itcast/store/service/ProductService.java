package cn.itcast.store.service;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    /**
     * 获取最新商品
     * @return
     * @throws SQLException
     */
    public List<Product> getNewProduct() throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 获取热门商品
     * @return
     * @throws SQLException
     */
    public List<Product> getHotProduct() throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据id获取商品
     * @param pid
     * @return
     */
    Product getProductById(String pid) throws IllegalAccessException, SQLException, InvocationTargetException;

    /**
     * 根据分类id,分页进行获取商品
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageModel<Product> getProductByCidWithPage(String cid, Integer currentPage, Integer pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageModel<Product> getProductWithPage(Integer currentPage, Integer pageSize) throws SQLException;

    /**
     * 添加商品
     * @param product
     */
    void addProduct(Product product) throws SQLException;
}
