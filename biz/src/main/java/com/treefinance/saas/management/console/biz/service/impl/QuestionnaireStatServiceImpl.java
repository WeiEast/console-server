package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.*;

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
    public SaasResult<Map<String, List>>
        queryAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest request) {

        MerchantResult<List<AppQuestionnaireStatisticsResult>> listMerchantResult =
            appQuestionnaireFacade.queryAppQuestionnaireStatistics(request);
        MerchantResult<List<AppQuestionnaireDetailStatisticsResult>> listMerchantResult1 =
            appQuestionnaireFacade.queryAppQuestionnaireDetailStatistics(request);

        if (!("SUCCESS").equals(listMerchantResult.getRetMsg())
            || !("SUCCESS").equals(listMerchantResult1.getRetMsg())) {
            return Results.newFailedResult(NO_RELATED_DATA,
                (listMerchantResult.getRetMsg() + listMerchantResult1.getRetMsg()));
        }

        Map<String, List> map = new Hashtable<>();
        map.put("result", listMerchantResult.getData());
        map.put("detailResult", listMerchantResult1.getData());

        return Results.newSuccessResult(map);

    }

    @Override
    public void downloadAppQuestionnaireStatistics(
        QueryAppQuestionnaireStatisticsRequest queryAppQuestionnaireStatisticsRequest, HttpServletRequest request,
        HttpServletResponse response) {
        MerchantResult<List<AppQuestionnaireStatisticsResult>> listMerchantResult =
            appQuestionnaireFacade.queryAppQuestionnaireStatistics(queryAppQuestionnaireStatisticsRequest);
        MerchantResult<List<AppQuestionnaireDetailStatisticsResult>> listMerchantResult1 =
            appQuestionnaireFacade.queryAppQuestionnaireDetailStatistics(queryAppQuestionnaireStatisticsRequest);

        MerchantResult<List<AppQuestionnaireDetailResult>> merchantResult =
            appQuestionnaireFacade.queryAppQuestionDetail(queryAppQuestionnaireStatisticsRequest);

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
            AppQuestionnaireDetailStatisticsResult appQuestionnaireDetailStatisticsResult =
                listMerchantResult1.getData().get(i);
            content1[i][0] = appQuestionnaireDetailStatisticsResult.getStep();
            content1[i][1] = appQuestionnaireDetailStatisticsResult.getContent();
            content1[i][2] = appQuestionnaireDetailStatisticsResult.getUserCounts().toString();
            content1[i][3] = appQuestionnaireDetailStatisticsResult.getPercentage();
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
            content2[i][6] = appQuestionnaireDetailResult.getCreateTime();
        }
        ExcelUtil excelUtil = new ExcelUtil();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName, title, content, hssfWorkbook);
        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName1, title1, content1, hssfWorkbook);
        hssfWorkbook = excelUtil.getHSSFWorkbook(sheetName2, title2, content2, hssfWorkbook);

        // 响应到客户端
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

    public static void main(String[] args) {
        String sheetName = "黑名单";
        String sheetName1 = "多头（1周）";
        String sheetName2 = "多头（1个月）";
        String sheetName3 = "多头（3个月）";
        String sheetName4 = "多头（6个月）";
        String sheetName5 = "多头（12个月）";
        String data="{\n" + "\t\"data\":\n" + "\t\t[\n" + "\t\t    {\"details\":\n"
            + "\t\t\t{\"ID_CARD\":{\"LevelH\":\"2\",\"LevelG\":\"1\",\"LevelB\":\"1\",\"LevelA\":\"9\",\"LevelN\":\"3\",\"LevelL\":\"7\",\"LevelM\":\"19\"},\n"
            + "\t\t\t\"MOBILE\":\n"
            + "\t\t\t\t{\"LevelJ\":\"1\",\"LevelH\":\"2\",\"LevelG\":\"1\",\"LevelB\":\"1\",\"LevelA\":\"9\",\"LevelN\":\"3\",\"LevelL\":\"7\",\"LevelM\":\"18\"}\n"
            + "\t\t\t},\n" + "\t\t\t\"id\":\"MP_LOAN_12M\",\n" + "\t\t\t\"value\":\"45\"},\n" + "\n"
            + "\t\t\t{\"details\":{\"ID_CARD\":{\"LevelG\":\"1\",\"LevelB\":\"1\",\"LevelA\":\"4\",\"LevelN\":\"3\",\"LevelL\":\"6\",\"LevelM\":\"16\"},\"MOBILE\":{\"LevelG\":\"1\",\"LevelB\":\"1\",\"LevelA\":\"4\",\"LevelN\":\"3\",\"LevelL\":\"6\",\"LevelM\":\"16\"}},\"id\":\"MP_LOAN_6M\",\"value\":\"31\"},\n"
            + "\n"
            + "\t\t\t{\"details\":{\"ID_CARD\":{\"LevelB\":\"1\",\"LevelA\":\"2\",\"LevelL\":\"6\",\"LevelM\":\"9\"},\"MOBILE\":{\"LevelB\":\"1\",\"LevelA\":\"2\",\"LevelL\":\"6\",\"LevelM\":\"9\"}},\"id\":\"MP_LOAN_3M\",\"value\":\"18\"},\n"
            + "\n"
            + "\t\t\t{\"details\":{\"ID_CARD\":{\"LevelB\":\"1\",\"LevelA\":\"2\",\"LevelL\":\"1\",\"LevelM\":\"6\"},\"MOBILE\":{\"LevelB\":\"1\",\"LevelA\":\"2\",\"LevelL\":\"1\",\"LevelM\":\"6\"}},\"id\":\"MP_LOAN_1M\",\"value\":\"10\"},\n"
            + "\n"
            + "\t\t\t{\"details\":{\"ID_CARD\":{\"LevelB\":\"1\",\"LevelM\":\"2\"},\"MOBILE\":{\"LevelB\":\"1\",\"LevelM\":\"2\"}},\"id\":\"MD_LOAN_1W\",\"value\":\"3\"},\n"
            + "\n" + "\t\t\t{\"value\":false,\"id\":\"IS_BLACK\"}\n" + "\t\t\t]\n" + "\n"
            + "\t\t\t,\"idcard\":\"430181198205058741\",\"mobile\":\"15802644777\",\"name\":\"钟玲\"}";

        JSONArray array = JSON.parseArray(data);

        String[][] content = new String[90][1];
        String[][] content1 = new String[90][1];




            for(int j=0;j<2000;j++){
                content[j][0]= array.getJSONObject(1).getString("name");
                content[j][1]= array.getJSONObject(1).getString("mobile");
                content[j][2]= array.getJSONObject(1).getString("idcard");
                for(int i=3;i<90;i++) {
                    JSONObject object = array.getJSONObject(1).getJSONObject("data");
                    String strings = object.getString("id");
                    try {
                        content[j][i] =
                            array.getJSONObject(1).getJSONArray("data").getJSONObject(0).getJSONObject("ID_CARD").getString("LevelA");
                    }
                catch(NullPointerException e){
                    content[j][i]=null;
                }

                
                
                
                
                
                }
            }

}

}
