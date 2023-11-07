package com.whl.hessian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;

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

}
