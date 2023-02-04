package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.example.Utils;
import com.example.work.R;

import static com.example.Utils.openDataPikerDialog;

public class HelthDataActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView textView_gender;
    LinearLayout ll_DOB,ll_Gender;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helth_data);

        Utils.hidestatusbar(this); //for Hide Status Bar..
        toolbar = findViewById(R.id.toolbar_helth_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        mDisplayDate = findViewById(R.id.dob_tvID);
        textView_gender = findViewById(R.id.gender_tv);
        ll_DOB = findViewById(R.id.ll_helth_dobID);
        ll_Gender = findViewById(R.id.ll_helth_genderID);

        textView_gender.setText(Utils.get_shareprefre_Gender(this));
        mDisplayDate.setText(Utils.getDOB_SP(this));

        ll_DOB.setOnClickListener(this);
        ll_Gender.setOnClickListener(this);
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
            case R.id.ll_helth_genderID:
                Utils.openMulti_radio_Dialog(this,textView_gender);
                Utils.showToast(this,"Gender Click");
                break;
            case R.id.ll_helth_dobID:
                openDataPikerDialog(this,mDisplayDate);
                Utils.showToast(this,"DOB Click");
                break;
        }
    }
}
