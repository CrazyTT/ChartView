package com.chenliuliu.chartview.bean;

/**
 * Created by liuliuchen on 16/1/13.
 */
public class DayCondition {
    private String createTime;
    private int avg;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getDayOfMonth() {
        return Integer.parseInt(createTime.split("-")[2]);
    }

    public String getDate() {
        return createTime.split("-")[1] + "/" + createTime.split("-")[2];
    }
}
