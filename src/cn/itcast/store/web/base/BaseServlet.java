package cn.itcast.store.web.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        Class clz = this.getClass();
        try {
            if (methodName == null || "".equals(methodName.trim())) {
                methodName = "execute";
            }
            Method method = clz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String path = (String) method.invoke(this, request, response);
            if (path != null) {
                //如果返回值不为空,则转发到指定页面
                request.getRequestDispatcher(path).forward(request, response);
            }


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用于子类复写的方法
        return null;
    }
}
