package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.impl.CategoryServiceImpl;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 通过ajax获取分类信息
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getAllCategory(HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException, IOException {

        //先从redis从取数据,如果没有就从数据库中取

        Jedis jedis = null;
        String value = null;
        try {
            jedis = JedisUtils.getJedis();
            value = jedis.get("all_category_list");
            if (value == null) {
                //缓存中没有数据,从数据库中读取分类数据
                System.out.println("缓存中没有数据,从数据库中读取分类数据");
                List<Category> list = null;
                list = categoryService.getAllCategory();
                //转json,并添加缓存
                value = JSONArray.fromObject(list).toString();
                jedis.set("all_category_list", value);
            }else{
                System.out.println("从redis中读取数据================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭jedis
            JedisUtils.closeJedis(jedis);
        }
        //响应数据到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(value);
        //System.out.println(jsonList);
        return null;
    }
}
