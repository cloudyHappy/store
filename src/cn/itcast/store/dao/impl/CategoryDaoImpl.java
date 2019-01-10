package cn.itcast.store.dao.impl;


import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.domain.Category;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> getAllCategory() throws SQLException {
        String sql = "select * from category";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<Category> list = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
        return list;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String sql = "insert into category values(?,?)";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql,category.getCid(),category.getCname());
    }

    @Override
    public void deleteCategoryByCid(String cid) throws SQLException {
        String sql1 = "update product set cid = null where cid = ?";
        String sql = "delete from category where cid = ?";
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql1,cid );
        runner.update(sql,cid );
    }
}
