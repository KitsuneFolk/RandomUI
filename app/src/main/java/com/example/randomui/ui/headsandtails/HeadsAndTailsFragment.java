package com.example.randomui.ui.headsandtails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.randomui.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class HeadsAndTailsFragment extends Fragment implements View.OnClickListener {

    private View root;

    private Random random = new Random();

    private Button HeadsAndTails_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.w("MyLogs", "HeadsAndTailsFragment: onCreateView");
        root = inflater.inflate(R.layout.fragment_headsandtails, container, false);
        HeadsAndTails_btn = root.findViewById(R.id.HeadsAndTails_btn);
        HeadsAndTails_btn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HeadsAndTails_btn:
                Log.w("MyLogs", "HeadsAndTailsFragment.onClick: HeadsAndTails_btn is clicked");
                getNum();
                short duration = 500;
                Snackbar sb = Snackbar.make(v, result, duration);
                sb.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                sb.show();


        }
    }

    private int min = 0;
    private int max = 1;
    private int diff = max - min;
    private int i;
    private String result;

    private void getNum() {
        i = random.nextInt(diff + 1);
        i += min;
        if (i == 0) {
            result = "Орёл";
        } else {
            result = "Решка";
        }
        Log.w("MyLogs", "HeadsAndTailsFragment.getNum: Toast is made.");
    }
}