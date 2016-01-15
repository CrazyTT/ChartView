package com.chenliuliu.chartview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;

import com.chenliuliu.chartview.bean.DayCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenll 自定义chart显示控件 使用示例 chartView = (ChartView)
 *         findViewById(R.id.chartView);
 *         chartView.setBackColor(Color.parseColor(
 *         getString(R.color.backColor)));
 *         chartView.setAxesLineColor(Color.parseColor
 *         (getString(R.color.axesLineColor)));
 *         chartView.setGridLineColor(Color.
 *         parseColor(getString(R.color.axesLineColor)));
 *         chartView.setPointColor
 *         (Color.parseColor(getString(R.color.ponitineColor)));
 *         chartView.setShapeColor(Color.YELLOW); chartView.setYMaxValue(150);
 *         chartView.setYMinValue(0); chartView.setData(dataList);
 *         chartView.setOnTapPointListener(new OnTapPointListener() {
 * @Override public void onTap(ChartAxes axes) {
 * <p/>
 * Toast.makeText(MainActivity.this, axes.Y + "yy",
 * Toast.LENGTH_SHORT).show(); } });
 * chartView.startChart();全部设置完成以后，通过这个方法进行启动chart
 */
public class ChartView extends SurfaceView {

    private Context context;
    private SurfaceHolder holder;
    private GestureDetector detector;

    /**
     * 设置数据
     */
    private List<ChartData> valueData = new ArrayList<>();

    /**
     * 由设置数据转换来的可绘制的数据
     */
    private List<ChartAxes> axesData = new ArrayList<>();
    /**
     * 画布宽度
     */
    private int width = 0;
    /**
     * 画布高度
     */
    private int height = 0;
    /**
     * Y轴最大值
     */
    private int yMaxValue = 0;
    /**
     * Y轴最小值
     */
    private int yMinValue = 0;
    /**
     * 左边距
     */
    private int leftPadding = 10;
    /**
     * 顶部边距
     */
    private int topPadding = 60;
    /**
     * 右侧边距
     */
    private int rightPadding = 60;
    /**
     * 底部边距
     */
    private int bottomPadding = 10;
    /**
     * X轴刻度文字显示区域高度
     */
    private int xTextHeight = 50;
    /**
     * Y轴刻度文字显示区域宽度
     */
    private int yTextWidth = 80;
    /**
     * 顶部文字显示区域高度
     */
    private int topTextHeight = 20;
    /**
     * 背景色
     */
    private int backColor = Color.WHITE;
    /**
     * 坐标轴和边框的颜色
     */
    private int axesLineColor = Color.GRAY;
    /**
     * 内部网格线的颜色
     */
    private int gridLineColor = Color.GRAY;
    /**
     * chart图线的颜色
     */
    private int chartLineColor = Color.GRAY;
    /**
     * 数据点的颜色
     */
    private int pointColor = Color.GRAY;
    /**
     * 阴影的颜色
     */
    private int shapeColor = Color.BLACK;
    /**
     * 数据点的位图
     */
    private Bitmap pointBitmap = null;
    /**
     * X轴一屏显示的数据的数量
     */
    private int xNum = 6;
    /**
     * Y轴一屏显示的数据的数量
     */
    private int yNum = 5;
    /**
     * 当前显示的数据的第一条数据下标
     */
    private int startIndexDisplay = 0;
    /**
     * 数据点的点击事件监听
     */
    private OnTapPointListener tapPointListener;
    /**
     * 顶部文字字体大小
     */
    private int topTextSize = 12;
    /**
     * X轴刻度文字字体大小
     */
    private int leftTextSize = 30;
    /**
     * Y轴刻度文字字体大小
     */
    private int bottomTextSize = 20;
    /**
     * 边框线宽度
     */
    private int chartFrameLineSize = 3;
    /**
     * 网格线宽度
     */
    private int gridLineSize = 1;
    /**
     * chart图线宽度
     */
    private int chartLineSize = 6;
    /**
     * 数据点的半径
     */
    private int pointSize = 10;
    /**
     * 阴影的透明度
     */
    private int shapeAlpha = 5;
    /**
     * 是否需要显示阴影
     */
    private boolean isShapeShow = true;
    /**
     * chart是否带惯性滚动
     */
    private boolean isEnableScroll = false;
    /**
     * 是否在惯性滚动的状态
     */
    private boolean isInertia = false;

    /**
     * 滚动代码执行有没有真正的停止
     */
    private boolean isScrollEnd = true;

    /**
     * 当前选中的点
     */
    private ChartAxes currentPressedPoint;

    /**
     * 按下点的画笔
     */
    private Paint pressedPaint;

    /**
     * 按下的颜色
     */
    private int pressedColor = Color.parseColor("#FFC000");

    /**
     * 滑动加载更多接口
     */
    private OnScrollToLoadMore loadMore;

    /**
     * 惯性线程
     */
    public FlingThread flingThread;

    /**
     * 各个点的x轴间距
     */
    private int spaceXLength;

    /**
     * 第一个点与最后一个点距离左边界和右边界的距离
     */
    private static final int POINT_PADDING = 40;

    /**
     * XY轴画笔
     */
    private Paint XYpaint;

    /**
     * Y轴上水平线间距
     */
    private int spaceYLength;

    /**
     * 最顶上一条线离封顶20px
     */
    int chartInnerTopPadding = 20;



    /*public OnScrollToLoadMore getLoadMore() {
        return loadMore;
    }

    public void setLoadMore(OnScrollToLoadMore loadMore) {
        this.loadMore = loadMore;
    }*/

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        holder = getHolder();
        holder.addCallback(new ChartCallBack());
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ChartView(Context context) {
        this(context, null);

    }

    /**
     * 全部设置完成后，开始chart的绘制
     */
    public void startChart() {
        width = getWidth();
        height = getHeight();

        int yHeight = height - topPadding - bottomPadding - topTextHeight - xTextHeight - chartInnerTopPadding;
        spaceYLength = yHeight / yNum;
        changeData();
        refreshChart(0);
        detector = new GestureDetector(ChartView.this.getContext(), new ChartGestureListener());
        setOnTouchListener(new ChartTouchListener());
    }

    /**
     * 设置显示的chart数据
     *
     * @param data
     */
    public void setData(List<ChartData> data) {
        this.valueData = data;
    }

    public List<ChartData> getData() {
        return valueData;
    }

    /**
     * 触摸事件
     */
    class ChartTouchListener implements OnTouchListener {

        private float startX;
        private float downX;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            detector.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    downX = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 是否已滚动到无法继续滑动
                    boolean isEnable = false;
                    // 手指move的像素值超过系统判定判定滚动的最小临界值,避免手指抖动造成chart图抖动
                    if (Math.abs(event.getX() - downX) > ViewConfiguration.get(context).getScaledTouchSlop()) {
                        int transValue = (int) (event.getX() - startX);
                        if (axesData.size() > 1) {
                            int max = axesData.get(axesData.size() - 1).X;
                            int min = axesData.get(0).X;
                            int maxLimit = width - rightPadding - POINT_PADDING;
                            int minLimit = leftPadding + yTextWidth + POINT_PADDING;
                            if (min + transValue > minLimit) { // 滑动以后左边界判断
                                if (min < minLimit) {
                                    // transValue = minLimit - min;
                                    isEnable = true;
                                } else {
                                    transValue = 0;
                                    if (loadMore != null) {
                                        loadMore.onLoad();
                                    }
                                }
                            } else {
                                isEnable = true;
                            }
                            if (max + transValue < maxLimit) {// 滑动后右边界判断
                                if (max > maxLimit) {
                                    // transValue = maxLimit - max;
                                    isEnable = true;
                                } else {
                                    transValue = 0;
                                }
                            } else {
                                isEnable = true;
                            }

                            if (isEnable) {
                                startX = event.getX();
                                refreshChart(transValue);
                            }
                        }
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    }

    /**
     * 手势事件
     */
    class ChartGestureListener implements OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            isInertia = false;
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isScrollEnd && isEnableScroll) {
                int v = (int) velocityX;
                new InertiaThread(v).start();
            }

            flingThread = new FlingThread(velocityX);
            flingThread.start();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            ChartAxes axes = isAvailableTap(e.getX(), e.getY());
            if (axes != null && tapPointListener != null) {
                tapPointListener.onTap(axes);
                currentPressedPoint = axes;
                refreshChart(0);
            }
            return true;
        }
    }

    class ChartCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startChart();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    /**
     * 设置纵坐标的最大值和最小值
     *
     * @param yMax 最大值
     * @param yMin 最小值
     */
    public void setLimitValues(int yMax, int yMin) {

        this.yMaxValue = yMax;
        this.yMinValue = yMin;
    }

    /**
     * 设置Y轴最大值
     *
     * @param yMax 最大值
     */
    public void setYMaxValue(int yMax) {
        this.yMaxValue = yMax;
    }

    /**
     * 设置Y轴最小值
     *
     * @param yMin 最小值
     */
    public void setYMinValue(int yMin) {
        this.yMinValue = yMin;
    }

    /**
     * chart图显示的边距
     *
     * @param left   左边距
     * @param top    顶部边距
     * @param right  右侧边距
     * @param bottom 底部边距
     */
    public void setChartPadding(int left, int top, int right, int bottom) {
        this.leftPadding = left;
        this.topPadding = top;
        this.rightPadding = right;
        this.bottomPadding = bottom;
    }

    /**
     * 设置chart图显示的左边距
     *
     * @param left 左边距
     */
    public void setChartLeftPadding(int left) {
        this.leftPadding = left;
    }

    /**
     * 设置chart图显示的顶部边距
     *
     * @param top 顶部边距
     */
    public void setChartTopPadding(int top) {
        this.topPadding = top;
    }

    /**
     * 设置chart图显示的右边距
     *
     * @param right 右侧边距
     */
    public void setChartRightPadding(int right) {
        this.rightPadding = right;
    }

    /**
     * 设置chart图显示的底部边距
     *
     * @param bottom 底部边距
     */
    public void setChartBottomPadding(int bottom) {
        this.bottomPadding = bottom;
    }

    /**
     * 设置X轴刻度文本显示区域的高度
     *
     * @param xTextWidth 区域高度值
     */
    public void setxTextWidth(int xTextWidth) {
        this.xTextHeight = xTextWidth;
    }

    /**
     * 设置Y轴刻度文本显示的宽度
     *
     * @param yTextWidth 宽度值
     */
    public void setyTextWidth(int yTextWidth) {
        this.yTextWidth = yTextWidth;
    }

    /**
     * 顶部文字字体大小
     *
     * @return 文字大小 PX单位
     */
    public int getTopTextSize() {
        return topTextSize;
    }

    /**
     * 设置顶部文字字体大小
     *
     * @param topTextSize 文字大小 PX单位
     */
    public void setTopTextSize(int topTextSize) {
        this.topTextSize = topTextSize;
    }

    /**
     * Y轴坐标的刻度文字字体大小
     *
     * @return 文字大小 PX单位
     */
    public int getLeftTextSize() {
        return leftTextSize;
    }

    /**
     * 设置Y轴坐标的刻度文字字体大小
     *
     * @param leftTextSize 文字大小 PX单位
     */
    public void setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
    }

    /**
     * X轴刻度文字字体大小
     *
     * @return 字体大小 PX单位
     */
    public int getBottomTextSize() {
        return bottomTextSize;
    }

    /**
     * 设置X轴刻度文字字体大小
     *
     * @param bottomTextSize 字体大小 PX单位
     */
    public void setBottomTextSize(int bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
    }

    /**
     * 设置chart图边框线宽度
     *
     * @param chartFrameLineSize 宽度值 PX单位
     */
    public void setChartFrameLineSize(int chartFrameLineSize) {
        this.chartFrameLineSize = chartFrameLineSize;
    }

    /**
     * 设置网格线宽度
     *
     * @param gridLineSize 网格线宽度值 PX单位
     */
    public void setGridLineSize(int gridLineSize) {
        this.gridLineSize = gridLineSize;
    }

    /**
     * 设置chart图折线宽度
     *
     * @param chartLineSize 线宽度值 PX单位
     */
    public void setChartLineSize(int chartLineSize) {
        this.chartLineSize = chartLineSize;
    }

    /**
     * 设置数据点绘制的大小
     *
     * @param pointSize 点的半径 单位PX
     */
    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }

    /**
     * 是否可以带惯性滚动
     *
     * @return true 可以 false 反之
     */
    public boolean isEnableScroll() {
        return isEnableScroll;
    }

    /**
     * 设置是否需要带惯性滚动
     *
     * @param isEnableScroll true 需要， false不需要
     */
    public void setEnableScroll(boolean isEnableScroll) {
        this.isEnableScroll = isEnableScroll;
    }

    /**
     * 设置线图下阴影区域的透明度
     *
     * @param shapeAlpha 透明度值
     */
    public void setShapeAlpha(int shapeAlpha) {
        this.shapeAlpha = shapeAlpha;
    }

    /**
     * 设置背景色
     *
     * @param backColor 背景色值
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    /**
     * 设置坐标轴的和边框的颜色
     *
     * @param axesLineColor 颜色值
     */
    public void setAxesLineColor(int axesLineColor) {
        this.axesLineColor = axesLineColor;
    }

    /**
     * 设置网格线的颜色
     *
     * @param gridLineColor 颜色值
     */
    public void setGridLineColor(int gridLineColor) {
        this.gridLineColor = gridLineColor;
    }

    /**
     * 设置Chart图线的颜色
     *
     * @param chartLineColor 颜色值
     */
    public void setChartLineColor(int chartLineColor) {
        this.chartLineColor = chartLineColor;
    }

    /**
     * 设置数据点的颜色
     *
     * @param pointColor 颜色值
     */
    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    /**
     * 设置阴影的颜色
     *
     * @param shapeColor 颜色值
     */
    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
    }

    /**
     * 设置数据点的想要显示的图形，可以不设置则会选择只用数据点颜色，想取消位图设置可以赋值“null”
     *
     * @param pointBitmap 图形位图
     */
    public void setPointBitmap(Bitmap pointBitmap) {
        this.pointBitmap = pointBitmap;
    }

    /**
     * 是否显示阴影区域
     *
     * @return true 为显示 false 反之
     */
    public boolean isShapeShow() {
        return isShapeShow;
    }

    /**
     * 设置是否需要显示阴影区域
     *
     * @param isShapeShow true为显示
     */
    public void setShapeShow(boolean isShapeShow) {
        this.isShapeShow = isShapeShow;
    }

    /**
     * 画整体chart图
     *
     * @param translatX chart图偏移量
     */
    public void refreshChart(int translatX) {
        synchronized (holder) {
            translationChart(translatX);

            if (holder != null) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(backColor);
                    drawGridLine(canvas);
                    drawChartLine(canvas);
                    drawLimitRect(canvas);
                    drawAxesLine(canvas);
                    drawYValueText(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /**
     * 画X，Y轴和边框
     *
     * @param canvas
     */
    private void drawAxesLine(Canvas canvas) {
        XYpaint = new Paint();
        XYpaint.setColor(axesLineColor);
        XYpaint.setAntiAlias(true);
        XYpaint.setStyle(Style.FILL_AND_STROKE);
        XYpaint.setStrokeWidth(chartFrameLineSize);
        // 左侧Y轴
        canvas.drawLine(leftPadding + yTextWidth, topPadding + topTextHeight, leftPadding + yTextWidth, height - bottomPadding - xTextHeight, XYpaint);
        // 三角箭头
        Path yTriangle = new Path();
        yTriangle.moveTo(leftPadding + yTextWidth - 10, topPadding + topTextHeight);
        yTriangle.lineTo(leftPadding + yTextWidth, topPadding + topTextHeight - 20);
        yTriangle.lineTo(leftPadding + yTextWidth + 10, topPadding + topTextHeight);
        yTriangle.close();
        canvas.drawPath(yTriangle, XYpaint);
        // 下方X轴
        canvas.drawLine(leftPadding + yTextWidth, height - bottomPadding - xTextHeight, width - rightPadding, height - bottomPadding - xTextHeight,
                XYpaint);
        // 三角箭头
        Path xTriangle = new Path();
        xTriangle.moveTo(width - rightPadding, height - bottomPadding - xTextHeight + 10);
        xTriangle.lineTo(width - rightPadding + 15, height - bottomPadding - xTextHeight);
        xTriangle.lineTo(width - rightPadding, height - bottomPadding - xTextHeight - 10);
        xTriangle.close();
        canvas.drawPath(xTriangle, XYpaint);
        // 上方封顶
        //canvas.drawLine(leftPadding + xTextHeight, topPadding + topTextHeight, width - rightPadding, topPadding + topTextHeight, XYpaint);
        // 右侧封边
        //canvas.drawLine(width - rightPadding, topPadding + topTextHeight, width - rightPadding, height - bottomPadding - xTextHeight, XYpaint);
    }

    /**
     * 画左右侧的边界区域（将超出的chart图形覆盖）
     *
     * @param canvas
     */
    private void drawLimitRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(backColor);
        paint.setStyle(Style.FILL);
        canvas.drawRect(0, 0, leftPadding + yTextWidth, height - bottomPadding, paint);
        canvas.drawRect(width - rightPadding, 0, width, height - bottomPadding, paint);
    }

    /**
     * 画折线图，网格竖线，X坐标值
     *
     * @param canvas
     */
    private void drawChartLine(Canvas canvas) {
        Paint paintChart = new Paint();
        paintChart.setColor(chartLineColor);
        paintChart.setAntiAlias(true);
        paintChart.setStyle(Style.STROKE);
        paintChart.setStrokeWidth(chartLineSize);

        Paint paintGrid = new Paint();
        paintGrid.setColor(gridLineColor);
        paintGrid.setAntiAlias(true);
        paintGrid.setStyle(Style.STROKE);
        paintGrid.setStrokeWidth(gridLineSize);

        Paint paintTopText = new Paint();
        paintTopText.setColor(axesLineColor);
        paintTopText.setAntiAlias(true);
        paintTopText.setSubpixelText(true);
        paintTopText.setTypeface(Typeface.MONOSPACE);
        paintTopText.setTextSize(topTextSize);
        paintTopText.setTextAlign(Align.CENTER);

        Paint paintBottomText = new Paint();
        paintBottomText.setColor(axesLineColor);
        paintBottomText.setAntiAlias(true);
        paintBottomText.setSubpixelText(true);
        paintBottomText.setTypeface(Typeface.MONOSPACE);
        paintBottomText.setTextSize(bottomTextSize);
        paintBottomText.setTextAlign(Align.CENTER);

        Paint paintPoint = new Paint();
        paintPoint.setColor(pointColor);
        paintPoint.setAntiAlias(true);
        paintPoint.setStyle(Style.FILL);

        pressedPaint = new Paint();
        pressedPaint.setColor(pressedColor);
        pressedPaint.setAntiAlias(true);
        pressedPaint.setStyle(Style.FILL);

        Paint paintShape = new Paint();
        paintShape.setStyle(Style.FILL);
        paintShape.setColor(shapeColor);
        paintShape.setAlpha(shapeAlpha);

        if (axesData.size() > 0) {
            for (int i = 0; i < axesData.size(); i++) {
                if (i < axesData.size() - 1) {
                    // 画折线
                    canvas.drawLine(axesData.get(i).X, axesData.get(i).Y, axesData.get(i + 1).X, axesData.get(i + 1).Y, paintChart);
                    if (isShapeShow) {
                        // 画阴影
                        Path path = new Path();
                        path.moveTo(axesData.get(i).X, height - bottomPadding - xTextHeight);
                        path.lineTo(axesData.get(i).X, axesData.get(i).Y + chartLineSize / 2);
                        path.lineTo(axesData.get(i + 1).X, axesData.get(i + 1).Y + chartLineSize / 2);
                        path.lineTo(axesData.get(i + 1).X, height - bottomPadding - xTextHeight);
                        canvas.drawPath(path, paintShape);
                    }
                }
                // 画网格竖线
                // canvas.drawLine(axesData.get(i).X, height - bottomPadding - xTextHeight, axesData.get(i).X, topPadding + topTextHeight, paintGrid);
                // 写X轴坐标的刻度值
                if (!TextUtils.isEmpty(axesData.get(i).getxText())) {
                    setTextSizeForWidth(paintBottomText, spaceXLength - 10, axesData.get(i).getxText());//10为相邻日期文字间隔
                    canvas.drawText(axesData.get(i).getxText(), axesData.get(i).X, height - bottomPadding - xTextHeight / 2, paintBottomText);
                }
                // 写顶部的刻度值
                if (!TextUtils.isEmpty(axesData.get(i).getTopText())) {
                    //取消了顶部隔年显示的数据
//					canvas.drawText(axesData.get(i).getTopText(), axesData.get(i).X, topPadding, paintTopText);
                }
                // 画数据点
                if (pointBitmap == null) {
                    canvas.drawCircle(axesData.get(i).X, axesData.get(i).Y, pointSize + 1, paintChart);
                    canvas.drawCircle(axesData.get(i).X, axesData.get(i).Y, pointSize, paintPoint);
                } else {
                    Matrix matrix = new Matrix();
                    canvas.drawBitmap(pointBitmap, matrix, paintPoint);
                }
            }

            // 画最后一个数据的网格竖线
            //canvas.drawLine(axesData.get(axesData.size() - 1).X, height - bottomPadding - xTextHeight, axesData.get(axesData.size() - 1).X,
            //        topPadding + topTextHeight, paintGrid);
            // 写X轴坐标的最后一个值的刻度值
            /*
             * canvas.drawText(axesData.get(axesData.size() - 1).X + "",
			 * axesData.get(axesData.size() - 1).X, height - bottomPadding -
			 * xTextHeight / 2, paintBottomText);
			 */
            // 写顶部的最后一个刻度值
            // canvas.drawText("2014", axesData.get(axesData.size() - 1).X,
            // topPadding, paintTopText);
            // 画数最后一个据点
            if (pointBitmap == null) {
                // canvas.drawCircle(axesData.get(axesData.size() - 1).X,
                // axesData.get(axesData.size() - 1).Y, pointSize + 1,
                // paintChart);
                // canvas.drawCircle(axesData.get(axesData.size() - 1).X,
                // axesData.get(axesData.size() - 1).Y, pointSize, paintPoint);
            } else {
                Matrix matrix = new Matrix();
                canvas.drawBitmap(pointBitmap, matrix, paintPoint);
            }
        }

        if (currentPressedPoint != null) {
            //canvas.drawCircle(currentPressedPoint.X, currentPressedPoint.Y, pointSize, pressedPaint);
        }
    }

    /**
     * 通过x轴间距,计算出字体大小,自动适应宽度
     *
     * @param paint        the Paint to set the text size for
     * @param desiredWidth the desired width
     * @param text         the text that should be that width
     * @return
     */
    private void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        //如果计算出的字体高度小于预设值
        if (bottomTextSize > desiredTextSize) {
            // Set the paint for that size.
            paint.setTextSize(desiredTextSize);
        } else {
            paint.setTextSize(bottomTextSize);
        }
    }

    /**
     * Y周的刻度值
     *
     * @param canvas
     */
    private void drawYValueText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(axesLineColor);
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(leftTextSize);
        paint.setTextAlign(Align.RIGHT);
        /*int yHeight = height - topPadding - bottomPadding - topTextHeight;
        int spaceLength = yHeight / yNum;
		int spaceValue = (yMaxValue - yMinValue) / yNum;
		for (int i = 0; i < 6; i++) {
			canvas.drawText((yMinValue + spaceValue * i) + "", leftPadding + yTextWidth - 5, height - bottomPadding - yTextWidth - (spaceLength * i),
					paint);
		}*/
        canvas.drawText("优秀", leftPadding + (yTextWidth + leftTextSize) / 2, height - bottomPadding - xTextHeight - (spaceYLength * 5) + leftTextSize / 2, paint);
        canvas.drawText("良好", leftPadding + (yTextWidth + leftTextSize) / 2, height - bottomPadding - xTextHeight - (spaceYLength * 4) + leftTextSize / 2, paint);
        canvas.drawText("一般", leftPadding + (yTextWidth + leftTextSize) / 2, height - bottomPadding - xTextHeight - (spaceYLength * 3) + leftTextSize / 2, paint);
        canvas.drawText("差劲", leftPadding + (yTextWidth + leftTextSize) / 2, height - bottomPadding - xTextHeight - (spaceYLength * 2) + leftTextSize / 2, paint);
        canvas.drawText("恶劣", leftPadding + (yTextWidth + leftTextSize) / 2, height - bottomPadding - xTextHeight - (spaceYLength * 1) + leftTextSize / 2, paint);
    }

    /**
     * 背景画网格的横线
     *
     * @param canvas
     */
    private void drawGridLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(gridLineColor);
        paint.setAntiAlias(true);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(gridLineSize);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        for (int i = 1; i <= yNum; i++) {
            // 画网格的横线
            canvas.drawLine(leftPadding + yTextWidth, height - bottomPadding - xTextHeight - (spaceYLength * i), width - rightPadding, height
                    - bottomPadding - xTextHeight - (spaceYLength * i), paint);
        }
    }

    /**
     * 要画的数据
     */
    public static class ChartData {
        public float Y;
        private String topText;
        private String xText;
        private DayCondition mDayCondition;

        public ChartData(float y, String topText, String xText) {
            Y = y;
            this.topText = topText;
            this.xText = xText;
        }

        public String getTopText() {
            return topText;
        }

        public void setTopText(String topText) {
            this.topText = topText;
        }

        public String getxText() {
            return xText;
        }

        public void setxText(String xText) {
            this.xText = xText;
        }

        @Override
        public String toString() {
            return "ChartData [Y=" + Y + ", topText=" + topText + ", xText=" + xText + "]";
        }

        public DayCondition getDayCondition() {
            return mDayCondition;
        }

        public void setDayCondition(DayCondition dayCondition) {
            mDayCondition = dayCondition;
        }
    }

    /**
     * 转化为X，Y的坐标值对象
     */
    public static class ChartAxes {

        public int X;
        // 点在折线图中Y轴高度百分比，越小越靠上
        public float Y;
        private String topText;
        private String xText;
        private DayCondition mDayCondition;

        public ChartAxes(int X, float Y, DayCondition mDayCondition) {
            this.X = X;
            this.Y = Y;
            this.mDayCondition = mDayCondition;
        }

        public String getTopText() {
            return topText;
        }

        public void setTopText(String topText) {
            this.topText = topText;
        }

        public String getxText() {
            return xText;
        }

        public void setxText(String xText) {
            this.xText = xText;
        }

        @Override
        public String toString() {
            return "ChartAxes [X=" + X + ", Y=" + Y + ", topText=" + topText + ", xText=" + xText + "]";
        }

        public void setDayCondition(DayCondition dayCondition) {
            this.mDayCondition = dayCondition;
        }

        public DayCondition getDayCondition() {
            return mDayCondition;
        }
    }

    /**
     * 将要画的数据转化为X，Y的坐标值对象
     */
    private void changeData() {
        axesData.clear();
        int yArea = height - topPadding - bottomPadding - xTextHeight - topTextHeight - chartInnerTopPadding;
        int xWidth = width - leftPadding - rightPadding - yTextWidth;
        spaceXLength = xWidth / xNum;
        for (int i = 0; i < valueData.size(); i++) {
            float yValue = topPadding + topTextHeight + chartInnerTopPadding + (yArea * valueData.get(i).Y);
            int xValue = leftPadding + yTextWidth + spaceXLength * i + POINT_PADDING;
            ChartAxes axes = new ChartAxes(xValue, yValue, valueData.get(i).getDayCondition());
            axes.setTopText(valueData.get(i).getTopText());
            axes.setxText(valueData.get(i).getxText());
            axesData.add(axes);
        }
    }

    /**
     * chart数据平移的计算
     *
     * @param offset 平移的值
     */
    private void translationChart(float offset) {
        for (int i = 0; i < axesData.size(); i++) {
            boolean isStartIndexLeft = false;
            boolean isStartIndexRight = false;
            if (axesData.get(i).X > leftPadding + yTextWidth) {
                isStartIndexLeft = true;
            }
            if (axesData.get(i).X < leftPadding + yTextWidth) {
                isStartIndexRight = true;
            }

            axesData.get(i).X += offset;

            if (axesData.get(i).X < leftPadding + yTextWidth && isStartIndexLeft) {
                startIndexDisplay = i;
            }
            if (axesData.get(i).X > leftPadding + yTextWidth && isStartIndexRight) {
                startIndexDisplay = i;
            }
        }
    }

    /**
     * 如果数据不满一屏
     * chart图移动到最左边
     * 否则右对齐
     */
    public void adjustChartOffset() {
        int offset = 0;
        if (axesData.get(axesData.size() - 1).X > width - POINT_PADDING) {
            offset = -(axesData.get(axesData.size() - 1).X - width + rightPadding + POINT_PADDING);
        }
        refreshChart(offset);
    }

    /**
     * 判断当前点击是否点击在有效区域
     *
     * @param x 点击的X坐标值
     * @param y 点击的Y坐标值
     * @return 返回当前的坐标的对应的数据实体, 如果是无效点击，将返回null
     */
    private ChartAxes isAvailableTap(float x, float y) {
        ChartAxes axes = null;
        // 点击有效区域的大小
        int availableArea = 20;
        int endIndex = startIndexDisplay + xNum + 3;
        if (endIndex > axesData.size()) {
            endIndex = axesData.size();
        }
        for (int i = startIndexDisplay; i < endIndex; i++) {
            if ((x > axesData.get(i).X - availableArea && x < axesData.get(i).X + availableArea)
                    && (y > axesData.get(i).Y - availableArea && y < axesData.get(i).Y + availableArea)) {
                axes = axesData.get(i);
            }
        }
        return axes;
    }

    /**
     * 数据点的点击事件监听
     */
    public interface OnTapPointListener {
        void onTap(ChartAxes axes);
    }

    public void setOnTapPointListener(OnTapPointListener tapPointListener) {
        this.tapPointListener = tapPointListener;
    }

    /**
     * 滑动至最左加载更多数据
     */
    public interface OnScrollToLoadMore {
        void onLoad();
    }

    public void clearPressed() {
        currentPressedPoint = null;
        refreshChart(0);
    }

    /**
     * 惯性滚动线程
     */
    class InertiaThread extends Thread {

        private int xVelocity;
        private int timeInterval = 1000 / 60; // 单位MS
        private int acceler;

        public InertiaThread(int xVelocity) {
            this.xVelocity = xVelocity;
        }

        @Override
        public void run() {
            isInertia = true;
            boolean isEnable = false;
            while (isInertia) {
                isScrollEnd = false;
                if (xVelocity < 0) {
                    acceler = -200;
                } else {
                    acceler = 200;
                }
                try {
                    int space = (xVelocity - acceler) / 20;

                    if (acceler < 0 && space >= 0) {
                        isInertia = false;
                    }
                    if (acceler > 0 && space <= 0) {
                        isInertia = false;
                    }
                    if (axesData.size() > 1 && (Math.abs(space) > 2)) {

                        int max = axesData.get(axesData.size() - 1).X;
                        int min = axesData.get(0).X;
                        int maxLimit = width - rightPadding - 20;
                        int minLimit = leftPadding + yTextWidth + 20;
                        if (min + space > minLimit) { // 滑动以后左边界判断
                            if (min < minLimit) {
                                space = minLimit - min;
                                isEnable = true;
                            } else {
                                space = 0;
                            }
                        } else {
                            isEnable = true;
                        }
                        if (max + space < maxLimit) {// 滑动后右边界判断
                            if (max > maxLimit) {
                                space = maxLimit - max;
                                isEnable = true;
                            } else {
                                space = 0;
                            }
                        } else {
                            isEnable = true;
                        }
                        if (isEnable) {

                            refreshChart(space);
                        }
                    }
                    xVelocity = xVelocity - acceler * 2;
                    sleep(timeInterval);
                    isScrollEnd = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //惯性线程
    class FlingThread extends Thread {
        //初始速度
        private int velocity;
        private static final float INFLEXION = 0.35f;
        //取得系统摩擦系数
        private float mFlingFriction = ViewConfiguration.getScrollFriction();
        private float mPhysicalCoeff;
        private final float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
        //计算出惯性总时间
        private double mDuration;
        //刷新频率
        private int timeInterval = 1000 / 60; // 单位MS
        //当前惯性耗时
        private int spendTime;

        public FlingThread(float velocityX) {
            velocity = (int) velocityX;
            final float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
            mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.37f // inch/meter
                    * ppi * 0.84f; // look and feel tuning
        }

        @Override
        public void run() {
            isInertia = true;
            boolean isEnable = false;
            if (velocity != 0) {
                mDuration = getSplineFlingDuration(velocity);
                //不断循环直到惯性时间到达或者用户点击屏幕中断滑动
                while (mDuration >= spendTime && isInertia) {
                    //每时间间隔内惯性滑动的距离
                    int space = (int) (velocity + (2 * spendTime / timeInterval + 1) * -velocity / mDuration * timeInterval / 2) * timeInterval
                            / 1000;
                    try {
                        if (axesData.size() > 1) {
                            int max = axesData.get(axesData.size() - 1).X;
                            int min = axesData.get(0).X;
                            int maxLimit = width - rightPadding - 20;
                            int minLimit = leftPadding + yTextWidth + 20;
                            if (min + space > minLimit) { // 滑动以后左边界判断
                                if (min < minLimit) {
                                    isEnable = true;
                                } else {
                                    space = 0;
                                    refreshChart(space);
                                    return;
                                }
                            } else {
                                isEnable = true;
                            }
                            if (max + space < maxLimit) {// 滑动后右边界判断
                                if (max > maxLimit) {
                                    isEnable = true;
                                } else {
                                    space = 0;
                                    refreshChart(space);
                                    return;
                                }
                            } else {
                                isEnable = true;
                            }
                            if (isEnable) {
                                refreshChart(space);
                            }
                        } else {
                            return;
                        }
                        spendTime += timeInterval;
                        sleep(timeInterval);
                        isScrollEnd = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        // 根据速度计算加速度
        private double getSplineDeceleration(int velocity) {
            return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * 2 * mPhysicalCoeff));
        }

        /* Returns the duration, expressed in milliseconds */
        private int getSplineFlingDuration(int velocity) {
            final double l = getSplineDeceleration(velocity);
            final double decelMinusOne = DECELERATION_RATE - 1.0;
            return (int) (1000.0 * Math.exp(l / decelMinusOne));
        }
    }

    public void clear() {
        valueData.clear();
        axesData.clear();
        refreshChart(0);
    }
}
