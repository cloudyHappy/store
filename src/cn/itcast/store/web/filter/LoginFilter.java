package cn.itcast.store.web.filter;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.impl.UserServiceImpl;
import cn.itcast.store.utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
       // System.out.println("servletPaht:"+servletPath+"\ncontextPath:"+request.getContextPath());
        //1.如果是登录页面,则放行
        if(servletPath.startsWith("UserServlet")){
            String method = request.getParameter("method");

            if("loginUI".equals(method)){
                chain.doFilter(request,response);
                return;
            }
        }
        //2.如果已经登录,放行,不需要自动登录
        User loginUser=(User)request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            chain.doFilter(request,response);
            return;
        }

        //3.获得自动登录信息
        Cookie userCookie = CookieUtils.getCookieByName("autoLoginUser", request.getCookies());
        //4.判断自动登录cookie是否存在,如果没有cookie,则不需要自动登录
        if (userCookie == null) {
            chain.doFilter(request,response);
            return;
        }
        //5.通过用户cookie中记录信息,查询用户
        String[] strings = userCookie.getValue().split("@");
        String username = strings[0];
        String password = strings[1];
        System.out.println("开始执行登录");
        //6.执行登录
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.userLogin(username, password);
            if (user == null) {
                //密码已经修改,放行
                chain.doFilter(request,response);
                return;
            }
            //7.自动登录
            request.getSession().setAttribute("loginUser",user);
            chain.doFilter(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("自动登录异常,自动忽略");
        }
    }



}
