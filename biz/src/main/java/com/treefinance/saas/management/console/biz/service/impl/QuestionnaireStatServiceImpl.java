package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.QuestionnaireStatService;
import com.treefinance.saas.management.console.common.utils.ExcelUtil;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppQuestionnaireStatisticsRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppQuestionnaireDetailResult;
import com.treefinance.saas.merchant.center.facade.result.console.AppQuestionnaireDetailStatisticsResult;
import com.treefinance.saas.merchant.center.facade.result.console.AppQuestionnaireStatisticsResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppQuestionnaireFacade;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.treefinance.saas.knife.common.CommonStateCode.NO_RELATED_DATA;


/**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:04
 */
@Service
public class QuestionnaireStatServiceImpl implements QuestionnaireStatService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireStatService.class);

    @Autowired
    private AppQuestionnaireFacade appQuestionnaireFacade;


    @Override
    public SaasResult<List> queryAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest request) {


        MerchantResult<List<AppQuestionnaireStatisticsResult>> listMerchantResult = appQuestionnaireFacade.queryAppQuestionnaireStatistics(request);
        MerchantResult<List<AppQuestionnaireDetailStatisticsResult>> listMerchantResult1 = appQuestionnaireFacade.queryAppQuestionnaireDetailStatistics(request);

        if (!("SUCCESS").equals(listMerchantResult.getRetMsg()) || !("SUCCESS").equals(listMerchantResult1.getRetMsg())) {
            return Results.newFailedResult(NO_RELATED_DATA, (listMerchantResult.getRetMsg() + listMerchantResult1.getRetMsg()));
        }

        List<Object> list = new ArrayList<>();
        list.addAll(listMerchantResult.getData());
        list.addAll(listMerchantResult1.getData());

        return Results.newSuccessResult(list);


    }

    @Override
    public void downloadAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest queryAppQuestionnaireStatisticsRequest, HttpServletRequest request, HttpServletResponse response) {
        MerchantResult<List<AppQuestionnaireStatisticsResult>> listMerchantResult = appQuestionnaireFacade.queryAppQuestionnaireStatistics(queryAppQuestionnaireStatisticsRequest);
        MerchantResult<List<AppQuestionnaireDetailStatisticsResult>> listMerchantResult1 = appQuestionnaireFacade.queryAppQuestionnaireDetailStatistics(queryAppQuestionnaireStatisticsRequest);

        MerchantResult<List<AppQuestionnaireDetailResult>> merchantResult = appQuestionnaireFacade.queryAppQuestionDetail(queryAppQuestionnaireStatisticsRequest);

        String[] title = {"环节", "总数", "问题数", "个人原因", "系统原因", "待定原因"};
        String[] title1 = {"环节", "内容", "用户数", "占比"};
        String[] title2 = {"商户名称", "问卷编码", "问卷名称", "任务ID", "uniqueId", "问卷内容", "问卷时间"};

        String[][] content = new String[listMerchantResult.getData().size()][];
        String[][] content1 = new String[listMerchantResult1.getData().size()][];
        String[][] content2 = new String[merchantResult.getData().size()][];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String fileName = "商户问卷统计表" + simpleDateFormat.format(new Date()) + ".xls";



        String sheetName = "概要统计";
        String sheetName1 = "明细统计";
        String sheetName2 = "明细清单";
        for (int i = 0; i < listMerchantResult.getData().size(); i++) {
            content[i] = new String[title.length];
            AppQuestionnaireStatisticsResult appQuestionnaireStatisticsResult = listMerchantResult.getData().get(i);
            content[i][0] = appQuestionnaireStatisticsResult.getStep();
            content[i][1] = appQuestionnaireStatisticsResult.getTotal().toString();
            content[i][2] = appQuestionnaireStatisticsResult.getQuestionCounts().toString();
            content[i][3] = appQuestionnaireStatisticsResult.getPersonReason().toString();
            content[i][4] = appQuestionnaireStatisticsResult.getSystemReason().toString();
            content[i][5] = appQuestionnaireStatisticsResult.getUndeterminedReason().toString();
        }
        for (int i = 0; i < listMerchantResult1.getData().size(); i++) {
            content1[i] = new String[title1.length];
            AppQuestionnaireDetailStatisticsResult appQuestionnaireDetailStatisticsResult = listMerchantResult1.getData().get(i);
            content1[i][0] = appQuestionnaireDetailStatisticsResult.getStep();
            content1[i][1] = appQuestionnaireDetailStatisticsResult.getContent();
            content1[i][2] = appQuestionnaireDetailStatisticsResult.getUserCounts().toString();
            content1[i][3] = appQuestionnaireDetailStatisticsResult.getPercentage().toString();
        }


        for (int i = 0; i < merchantResult.getData().size(); i++) {
            content2[i] = new String[title2.length];
            AppQuestionnaireDetailResult appQuestionnaireDetailResult = merchantResult.getData().get(i);
            content2[i][0] = appQuestionnaireDetailResult.getAppName();
            content2[i][1] = appQuestionnaireDetailResult.getQuestionnaireCode();
            content2[i][2] = appQuestionnaireDetailResult.getQuestionnaireName();
            content2[i][3] = appQuestionnaireDetailResult.getTaskId().toString();
            content2[i][4] = appQuestionnaireDetailResult.getUniqueId();
            content2[i][5] = appQuestionnaireDetailResult.getContent();
            content2[i][6] = appQuestionnaireDetailResult.getCreateTime().toString();
        }
        ExcelUtil excelUtil = new ExcelUtil();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName, title, content, hssfWorkbook);
        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName1, title1, content1, hssfWorkbook);
        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName2, title2, content2, hssfWorkbook);

        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            hssfWorkbook.write(os);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
