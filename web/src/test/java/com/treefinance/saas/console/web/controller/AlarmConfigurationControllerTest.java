package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @author chengtong
 * @date 18/7/19 19:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlarmConfigurationControllerTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String baseUrl = "/saas/console/alarm/config";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }


    @After
    public void after() throws UnsupportedEncodingException {
        logger.info(JSON.toJSONString(result.getResponse().getContentAsString()));
    }


    private MvcResult result;

    @Test
    public void queryAlarmLogList() throws Exception {

    }


}