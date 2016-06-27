package com.chenliuliu.chartview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 16/6/27.
 */

public class CanvasView extends View {
    int poY[];
    int poX[];
    //背景的坐标
    private int hi, wi, widthBg, heightBg;
    private Paint mPaintLineOne, mPaintLineTwo, mPaintLineX, mPaintLineY, mPaintLineDefault, mPaintText, mPaintLineOneRound, mPaintLineTwoRound;
    private int lineCount = 5;
    private int pointCount = 7;
    ArrayList<Integer> lineTwo = new ArrayList<>();
    ArrayList<Integer> lineOne = new ArrayList<>();


    public CanvasView(Context context, AttributeSet set) {
        this(context, set, 0);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaintLineOne = new Paint();
        mPaintLineOne.setAntiAlias(true);
        mPaintLineOne.setColor(Color.BLUE);
        mPaintLineOne.setStyle(Paint.Style.STROKE);
        mPaintLineOne.setStrokeWidth(4);


        mPaintLineTwo = new Paint();
        mPaintLineTwo.setAntiAlias(true);
        mPaintLineTwo.setColor(Color.RED);
        mPaintLineTwo.setStyle(Paint.Style.STROKE);
        mPaintLineTwo.setStrokeWidth(4);


        mPaintLineX = new Paint();
        mPaintLineX.setAntiAlias(true);
        mPaintLineX.setColor(Color.GRAY);
        mPaintLineX.setStyle(Paint.Style.STROKE);
        mPaintLineX.setStrokeWidth(3);
        mPaintLineX.setStyle(Paint.Style.FILL);

        mPaintLineY = new Paint();
        mPaintLineY.setAntiAlias(true);
        mPaintLineY.setColor(Color.GRAY);
        mPaintLineY.setStyle(Paint.Style.STROKE);
        mPaintLineY.setStrokeWidth(3);
        mPaintLineY.setStyle(Paint.Style.FILL);

        mPaintLineDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLineDefault.setStyle(Paint.Style.STROKE);
        mPaintLineDefault.setColor(Color.GRAY);
        mPaintLineDefault.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mPaintLineDefault.setPathEffect(effects);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.GRAY);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(14);

        mPaintLineOneRound = new Paint();
        mPaintLineOneRound.setColor(Color.BLUE);

        mPaintLineTwoRound = new Paint();
        mPaintLineTwoRound.setColor(Color.RED);

    }

    @Override
    //重写该方法,进行绘图
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);
        //画Y轴
        canvas.drawLine(50, heightBg - 50, 50, 50, mPaintLineY);
        Path path1 = new Path();
        path1.moveTo(50, 0);
        path1.lineTo(25, 50);
        path1.lineTo(75, 50);
        path1.close();
        canvas.drawPath(path1, mPaintLineY);
        //画X轴
        canvas.drawLine(50, heightBg - 50, widthBg - 50, heightBg - 50, mPaintLineX);
        Path path2 = new Path();
        path2.moveTo(widthBg, heightBg - 50);
        path2.lineTo(widthBg - 50, heightBg - 25);
        path2.lineTo(widthBg - 50, heightBg - 75);
        path2.close();
        canvas.drawPath(path2, mPaintLineX);
        //画虚线
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(50, poY[i], widthBg - 50, poY[i], mPaintLineDefault);
        }
        //画X标签
        for (int i = 0; i < pointCount; i++) {
            canvas.drawText("tagX" + i, poX[i] - 10, heightBg - 15, mPaintText);
        }
        //画Y标签
        for (int i = 0; i < lineCount; i++) {
            canvas.drawText("tagY" + i, 10, poY[i], mPaintText);
        }

        Path pathLineone = new Path();
        pathLineone.moveTo(100, 25);
        pathLineone.lineTo(140, 25);
        canvas.drawPath(pathLineone, mPaintLineOne);
        canvas.drawText("收入", 144, 35, mPaintText);

        Path pathLineTwo = new Path();
        pathLineTwo.moveTo(180, 25);
        pathLineTwo.lineTo(220, 25);
        canvas.drawPath(pathLineTwo, mPaintLineTwo);
        canvas.drawText("支出", 224, 35, mPaintText);

        //画折线图1

//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);
//        lineOne.add(poY[0]);

        Path path3 = new Path();

        for (int i = 0; i < pointCount; i++) {
            if(lineOne.size()<=0){
                break;
            }
            if (i == 0) {
                path3.moveTo(poX[i], lineOne.get(i));
            } else {
                path3.lineTo(poX[i], lineOne.get(i));
            }
            canvas.drawCircle(poX[i], lineOne.get(i), 10, mPaintLineOneRound);
        }
        canvas.drawPath(path3, mPaintLineOne);
        //画折线图2
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
//        lineTwo.add(poY[4]);
        Path path4 = new Path();
        for (int i = 0; i < pointCount; i++) {
            if(lineTwo.size()<=0){
                break;
            }
            if (i == 0) {
                path4.moveTo(poX[i], lineTwo.get(i));
            } else {
                path4.lineTo(poX[i], lineTwo.get(i));
            }
            canvas.drawCircle(poX[i], lineTwo.get(i), 10, mPaintLineTwoRound);
        }
        canvas.drawPath(path4, mPaintLineTwo);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的宽度的1/2
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize * 3 / 4;
        }
        widthBg = width;
        heightBg = height;
        setMeasuredDimension(width, height);
        int hightTemp = (heightBg - 100 - 40) / (lineCount - 1);
        int hightOne = heightBg - 50 - 20;
        int hightTwo = heightBg - 50 - 20 - hightTemp;
        int hightThree = heightBg - 50 - 20 - hightTemp * 2;
        int hightFour = heightBg - 50 - 20 - hightTemp * 3;
        int hightFive = heightBg - 50 - 20 - hightTemp * 4;
        poY = new int[]{hightOne, hightTwo, hightThree, hightFour, hightFive};
        int widthTemp = (widthBg - 100 - 40) / (pointCount - 1);
        poX = new int[]{50 + 20, 50 + 20 + widthTemp, 50 + 20 + widthTemp * 2, 50 + 20 + widthTemp * 3, 50 + 20 + widthTemp * 4, 50 + 20 + widthTemp * 5, 50 + 20 + widthTemp * 6};
    }

    public void setData(ArrayList<Integer> one, ArrayList<Integer> two) {
        this.lineOne.clear();
        this.lineTwo.clear();
        for (int temp : one) {
            this.lineOne.add(poY[temp]);
        }
        for (int temp2 : two) {
            this.lineTwo.add(poY[temp2]);
        }
        invalidate();
    }
}
