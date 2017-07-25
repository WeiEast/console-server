package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.ConsoleTrafficLimitConfig;
import com.treefinance.saas.management.console.dao.entity.ConsoleTrafficLimitConfigCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ConsoleTrafficLimitConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    long countByExample(ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int deleteByExample(ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int insert(ConsoleTrafficLimitConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int insertSelective(ConsoleTrafficLimitConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    List<ConsoleTrafficLimitConfig> selectByExampleWithRowbounds(ConsoleTrafficLimitConfigCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    List<ConsoleTrafficLimitConfig> selectByExample(ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    List<ConsoleTrafficLimitConfig> selectPaginationByExample(ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    void batchInsert(List<ConsoleTrafficLimitConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    void batchUpdateByPrimaryKey(List<ConsoleTrafficLimitConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    void batchUpdateByPrimaryKeySelective(List<ConsoleTrafficLimitConfig> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    ConsoleTrafficLimitConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int updateByExampleSelective(@Param("record") ConsoleTrafficLimitConfig record, @Param("example") ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int updateByExample(@Param("record") ConsoleTrafficLimitConfig record, @Param("example") ConsoleTrafficLimitConfigCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int updateByPrimaryKeySelective(ConsoleTrafficLimitConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 15:51:21 CST 2017
     */
    int updateByPrimaryKey(ConsoleTrafficLimitConfig record);
}