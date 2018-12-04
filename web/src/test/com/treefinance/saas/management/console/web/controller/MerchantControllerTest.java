package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.console.common.domain.vo.MerchantBaseVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/3/28 16:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MerchantControllerTest {

    private String baseUrl = "/saas/console/merchant";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMerchantBaseList() throws Exception {
        this.mockMvc.perform(post(baseUrl+"/simple/list").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addMerchant() throws Exception {

        AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
        appBizLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appBizLicenseVO.setBizName("小开心");
        appBizLicenseVO.setBizType((byte)1);
        appBizLicenseVO.setIsValid((byte)1);
        appBizLicenseVO.setIsShowLicense((byte)1);
        appBizLicenseVO.setIsShowLicense((byte)1);
        appBizLicenseVO.setTrafficLimit(new BigDecimal(100));
        appBizLicenseVO.setLicenseTemplate("DEFAULT");

        AppLicenseVO appLicenseVO = new AppLicenseVO();
        appLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appLicenseVO.setAppName("小开心");

        MerchantBaseVO merchantBase = new MerchantBaseVO();
        merchantBase.setAppBizLicenseVOList(Arrays.asList(appBizLicenseVO));
        merchantBase.setAppId("test_MIASDJ1JBI1EC7IU");
        merchantBase.setAppLicenseVO(appLicenseVO);
        merchantBase.setAppName("小开心");
        merchantBase.setBussiness("放款");
        merchantBase.setBussiness2("理财");
        merchantBase.setChName("天行国际");
        merchantBase.setContactPerson("程通");
        merchantBase.setCompany("大树金融");
        merchantBase.setContactValue("18258265028");
        this.mockMvc.perform(post(baseUrl+"/add").content(JSON.toJSONString(merchantBase)).contentType(MediaType.APPLICATION_JSON)
                .accept
                (MediaType
                .APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateMerchant() throws Exception {

        AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
        appBizLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appBizLicenseVO.setBizName("小开心");
        appBizLicenseVO.setBizType((byte)1);
        appBizLicenseVO.setIsValid((byte)1);
        appBizLicenseVO.setIsShowLicense((byte)1);
        appBizLicenseVO.setIsShowLicense((byte)1);
        appBizLicenseVO.setTrafficLimit(new BigDecimal(100));
        appBizLicenseVO.setLicenseTemplate("DEFAULT");

        AppLicenseVO appLicenseVO = new AppLicenseVO();
        appLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appLicenseVO.setAppName("小开心");

        MerchantBaseVO merchantBase = new MerchantBaseVO();
        merchantBase.setAppBizLicenseVOList(Arrays.asList(appBizLicenseVO));
        merchantBase.setAppId("test_MIASDJ1JBI1EC7IU");
        merchantBase.setAppLicenseVO(appLicenseVO);
        merchantBase.setAppName("小开心");
        merchantBase.setBussiness("放款");
        merchantBase.setBussiness2("理财");
        merchantBase.setChName("天行国际");
        merchantBase.setContactPerson("程通");
        merchantBase.setCompany("大树金融");
        merchantBase.setContactValue("18258265028");


        this.mockMvc.perform(post(baseUrl+"/add").content(JSON.toJSONString(merchantBase)).accept(MediaType.APPLICATION_JSON)
                .contentType
                (MediaType
                .APPLICATION_JSON));
    }
    @Test
    public void getMerchantList() throws Exception {
        this.mockMvc.perform(post(baseUrl+"/list").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMerchantById() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/get/171621292888698880").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void resetPassword() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/reset/pwd/44026148916563977").accept(MediaType.APPLICATION_JSON));
    }
    @Test
    public void resetKey() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/reset/key/44026148916563977").accept(MediaType.APPLICATION_JSON));
    }
    @Test
    public void queryAllEcommerceMonitor() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/stat/merchant/list?bizType=3").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void toggleMerchant() throws Exception {
        MerchantBaseVO merchantBase = new MerchantBaseVO();
        merchantBase.setAppId("test_6u0idX7yCWzA0JKD");
        merchantBase.setIsActive((byte)1);

        this.mockMvc.perform(post(baseUrl+"/toggle/active").content(JSON.toJSONString(merchantBase)).accept(MediaType
                .APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }


}