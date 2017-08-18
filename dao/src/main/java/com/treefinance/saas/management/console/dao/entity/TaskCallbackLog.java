package com.treefinance.saas.management.console.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TaskCallbackLog implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.id
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.taskId
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private Long taskId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.url
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.requestParam
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private String requestParam;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.responseData
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private String responseData;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.consumeTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private Integer consumeTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.createTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_callback_log.lastUpdateTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_callback_log
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.id
     *
     * @return the value of task_callback_log.id
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.id
     *
     * @param id the value for task_callback_log.id
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.taskId
     *
     * @return the value of task_callback_log.taskId
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.taskId
     *
     * @param taskId the value for task_callback_log.taskId
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.url
     *
     * @return the value of task_callback_log.url
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.url
     *
     * @param url the value for task_callback_log.url
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.requestParam
     *
     * @return the value of task_callback_log.requestParam
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public String getRequestParam() {
        return requestParam;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.requestParam
     *
     * @param requestParam the value for task_callback_log.requestParam
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam == null ? null : requestParam.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.responseData
     *
     * @return the value of task_callback_log.responseData
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.responseData
     *
     * @param responseData the value for task_callback_log.responseData
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setResponseData(String responseData) {
        this.responseData = responseData == null ? null : responseData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.consumeTime
     *
     * @return the value of task_callback_log.consumeTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public Integer getConsumeTime() {
        return consumeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.consumeTime
     *
     * @param consumeTime the value for task_callback_log.consumeTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setConsumeTime(Integer consumeTime) {
        this.consumeTime = consumeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.createTime
     *
     * @return the value of task_callback_log.createTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.createTime
     *
     * @param createTime the value for task_callback_log.createTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_callback_log.lastUpdateTime
     *
     * @return the value of task_callback_log.lastUpdateTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_callback_log.lastUpdateTime
     *
     * @param lastUpdateTime the value for task_callback_log.lastUpdateTime
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_callback_log
     *
     * @mbg.generated Fri Aug 18 10:51:37 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskId=").append(taskId);
        sb.append(", url=").append(url);
        sb.append(", requestParam=").append(requestParam);
        sb.append(", responseData=").append(responseData);
        sb.append(", consumeTime=").append(consumeTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}