package cn.itcast.store.dao;

import cn.itcast.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类模块的DAO
 */
public interface CategoryDao {
    /**
     * 获取所有的category
     * @return
     */
    List<Category> getAllCategory()throws SQLException;

    void addCategory(Category category) throws SQLException;

    /**
     * 根据分类id删除分类
     * @param cid
     */
    void deleteCategoryByCid(String cid) throws SQLException;
}
