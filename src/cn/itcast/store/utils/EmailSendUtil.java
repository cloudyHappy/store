package cn.itcast.store.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class EmailSendUtil {
    private static String host; // smtp服务器
    private static String from; // 发件人地址
    private static String user; // 用户名===>与发件人地址相同
    private static String password; // 163的授权码

    //private static String[] TOS = new String[]{"2430879746@qq.com"};
    static {
        Properties p = new Properties();
        try {
            String path = EmailSendUtil.class.getClassLoader().getResource("email-config.properties").toString().substring(6);
            System.out.println(path);
            p.load(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        host = p.getProperty("host");
        from = p.getProperty("from");
        user = p.getProperty("user");
        password = p.getProperty("password");
    }

    public static void main(String[] args) {
        URL resource = EmailSendUtil.class.getClassLoader().getResource("email-config.properties");
        System.out.println(resource);
    }

    /**
     *
     * @param title 邮件标题
     * @param context 邮件内容
     * @param TOS 目标数组,可以是多个邮箱
     */
    public static void send(String title,String context, String[] TOS) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.auth", "true");  //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        Session session = Session.getDefaultInstance(props);//用props对象构建一个session
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);//用session为参数定义消息对象
        try {
            message.setFrom(new InternetAddress(from));// 加载发件人地址
            InternetAddress[] sendTo = new InternetAddress[TOS.length]; // 加载收件人地址
            for (int i = 0; i < TOS.length; i++) {
                sendTo[i] = new InternetAddress(TOS[i]);
            }
            message.addRecipients(Message.RecipientType.TO, sendTo);
            message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(from));//设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
            message.setSubject(title);//加载标题
           /* Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件

            BodyPart contentPart = new MimeBodyPart();//设置邮件的文本内容
            contentPart.setText(context);
            multipart.addBodyPart(contentPart);
            message.setContent(multipart,"text/html;charset=utf-8;");//将multipart对象放到message中*/
            message.setContent(context,"text/html;charset=UTF-8");
            message.saveChanges(); //保存邮件
            Transport transport = session.getTransport("smtp");//发送邮件
            transport.connect(host, user, password);//连接服务器的邮箱
            transport.sendMessage(message, message.getAllRecipients());//把邮件发送出去
            transport.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
