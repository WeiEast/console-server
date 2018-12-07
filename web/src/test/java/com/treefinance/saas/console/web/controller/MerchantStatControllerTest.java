package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.enums.EBizTypeEnum;
import com.treefinance.saas.console.common.domain.request.StatRequest;
import com.treefinance.toolkit.util.DateUtils;
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
        statRequest.setBizType(EBizTypeEnum.OPERATOR.getCode());
        statRequest.setStartDate(DateUtils.parse("2018-04-18","yyyy-mm-dd"));
        statRequest.setEndDate(DateUtils.parse("2018-04-19","yyyy-mm-dd"));
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
    public void getOverview() throws Exception {

        this.mockMvc.perform(get(baseUrl + "/stataccess/all/overview?"+"bizType=0&startDate=2018-04-17&endDate=2018" +
                "-04-23" +
                "&statType=1&intervalMins=10&pageNumber=1&pageSize=7&saasEnv=0").contentType
                (MediaType
                        .APPLICATION_JSON).accept
                (MediaType
                        .APPLICATION_JSON));
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