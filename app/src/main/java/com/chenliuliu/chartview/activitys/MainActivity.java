package com.chenliuliu.chartview.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.chenliuliu.chartview.R;
import com.chenliuliu.chartview.chart.CharterLine;
import com.chenliuliu.chartview.chart.CharterXLabels;
import com.chenliuliu.chartview.chart.CharterYLabels;
import com.chenliuliu.chartview.views.CanvasView;
import com.chenliuliu.chartview.views.ChartView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.charter_line_YLabel2)
    CharterYLabels mCharterLineYLabel2;
    @Bind(R.id.charter_line)
    CharterLine mCharterLine;
    @Bind(R.id.charter_line_XLabel)
    CharterXLabels mCharterLineXLabel;
    @Bind(R.id.chartview)
    ChartView chartView;
    @Bind(R.id.chartview2)
    CanvasView mChartview2;

    private String[] valuesY = new String[]{"优秀", "一般", "良好", "非常", "差劣", "超级"};

    private String[] valueX = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private float[] value = new float[]{0, 5, 3, 1, 4, 3, 0, 3, 4, 3, 1, 4, 3, 0, 2, 4, 3, 1, 4, 3, 0, 1, 4, 3, 1, 4, 3, 0, 4, 4, 3, 1, 4, 3, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setViewOne();
        setViewTwo();
        setViewThree();
//        applyKitKatTranslucency();
    }

    private void setViewThree() {
        mChartview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChartview2.setLineCount(9);
                mChartview2.setPointCount(15);
                ArrayList<Integer> one = new ArrayList<Integer>();
                ArrayList<Integer> two = new ArrayList<Integer>();
                for (int i = 0; i < 15; i++) {
                    Random rand = new Random();
                    one.add(rand.nextInt(9));
                    two.add(rand.nextInt(9));
                }
                mChartview2.setData(one, two);
            }
        });
    }

    private void setViewTwo() {
        chartView.setYMaxValue(100);
        chartView.setYMinValue(0);
        List<ChartView.ChartData> chartDatas = new ArrayList<>();
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/01"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/02"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/03"));
        chartDatas.add(new ChartView.ChartData(0.8f, "", "01/04"));
        chartDatas.add(new ChartView.ChartData(1.0f, "", "01/05"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/06"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/07"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/08"));
        chartDatas.add(new ChartView.ChartData(0.8f, "", "01/10"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/11"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/12"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/13"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/14"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/15"));
        chartDatas.add(new ChartView.ChartData(0.0f, "", "01/16"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/17"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/18"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/19"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/20"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/21"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/22"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/23"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/24"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/25"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/26"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/27"));
        chartDatas.add(new ChartView.ChartData(0.4f, "", "01/28"));
        chartDatas.add(new ChartView.ChartData(0.6f, "", "01/29"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/30"));
        chartDatas.add(new ChartView.ChartData(0.2f, "", "01/31"));

        chartView.setData(chartDatas);
        chartView.startChart();
    }

    private void setViewOne() {
        mCharterLineXLabel.setStickyEdges(true);
        mCharterLineXLabel.setValues(valueX);
        mCharterLineYLabel2.setValues(valuesY);
        mCharterLineYLabel2.setStickyEdges(true);
        mCharterLine.setValues(value);
        mCharterLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.string_author, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void applyKitKatTranslucency() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            // mTintManager.setTintColor(0x63AE0A);
            mTintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.backgroud_title_state));

        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
