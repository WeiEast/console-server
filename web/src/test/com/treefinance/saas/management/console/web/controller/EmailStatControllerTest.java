package com.treefinance.saas.management.console.web.controller;

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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author chengtong
 * @date 18/4/20 18:48
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailStatControllerTest {

    private String baseUrl = "/saas/console/email";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }
//    /monitor/list
    @Test
    public void queryEmailMonitorDayAccessList() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/monitor/list?startDate=2018-04-14&endDate=2018-04-20&pageNumber=1" +
                "&pageSize=7&statType=1&appId=virtual_total_stat_appId&email=virtual_total_stat_email").accept
                (MediaType.APPLICATION_JSON));
    }

    @Test
    public void queryEmailMonitorDayAccessListDetail() {
    }

    @Test
    public void queryEmailMonitorSupportList() {
    }
}