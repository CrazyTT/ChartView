package com.chenliuliu.chartview.activitys;

/**
 * Created by liuliuchen on 16/1/14.
 */
public class DoubleCheck {
    private volatile static DoubleCheck ins;

    private DoubleCheck() {
    }

    public static DoubleCheck getIns() {
        if (null == ins) {
            synchronized (DoubleCheck.class) {
                if (null == ins) {
                    ins = new DoubleCheck();
                }
            }
        }
        return ins;
    }
}