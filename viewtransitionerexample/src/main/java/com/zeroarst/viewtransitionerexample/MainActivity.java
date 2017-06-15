package com.zeroarst.viewtransitionerexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
            case R.id.bt2:
            case R.id.bt3:
            case R.id.bt4:
            case R.id.bt5:
                showExample(Integer.parseInt((String) v.getTag()));
                break;
        }
    }

    private void showExample(int id) {
        getSupportFragmentManager().beginTransaction()
            .add(android.R.id.content, MainFragment.create(id))
            .addToBackStack("BACK_STACK_EXAMPLE")
            .commit();
    }

}
