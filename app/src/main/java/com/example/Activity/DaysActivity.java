package com.example.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.Data.Blog;
import com.example.Data.PageData;
import com.example.Database.MyDataBase_1;
import com.example.RV.RVCatogryAdapter;
import com.example.RV.RecyclerViewAdapter_1;
import com.example.RV.RecyclerViewAdapter_2;
import com.example.RV.RecyclerViewAdapter_3;
import com.example.Utils;
import com.example.work.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.Utils.INTENT_CODE_1;
import static com.example.Utils.INTENT_CODE_2;
import static com.example.Utils.INTENT_CODE_3;
import static com.example.Utils.RESTDAY_CODE_1;
import static com.example.Utils.RESTDAY_CODE_2;
import static com.example.Utils.RESTDAY_CODE_3;
import static com.example.Utils.getBasicDiscription;
import static com.example.Utils.getExercisename1;
import static com.example.Utils.getImagelist1;
import static com.example.Utils.getTimerlist1;
import static com.example.Utils.hidestatusbar;
import static com.example.Utils.showToast;


public class DaysActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_1 adapter_1;
    private RecyclerViewAdapter_2 adapter_2;
    private RecyclerViewAdapter_3 adapter_3;

    private List<Blog> blogArrayList_1 = new ArrayList<>();
    private List<Blog> blogArrayList_2 = new ArrayList<>();
    private List<Blog> blogArrayList_3 = new ArrayList<>();
    private Toolbar toolbar;

    private List<PageData> pageDataList = new ArrayList<>();

    private RecyclerView recyclerView_cat;
    //one catagory
    int left_day_1=28;
    int seek_day_1=0;

    //two_catogory
    int left_day_2=28;
    int seek_day_2 = 0;

    //three catagory
    int left_day_3=28;
    int seek_day_3=0;


    private RVCatogryAdapter rvCatogryAdapter;
    //database
    private MyDataBase_1 dataBase_1;

    private List<Blog> blogList_database = new ArrayList<>();

    //animation for recyclerview
    private LayoutAnimationController controller;
    //catagory rv layout manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        //Language...

        hidestatusbar(this);

        Utils.updateResourse(this,Utils.getLanguage(this));

        dataBase_1 = new MyDataBase_1(DaysActivity.this);

        toolbar = findViewById(R.id.toolbarlistID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.armworkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        recyclerView = findViewById(R.id.revyvlerVireID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);

        //animation recyclerview
        controller = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation);

        //Adapter initialization..

        adapter_1 = new RecyclerViewAdapter_1(DaysActivity.this,blogArrayList_1);
        adapter_2 = new RecyclerViewAdapter_2(DaysActivity.this,blogArrayList_2);
        adapter_3 = new RecyclerViewAdapter_3(DaysActivity.this,blogArrayList_3);


        recyclerView.setAdapter(adapter_1);
        adapter_1.notifyDataSetChanged();


        recyclerView_cat = findViewById(R.id.rv_day_layout);
        recyclerView_cat.setHasFixedSize(true);
        recyclerView_cat.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView_cat.setLayoutManager(linearLayoutManager);
        recyclerView_cat.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        rvCatogryAdapter = new RVCatogryAdapter(Utils.getImageList(this),DaysActivity.this,blogArrayList_1);

        recyclerView_cat.setAdapter(rvCatogryAdapter);
        rvCatogryAdapter.notifyDataSetChanged();




        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_cat);

        blogArrayList_1.clear();
        blogArrayList_1.add(new Blog(getString(R.string.day1),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day2),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day3),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day4),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day5),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day6),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day7),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day8),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day9),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog(getString(R.string.day10),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 11","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 12","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 13","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 14","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 15","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 16","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 17","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 18","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 19","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 20","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 21","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 22","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 23","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 24","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 25","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 26","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 27","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 28","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 29","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_1.add(new Blog("Day 30","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));


        blogArrayList_2.clear();
        blogArrayList_2.add(new Blog(getString(R.string.day1),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day2),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day3),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day4),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day5),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day6),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day7),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day8),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day9),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog(getString(R.string.day10),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 11","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 12","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 13","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 14","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 15","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 16","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 17","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 18","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 19","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 20","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 21","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 22","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 23","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 24","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 25","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 26","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 27","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 28","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 29","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_2.add(new Blog("Day 30","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));


        blogArrayList_3.clear();
        blogArrayList_3.add(new Blog(getString(R.string.day1),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day2),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day3),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day4),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day5),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day6),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day7),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day8),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day9),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog(getString(R.string.day10),"17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 11","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 12","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 13","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 14","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 15","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 16","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 17","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 18","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 19","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 20","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 21","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 22","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 23","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 24","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 25","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 26","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 27","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 28","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 29","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));
        blogArrayList_3.add(new Blog("Day 30","17",getImagelist1(),getExercisename1(this),getTimerlist1(),getBasicDiscription()));



        //first get all data from db...

        if (dataBase_1.isChekDatabaseEmpty_Catagory_one()){
            pageDataList.clear();
            pageDataList = dataBase_1.getData_catagory_one();
            for (int i=0;i<pageDataList.size();i++){
                left_day_1 = Integer.parseInt(pageDataList.get(i).getCat_day());
                seek_day_1 = pageDataList.get(i).getCat_progress();

                rvCatogryAdapter.setCat_UPdate_Data(pageDataList.get(i).getAdapter_position_cat(),
                        pageDataList.get(i).getCat_progress(),pageDataList.get(i).getCat_persanag(),
                        pageDataList.get(i).getCat_day()+" LEFTS DAY ");
                Log.d("MMM","Yess is innn");

            }
        }

        if (dataBase_1.isChekDatabaseEmpty_Catagory_two()){
            pageDataList.clear();
            pageDataList = dataBase_1.getData_catagory_two();
            for (int i=0;i<pageDataList.size();i++){
                left_day_2 = Integer.parseInt(pageDataList.get(i).getCat_day());
                seek_day_2 = pageDataList.get(i).getCat_progress();

                rvCatogryAdapter.setCat_UPdate_Data(pageDataList.get(i).getAdapter_position_cat(),
                        pageDataList.get(i).getCat_progress(),pageDataList.get(i).getCat_persanag(),
                        pageDataList.get(i).getCat_day()+" LEFTS DAY ");
                Log.d("MMM","Yess is innn");

            }
        }


        if (dataBase_1.isChekDatabaseEmpty_Catagory_three()){
            pageDataList.clear();
            pageDataList = dataBase_1.getData_catagory_three();
            for (int i=0;i<pageDataList.size();i++){
                left_day_3 = Integer.parseInt(pageDataList.get(i).getCat_day());
                seek_day_3 = pageDataList.get(i).getCat_progress();

                rvCatogryAdapter.setCat_UPdate_Data(pageDataList.get(i).getAdapter_position_cat(),
                        pageDataList.get(i).getCat_progress(),pageDataList.get(i).getCat_persanag(),
                        pageDataList.get(i).getCat_day()+" LEFTS DAY ");
                Log.d("MMM","Yess is innn");

            }
        }


        if (dataBase_1.isChekDatabaseEmpty_one()){
            blogList_database.clear();
            blogList_database = dataBase_1.getListData_one();
            for (int i=0;i<blogList_database.size();i++) {
                adapter_1.setCondition(blogList_database.get(i).getAdapter_position(),
                        blogList_database.get(i).getSeekbarprogress(),
                        blogList_database.get(i).getList_size(),
                        blogList_database.get(i).getRestday_code());
                Log.d("FFM", "R_CODE: " +blogList_database.get(i).getRestday_code() +
                        " | Progress_s: "+blogList_database.get(i).getSeekbarprogress()+
                        " | List_size: "+blogList_database.get(i).getList_size()+
                        " | Adater_P: "+ blogList_database.get(i).getAdapter_position());
            }
        }
        if (dataBase_1.isChekDatabaseEmpty_two()){
            blogList_database.clear();
            blogList_database= dataBase_1.getListData_two();
            for (int i=0;i<blogList_database.size();i++) {
                adapter_2.setCondition(blogList_database.get(i).getAdapter_position(),
                        blogList_database.get(i).getSeekbarprogress(),
                        blogList_database.get(i).getList_size(),
                        blogList_database.get(i).getRestday_code());
                Log.d("FFM", "R_CODE: " +blogList_database.get(i).getRestday_code() +
                        " | Progress_s: "+blogList_database.get(i).getSeekbarprogress()+
                        " | List_size: "+blogList_database.get(i).getList_size()+
                        " | Adater_P: "+ blogList_database.get(i).getAdapter_position());

            }
        }

        if (dataBase_1.isChekDatabaseEmpty_three()){
            blogList_database.clear();
            blogList_database = dataBase_1.getListData_three();
            for (int i=0;i<blogList_database.size();i++) {
                adapter_3.setCondition(blogList_database.get(i).getAdapter_position(),
                        blogList_database.get(i).getSeekbarprogress(),
                        blogList_database.get(i).getList_size(),
                        blogList_database.get(i).getRestday_code());
                Log.d("FFM", "R_CODE: " +blogList_database.get(i).getRestday_code() +
                        " | Progress_s: "+blogList_database.get(i).getSeekbarprogress()+
                        " | List_size: "+blogList_database.get(i).getList_size()+
                        " | Adater_P: "+ blogList_database.get(i).getAdapter_position());
            }
        }

        //cat_recycelrview scroll position ..
        recyclerView_cat.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerViewA, int newState) {
                super.onScrollStateChanged(recyclerViewA, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();

                    Log.d("FFM", String.valueOf(position));

                    recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(DaysActivity.this, R.anim.layout_animation_fall_down));
                    recyclerView.scheduleLayoutAnimation();

                    switch (position) {
                        case 0:
                            toolbar.setTitle(R.string.armworkout);
                            recyclerView.setAdapter(adapter_1);
                            adapter_1.notifyDataSetChanged();
                            break;

                        case 1:
                            toolbar.setTitle(getString(R.string.intermediat));
                            recyclerView.setAdapter(adapter_2);
                            adapter_2.notifyDataSetChanged();
                            break;

                        case 2:
                            toolbar.setTitle(getString(R.string.advance));
                            recyclerView.setAdapter(adapter_3);
                            adapter_3.notifyDataSetChanged();
                            break;
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.bmiID:
                startActivity(new Intent(this,BMIActivity.class));
                break;

            case R.id.settingID:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){

                //adapter One
                case INTENT_CODE_1:
                    int position_adapter_1 = data.getIntExtra("ddposition",0);
                    int seekbar_progress_1 = data.getIntExtra("seekbar",0);
                    int size_list_1= data.getIntExtra("size",0);
                    Log.d("Dm","day Activity on Activity : " + position_adapter_1 + " Seek: "+seekbar_progress_1 + " size: "+size_list_1);
                    dataBase_1.insertdatabase_one(new Blog(position_adapter_1,seekbar_progress_1,size_list_1,0));

                    if (seekbar_progress_1 == size_list_1 || seekbar_progress_1 == 16) {
                        seek_day_1++;
                        left_day_1--;
                        rvCatogryAdapter.setCat_UPdate_Data(0, seek_day_1, seek_day_1 * 100 / 30 + "%", left_day_1 + " LEFTS DAY");
                        dataBase_1.insertData_catagory_one(new PageData(left_day_1+"",seek_day_1*100/30+"%",seek_day_1,0));
                    }
                    if (seekbar_progress_1 == 16)
                        adapter_1.setCondition(position_adapter_1,seekbar_progress_1+1,size_list_1,0);
                    else
                        adapter_1.setCondition(position_adapter_1,seekbar_progress_1,size_list_1,0);
                    break;

                case RESTDAY_CODE_1:

                    int day_code_1 = data.getIntExtra("day_code",0);
                    Log.d("DM",day_code_1+ " Activity ");
                    seek_day_1++;
                    left_day_1--;

                    rvCatogryAdapter.setCat_UPdate_Data(0,seek_day_1,seek_day_1*100/30+"%",left_day_1+" LEFTS DAY");
                    dataBase_1.insertData_catagory_one(new PageData(left_day_1+"",seek_day_1*100/30+"%",seek_day_1,0));

                    dataBase_1.insertdatabase_one(new Blog(0,0,0,day_code_1));
                    adapter_1.setCondition(0,0,0,day_code_1);

                    break;

                //adapter Two

                case INTENT_CODE_2:
                    int position_adapter_2 = data.getIntExtra("ddposition",0);
                    int seekbar_progress_2 = data.getIntExtra("seekbar",0);
                    int size_list_2 = data.getIntExtra("size",0);

                    if (16 == seekbar_progress_2 || seekbar_progress_2 == size_list_2) {
                        seek_day_2++;
                        left_day_2--;
                        rvCatogryAdapter.setCat_UPdate_Data(1, seek_day_2, seek_day_2 * 100 / 30 + "%", left_day_2 + " LEFTS DAY");
                        dataBase_1.insertData_catagory_two(new PageData(left_day_2+"",seek_day_2*100/30+"%",seek_day_2,1));
                    }


                    dataBase_1.insertdatabase_two(new Blog(position_adapter_2,seekbar_progress_2,size_list_2,0));

                    if (seekbar_progress_2 == 16)
                        adapter_2.setCondition(position_adapter_2,seekbar_progress_2+1,size_list_2,0);
                    else
                        adapter_2.setCondition(position_adapter_2,seekbar_progress_2,size_list_2,0);


                    break;

                case RESTDAY_CODE_2:
                    int day_code_2 = data.getIntExtra("day_code",0);
                    Log.d("DM",day_code_2 + " Activity ");

                    seek_day_2++;
                    left_day_2--;

                    rvCatogryAdapter.setCat_UPdate_Data(1,seek_day_2,seek_day_2*100/30+"%",left_day_2+" LEFTS DAY");
                    dataBase_1.insertData_catagory_two(new PageData(left_day_2+"",seek_day_2*100/30+"%",seek_day_2,1));

                    dataBase_1.insertdatabase_two(new Blog(0,0,0,day_code_2));
                    adapter_2.setCondition(0,0,0,day_code_2);

                    break;

                //adapter Three
                case INTENT_CODE_3:
                    int position_adapter_3 = data.getIntExtra("ddposition",0);
                    int seekbar_progress_3 = data.getIntExtra("seekbar",0);
                    int size_list_3 = data.getIntExtra("size",0);

                    if (seekbar_progress_3 == size_list_3 || seekbar_progress_3 == 16) {
                        seek_day_3++;
                        left_day_3--;
                        rvCatogryAdapter.setCat_UPdate_Data(2, seek_day_3, seek_day_3 * 100 / 30 + "%", left_day_3 + " LEFTS DAY");
                        dataBase_1.insertData_catagory_three(new PageData(left_day_3+"",seek_day_3*100/30+"%",seek_day_3,2));
                    }

                    dataBase_1.insertdatabase_three(new Blog(position_adapter_3,seekbar_progress_3,size_list_3,0));

                    if (seekbar_progress_3 == 16)
                        adapter_3.setCondition(position_adapter_3,seekbar_progress_3+1,size_list_3,0);
                    else
                        adapter_3.setCondition(position_adapter_3,seekbar_progress_3,size_list_3,0);

                    break;


                case RESTDAY_CODE_3:
                    int day_code_3= data.getIntExtra("day_code",0);
                    Log.d("DM",day_code_3+ " Activity ");

                    seek_day_3++;
                    left_day_3--;

                    rvCatogryAdapter.setCat_UPdate_Data(2,seek_day_3,seek_day_3*100/30+"%",left_day_3+" LEFTS DAY");
                    dataBase_1.insertData_catagory_three(new PageData(left_day_3+"",seek_day_3*100/30+"%",seek_day_3,2));

                    dataBase_1.insertdatabase_three(new Blog(0,0,0,day_code_3));
                    adapter_3.setCondition(0,0,0,day_code_3);

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
