package com.cpxiao.sudoku.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cpxiao.AppConfig;
import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.sudoku.mode.extra.Difficulty;
import com.cpxiao.sudoku.mode.extra.Extra;
import com.cpxiao.sudoku.mode.extra.GameMode;
import com.cpxiao.sudoku.views.game.GameView;

/**
 * @author cpxiao on 2017/09/05.
 */

public class BestScoreView extends View {
    private static final boolean DEBUG = AppConfig.DEBUG;
    private static final String TAG = GameView.class.getSimpleName();


    private long[][] mScoreArray;
    private static Paint mPaint = new Paint();

    private int countX = Difficulty.DIFFICULTY_ARRAY.length;
    private int countY = GameMode.MODE_ARRAY.length;

    static {
        mPaint.setAntiAlias(true);//抗锯齿,一般加这个就可以了，加另外两个可能会卡
        //        mPaint.setDither(true);//防抖动
        //        mPaint.setFilterBitmap(true);//用来对位图进行滤波处理
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public BestScoreView(Context context) {
        super(context);
        init();
    }

    public BestScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BestScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Context context = getContext();
        mScoreArray = new long[countY][countX];
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                String key = Extra.Key.getBestScoreKey(GameMode.MODE_ARRAY[y][1], Difficulty.DIFFICULTY_ARRAY[x][1]);
                mScoreArray[y][x] = PreferencesUtils.getLong(context, key, Extra.Key.KEY_BEST_SCORE_DEFAULT);
            }
        }
        if (DEBUG) {
            Log.d(TAG, "init: countX = " + countX + ", countY = " + countY);
        }
    }


    private RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();

        float scoreViewW = width / 6;
        float scoreViewH = height / 8;

        float titleW = width;
        float titleH = height - scoreViewH * (countY + 1);

        float modeW = width - scoreViewW * countX;
        float modeH = scoreViewH;

        float difficultW = scoreViewW;
        float difficultH = difficultW;

        //绘制title
        mPaint.setTextSize(0.1F * width);
        String title = getResources().getString(R.string.best_score);
        canvas.drawText(title, 0.5F * titleW, 0.6F * titleH, mPaint);


        //绘制rectF
        rectF.left = 0;
        rectF.right = width;
        rectF.top = titleH;
        rectF.bottom = height;
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, mPaint);

        mPaint.setTextSize(0.045F * width);
        //绘制mode, 竖向
        for (int y = 0; y < countY; y++) {
            String msg = getResources().getString(GameMode.MODE_ARRAY[y][0]);
            float cY = titleH + modeH + scoreViewH * (y + 0.5F);
            canvas.drawText(msg, 0.5F * modeW, cY, mPaint);
        }

        //绘制difficult，横向
        for (int x = 0; x < countX; x++) {
            String msg = getResources().getString(Difficulty.DIFFICULTY_ARRAY[x][0]);
            float cX = modeW + (x + 0.5F) * scoreViewW;
            canvas.drawText(msg, cX, titleH + 0.5F * difficultH, mPaint);
        }

        mPaint.setTextSize(0.04F * width);
        //绘制分数
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                String score = formatBestScore(mScoreArray[y][x]);
                float cX = modeW + (x + 0.5F) * scoreViewW;
                float cY = titleH + modeH + scoreViewH * (y + 0.5F);
                canvas.drawText(score, cX, cY, mPaint);
            }
        }
    }

    private String formatBestScore(long bestScore) {
        return bestScore / 1000 / 60 + "\"" + bestScore / 1000 % 60 + "\'";
    }
}
