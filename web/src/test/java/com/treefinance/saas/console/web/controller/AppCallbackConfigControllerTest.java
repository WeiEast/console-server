package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.common.domain.vo.AppCallbackBizVO;
import com.treefinance.saas.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.console.common.domain.vo.AppCallbackDataTypeVO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author chengtong
 * @date 18/4/4 10:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppCallbackConfigControllerTest {

    private String baseUrl = "/saas/console/callback";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAppCallbackConfigList() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/get/120").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getConfigById() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/list?pageSize=7").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addConfig() throws Exception {
        AppCallbackConfigVO appCallbackConfigVO = new AppCallbackConfigVO();
        appCallbackConfigVO.setUrl("https://www.baidu.com");
        appCallbackConfigVO.setAppId("test_EasyToWin111");
        appCallbackConfigVO.setAppName("么么哒");
        appCallbackConfigVO.setIsNewKey((byte)1);
        appCallbackConfigVO.setIsNotifyCancel((byte)1);
        appCallbackConfigVO.setIsNotifyFailure((byte)1);
        appCallbackConfigVO.setIsNotifySuccess((byte)1);
        appCallbackConfigVO.setNotifyModel((byte)1);
        appCallbackConfigVO.setIsNewKey((byte)1);
        appCallbackConfigVO.setReceiver("aasdasdasdasdasdas");
        appCallbackConfigVO.setUrl("https://www.baidu.com");

        List<AppCallbackBizVO> bizTypes = new ArrayList<>();
        AppCallbackBizVO callbackBizVO = new AppCallbackBizVO();
        callbackBizVO.setBizType((byte)1);
        callbackBizVO.setBizName("");
        bizTypes.add(callbackBizVO);

        appCallbackConfigVO.setBizTypes(bizTypes);

        this.mockMvc.perform(post(baseUrl+"/add").content(JSON.toJSONString(appCallbackConfigVO)).contentType(MediaType
                .APPLICATION_JSON).accept
                (MediaType
                .APPLICATION_JSON));
    }

    @Test
    public void updateConfig() throws Exception {
        AppCallbackConfigVO appCallbackConfigVO = new AppCallbackConfigVO();
        appCallbackConfigVO.setUrl("https://www.baidu.com");
        appCallbackConfigVO.setId(120);
        appCallbackConfigVO.setAppId("test_P7gNtF0WB0m38znV");
        appCallbackConfigVO.setAppName("么么哒");
        appCallbackConfigVO.setIsNewKey((byte)1);
        appCallbackConfigVO.setIsNotifyCancel((byte)1);
        appCallbackConfigVO.setIsNotifyFailure((byte)1);
        appCallbackConfigVO.setIsNotifySuccess((byte)1);
        appCallbackConfigVO.setNotifyModel((byte)1);
        appCallbackConfigVO.setIsNewKey((byte)0);
        appCallbackConfigVO.setReceiver("aasdasdasdasdasdas");
        appCallbackConfigVO.setUrl("https://www.baidu.com");

        List<AppCallbackBizVO> bizTypes = new ArrayList<>();
        AppCallbackBizVO callbackBizVO = new AppCallbackBizVO();
        callbackBizVO.setBizType((byte)1);
        callbackBizVO.setBizName("");
        bizTypes.add(callbackBizVO);

        appCallbackConfigVO.setBizTypes(bizTypes);

        AppCallbackDataTypeVO dataTypeVO = new AppCallbackDataTypeVO();
        dataTypeVO.setCode((byte)1);

        appCallbackConfigVO.setDataTypeVO(dataTypeVO);
        this.mockMvc.perform(post(baseUrl+"/update").content(JSON.toJSONString(appCallbackConfigVO)).contentType
                (MediaType
                .APPLICATION_JSON).accept
                (MediaType
                        .APPLICATION_JSON));
    }

    @Test
    public void deleteConfigById() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/delete/69").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getCallbackBizList() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/biz/list").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getDataTypeList() throws Exception {
        this.mockMvc.perform(get(baseUrl+"/datatype/list").accept(MediaType.APPLICATION_JSON));
    }
}