package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.Data.PageData;
import com.example.RV.RVRemainderAdapter;
import com.example.Utils;
import com.example.work.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.Utils.getReminderTime;
import static com.example.Utils.setRemainderTime;
import static com.example.Utils.showToast;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RVRemainderAdapter rvRemainderAdapter;
    List<PageData> pageDataList = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Utils.hidestatusbar(this);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getReminderTime(getApplicationContext()));

        toolbar = findViewById(R.id.toolbar_remainderID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        floatingActionButton = findViewById(R.id.float_add_ID);
        recyclerView = findViewById(R.id.rv_remainderID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        rvRemainderAdapter = new RVRemainderAdapter(ReminderActivity.this,pageDataList);
        recyclerView.setAdapter(rvRemainderAdapter);
        rvRemainderAdapter.notifyDataSetChanged();


        //regerster View..

        floatingActionButton.setOnClickListener(this);

        pageDataList.clear();

        pageDataList.add(new PageData("05:31","Sun,Mon,Tue,Thu,Fri,Sat",true));
        pageDataList.add(new PageData("05:31","Mon,Tue,Thu,Fri,Sat",true));
        pageDataList.add(new PageData("05:31","SunThu,Fri,Sat",false));
        pageDataList.add(new PageData("05:31","Sun,Mon,Fri,Sat",true));
        pageDataList.add(new PageData("05:31","Sun,Mon,Tue,Thu,Sat",false));



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
            case R.id.float_add_ID:
                TextView textView = new TextView(this);
                setRemainderTime(this,calendar,textView);
                showToast(this,"Float Btn Click");
                break;

        }
    }
}
