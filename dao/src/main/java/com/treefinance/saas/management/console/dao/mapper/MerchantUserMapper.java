package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.MerchantUser;
import com.treefinance.saas.management.console.dao.entity.MerchantUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MerchantUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    long countByExample(MerchantUserCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int deleteByExample(MerchantUserCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int insert(MerchantUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int insertSelective(MerchantUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    List<MerchantUser> selectByExampleWithRowbounds(MerchantUserCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    List<MerchantUser> selectByExample(MerchantUserCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    MerchantUser selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int updateByExampleSelective(@Param("record") MerchantUser record, @Param("example") MerchantUserCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int updateByExample(@Param("record") MerchantUser record, @Param("example") MerchantUserCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int updateByPrimaryKeySelective(MerchantUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    int updateByPrimaryKey(MerchantUser record);
}