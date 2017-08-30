package com.treefinance.saas.management.console.common.domain.vo.rawdata;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/30.
 */
public class ProxyDetailsVO implements Serializable {
    private static final long serialVersionUID = -2121294301139434677L;

    private String nodeName; // 节点实例名
    private String proxy; // 代理IP，有可能ip-tables映射后的IP
    private String realProxy; // 申请到的真实代理IP
    private String type; // 类型，偏向于代理类型，比如:http, socks4, socks5
    private int used; // 使用次数
    private int failure;  // 失败次数
    private Date serviceTime; // 最近一次服务时间
    private Date nextServiceTime; // 下次服务时间
    private Date registerTime;  // 注册时间
    private String status;//状态

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getRealProxy() {
        return realProxy;
    }

    public void setRealProxy(String realProxy) {
        this.realProxy = realProxy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Date getNextServiceTime() {
        return nextServiceTime;
    }

    public void setNextServiceTime(Date nextServiceTime) {
        this.nextServiceTime = nextServiceTime;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
