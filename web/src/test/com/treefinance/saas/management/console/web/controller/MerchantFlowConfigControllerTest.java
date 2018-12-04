package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.knife.request.PageRequest;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/4/11 16:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MerchantFlowConfigControllerTest {

    private String baseUrl = "/saas/console/merchant/flow/";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getList() throws Exception {
        this.mockMvc.perform(get(baseUrl + "list").contentType(MediaType.APPLICATION_JSON).accept(MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void batchUpdate() throws Exception {
        List<MerchantFlowConfigVO> list = new ArrayList<>();
        MerchantFlowConfigVO vo = new MerchantFlowConfigVO();
        vo.setAppName("appName1");
        vo.setId(107479404090781696L);
        vo.setServiceTag("tag1");
        vo.setServiceTagName("tagName1");

        list.add(vo);

        this.mockMvc.perform(post(baseUrl + "update").content(JSON.toJSONString(list)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType
                .APPLICATION_JSON));
    }
    @Test
    public void getAllotList() throws Exception {
        PageRequest request = new PageRequest();

        request.setPageNumber(1);
        request.setPageSize(20);


        this.mockMvc.perform(post(baseUrl + "/allot/list").content(JSON.toJSONString(request)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType
                .APPLICATION_JSON));
    }
    @Test
    public void init1() throws Exception {
        this.mockMvc.perform(get(baseUrl + "init").contentType(MediaType.APPLICATION_JSON).accept(MediaType
                .APPLICATION_JSON));
    }
}