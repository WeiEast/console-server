package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest;
import com.treefinance.saas.management.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest;
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
 * @date 18/6/1 10:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlarmRecordControllerTest {

    private String baseUrl = "/saas/console/alarm";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void queryAlarmList() throws Exception {

        AlarmRecordRequest alarmRecordRequest = new AlarmRecordRequest();

        this.mockMvc.perform(post(baseUrl+"/record/list").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType
                .APPLICATION_JSON));

    }

    @Test
    public void queryWorkOrderList() throws Exception {
        AlarmWorkOrderRequest alarmRecordRequest = new AlarmWorkOrderRequest();

        this.mockMvc.perform(post(baseUrl+"/workOrder/list").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void queryWorkOrderHistory() throws Exception {

        this.mockMvc.perform(post(baseUrl+"/workOrder/history/186791617926033408").accept(MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void querySaasWorkerList() throws Exception {

        this.mockMvc.perform(post(baseUrl+"/saasWorker/list").accept(MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void updateWorkOrderProcessor() throws Exception {
        UpdateWorkOrderRequest alarmRecordRequest = new UpdateWorkOrderRequest();

        alarmRecordRequest.setId(186791617926033409L);
        alarmRecordRequest.setProcessorName("程通");

        this.mockMvc.perform(post(baseUrl+"/workOrder/update/processor").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateWorkOrderStatus() throws Exception {
        UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();

        request.setId(186791617926033409L);
        request.setStatus(2);
        request.setProcessorName("程通");

        this.mockMvc.perform(post(baseUrl+"/workOrder/update/status").content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }
}