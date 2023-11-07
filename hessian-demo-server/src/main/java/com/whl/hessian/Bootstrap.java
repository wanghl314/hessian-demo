package com.whl.hessian;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class Bootstrap {
    private static final int PORT = 8080;

    private static final String URI_ENCODING = "UTF-8";

    private static final String CONTEXT_PATH = "/hessian";

    private static final String WEBAPP = "src/main/webapp";

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        File baseDir = createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());

        Connector connector = new Connector();
        connector.setPort(PORT);
        connector.setURIEncoding(URI_ENCODING);

        tomcat.getService().addConnector(connector);

        File docBase = new File(WEBAPP);
        tomcat.addWebapp(CONTEXT_PATH, docBase.getAbsolutePath());

        tomcat.getHost().setAutoDeploy(false);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static File createTempDir(String prefix) throws IOException {
        File tempDir = File.createTempFile(prefix + ".", "." + PORT);
        tempDir.delete();
        tempDir.mkdir();
        tempDir.deleteOnExit();
        return tempDir;
    }

}
