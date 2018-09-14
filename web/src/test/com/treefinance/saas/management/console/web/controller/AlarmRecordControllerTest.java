package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest;
import com.treefinance.saas.management.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.management.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/6/1 10:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlarmRecordControllerTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String baseUrl = "/saas/console/alarm";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    private MvcResult result;

    @After
    public void after() throws UnsupportedEncodingException {
        logger.info(JSON.toJSONString(result.getResponse().getContentAsString()));
    }

    @Test
    public void queryAlarmList() throws Exception {

        AlarmRecordRequest alarmRecordRequest = new AlarmRecordRequest();

        result = this.mockMvc.perform(post(baseUrl+"/record/list").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();

    }


    @Test
    public void queryAlarmType() throws Exception {
        result = this.mockMvc.perform(post(baseUrl+"/type/list").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();

    }

    @Test
    public void queryWorkOrderList() throws Exception {
        AlarmWorkOrderRequest alarmRecordRequest = new AlarmWorkOrderRequest();

        result = this.mockMvc.perform(post(baseUrl+"/workOrder/list").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType
                .APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void queryWorkOrderHistory() throws Exception {

        result =  this.mockMvc.perform(post(baseUrl+"/workOrder/history/186791617926033408").accept(MediaType
                .APPLICATION_JSON)).andReturn();
    }

    @Test
    public void querySaasWorkerList() throws Exception {

        result = this.mockMvc.perform(post(baseUrl+"/saasWorker/list").accept(MediaType
                .APPLICATION_JSON)).andReturn();
    }

    @Test
    public void updateWorkOrderProcessor() throws Exception {
        UpdateWorkOrderRequest alarmRecordRequest = new UpdateWorkOrderRequest();

        alarmRecordRequest.setId(188973703973859328L);
        alarmRecordRequest.setProcessorName("程通");

        result = this.mockMvc.perform(post(baseUrl+"/workOrder/update/processor").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void updateWorkOrderStatus() throws Exception {
        UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();

        request.setId(188973703973859328L);
        request.setStatus(2);
        request.setProcessorName("程通");
//        request.setOpName();

        result = this.mockMvc.perform(post(baseUrl+"/workOrder/update/status").content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void queryAlarmListAndhandleMessge()
    {
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yy-mm-dd HH-MM-SS");
        AlarmRecordRequest alarmRecordRequest = new AlarmRecordRequest();
        alarmRecordRequest.setAlarmType("operator_alarm");
        alarmRecordRequest.setStartTime("2018-06-15 14:23:11");
        alarmRecordRequest.setEndTime("2018-07-02 11:05:56");

        try {
            result = this.mockMvc.perform(post(baseUrl+"/record/handleMessgeList").content(JSON.toJSONString(alarmRecordRequest)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();
        } catch (Exception e) {
            logger.error("{}",e);
        }


    }

    @Test
    public void queryStatList() throws Exception {
        AlarmRecordRequest request = new AlarmRecordRequest();

        result = this.mockMvc.perform(post(baseUrl+"/error/stat/list").content(JSON.toJSONString(request)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void queryDashBoardAlarmRecord() throws Exception {
        DashboardRequest request = new DashboardRequest();

//        request.setBizType((byte)3);
//        request.setSaasEnv((byte)1);
//        request.setStartDate("");
//        request.setEndDate(new Date());

        JSONObject object = new JSONObject();

        object.put("bizType", 3);
        object.put("saasEnv", 1);
        object.put("startDate", "2018-01-01");
        object.put("endDate", "2018-08-10");

        result = this.mockMvc.perform(post(baseUrl+"/error/list/dashboard").content(JSON.toJSONString(object)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andReturn();

    }
}