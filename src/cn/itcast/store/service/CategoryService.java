package cn.itcast.store.service;

import cn.itcast.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类模块service层接口
 */
public interface CategoryService {
    /**
     * 获取所有的分类
     *
     * @return
     * @throws SQLException
     */
    List<Category> getAllCategory()throws SQLException;

    /**
     * 添加分类
     * @param category
     * @throws SQLException
     */
    void addCategory(Category category) throws SQLException;

    /**
     * 根据分类id删除分类
     * @param cid
     */
    void deleteCategoryByCid(String cid) throws SQLException;
}
