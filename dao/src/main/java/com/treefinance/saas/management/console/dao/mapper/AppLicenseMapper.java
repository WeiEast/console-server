package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.AppLicense;
import com.treefinance.saas.management.console.dao.entity.AppLicenseCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AppLicenseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int countByExample(AppLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int deleteByExample(AppLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int insert(AppLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int insertSelective(AppLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    List<AppLicense> selectByExampleWithRowbounds(AppLicenseCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    List<AppLicense> selectByExample(AppLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    AppLicense selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int updateByExampleSelective(@Param("record") AppLicense record, @Param("example") AppLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int updateByExample(@Param("record") AppLicense record, @Param("example") AppLicenseCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int updateByPrimaryKeySelective(AppLicense record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_app_license
     *
     * @mbggenerated Fri May 19 19:07:27 CST 2017
     */
    int updateByPrimaryKey(AppLicense record);
}