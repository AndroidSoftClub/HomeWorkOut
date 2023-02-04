package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Utils;
import com.example.work.R;

public class BMIActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText editText_Weight,editText_height;
    Button button_cal;
    TextView textView_score,textView_calremark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        Utils.hidestatusbar(this);

        toolbar = findViewById(R.id.bmi_tool_ID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        editText_Weight = findViewById(R.id.weight_bmi_id);
        editText_height = findViewById(R.id.height_bmi_id);
        button_cal = findViewById(R.id.btn_bmi_id);
        textView_calremark = findViewById(R.id.tv_bmi_remarkcatID);
        textView_score = findViewById(R.id.tv_bmi_scoreID);

        button_cal.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bmi_id:
                String weight = editText_Weight.getText().toString();
                String height = editText_height.getText().toString();

                if (!TextUtils.isEmpty(weight) && !TextUtils.isEmpty(height)){
                    Utils.showToast(this,"Yes");
                    closeKeyboard();
                    calculate();
                }
                else{

                    Utils.showToast(this,"No");
                }

                break;
        }
    }

    public void closeKeyboard(){

        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }



    }  //Collapse Keyboard

    public void calculate(){
        String weight = editText_Weight.getText().toString();
        String height = editText_height.getText().toString();
        if(weight.isEmpty()){
            editText_Weight.setError("Kindly enter your Weight");
        }
        else if(height.isEmpty()){
            editText_height.setError("Kindly enter your Height");
        }

        else {
            float h = Float.parseFloat(height);
            float w = Float.parseFloat(weight);
            double a = (h * h * 0.01 * 0.01 );

            double bmi = w / a;

            if(bmi >= 18.5 && bmi <= 25.0)
            {
                textView_calremark.setText("Normal");
            }
            else if( bmi <=18.5){
                textView_calremark.setText("UnderWeight");
            }
            else if( bmi >= 25.0 && bmi <= 30.0){
                textView_calremark.setText("ovrWeight");
            }
            else if( bmi >30.0){
                textView_calremark.setText("obese");
            }
            int bmi_result = (int)bmi;
            textView_score.setText("Score: "+bmi_result);

            int res = (int) bmi;
//            textView_score.setText(String.valueOf("res"));


        }


    }

}
