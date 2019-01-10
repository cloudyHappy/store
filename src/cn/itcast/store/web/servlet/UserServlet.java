package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.impl.UserServiceImpl;
import cn.itcast.store.utils.EmailSendUtil;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService = new UserServiceImpl();

    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String register(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        //进行注册
        System.out.println("UserServlet.register()================" + request.getMethod());
        Map<String, String[]> parameterMap = request.getParameterMap();
        /*for (Map.Entry<String, String[]> stringEntry : parameterMap
        .entrySet()) {
            System.out.println(stringEntry.getKey()+":"+stringEntry.getValue
            ()[0]);
        }*/
        User user = new User();
        try {
            //处理日期格式注入bean
            DateConverter dc = new DateConverter();
            dc.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dc, java.util.Date.class);
            //填充属性
            BeanUtils.populate(user, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setUid(UUIDUtils.getId());
        user.setCode(UUIDUtils.getUUID64());//激活码
        user.setState(0);//未激活
        //检查用户信息
        System.out.println(user);
        //开始注册

        try {
            userService.register(user);
            String url = request.getRequestURL().substring(0,
                    request.getRequestURL().toString().lastIndexOf("/")).toString();
            String activatePath = url + "/UserServlet?method=activate" +
                    "&activateCode=" + user.getCode();
            String activeCode = "<h1>您已经注册了黑马网上商城,如需使用,请点击下面链接进行激活," +
                    "<span style='color:red'>如不是本人操作,请您无视!</span></h1>" +
                    "<br/><a href='" + activatePath + "'>" + activatePath +
                    "</a>";
            EmailSendUtil.send("激活码", activeCode,
                    new String[]{user.getEmail()});
            request.setAttribute("message", "注册成功!请登录邮箱激活后重新登录!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "注册失败!请重试");
        }


        return "jsp/info.jsp";
    }

    /**
     * 账号激活
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String activate(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.activate()=================");
        String z = "";
        String activateCode = request.getParameter("activateCode");
        boolean flag = false;
        try {
            flag = userService.activate(activateCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!flag) {
            request.setAttribute("message", "激活失败,请重试!");
            return "jsp/info.jsp";
        } else {
            request.setAttribute("message", "用户激活成功,请登录!");
            return "jsp/login.jsp";
        }
    }

    /**
     * 跳转至注册界面
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String registerUI(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        //跳转注册界面
        System.out.println("UserServlet.registerUI()===================");
        return "jsp/register.jsp";
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException
            , IOException {
        System.out.println("Userservlet.login()========");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userService.userLogin(username, password);
            //登录成功!

            //判断是否自动登录
            String autoLogin = request.getParameter("autoLogin");
            if("1".equals(autoLogin)){
                Cookie loginCookie = new Cookie("autoLoginUser",user.getUsername()+"@"+user.getPassword() );
                loginCookie.setPath("/");//设置当前项目所有路径都携带cookie
                loginCookie.setMaxAge(60*60*24*7);//设置cookie失效时间为7天
                response.addCookie(loginCookie);
            }else {
                //清除之前的cookie
                Cookie loginCookie = new Cookie("autoLoginUser", null);
                loginCookie.setPath("/");
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
            }
            //判断是否记住用户名

            String rememberUserName = request.getParameter("rememberUserName");
            if("1".equals(rememberUserName)){
                System.out.println("添加用户名cookie================");
                Cookie cookie = new Cookie("rememberUserName", user.getUsername());
                cookie.setPath("/store/UserServlet");
                cookie.setMaxAge(60*60*24*7);
                response.addCookie(cookie);
            }else{
                //如果没有选中,则清除cookie
                System.out.println("删除用户名cookie=============");
                Cookie cookie = new Cookie("rememberUserName", null);
                cookie.setPath("/store/UserServlet");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            //向会话中存储信息,并重定向至首页
            request.getSession().setAttribute("loginUser", user);
            response.sendRedirect("index.jsp");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", e.getMessage());
            return "jsp/login.jsp";
        }
    }

    public String findAll(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet.findAll()===================");
        return null;
    }

    /**
     * 跳转至用户登录界面
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loginUI(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("UserServlet.loginUI()======================");
        return "jsp/login.jsp";
    }

    /**
     * 退出登录,跳转至登录界面
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("UserServlet.logout()=======================");
        request.getSession().removeAttribute("loginUser");
        response.sendRedirect("UserServlet?method=loginUI");
        return null;

    }

    /**
     * check the username whether exists;
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String checkUsername(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("username");
        try {
            boolean flag = userService.existsUserName(username);
            //set response character set;
            response.setContentType("text/plain;charset=utf-8");
            if(flag){//If the username already exists,it return one;
                response.getWriter().print(1);
            }else{
                response.getWriter().print(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
