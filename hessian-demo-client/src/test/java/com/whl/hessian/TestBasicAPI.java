package com.whl.hessian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.junit.jupiter.api.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.whl.hessian.api.BasicAPI;

public class TestBasicAPI {

    @Test
    public void testHessian() throws MalformedURLException {
        String url = "http://127.0.0.1:8080/hessian/test";

        HessianProxyFactory factory = new HessianProxyFactory();
        BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, url);

        String result = basic.hello("world");
        assertEquals("Hello, world", result);
    }

    @Test
    public void testManual () throws IOException {
        String url = "http://127.0.0.1:8080/hessian/test";

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "x-application/hessian");
        conn.setRequestProperty("Accept-Encoding", "deflate");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        OutputStream os = conn.getOutputStream();
        String method = "hello";
        String value = "world";

        // startCall
        os.write('c');
        os.write(2);
        os.write(0);

        int len = method.length();
        os.write('m');
        os.write(len >> 8);
        os.write(len);

        // printString method
        for (int i = 0; i < len; i++) {
            char ch = method.charAt(i);
            os.write(ch);
        }

        // writeString
        int length = value.length();
        os.write('S');
        os.write(length >> 8);
        os.write(length);

        // printString args
        for (int i = 0; i < length; i++) {
            char ch = value.charAt(i);
            os.write(ch);
        }

        // completeCall
        os.write('z');
        os.flush();
        InputStream is = conn.getInputStream();

        if ("deflate".equals(conn.getContentEncoding())) {
            is = new InflaterInputStream(is, new Inflater(true));
        }
        int code = is.read();

        if (code == 'H') {
            int major = is.read();
            int minor = is.read();

            int tag = is.read();

            if (tag == 'R') {
                tag = is.read();// reply string length
                StringBuilder sb = new StringBuilder();
                int ch;

                while ((ch = is.read()) >= 0) {
                    sb.append((char)ch);
                }
                System.out.println(sb);
            }
        } else if (code == 'r') {

        } else {

        }
    }

    @Test
    public void createPostmanBody() throws IOException {
        // c20m05helloS05worldz
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\aaa")) {
            fos.write(99);
            fos.write(2);
            fos.write(0);
            fos.write(109);
            fos.write(0);
            fos.write(5);
            fos.write(104);
            fos.write(101);
            fos.write(108);
            fos.write(108);
            fos.write(111);
            fos.write(83);
            fos.write(0);
            fos.write(5);
            fos.write(119);
            fos.write(111);
            fos.write(114);
            fos.write(108);
            fos.write(100);
            fos.write(122);
            fos.flush();
        }
    }

}
