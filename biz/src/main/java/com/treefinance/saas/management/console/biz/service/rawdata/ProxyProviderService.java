package com.treefinance.saas.management.console.biz.service.rawdata;


/**
 * Created by haojiahong on 2017/8/30.
 */
public interface ProxyProviderService {

    /**
     * 站点下拉接口
     *
     * @return
     */
    String queryUserList();

    /**
     * 列表及状态信息
     *
     * @param user 选择的站点
     * @return
     */
    String queryProxyCat(String user);
}
