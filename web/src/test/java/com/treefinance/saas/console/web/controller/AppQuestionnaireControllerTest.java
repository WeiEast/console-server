package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.common.domain.request.AppQuestionnaireDetailRequest;
import com.treefinance.saas.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.console.common.domain.request.QueryQuestionnaireRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/8/21 11:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppQuestionnaireControllerTest {

    private String baseUrl = "/saas/console/merchant/questionnaire";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void queryAppQuestionnaire() throws Exception {
        QueryQuestionnaireRequest request = new QueryQuestionnaireRequest();

        this.mockMvc.perform(post(baseUrl + "/list").content(JSON.toJSONString(request)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType.APPLICATION_JSON));

    }

    @Test
    public void addAppQuestionnaire() throws Exception {
        AppQuestionnaireRequest request = new AppQuestionnaireRequest();

        request.setAppId("QATestabcdefghQA");
        request.setBizType((byte)1);
        request.setQuestionnaireCode("code");
        request.setQuestionnaireName("测试");

        request.setStep("导入");

        List<AppQuestionnaireDetailRequest> details = new ArrayList<>();

        AppQuestionnaireDetailRequest detailRequest = new AppQuestionnaireDetailRequest();

        detailRequest.setCategory((byte)1);
        detailRequest.setContent("测试问题");
        detailRequest.setDetailIndex(1);
        details.add(detailRequest);
        request.setDetails(details);

        this.mockMvc.perform(post(baseUrl + "/add").content(JSON.toJSONString(request)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType.APPLICATION_JSON));

    }

    @Test
    public void updateAppQuestionnaire() throws Exception {
        AppQuestionnaireRequest request = new AppQuestionnaireRequest();

        request.setAppId("QATestabcdefghQA");
        request.setBizType((byte)1);
        request.setQuestionnaireCode("code");
        request.setQuestionnaireName("测试");

        request.setStep("导入");

        List<AppQuestionnaireDetailRequest> details = new ArrayList<>();
        AppQuestionnaireDetailRequest detailRequest = new AppQuestionnaireDetailRequest();

        detailRequest.setCategory((byte)1);
        detailRequest.setContent("测试问题-updated");
        detailRequest.setDetailIndex(1);
        details.add(detailRequest);
        request.setDetails(details);

        this.mockMvc.perform(post(baseUrl + "/update").content(JSON.toJSONString(request)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType.APPLICATION_JSON));
    }




}