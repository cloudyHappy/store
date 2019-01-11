package cn.itcast.store.dao;

import cn.itcast.store.domain.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 商品dao层
 */
public interface ProductDao {
    /**
     * 查询热门商品
     * @return
     * @throws SQLException
     */
    public List<Product> getHotProduct() throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 查询最新商品
     * @return
     * @throws SQLException
     */
    public List<Product> getNewProduct() throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据商品id获取商品
     * @param pid
     * @return
     */
    Product getProductById(String pid) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 根据分类,获取分页的总记录数
     * @param cid
     * @return
     */
    Integer getTotalRecordsByCid(String cid) throws SQLException;

    /**
     * 根据分类,分页进行获取商品
     * @param cid
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Product> getProductByCidWithPage(String cid, Integer startIndex, Integer pageSize) throws SQLException,
            InvocationTargetException, IllegalAccessException;

    /**
     * 获取商品总记录数
     * @return
     */
    int getTotalRecores() throws SQLException;

    /**
     * 分页获取商品信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Product> getProductWithPage(Integer currentPage, Integer pageSize) throws SQLException;

    /**
     * 添加商品
     * @param product
     */
    void addProduct(Product product) throws SQLException;

    /**
     * 修改商品信息
     *
     * @param product
     */
    void updateProduct(Product product) throws SQLException;

    /**
     * 下架商品
     *
     * @param pid
     */
    void lowerSelf(String pid) throws SQLException;

    /**
     * 获取下架商品
     *
     * @return
     */
    List<Product> getAllLowerSelf() throws SQLException;

    /**
     * 上架商品
     *
     * @param pid
     */
    void upperSelfByPid(String pid) throws SQLException;
}
