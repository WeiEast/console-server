package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskBuryPointLog;
import com.treefinance.saas.management.console.dao.entity.TaskBuryPointLogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaskBuryPointLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    long countByExample(TaskBuryPointLogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int deleteByExample(TaskBuryPointLogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int insert(TaskBuryPointLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int insertSelective(TaskBuryPointLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    List<TaskBuryPointLog> selectByExampleWithRowbounds(TaskBuryPointLogCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    List<TaskBuryPointLog> selectByExample(TaskBuryPointLogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    TaskBuryPointLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int updateByExampleSelective(@Param("record") TaskBuryPointLog record, @Param("example") TaskBuryPointLogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int updateByExample(@Param("record") TaskBuryPointLog record, @Param("example") TaskBuryPointLogCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int updateByPrimaryKeySelective(TaskBuryPointLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_bury_point_log
     *
     * @mbg.generated Wed Jun 27 10:26:36 CST 2018
     */
    int updateByPrimaryKey(TaskBuryPointLog record);
}