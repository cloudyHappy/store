package cn.itcast.store.web.filter;

import cn.itcast.store.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "PrivilegedFilter")
public class PrivilegedFilter implements Filter {
    public void destroy() {
    }

    /**
     * 进行权限校验,如果没有登录,不允许访问购物车,订单项等
     *
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            chain.doFilter(req, resp);
        }else{
            request.setAttribute("message", "请登陆后再进行此操作!");
            request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
