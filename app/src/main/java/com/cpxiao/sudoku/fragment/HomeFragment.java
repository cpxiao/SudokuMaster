package com.cpxiao.sudoku.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
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
        Button bestScoreBtn = (Button) view.findViewById(R.id.btn_best_score);
        Button settingsBtn = (Button) view.findViewById(R.id.btn_settings);
        Button quitBtn = (Button) view.findViewById(R.id.btn_quit);

        btn4Grid.setOnClickListener(this);
        btn6Grid.setOnClickListener(this);
        btn9Grid.setOnClickListener(this);
        btn12Grid.setOnClickListener(this);
        btn16Grid.setOnClickListener(this);
        bestScoreBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Context context = getHoldingActivity();
        if (id == R.id.btn_4_grid) {
            //            Bundle bundle = GameActivity.makeBundle(2, 2, Extra.Difficulty.DIFFICULTY_DEFAULT, true);
            //            Intent intent = GameActivity.makeIntent(HomeActivity.this, bundle);
            //            startActivity(intent);
            Bundle bundle = GameFragment.makeBundle(2, 2, Difficulty.DIFFICULTY_DEFAULT[1], true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_6_grid) {
            //            Bundle bundle = GameActivity.makeBundle(2, 3, Extra.Difficulty.DIFFICULTY_DEFAULT, true);
            //            Intent intent = GameActivity.makeIntent(HomeActivity.this, bundle);
            //            startActivity(intent);
            Bundle bundle = GameFragment.makeBundle(2, 3, Difficulty.DIFFICULTY_DEFAULT[1], true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_9_grid) {
            //            Bundle bundle = GameActivity.makeBundle(3, 3, Extra.Difficulty.DIFFICULTY_DEFAULT, true);
            //            Intent intent = GameActivity.makeIntent(HomeActivity.this, bundle);
            //            startActivity(intent);
            Bundle bundle = GameFragment.makeBundle(3, 3, Difficulty.DIFFICULTY_DEFAULT[1], true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_12_grid) {
            //            Bundle bundle = GameActivity.makeBundle(3, 4, Extra.Difficulty.DIFFICULTY_DEFAULT, true);
            //            Intent intent = GameActivity.makeIntent(HomeActivity.this, bundle);
            //            startActivity(intent);
            Bundle bundle = GameFragment.makeBundle(3, 4, Difficulty.DIFFICULTY_DEFAULT[1], true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_16_grid) {
            //            Bundle bundle = GameActivity.makeBundle(4, 4, Extra.Difficulty.DIFFICULTY_DEFAULT, true);
            //            Intent intent = GameActivity.makeIntent(HomeActivity.this, bundle);
            //            startActivity(intent);
            Bundle bundle = GameFragment.makeBundle(4, 4, Difficulty.DIFFICULTY_DEFAULT[1], true);
            addFragment(GameFragment.newInstance(bundle));
        } else if (id == R.id.btn_best_score) {
            addFragment(BestScoreFragment.newInstance(null));
        } else if (id == R.id.btn_settings) {
            addFragment(SettingsFragment.newInstance(null));
        } else if (id == R.id.btn_quit) {
            //            showQuitConfirmDialog();
            removeFragment();
        }
    }
}
