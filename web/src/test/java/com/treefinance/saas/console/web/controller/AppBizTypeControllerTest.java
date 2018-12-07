package com.treefinance.saas.console.web.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author chengtong
 * @date 18/4/11 15:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppBizTypeControllerTest {

    private String baseUrl = "/saas/console/biz/";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBizList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "list").contentType(MediaType.APPLICATION_JSON).accept(MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void getBizList1() throws Exception {

        this.mockMvc.perform(get(baseUrl + "list/test_MIASDJ1JBI1EC7IU").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMerchantBaseList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "task/list").contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMerchantBaseAccessList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "task/access/list").contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMerchantBaseDetailAccessList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "task/access/detail/list").contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON));
    }
}