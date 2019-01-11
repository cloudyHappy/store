package cn.itcast.store.service.impl;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.impl.ProductDaoImpl;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    @Override
    public List<Product> getNewProduct() throws SQLException, InvocationTargetException, IllegalAccessException {
        return productDao.getNewProduct();
    }

    @Override
    public List<Product> getHotProduct() throws SQLException, InvocationTargetException, IllegalAccessException {
        return productDao.getHotProduct();
    }

    @Override
    public Product getProductById(String pid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return productDao.getProductById(pid);
    }

    @Override
    public PageModel<Product> getProductByCidWithPage(String cid, Integer currentPage, Integer pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {

        Integer totalRecords = productDao.getTotalRecordsByCid(cid);
        PageModel<Product> pageModel = new PageModel<>(currentPage,totalRecords ,pageSize );
        pageModel.setData(productDao.getProductByCidWithPage(cid,pageModel.getStartIndex(),pageSize));
        return pageModel;
    }

    @Override
    public PageModel<Product> getProductWithPage(Integer currentPage, Integer pageSize) throws SQLException {
        int totalCount = productDao.getTotalRecores();
        PageModel<Product> pageModel = new PageModel<>(currentPage, totalCount,pageSize);
        List<Product> list = productDao.getProductWithPage(pageModel.getStartIndex(),pageModel.getPageSize());
        pageModel.setData(list);
        return pageModel;
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        productDao.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        productDao.updateProduct(product);
    }

    @Override
    public void lowerSelf(String pid) throws SQLException {
        productDao.lowerSelf(pid);
    }

    @Override
    public List<Product> getLowerSelfProduct() throws SQLException {
        return productDao.getAllLowerSelf();
    }

    @Override
    public void upperSelfByPid(String pid) throws SQLException {
        productDao.upperSelfByPid(pid);
    }
}
