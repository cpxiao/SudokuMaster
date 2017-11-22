package com.cpxiao.sudoku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.androidutils.library.utils.RateAppUtils;
import com.cpxiao.androidutils.library.utils.ShareAppUtils;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.sudoku.mode.extra.Difficulty;
import com.cpxiao.zads.core.ZAdPosition;

/**
 * @author cpxiao on 2017/09/05.
 */

public class HomeFragment extends BaseZAdsFragment implements View.OnClickListener {
    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadZAds(ZAdPosition.POSITION_HOME);

        Button btn4Grid = (Button) view.findViewById(R.id.btn_4_grid);
        Button btn6Grid = (Button) view.findViewById(R.id.btn_6_grid);
        Button btn9Grid = (Button) view.findViewById(R.id.btn_9_grid);
        Button btn12Grid = (Button) view.findViewById(R.id.btn_12_grid);
        Button btn16Grid = (Button) view.findViewById(R.id.btn_16_grid);
        Button btn20Grid = (Button) view.findViewById(R.id.btn_20_grid);
        ImageButton bestRateApp = (ImageButton) view.findViewById(R.id.btn_rate_app);
        ImageButton bestShare = (ImageButton) view.findViewById(R.id.btn_share);
        ImageButton bestScoreBtn = (ImageButton) view.findViewById(R.id.btn_best_score);
        ImageButton settingsBtn = (ImageButton) view.findViewById(R.id.btn_settings);

        btn4Grid.setOnClickListener(this);
        btn6Grid.setOnClickListener(this);
        btn9Grid.setOnClickListener(this);
        btn12Grid.setOnClickListener(this);
        btn16Grid.setOnClickListener(this);
        btn20Grid.setOnClickListener(this);
        bestRateApp.setOnClickListener(this);
        bestShare.setOnClickListener(this);
        bestScoreBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Context context = getHoldingActivity();
        int difficulty = PreferencesUtils.getInt(context, Difficulty.KEY_DIFFICULTY, Difficulty.DIFFICULTY_DEFAULT[1]);
        if (id == R.id.btn_4_grid) {
            Bundle bundle = GameFragment.makeBundle(2, 2, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_6_grid) {
            Bundle bundle = GameFragment.makeBundle(2, 3, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_9_grid) {
            Bundle bundle = GameFragment.makeBundle(3, 3, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_12_grid) {
            Bundle bundle = GameFragment.makeBundle(3, 4, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_16_grid) {
            Bundle bundle = GameFragment.makeBundle(4, 4, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_20_grid) {
            Bundle bundle = GameFragment.makeBundle(4, 5, difficulty, true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_rate_app) {
            RateAppUtils.rate(context);
        } else if (id == R.id.btn_share) {
            String msg = getString(R.string.share_msg) + "\n" +
                    getString(R.string.app_name) + "\n" +
                    "https://play.google.com/store/apps/details?id=" + context.getPackageName();
            ShareAppUtils.share(context, getString(R.string.share), msg);
        } else if (id == R.id.btn_best_score) {
            addFragment(BestScoreFragment.newInstance(null));
        } else if (id == R.id.btn_settings) {
            addFragment(SettingsFragment.newInstance(null));
        }
    }
}
