package com.treefinance.saas.console.util;

import com.treefinance.toolkit.util.http.client.MoreHttp;
import com.treefinance.toolkit.util.http.client.MoreHttpFactory;
import com.treefinance.toolkit.util.http.exception.HttpException;

/**
 * 数据下载器
 */
public final class RemoteDataUtils {

    private static final MoreHttp CLIENT = MoreHttpFactory.createCustom();

    private RemoteDataUtils() {
    }

    public static <T> T download(String url, Class<T> clazz) throws HttpException {
        return CLIENT.get(url, clazz);
    }

}
