package com.whl.hessian.api;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String upload(String name, InputStream is) throws IOException;

    InputStream download(String name) throws IOException;
}
