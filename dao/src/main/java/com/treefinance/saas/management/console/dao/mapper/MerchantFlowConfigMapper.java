package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfig;
import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfigCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MerchantFlowConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    long countByExample(MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int deleteByExample(MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int insert(MerchantFlowConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int insertSelective(MerchantFlowConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    List<MerchantFlowConfig> selectByExampleWithRowbounds(MerchantFlowConfigCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    List<MerchantFlowConfig> selectByExample(MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    List<MerchantFlowConfig> selectPaginationByExample(MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    void batchInsert(List<MerchantFlowConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    void batchUpdateByPrimaryKey(List<MerchantFlowConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    void batchUpdateByPrimaryKeySelective(List<MerchantFlowConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    MerchantFlowConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int updateByExampleSelective(@Param("record") MerchantFlowConfig record, @Param("example") MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int updateByExample(@Param("record") MerchantFlowConfig record, @Param("example") MerchantFlowConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int updateByPrimaryKeySelective(MerchantFlowConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Tue Apr 17 16:28:41 CST 2018
     */
    int updateByPrimaryKey(MerchantFlowConfig record);
}