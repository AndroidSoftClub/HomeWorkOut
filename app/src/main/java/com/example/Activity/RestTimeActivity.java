package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.work.R;

import static com.example.Utils.hidestatusbar;


public class RestTimeActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Button button;
    int adapter_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_time);

        hidestatusbar(this);
        toolbar = findViewById(R.id.toolrestID);
        setSupportActionBar(toolbar);

        String name = getIntent().getExtras().getString("tool");
        adapter_position = getIntent().getExtras().getInt("position");

        getSupportActionBar().setTitle(name + "(Rest Time)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        button = findViewById(R.id.btnfinishID);
        button.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnfinishID:
                setResult(RESULT_OK,new Intent().putExtra("day_code",adapter_position));
                finish();
                break;
        }
    }
}
