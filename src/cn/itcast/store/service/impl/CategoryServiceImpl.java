package cn.itcast.store.service.impl;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.impl.CategoryDaoImpl;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAllCategory() throws SQLException {
        return categoryDao.getAllCategory();
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        categoryDao.addCategory(category);
    }

    @Override
    public void deleteCategoryByCid(String cid) throws SQLException {
        categoryDao.deleteCategoryByCid(cid);
    }
}
