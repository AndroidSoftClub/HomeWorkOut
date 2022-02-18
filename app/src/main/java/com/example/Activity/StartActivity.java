package com.example.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.example.Broadcast.MyReceiver;
import com.example.CountDown.CountDownTimerA;
import com.example.Utils;
import com.example.work.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.Utils.hidestatusbar;
import static com.example.Utils.setVideoUri;
import static com.example.Utils.showToast;
import static com.example.Utils.shownotification;
import static com.example.Utils.underlineTV;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextToSpeech tts;
    private ImageView nextBtn,imageViewPlay,imageViewSound,backBtn,mainimageview,next_imageview_dialog,back_iv_dialog;
    private TextView textRedytoGo,textViewSTART,e_name_nextdialog,timer_next_dialog,textView_last_dialog,back_tv_ename_dialog,bac_tv_timer_dialog;
    private Button button_last_dialog,back_dialog_btn_skip;
    private ProgressBar progressBarCOUNT;

    private ArrayList<Integer> imageArrayList = new ArrayList<>();
    private ArrayList<Integer> timerarraylist = new ArrayList<>();
    private ArrayList<String> exercisenamelist = new ArrayList<>();
    private ArrayList<String> discriptionlist = new ArrayList<>();

    private boolean isChekSound=true;
    private boolean isPlay = false;
    private int adapterPostion = 0;
    private int position =0;
    private int size;
    private int counterSeek = 0;

    private CountDownTimerA countDownNextDialog;
    private CountDownTimerA maincoutdownTimer;
    private CountDownTimerA countDownPrevDialog;

    private Button next_skip_button;
    private AlertDialog.Builder buildernextDialod;
    private AlertDialog.Builder builder_lastdialog;
    private AlertDialog.Builder builder_back_dialog;
    private AlertDialog.Builder onbackpress_dialog;

    private AlertDialog alertDialognext;
    private AlertDialog alertDialog_lastDialog;
    private AlertDialog alertDialog_back_btn;
    private AlertDialog alertDialog_onbackepress;

    private View viewNextDialog;
    private View view_last_dialog;
    private View view_back_dialog;
    private View view_onback_dialog;
    private View dialogView;


    //bottom sheet
    private BottomSheetDialog dialog_bottmsheet;
    private BottomSheetBehavior<FrameLayout> behavior;
    private ImageView main_IV;
    private TextView textView_tital;
    private Button button_continu_bottmsheet;
    private TextView textView_discription_bottm;


    private SoundPool soundPool;
    private SoundPool soundPoolLasteTime;
    private boolean isNBA = true;
    private int soundId;
    private int soundlasteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Initialize AlerDialog...
        buildernextDialod = new AlertDialog.Builder(this);
        builder_back_dialog = new AlertDialog.Builder(this);
        builder_lastdialog = new AlertDialog.Builder(this);
        onbackpress_dialog = new AlertDialog.Builder(this);


        hidestatusbar(this);         // Hide Status bar
        findallView();                     //View Finding
        getDataFromRV();                   //get all data from RV


        backBtn.setVisibility(View.GONE);
        nextBtn.setVisibility(View.VISIBLE);

        //set a Raw audio id..
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPoolLasteTime = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundId = soundPool.load(StartActivity.this, R.raw.td_tick, 1);
        soundlasteID = soundPoolLasteTime.load(StartActivity.this,R.raw.td_ding,1);



        //set a tts voice
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                    }
                }
            }
        });

        createnextDialog();                 //First create a next exercise dialog
    }

    @SuppressLint("ResourceType")
    private void maincounterMethod() {// Main Activity Timer
        if (countDownPrevDialog != null) {
            countDownPrevDialog.cancel();
            countDownPrevDialog = null;
        }else if (countDownNextDialog != null) {
            countDownNextDialog.cancel();
            countDownNextDialog = null;
        }

        tts.stop();

        if (isChekSound)
            setvoiceSpeech("start our exercise" + exercisenamelist.get(position));

        progressBarCOUNT.setVisibility(position == imageArrayList.size() - 1 ? View.GONE : View.VISIBLE);
        progressBarCOUNT.setMax(timerarraylist.get(position)/1000);
        Glide.with(this)
                .load(imageArrayList.get(position))
                .placeholder(Color.WHITE)
                .into(mainimageview);
        textViewSTART.setText(exercisenamelist.get(position));
        nextBtn.setImageResource(position == imageArrayList.size() - 1 ? R.drawable.greentikck_image : R.drawable.right_button);

        maincoutdownTimer = new CountDownTimerA(timerarraylist.get(position), 1000) { //1 sec gap
            @SuppressLint("ResourceType")
            public void onTick(long millisUntilFinished) {
                long countertime = millisUntilFinished/1000;

                textRedytoGo.setText(position == imageArrayList.size() - 1 ? "Done.." : countertime +"");

                Log.d("DM","Main Cpunter Time: "+countertime+"");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    progressBarCOUNT.setProgress((int) countertime,true);
                else
                    progressBarCOUNT.setProgress(counterSeek);

                if (countertime == timerarraylist.get(position)/1000/2+7 && isChekSound)
                    setvoiceSpeech(discriptionlist.get(position));
                else if (countertime  == timerarraylist.get(position)/1000/2 && isChekSound)
                    setvoiceSpeech("Half the Time");
                else if (countertime == 3 && isChekSound)
                    setvoiceSpeech("Three");
                else if (countertime == 2 && isChekSound)
                    setvoiceSpeech("Two");
                else if (countertime == 1 && isChekSound)
                    setvoiceSpeech("one");
                else {
                    if (isChekSound)
                        soundPool.play(soundId, 1, 1, 0, 0, 1);
                }
            }
            @SuppressLint("ResourceType")
            public void onFinish() {

               if (isChekSound)
                   soundPoolLasteTime.play(soundId, 1, 1, 0, 0, 1);

               position++;
               counterSeek++;
               createnextDialog();
            }
        };

        if (position == imageArrayList.size()-1)
            maincoutdownTimer.cancel();
        else
            maincoutdownTimer.start();

    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    private void createnextDialog() {
        tts.stop();
        if (maincoutdownTimer != null) {
            maincoutdownTimer.cancel();
            maincoutdownTimer = null;
        }
        else if (countDownPrevDialog != null) {
            countDownPrevDialog.cancel();
            countDownPrevDialog = null;
        }


        mainimageview.setImageResource(imageArrayList.get(position));  //Pic Pause
        progressBarCOUNT.setMax(timerarraylist.get(position) / 1000);  //set Timer max
        progressBarCOUNT.setProgress(0);
        nextBtn.setVisibility(View.VISIBLE);
        isPlay = false;
        backBtn.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        textRedytoGo.setText(position == imageArrayList.size() - 1 ? "✔" : "0");
        textRedytoGo.setTextColor(position == imageArrayList.size() -1 ? Color.BLUE : Color.BLACK);

        textViewSTART.setText(exercisenamelist.get(position));
        progressBarCOUNT.setVisibility(position == imageArrayList.size() - 1 ? View.GONE : View.VISIBLE);
        nextBtn.setImageResource(position == imageArrayList.size() - 1 ? R.drawable.greentikck_image : R.drawable.right_button);
        nextBtn.setClickable(position == imageArrayList.size() - 1);
        backBtn.setClickable(position == imageArrayList.size() - 1);

        //after create a next Dialog
        alertDialognext.show();

        Glide.with(this)
                .load(imageArrayList.get(position))
                .placeholder(Color.WHITE)
                .into(next_imageview_dialog);
        e_name_nextdialog.setText(this.getString(R.string.next_name) + exercisenamelist.get(position) + "\nTime: " + timerarraylist.get(position) / 1000 + " sec..");


        counterNextDialog();

    }

    @SuppressLint("ResourceType")
    private void counterNextDialog() {

        if (isChekSound)
            setvoiceSpeech("tack a rest time");

        countDownNextDialog = new CountDownTimerA(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                long temp_sec = millisUntilFinished/1000;
                timer_next_dialog.setText("Rest Time: \n " +temp_sec);

                 if (temp_sec == 3 && isChekSound)
                        setvoiceSpeech("Three");
                else if (temp_sec == 2 && isChekSound)
                        setvoiceSpeech("Two");
                else if (millisUntilFinished /1000 == 1 && isChekSound)
                        setvoiceSpeech("One");
            }

            public void onFinish() {
                tts.stop();
                Glide.with(getApplicationContext())
                        .load(imageArrayList.get(position))
                        .placeholder(Color.WHITE)
                        .into(mainimageview);
                nextBtn.setClickable(true);
                backBtn.setClickable(true);
                imageViewPlay.setClickable(true);
                alertDialognext.dismiss();
                imageViewPlay.setImageResource(R.drawable.pause);

                maincounterMethod();
            }
        }.start();
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
    public void onBackPressed() {
        //setResult
        setResult(RESULT_OK, new Intent()
                .putExtra("seekbar",counterSeek)
                .putExtra("ddposition",adapterPostion)
                .putExtra("size",size));

        alertDialog_onbackepress.show();

    }


    @Override
    protected void onRestart(){
        if (maincoutdownTimer != null && imageViewPlay.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.play).getConstantState())
            Log.d("DM","No Action");
        else if (countDownNextDialog != null)
            countDownNextDialog.resume();
        else {
            if (maincoutdownTimer != null)
                maincoutdownTimer.resume();
            else
                countDownPrevDialog.resume();
        }
        super.onRestart();
        Log.d("DM", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPoolLasteTime.release();
        soundPoolLasteTime = null;
        soundPool = null;
        tts.stop();
        tts.shutdown();
        Log.d("DM","onDestroy");

    }


    @Override
    protected void onPause() {
        if (maincoutdownTimer != null)
            maincoutdownTimer.pause();
        else if (countDownNextDialog != null)
            countDownNextDialog.pause();
        else if (countDownPrevDialog != null)
            countDownPrevDialog.pause();
        super.onPause();
        Log.d("DM","onPause");
    }


    private void getDataFromRV() {
        size = (int) getIntent().getExtras().get("size");
        exercisenamelist = getIntent().getExtras().getStringArrayList("startTV");
        imageArrayList = getIntent().getExtras().getIntegerArrayList("startIV");
        timerarraylist = getIntent().getExtras().getIntegerArrayList("Timerrrr");
        discriptionlist = getIntent().getExtras().getStringArrayList("exercisedescriptionlist");
        adapterPostion = getIntent().getExtras().getInt("postion");
    }

    @SuppressLint("SetTextI18n")
    private void findallView() {
        toolbar = findViewById(R.id.toolbaraStartID);
        progressBarCOUNT = findViewById(R.id.progress_bar_2);
        imageViewSound = findViewById(R.id.sound_ivID);
        backBtn = findViewById(R.id.backImgID);
        imageViewPlay = findViewById(R.id.ivplayID);
        textRedytoGo = findViewById(R.id.redyTVID);
        nextBtn = findViewById(R.id.nextBTNID);
        mainimageview = findViewById(R.id.startIMGID);
        textViewSTART = findViewById(R.id.textStartID);

        //Regester view..
        progressBarCOUNT.setOnClickListener(this);
        imageViewSound.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        textRedytoGo.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        //Action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.back_button);
        upArrow.setColorFilter(getResources().getColor(R.color.view_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        //Activity Click..
        mainimageview.setOnClickListener(this);
        textViewSTART.setOnClickListener(this);


        //bottom sheet dialog..

        dialogView = LayoutInflater.from(this).inflate(R.layout.buttomsheet_custom_layout, null);
        ImageView imageViewCClose;
        LinearLayout linearLayout_video;
        TextView textView_video;

        imageViewCClose = dialogView.findViewById(R.id.close_bottm_id);
        button_continu_bottmsheet = dialogView.findViewById(R.id.btn_bottomsheet_id);
        textView_video = dialogView.findViewById(R.id.video_text_ID);
        linearLayout_video = dialogView.findViewById(R.id.iv_llvideo_bottm_id);
        textView_tital = dialogView.findViewById(R.id.tv_bottmname_id);
        main_IV = dialogView.findViewById(R.id.iv_bottmsheet_id);
        textView_discription_bottm = dialogView.findViewById(R.id.tb_basicdiscription_id);


        textView_video.setText(underlineTV("VIDEO"));



        imageViewCClose.setOnClickListener(this);
        linearLayout_video.setOnClickListener(this);
        button_continu_bottmsheet.setOnClickListener(this);

        dialog_bottmsheet= new BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme);
        dialog_bottmsheet.setCancelable(true);
        dialog_bottmsheet.setContentView(dialogView);




        //design_bottom_sheet
        FrameLayout bottomSheet = (FrameLayout) dialog_bottmsheet.findViewById(R.id.design_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        dialog_bottmsheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
                maincoutdownTimer.resume();
                imageViewPlay.setImageResource(R.drawable.pause);
                isPlay=false;
        Log.d("FFM","Discmiiss Lis");
            }
        });

        //onBackpress time Dialog
        view_onback_dialog = LayoutInflater.from(this).inflate(R.layout.onback_layout,null);
        onbackpress_dialog.setView(view_onback_dialog);
        onbackpress_dialog.setCancelable(true);
        alertDialog_onbackepress= onbackpress_dialog.create(); //created view


        Button onback_cancel_btn = view_onback_dialog.findViewById(R.id.onback_dialog_quit_btn);
        Button onback_continu_btn = view_onback_dialog.findViewById(R.id.onback_continue_dialog_brn);
        ImageView imageView_cancel_btn = view_onback_dialog.findViewById(R.id.omback_dialog_iv_canvel);
        TextView onback_tv = view_onback_dialog.findViewById(R.id.onback_dialog_tv_id);


        onback_tv.setText(underlineTV(this.getString(R.string.comeback)));



        onback_cancel_btn.setOnClickListener(this);
        onback_continu_btn.setOnClickListener(this);
        onback_tv.setOnClickListener(this);
        imageView_cancel_btn.setOnClickListener(this);

        //Dialog_next
        viewNextDialog = LayoutInflater.from(this)
                .inflate(R.layout.rest_time, null);

        buildernextDialod.setCancelable(false);
        buildernextDialod.setView(viewNextDialog);
        alertDialognext = buildernextDialod.create(); //created..

        next_skip_button = viewNextDialog.findViewById(R.id.next_skipp_btn);
        next_imageview_dialog= viewNextDialog.findViewById(R.id.ttivID);
        e_name_nextdialog = viewNextDialog.findViewById(R.id.tttvID);
        timer_next_dialog = viewNextDialog.findViewById(R.id.tttvNameID);

        next_skip_button.setOnClickListener(this);

        //Dialog Last
        view_last_dialog = LayoutInflater.from(this).inflate(R.layout.buttomsheet_context,null);

        builder_lastdialog.setView(view_last_dialog);
        builder_lastdialog.setCancelable(false);
        alertDialog_lastDialog= builder_lastdialog .create(); //create a Dialog


        button_last_dialog = view_last_dialog.findViewById(R.id.btn_next_last_dialogID);
        textView_last_dialog = view_last_dialog.findViewById(R.id.tv_last_dialog);

        textView_last_dialog.setText(this.getString(R.string.well));

        button_last_dialog.setOnClickListener(this);

        //Dialog Back btn
        view_back_dialog = LayoutInflater.from(this).inflate(R.layout.rest_time_back,null);

        builder_back_dialog.setCancelable(false);
        builder_back_dialog.setView(view_back_dialog);
        alertDialog_back_btn= builder_back_dialog.create();


        back_dialog_btn_skip = view_back_dialog.findViewById(R.id.back_btn_dialog_skip);
        back_tv_ename_dialog = view_back_dialog.findViewById(R.id.bbtttvID);
        bac_tv_timer_dialog  = view_back_dialog.findViewById(R.id.bbtttvNameID);
        back_iv_dialog = view_back_dialog.findViewById(R.id.bbttivID);
        back_dialog_btn_skip.setOnClickListener(this);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()){


            //bottom sheet cancel dialog..

            case R.id.btn_bottomsheet_id:
                dialog_bottmsheet.dismiss();
                maincoutdownTimer.resume();
                imageViewPlay.setImageResource(R.drawable.pause);
                isPlay=false;

                break;
            case R.id.iv_llvideo_bottm_id:

                maincoutdownTimer.pause();
                dialog_bottmsheet.dismiss();
                imageViewPlay.setImageResource(R.drawable.play);
                isPlay=true;
                setVideoUri(this,"https://www.youtube.com/watch?v=fcN37TxBE_s");

                break;

            case R.id.close_bottm_id:
                maincoutdownTimer.resume();
                imageViewPlay.setImageResource(R.drawable.pause);
                isPlay=false;
                dialog_bottmsheet.dismiss();

                break;


            case R.id.back_btn_dialog_skip:
                soundPool.pause(soundId);
                tts.stop();

                if (countDownPrevDialog != null) {
                    countDownPrevDialog.cancel();
                    countDownPrevDialog = null;
                }
                else if (countDownNextDialog != null){
                    countDownNextDialog.cancel();
                    countDownNextDialog = null;
                }
                else if (maincoutdownTimer != null){
                    maincoutdownTimer.cancel();
                    maincoutdownTimer = null;
                }


                alertDialog_back_btn.dismiss();
                isPlay = false;
                imageViewPlay.setImageResource(R.drawable.pause);
                backBtn.setVisibility(position == 0 ? View.GONE : View.VISIBLE);


                imageViewPlay.setClickable(true);
                backBtn.setClickable(true);
                nextBtn.setClickable(true);
                maincounterMethod();

                break;


            case R.id.sound_ivID:
                imageViewSound.setImageResource(isChekSound ? R.drawable.sound_off : R.drawable.sound_1);
                isChekSound= !isChekSound;

                tts.stop();
                soundPool.pause(soundId);


                break;

                //play and puase button..
            case R.id.ivplayID:
                soundPool.pause(soundId);
                tts.stop();

                if (nextBtn.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.greentikck_image).getConstantState()) {
                    imageViewPlay.setImageResource(R.drawable.pause);
                }
                else {

                    if (isPlay) {
                        if (countDownPrevDialog != null) {
                            countDownPrevDialog.cancel();
                            countDownPrevDialog = null;
                        } else if (countDownNextDialog != null) {
                            countDownNextDialog.cancel();
                            countDownNextDialog = null;
                        }
                        else
                        if (maincoutdownTimer != null && isNBA) {
                            maincoutdownTimer.resume();
                            isNBA=true;
                            isPlay=!isPlay;
                            imageViewPlay.setImageResource(R.drawable.pause);
                        }

                    }
                    else {
                        if (countDownNextDialog != null){
                            countDownNextDialog.cancel();
                            countDownNextDialog = null;
                        }
                        else if (countDownPrevDialog != null){
                            countDownPrevDialog.cancel();
                            countDownPrevDialog= null;
                        }
                        else
                            if (maincoutdownTimer != null && isNBA) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isNBA=true;
                                    }
                                },500);
                                maincoutdownTimer.pause();
                                isNBA=false;
                                isPlay=!isPlay;
                                dialog_bottmsheet.show();
                                imageViewPlay.setImageResource(R.drawable.play);
                                textView_discription_bottm.setText(discriptionlist.get(position));
                                textView_tital.setText(exercisenamelist.get(position));
                                Glide.with(this)
                                        .load(imageArrayList.get(position))
                                        .placeholder(Color.WHITE)
                                        .into(main_IV);

                            }
                    }
                }

                Glide.with(this)
                        .load(imageArrayList.get(position))
                        .placeholder(Color.WHITE)
                        .into(mainimageview);

                //showBottomSheetDialog(this);

                break;

            //back button
            case R.id.backImgID:
                imageViewPlay.setClickable(false);
                nextBtn.setClickable(false);
                backBtn.setClickable(false);

                soundPool.pause(soundId);

                tts.stop();

                counterSeek--;
                position--;
                nextBtn.setImageResource(R.drawable.right_button);
                textRedytoGo.setText("0");
                isPlay = false;
                imageViewPlay.setImageResource(R.drawable.pause);

                if (position == imageArrayList.size())
                    maincoutdownTimer=null;
                else {
                    if (countDownPrevDialog != null) {
                        countDownPrevDialog.cancel();
                        countDownPrevDialog = null;
                    }
                    else if (countDownNextDialog != null){
                        countDownNextDialog.cancel();
                        countDownNextDialog = null;
                    }
                    else if (maincoutdownTimer != null){
                        maincoutdownTimer.cancel();
                        maincoutdownTimer = null;
                    }

                }

                backBtn.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                mainimageview.setImageResource(imageArrayList.get(position));

                alertDialognext.dismiss();
                alertDialog_back_btn.show();


                back_tv_ename_dialog.setText(this.getString(R.string.preveuse_exercise) + exercisenamelist.get(position) + "\nTime: " + timerarraylist.get(position) / 1000 + " sec..");
                Glide.with(this)
                        .load(imageArrayList.get(position))
                        .placeholder(Color.WHITE)
                        .into(back_iv_dialog);

                preverseCounter();

                break;

                //nextbutton..
            case R.id.nextBTNID:
                imageViewPlay.setClickable(false);
                nextBtn.setClickable(false);
                backBtn.setClickable(false);

                soundPool.pause(soundId);

                tts.stop();

                alertDialog_back_btn.dismiss();

                counterSeek++;
                isPlay = false;
                imageViewPlay.setImageResource(R.drawable.pause);
                backBtn.setVisibility(View.VISIBLE);
                if (countDownPrevDialog != null) {
                    countDownPrevDialog.cancel();
                    countDownPrevDialog = null;
                }
                else if (countDownNextDialog != null){
                    countDownNextDialog.cancel();
                    countDownNextDialog = null;
                }
                else if (maincoutdownTimer != null){
                    maincoutdownTimer.cancel();
                    maincoutdownTimer = null;
                }

                progressBarCOUNT.setVisibility(position == imageArrayList.size() - 1 ? View.GONE : View.VISIBLE);
                position = position == imageArrayList.size()- 1 ?  position=0 : position+1 ;

                if (nextBtn.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.greentikck_image).getConstantState())
                    alertDialog_lastDialog.show();
                else
                    createnextDialog();

                break;

            case R.id.next_skipp_btn:
                soundPool.pause(soundId);

                tts.stop();

                imageViewPlay.setImageResource(R.drawable.pause);
                imageViewPlay.setClickable(true);
                nextBtn.setImageResource(R.drawable.right_button);
                backBtn.setClickable(true);
                nextBtn.setClickable(true);

                if (countDownPrevDialog != null) {
                    countDownPrevDialog.cancel();
                    countDownPrevDialog = null;
                }
                else if (countDownNextDialog != null){
                    countDownNextDialog.cancel();
                    countDownNextDialog = null;
                }
                else if (maincoutdownTimer != null){
                    maincoutdownTimer.cancel();
                    maincoutdownTimer = null;
                }

                backBtn.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

                alertDialognext.dismiss();
                maincounterMethod();
                break;
            case R.id.btn_next_last_dialogID:

                if (maincoutdownTimer != null){
                    maincoutdownTimer.cancel();
                    maincoutdownTimer=null;
                }
                setResult(RESULT_OK,new Intent()
                        .putExtra("ddposition",adapterPostion)
                        .putExtra("seekbar",counterSeek)
                        .putExtra("size",size));

                alertDialog_lastDialog.dismiss();
                finish();

                break;

            case R.id.onback_continue_dialog_brn:
                alertDialog_onbackepress.dismiss();
                break;

            case R.id.onback_dialog_quit_btn:
                tts.stop();
                soundPool.pause(soundId);
                alertDialog_onbackepress.dismiss();
                finish();
                break;

            case R.id.onback_dialog_tv_id:
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this.getApplicationContext(), 234324243, new Intent(this,MyReceiver.class), 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10 * 1000), pendingIntent);

                showToast(this,this.getString(R.string.alarm));
                finish();
                break;

            case R.id.omback_dialog_iv_canvel:
                alertDialog_onbackepress.dismiss();
                break;


        }
    }

    private void setvoiceSpeech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void preverseCounter() {
        if (isChekSound)
            setvoiceSpeech("Tack a rest time");

        if (maincoutdownTimer != null) {
            maincoutdownTimer.cancel();
            maincoutdownTimer = null;
        }
        else if (countDownNextDialog != null){
            countDownNextDialog.cancel();
            countDownNextDialog = null;
        }

        countDownPrevDialog  = new CountDownTimerA(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long temp_sec = millisUntilFinished/1000;
                bac_tv_timer_dialog .setText("Rest Time:\n" + temp_sec +"");
                if (temp_sec  == 3 && isChekSound)
                    setvoiceSpeech("Three");
                else if (temp_sec == 2 && isChekSound)
                    setvoiceSpeech("Two");
                else if (temp_sec == 1 && isChekSound)
                    setvoiceSpeech("One");
            }

            @Override
            public void onFinish() {
                alertDialog_back_btn.dismiss();
                imageViewPlay.setImageResource(R.drawable.pause);
                imageViewPlay.setClickable(true);
                backBtn.setClickable(true);
                nextBtn.setClickable(true);
                maincounterMethod();
            }
        }.start();
    }
}

