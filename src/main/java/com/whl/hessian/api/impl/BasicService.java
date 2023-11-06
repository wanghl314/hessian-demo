package com.whl.hessian.api.impl;

import javax.servlet.annotation.WebServlet;

import com.caucho.hessian.server.HessianServlet;
import com.whl.hessian.api.BasicAPI;

@WebServlet("/test")
public class BasicService extends HessianServlet implements BasicAPI {
    private static final long serialVersionUID = 4837876135906291802L;

    @Override
    public String hello(String name) {
        return "Hello, " + name;
    }

}
