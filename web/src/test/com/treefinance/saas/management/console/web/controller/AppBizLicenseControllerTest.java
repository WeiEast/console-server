package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/4/11 11:18
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppBizLicenseControllerTest {

    private String baseUrl = "/saas/console/bizlicense/";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void selectBizLicenseByAppIdBizType() throws Exception {
        AppBizLicenseRequest request = new AppBizLicenseRequest();

        request.setAppId("test_MIASDJ1JBI1EC7IU");

        this.mockMvc.perform(post(baseUrl + "select").contentType(MediaType.APPLICATION_JSON).
                content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void selectQuotaByAppIdBizType() throws Exception {

        AppBizLicenseRequest request = new AppBizLicenseRequest();
        request.setAppId("test_MIASDJ1JBI1EC7IU");
        this.mockMvc.perform(post(baseUrl + "quota/select").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void selectTrafficByAppIdBizType() throws Exception {
        AppBizLicenseRequest request = new AppBizLicenseRequest();
        request.setAppId("test_MIASDJ1JBI1EC7IU");
        this.mockMvc.perform(post(baseUrl + "traffic/select").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateAppBizLicense() throws Exception {
        AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();

        appBizLicenseVO.setLicenseTemplate("DEFAULT");
        appBizLicenseVO.setTrafficLimit(new BigDecimal(99));
        appBizLicenseVO.setIsShowLicense((byte) 1);
        appBizLicenseVO.setBizName("");
        appBizLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appBizLicenseVO.setDailyLimit(0);
        appBizLicenseVO.setBizType((byte) 1);
        appBizLicenseVO.setIsValid((byte) 1);

        this.mockMvc.perform(post(baseUrl + "update").contentType(MediaType.APPLICATION_JSON).content(JSON
                .toJSONString(appBizLicenseVO)).accept
                (MediaType
                        .APPLICATION_JSON));
    }

    @Test
    public void updateQuota() throws Exception {

        AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();

        appBizLicenseVO.setLicenseTemplate("DEFAULT");
        appBizLicenseVO.setTrafficLimit(new BigDecimal(99));
        appBizLicenseVO.setIsShowLicense((byte) 1);
        appBizLicenseVO.setBizName("");
        appBizLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appBizLicenseVO.setDailyLimit(12);
        appBizLicenseVO.setBizType((byte) 1);
        appBizLicenseVO.setIsValid((byte) 1);


        this.mockMvc.perform(post(baseUrl + "quota/update").contentType(MediaType.APPLICATION_JSON).content(JSON
                .toJSONString(appBizLicenseVO)).accept
                (MediaType
                        .APPLICATION_JSON));
    }

    @Test
    public void updateTraffic() throws Exception {

        AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();

        appBizLicenseVO.setLicenseTemplate("DEFAULT");
        appBizLicenseVO.setTrafficLimit(new BigDecimal(99));
        appBizLicenseVO.setIsShowLicense((byte) 1);
        appBizLicenseVO.setBizName("");
        appBizLicenseVO.setAppId("test_MIASDJ1JBI1EC7IU");
        appBizLicenseVO.setDailyLimit(12);
        appBizLicenseVO.setBizType((byte) 1);
        appBizLicenseVO.setIsValid((byte) 1);


        this.mockMvc.perform(post(baseUrl + "traffic/update").contentType(MediaType.APPLICATION_JSON).content(JSON
                .toJSONString(appBizLicenseVO)).accept
                (MediaType
                        .APPLICATION_JSON));
    }
}