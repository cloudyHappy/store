package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.impl.CategoryServiceImpl;
import cn.itcast.store.service.impl.ProductServiceImpl;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
    /**
     * 分页获取商品
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getAllProductWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        ProductService productService = new ProductServiceImpl();
        Integer pageSize = 10;
        PageModel<Product> pageModel = null;
        try {
            pageModel = productService.getProductWithPage(currentPage, pageSize);
            pageModel.setUrl("AdminProductServlet?method=getAllProductWithPage");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("pageModel", pageModel);

        return "/admin/product/list.jsp";
    }

    //跳转添加商品页面
    public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        try {
            List<Category> list = categoryService.getAllCategory();
            request.setAttribute("categoryList", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/admin/product/add.jsp";
    }

    //添加商品
    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(1024 * 1024 * 3);//设置缓冲区大小
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");//解决中文文件名上传乱码
        fileUpload.setSizeMax(1024 * 1024 * 10);//设置表单所有文件项的大小
        fileUpload.setFileSizeMax(1024 * 1024 * 5);//设置单个文件的大小
        Map<String, Object> map = new HashMap<>();
        Product product = new Product();
        String pimage = null;
        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {//如果是表单普通字段
                    map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));//解决中文乱码
                } else {
                    pimage = this.uploadFileMethod(fileItem);
                }
            }
            BeanUtils.populate(product, map);
            //填充属性
            product.setPid(UUIDUtils.getId());
            product.setPimage("upload/" + pimage);
            product.setPdate(new Date());
            product.setPflag(0);//设置上架
            ProductService productService = new ProductServiceImpl();
            productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(product);
        response.sendRedirect("AdminProductServlet?method=getAllProductWithPage&currentPage=1");
        return null;
    }

    public String uploadFileMethod(FileItem fileItem) throws IOException {
        String fileName = fileItem.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String uploadPath = this.getServletContext().getRealPath("/upload/");
        String pimage = UUIDUtils.getId() + suffix;
        File file = new File(uploadPath, pimage);
        if (file.exists()) {
            //如果文件不存在,就创建文件
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);
        InputStream in = fileItem.getInputStream();
        IOUtils.copy(in, out);
        return pimage;
    }

    public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        try {
            Product product = productService.getProductById(pid);
            request.setAttribute("product", product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/product/edit.jsp";
    }

    public String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(1024 * 1024 * 3);//设置缓冲区大小
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");//解决中文文件名上传乱码
        fileUpload.setSizeMax(1024 * 1024 * 10);//设置表单所有文件项的大小
        fileUpload.setFileSizeMax(1024 * 1024 * 5);//设置单个文件的大小
        Map<String, Object> map = new HashMap<>();
        Product product = new Product();
        String pimage = null;
        boolean isUploadNewFile = false;
        String cid = null;
        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {//如果是表单普通字段
                    map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));//解决中文乱码
                    if (fileItem.getFieldName().equals("cid")) {
                        cid = fileItem.getString("UTF-8");
                    }
                } else {
                    if (fileItem.getSize() > 0) {
                        //如果有上传文件
                        isUploadNewFile = true;
                        pimage = this.uploadFileMethod(fileItem);
                    }
                    /**/
                }
            }
            BeanUtils.populate(product, map);
            //填充属性
            if (isUploadNewFile) {//如果有上传新文件
                product.setPimage("upload/" + pimage);
            }
            product.getCategory().setCid(cid);
            ProductService productService = new ProductServiceImpl();
            productService.updateProduct(product);
            //  productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(product);
        response.sendRedirect("AdminProductServlet?method=getAllProductWithPage&currentPage=1");
        return null;
    }

    /**
     * 下架商品
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String lowerShelf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        try {
            productService.lowerSelf(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("AdminProductServlet?method=getAllProductWithPage&currentPage=1");
        return null;
    }

    /**
     * 获取下架的商品
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getLowerSelfProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductServiceImpl();
        try {
            List<Product> list = productService.getLowerSelfProduct();
            System.out.println(list);
            request.setAttribute("productList", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/admin/product/pushDown_list.jsp";
    }

    public String upperSelf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        try {
            productService.upperSelfByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("AdminProductServlet?method=getLowerSelfProduct");
        return null;
    }
}
