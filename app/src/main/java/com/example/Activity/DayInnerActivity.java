package com.example.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.Data.Blog;
import com.example.Database.MyDataBase_1;
import com.example.work.R;
import com.example.RV.RecyclerViewInnerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.Utils.INTENT_CODE_INNER;
import static com.example.Utils.hidestatusbar;


public class DayInnerActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerViewInnerAdapter adapter;
    private Toolbar toolbar;
    private Button button;
    private ArrayList<Integer> imagelistArrayList = new ArrayList<>();
    private ArrayList<String> stringArrayListexercisename = new ArrayList<>();
    private ArrayList<Integer> integerArrayListTime = new ArrayList<>();
    private ArrayList<String> exerciselist = new ArrayList<>();

    private int position;
    private int size;
    private MyDataBase_1 dataBase_1;
    private List<Blog> blogList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_inner);

        hidestatusbar(this);

        toolbar = findViewById(R.id.toolbarainner);
        button = findViewById(R.id.btnStartID);
        recyclerView = findViewById(R.id.recyclerviewinnerID);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("toolbarName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        dataBase_1 = new MyDataBase_1(this);

        imagelistArrayList = getIntent().getExtras().getIntegerArrayList("imagelist");
        stringArrayListexercisename = getIntent().getExtras().getStringArrayList("exerciseList");
        exerciselist = getIntent().getExtras().getStringArrayList("disciprion_exercise");
        integerArrayListTime = getIntent().getExtras().getIntegerArrayList("timer");


        size =  getIntent().getExtras().getInt("size");
        position = getIntent().getExtras().getInt("position");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecyclerViewInnerAdapter(this,size,stringArrayListexercisename,imagelistArrayList,integerArrayListTime);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        if (dataBase_1.isChekDatabaseEmpty_TimerData()) {
            blogList = dataBase_1.getTimerBlogListDB();
            for (int i = 0; i < blogList.size(); i++) {
                adapter.updateDatafromDB(blogList.get(i).getA_positionDB(),blogList.get(i).getTimer_value_DB());
                Log.d("FFM","Position: "+ blogList.get(i).getA_positionDB() +
                        " | Timer Value: " + blogList.get(i).getTimer_value_DB());
            }
            adapter.notifyDataSetChanged();
        }
        //regester view...
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            switch (requestCode) {

                case INTENT_CODE_INNER:
                    int position = data.getIntExtra("ddposition", 0);
                    int seekbar = data.getIntExtra("seekbar", 0);
                    int size = data.getIntExtra("size", 0);
                    Log.d("Dm", "day inner on Activity : " + position + ": seekbar: " + seekbar);
                    setResult(RESULT_OK, new Intent().putExtra("ddposition", position).putExtra("seekbar", seekbar).putExtra("size", size));
                    finish();
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnStartID:
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                intent.putExtra("startIV",imagelistArrayList);
                intent.putExtra("startTV",stringArrayListexercisename);
                intent.putExtra("Timerrrr",integerArrayListTime);
                intent.putExtra("exercisedescriptionlist",exerciselist);
                intent.putExtra("postion",position);
                intent.putExtra("size",size);
                startActivityForResult(intent,INTENT_CODE_INNER);
                break;
        }
    }
}
