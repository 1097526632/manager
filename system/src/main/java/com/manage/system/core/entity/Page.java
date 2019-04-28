package com.manage.system.core.entity;

import com.manage.system.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class Page<T> {

    private int page=1;
    private int pageSize=20;// -1 不分页不统计  -2 不分页统计   >0 分页统计
    private int totalRecord=0;
    private String orderBy;
    private String orderField;
    private List<T> records;
    private Map<String,String> sumParams;
    private Map<String,String> sumResult;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Map<String, String> getSumParams() {
        return sumParams;
    }

    public void setSumParams(Map<String, String> sumParams) {
        this.sumParams = sumParams;
    }

    public Map<String, String> getSumResult() {
        return sumResult;
    }

    public void setSumResult(Map<String, String> sumResult) {
        this.sumResult = sumResult;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderByField(){
        if(StringUtils.isNotBlank(orderField)){
            return orderField +" "+ (StringUtils.isNotBlank(orderBy)?orderBy:"");
        }
        return "";
    }
}
