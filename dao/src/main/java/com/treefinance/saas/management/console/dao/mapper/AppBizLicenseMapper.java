package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AppBizLicenseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    long countByExample(AppBizLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int deleteByExample(AppBizLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int insert(AppBizLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int insertSelective(AppBizLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    List<AppBizLicense> selectByExampleWithRowbounds(AppBizLicenseCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    List<AppBizLicense> selectByExample(AppBizLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    AppBizLicense selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int updateByExampleSelective(@Param("record") AppBizLicense record, @Param("example") AppBizLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int updateByExample(@Param("record") AppBizLicense record, @Param("example") AppBizLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int updateByPrimaryKeySelective(AppBizLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Tue Jul 04 10:52:24 CST 2017
     */
    int updateByPrimaryKey(AppBizLicense record);
}