package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.utils.DateUtils;
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
 * @date 18/4/19 19:09
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MerchantStatControllerTest {


    private String baseUrl = "/saas/console/merchant/stat";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDays() {
    }

    @Test
    public void getWeeks() {
    }

    @Test
    public void getMonths() {
    }

    @Test
    public void getAll() throws Exception {


        StatRequest statRequest = new StatRequest();
        statRequest.setBizType(EBizType.OPERATOR.getCode());
        statRequest.setStartDate(DateUtils.strToDate("2018-04-18","yyyy-mm-dd"));
        statRequest.setEndDate(DateUtils.strToDate("2018-04-19","yyyy-mm-dd"));
        statRequest.setDateType(1);
        statRequest.setIntervalMins(10);

        this.mockMvc.perform(get(baseUrl + "/stataccess/all?"+"bizType=3&startDate=2018-04-18&endDate=2018-04-19" +
                "&dataType=1&intervalMins=10").contentType
                (MediaType
                .APPLICATION_JSON).accept
                (MediaType
                        .APPLICATION_JSON));

    }

    @Test
    public void getAllPie() {
    }

    @Test
    public void getNumber() {
    }

    @Test
    public void getRate() {
    }

    @Test
    public void getOverview() {
    }

    @Test
    public void getOverviewDetail() {
    }

    @Test
    public void getTaskStepFailInfo() {
    }

    @Test
    public void getSourceTypeList() {
    }
}