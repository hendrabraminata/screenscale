package com.touchplanet.myapplication;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.touchplanet.myapplication.DisplayUtils.AUX_DISPLAY;
import static com.touchplanet.myapplication.DisplayUtils.DISPLAY;
import static com.touchplanet.myapplication.DisplayUtils.DISPLAY_OVERSCAN_BOTTOM;
import static com.touchplanet.myapplication.DisplayUtils.DISPLAY_OVERSCAN_LEFT;
import static com.touchplanet.myapplication.DisplayUtils.DISPLAY_OVERSCAN_RIGHT;
import static com.touchplanet.myapplication.DisplayUtils.DISPLAY_OVERSCAN_TOP;
import static com.touchplanet.myapplication.DisplayUtils.MAIN_DISPLAY;
import static com.touchplanet.myapplication.DisplayUtils.OVER_SCAN_BOTTOM;
import static com.touchplanet.myapplication.DisplayUtils.OVER_SCAN_LEFT;
import static com.touchplanet.myapplication.DisplayUtils.OVER_SCAN_RIGHT;
import static com.touchplanet.myapplication.DisplayUtils.OVER_SCAN_TOP;
import static com.touchplanet.myapplication.DisplayUtils.PREF;
import static com.touchplanet.myapplication.DisplayUtils.initOverScan;
import static com.touchplanet.myapplication.DisplayUtils.setOverScan;

public class MainActivity extends AppCompatActivity {

    /*============================================================================================*/
    // Member

    private static final int MODE_ADD = 1;
    private static final int MODE_SUB = 2;

    private Rect overscan;
    private Integer mode;
    private Integer display;

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Butterknife

    @BindView(R.id.activity_main_up)
    ImageButton activity_main_up;

    @BindView(R.id.activity_main_down)
    ImageButton activity_main_down;

    @BindView(R.id.activity_main_left)
    ImageButton activity_main_left;

    @BindView(R.id.activity_main_right)
    ImageButton activity_main_right;

    @BindView(R.id.activity_main_up_rev)
    ImageButton activity_main_up_rev;

    @BindView(R.id.activity_main_down_rev)
    ImageButton activity_main_down_rev;

    @BindView(R.id.activity_main_left_rev)
    ImageButton activity_main_left_rev;

    @BindView(R.id.activity_main_right_rev)
    ImageButton activity_main_right_rev;

    @BindView(R.id.activity_main_button_exit)
    Button activity_main_button_exit;

    @BindView(R.id.activity_main_spinner)
    Spinner activity_main_spinner;

    @OnClick(R.id.activity_main_button_mode)
    public void activity_main_button_mode() {
        switchMode();
    }

    @OnClick(R.id.activity_main_up)
    public void activity_main_up() {
        overscan.top = overscan.top + 1;
        setOverScan(DISPLAY_OVERSCAN_TOP, overscan.top, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_down)
    public void activity_main_down() {
        overscan.bottom = overscan.bottom + 1;
        setOverScan(DISPLAY_OVERSCAN_BOTTOM, overscan.bottom, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_left)
    public void activity_main_left() {
        overscan.left = overscan.left + 1;
        setOverScan(DISPLAY_OVERSCAN_LEFT, overscan.left, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_right)
    public void activity_main_right() {
        overscan.right = overscan.right + 1;
        setOverScan(DISPLAY_OVERSCAN_RIGHT, overscan.right, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_up_rev)
    public void activity_main_up_rev() {
        overscan.bottom = overscan.bottom - 1;
        setOverScan(DISPLAY_OVERSCAN_BOTTOM, overscan.bottom, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_down_rev)
    public void activity_main_down_rev() {
        overscan.top = overscan.top - 1;
        setOverScan(DISPLAY_OVERSCAN_TOP, overscan.top, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_left_rev)
    public void activity_main_left_rev() {
        overscan.right = overscan.right - 1;
        setOverScan(DISPLAY_OVERSCAN_RIGHT, overscan.right, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_right_rev)
    public void activity_main_right_rev() {
        overscan.left = overscan.left - 1;
        setOverScan(DISPLAY_OVERSCAN_LEFT, overscan.left, display);
        saveOverScan();
    }

    @OnClick(R.id.activity_main_button_exit)
    public void onClick(View view) {
        finish();
        System.exit(0);
    }


    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Override Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreatePrepareMember();
        onCreatePrepareView();
        onCreatePrepareOther();

    }

    private void updateArrowButton() {

        int normal = mode == MODE_ADD ? VISIBLE : GONE;
        int reverse = mode == MODE_ADD ? GONE : VISIBLE;

        activity_main_up.setVisibility(normal);
        activity_main_down.setVisibility(normal);
        activity_main_right.setVisibility(normal);
        activity_main_left.setVisibility(normal);
        activity_main_up_rev.setVisibility(reverse);
        activity_main_down_rev.setVisibility(reverse);
        activity_main_right_rev.setVisibility(reverse);
        activity_main_left_rev.setVisibility(reverse);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LockUtils.hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LockUtils.showSystemUi();
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Other Method

    private void onCreatePrepareMember() {
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
        display = prefs.getInt(DISPLAY, MAIN_DISPLAY);

        mode = MODE_ADD;

        overscan = initOverScan(this);
    }

    private void onCreatePrepareView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        updateArrowButton();
    }

    private void onCreatePrepareOther() {
        Integer[] item = new Integer[]{MAIN_DISPLAY, AUX_DISPLAY};
        final ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item);
        activity_main_spinner.setAdapter(adapter);
        activity_main_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                display = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveOverScan() {
        SharedPreferences.Editor editor = getSharedPreferences(PREF, MODE_PRIVATE).edit();
        editor.putInt(OVER_SCAN_TOP, overscan.top);
        editor.putInt(OVER_SCAN_BOTTOM, overscan.bottom);
        editor.putInt(OVER_SCAN_RIGHT, overscan.right);
        editor.putInt(OVER_SCAN_LEFT, overscan.left);
        editor.putInt(DISPLAY, display);
        editor.commit();
    }

    private void switchMode() {
        mode = mode == MODE_ADD ? MODE_SUB : MODE_ADD;
        updateArrowButton();
    }

    /*--------------------------------------------------------------------------------------------*/

}
