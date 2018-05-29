package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.BasicDataService;
import com.treefinance.saas.management.console.common.domain.vo.BasicDataVO;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.request.autostat.BasicDataHistoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author:guoguoyun
 * @date:Created in 2018/4/23下午5:02
 */
@RestController
@RequestMapping("/saas/console/monitor/basicdata/")
public class BasicDataController {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataController.class);

    @Autowired
    private BasicDataService basicDataService;

    /**
     * 基础数据列表查询（分页）
     *
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public SaasResult<Map<String, Object>> queryAllBasicData(PageRequest pageRequest) {
        logger.info("基础数据列表查询接口，分页信息为{}", JSON.toJSON(pageRequest));

        return basicDataService.queryAllBasicData(pageRequest);

    }


    /**
     * 新增基础数据
     *
     * @param basicDataVO
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public SaasResult<Boolean> addBasicData(@RequestBody BasicDataVO basicDataVO) {
        if (basicDataVO.getDataCode() == null || basicDataVO.getDataJson() == null || basicDataVO.getDataName() == null || basicDataVO.getDataSource() == null || basicDataVO.getDataSourceConfigJson() == null) {
            logger.error("新增数据，基础数据参数不能为空", basicDataVO.toString());
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        logger.info("新增数据为{}", basicDataVO.toString());

        return basicDataService.addBasciData(basicDataVO);

    }

    /**
     * 更新基础数据
     *
     * @param basicDataVO
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public SaasResult<Boolean> updateBasicData(@RequestBody BasicDataVO basicDataVO) {
        if (basicDataVO.getDataCode() == null || basicDataVO.getDataJson() == null || basicDataVO.getDataName() == null || basicDataVO.getDataSource() == null || basicDataVO.getDataSourceConfigJson() == null) {
            logger.error("更新数据列表，基础数据参数不能为空", basicDataVO.toString());
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        logger.info("更新数据为{}", basicDataVO.toString());
        return basicDataService.updateBasciData(basicDataVO);

    }

    /**
     * 查询所有的基础数据名字
     *
     * @param baseRequest
     * @return
     */
    @RequestMapping(value = "querydataName", method = RequestMethod.GET)
    public SaasResult<List<String>> querydataName(BaseRequest baseRequest) {
        return basicDataService.querydataName(baseRequest);

    }

    /**
     * 根据ID返回基础数据名字
     *
     * @param basicDataVO
     * @return
     */
    @RequestMapping(value = "getdataNameById", method = RequestMethod.POST)
    public SaasResult<String> getdataNameById(@RequestBody BasicDataVO basicDataVO) {
        return basicDataService.getdataNameById(basicDataVO);

    }


    @RequestMapping(value = "getHistory", method = RequestMethod.POST)
    public Object queryHistory(@RequestBody BasicDataHistoryRequest request) {
        return basicDataService.queryHistory(request);
    }
}
