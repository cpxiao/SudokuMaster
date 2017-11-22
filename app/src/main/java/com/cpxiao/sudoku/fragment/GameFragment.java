package com.cpxiao.sudoku.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.gamelib.views.TimeTextView;
import com.cpxiao.sudoku.imps.OnGameListener;
import com.cpxiao.sudoku.mode.extra.Difficulty;
import com.cpxiao.sudoku.mode.extra.Extra;
import com.cpxiao.sudoku.mode.extra.GameMode;
import com.cpxiao.sudoku.views.game.GameView;
import com.cpxiao.zads.core.ZAdPosition;

/**
 * @author cpxiao on 2017/09/05.
 */

public class GameFragment extends BaseZAdsFragment {
    private TimeTextView mTimeTextView;
    private int mBigBlockCountX;
    private int mBigBlockCountY;
    private int mDifficulty;
    private LinearLayout mGameViewLayout;

    public static GameFragment newInstance(Bundle bundle) {
        GameFragment fragment = new GameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadZAds(ZAdPosition.POSITION_GAME);

        Bundle bundle = getArguments();
        Context context = getHoldingActivity();
        if (bundle == null) {
            if (DEBUG) {
                throw new NullPointerException("bundle must not be null!");
            }
            return;
        }

        mTimeTextView = (TimeTextView) view.findViewById(R.id.time_view);
        mGameViewLayout = (LinearLayout) view.findViewById(R.id.game_view_layout);

        mBigBlockCountX = bundle.getInt(GameMode.BIG_BLOCK_COUNT_X, 3);
        mBigBlockCountY = bundle.getInt(GameMode.BIG_BLOCK_COUNT_Y, 3);
        mDifficulty = bundle.getInt(Difficulty.KEY_DIFFICULTY, Difficulty.DIFFICULTY_DEFAULT[1]);


        initData(context, bundle);

    }

    private void initData(Context context, Bundle bundle) {
        mTimeTextView.setTimeMillis(0);
        mTimeTextView.start();
        loadGameView(context, bundle);
    }

    private void loadGameView(Context context, Bundle bundle) {
        GameView gameView = new GameView(context, bundle);
        gameView.setOnGameListener(mOnGameListener);
        mGameViewLayout.removeAllViews();
        mGameViewLayout.addView(gameView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimeTextView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimeTextView.pause();
    }

    public static Bundle makeBundle(int modeX, int modeY, int difficulty, boolean isNewGame) {
        Bundle bundle = new Bundle();
        bundle.putInt(GameMode.BIG_BLOCK_COUNT_X, modeX);
        bundle.putInt(GameMode.BIG_BLOCK_COUNT_Y, modeY);
        bundle.putInt(Difficulty.KEY_DIFFICULTY, difficulty);
        bundle.putBoolean(Extra.Name.INTENT_EXTRA_IS_NEW_GAME, isNewGame);
        return bundle;
    }


    private OnGameListener mOnGameListener = new OnGameListener() {

        /**
         * 防止多次回调
         */
        private boolean isSuccess = false;

        @Override
        public void onGameSuccess() {
            if (isSuccess) {
                return;
            }
            isSuccess = true;

            final Context context = getHoldingActivity();

            long time = mTimeTextView.getTimeMillis();
            long bestScore = getBestScore(context);
            if (bestScore == 0 || time < bestScore) {
                updateBestScore(context, time);
            }
            //停止计时，并记录最高分
            mTimeTextView.pause();

            //弹出dialog
            AlertDialog dialog = new AlertDialog.Builder(context).
                    setTitle(R.string.you_win).
                    setMessage(R.string.start_a_new_game).
                    setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = makeBundle(mBigBlockCountX, mBigBlockCountY, mDifficulty, true);

                            initData(context, bundle);
                            isSuccess = false;
                        }
                    }).
                    setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeFragment();
                        }
                    }).
                    create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            if (DEBUG) {
                Toast.makeText(context, "onGameSuccess", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private long getBestScore(Context context) {
        int mode = mBigBlockCountX * mBigBlockCountY;
        String key = Extra.Key.getBestScoreKey(mode, mDifficulty);
        return PreferencesUtils.getLong(context, key, Extra.Key.KEY_BEST_SCORE_DEFAULT);
    }

    private void updateBestScore(Context context, long bestScore) {
        int mode = mBigBlockCountX * mBigBlockCountY;
        String key = Extra.Key.getBestScoreKey(mode, mDifficulty);
        PreferencesUtils.putLong(context, key, bestScore);
    }

}
