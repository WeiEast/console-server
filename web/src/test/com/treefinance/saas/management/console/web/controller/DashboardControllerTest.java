package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.common.domain.request.DashboardRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/9/13 11:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DashboardControllerTest {

    private String baseUrl = "/saas/console/";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getDashboard() throws Exception {

        DashboardRequest request = new DashboardRequest();

        request.setBizType((byte)3);
        request.setSaasEnv((byte)1);

        this.mockMvc.perform(post(baseUrl + "/dashboard").content(JSON.toJSONString(request)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType.APPLICATION_JSON));

    }
}