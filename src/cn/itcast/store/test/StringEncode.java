package cn.itcast.store.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class StringEncode {
    public static void main(String[] args) throws Exception {
        String message = "我喜欢你是我独家的记忆,埋在心里不管别人说的多么动听!";
        String encode = URLEncoder.encode(message, "UTF-8");
        File file = new File("C:\\Users\\Administrator\\Desktop\\test.txt");
        OutputStream  out = new FileOutputStream(file);

        byte[] data = encode.getBytes();

        out.write(data);

        out.close();
    }


}
