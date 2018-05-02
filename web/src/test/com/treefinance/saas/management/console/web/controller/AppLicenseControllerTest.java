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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/4/24 15:10
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppLicenseControllerTest {

    private String baseUrl = "/saas/console/applicense";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getApplicenseByAppId() {
    }

    @Test
    public void generate() {
    }

    @Test
    public void generate1() {
    }

    @Test
    public void getKeyList() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/list").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void initHistorySecretKey() {
    }
}