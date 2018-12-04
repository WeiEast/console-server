package com.treefinance.saas.console.manager;

import com.treefinance.saas.console.manager.domain.DailyErrorStepStatBO;
import com.treefinance.saas.console.manager.domain.MerchantAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatResultSet;
import com.treefinance.saas.console.manager.query.DailyErrorStepStatQuery;
import com.treefinance.saas.console.manager.query.MerchantAccessStatQuery;
import com.treefinance.saas.console.manager.query.MerchantDailyAccessStatQuery;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/11/29 15:48
 */
public interface MerchantStatManager {

    MerchantDailyAccessStatResultSet queryDailyAccessStatisticsResultSet(@Nonnull MerchantDailyAccessStatQuery query);

    List<MerchantDailyAccessStatBO> queryDailyAccessStatisticsRecords(@Nonnull MerchantDailyAccessStatQuery query);

    List<MerchantAccessStatBO> queryAccessStatisticsRecords(@Nonnull MerchantAccessStatQuery query);

    List<DailyErrorStepStatBO> queryDailyErrorStepStatisticsRecords(@Nonnull DailyErrorStepStatQuery query);
}
