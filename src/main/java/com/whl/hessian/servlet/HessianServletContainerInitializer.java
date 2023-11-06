package com.whl.hessian.servlet;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.WebServlet;

import com.caucho.hessian.server.HessianServlet;

@HandlesTypes(HessianServlet.class)
public class HessianServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        for (Class<?> cls : c) {
            WebServlet annotation = cls.getAnnotation(WebServlet.class);

            if (annotation != null) {
                String[] value = annotation.value();
                String[] urlPatterns = annotation.urlPatterns();

                if (value != null && value.length > 0 && urlPatterns != null && urlPatterns.length > 0) {
                    throw new IllegalArgumentException();
                }
                String servletName = cls.getName();
                ServletRegistration.Dynamic hessianServlet = ctx.addServlet(servletName, new HessianServlet());
                hessianServlet.setInitParameter("service-class", servletName);

                if (value != null && value.length > 0) {
                    hessianServlet.addMapping(value);
                }

                if (urlPatterns != null && urlPatterns.length > 0) {
                    hessianServlet.addMapping(urlPatterns);
                }
            }
        }
    }

}
