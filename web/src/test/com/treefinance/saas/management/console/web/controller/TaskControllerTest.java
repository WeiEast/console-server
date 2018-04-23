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
 * @date 18/4/20 16:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskControllerTest {

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
    public void taskList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "tasks?taskId=171655444996435968&type=ECOMMERCE&startDate=2018-01-20" +
                "&endDate=2018-04-20").contentType
                (MediaType
                        .APPLICATION_JSON).accept
                (MediaType
                        .APPLICATION_JSON));
    }

    @Test
    public void task() {
    }
}