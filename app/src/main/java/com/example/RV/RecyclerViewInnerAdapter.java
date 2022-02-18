package com.example.RV;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Data.Blog;
import com.example.Database.MyDataBase_1;
import com.example.work.R;

import java.util.ArrayList;

public class RecyclerViewInnerAdapter extends RecyclerView.Adapter<RecyclerViewInnerAdapter.VH> {
    private Activity context;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayList<Integer> imageViews = new ArrayList<>();
    private ArrayList<Integer> timerIntegerArrayList = new ArrayList<>();
    private int size;
    private int positionAdd=0;
    private MyDataBase_1 dataBase_1;
    private Blog blog_DB = new Blog();

    public RecyclerViewInnerAdapter(Activity context, int sizeA,ArrayList<String> stringArrayList, ArrayList<Integer> imageViews,ArrayList<Integer> timerIntegerArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
        this.imageViews = imageViews;
        this.timerIntegerArrayList = timerIntegerArrayList;
        this.size = sizeA;
        this.dataBase_1 = new MyDataBase_1(context);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_context,parent,false));
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.itemView.setTag(position);
        String name = stringArrayList.get(position);
        int image = imageViews.get(position);

        holder.textView1timer.setText(timerIntegerArrayList.get(position)/1000+" s");


        holder.textViewName.setText(name);
        Glide.with(context)
                .load(image)
                .centerCrop()
                .placeholder(Color.WHITE)
                .into(holder.imageViewList);
    }

    @Override
    public int getItemCount() {
        return imageViews == null ? 0 : imageViews.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
      TextView textViewName,textView1timer;
      ImageView imageViewList;

        public VH(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.exerNamID);
            imageViewList = itemView.findViewById(R.id.innerIVID);
            textView1timer = itemView.findViewById(R.id.tv_timerID);


            itemView.setOnClickListener(this);

        }

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View v)  {

            final int position = (int) itemView.getTag();
            Button buttonSave,buttonReste;
            final ImageView imageViewCancle,imageViewMainIV,imageViewAdd,imageViewRemove;
            final TextView textView,textViewCount;

            final AlertDialog alertDialog;
            AlertDialog.Builder builder;

            View view = LayoutInflater.from(context).inflate(R.layout.rv_eachexercise_layout,null);

            buttonSave = view.findViewById(R.id.saveID);
            buttonReste = view.findViewById(R.id.resetID);
            textViewCount = view.findViewById(R.id.tvcountID);
            imageViewCancle = view.findViewById(R.id.closeID);
            imageViewMainIV = view.findViewById(R.id.iveachID);
            imageViewAdd = view.findViewById(R.id.addID);
            imageViewRemove = view.findViewById(R.id.ivremoveID);
            textView = view.findViewById(R.id.nameID);

            textView.setText("Exercise Name: "+stringArrayList.get(position));
            Glide.with(context).load(imageViews.get(position)).placeholder(Color.WHITE).into(imageViewMainIV);

            textViewCount.setText("Timer: "+timerIntegerArrayList.get(position)/1000+"");


            builder = new AlertDialog.Builder(context);
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();


            positionAdd = timerIntegerArrayList.get(position)/1000;

            imageViewCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            imageViewAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionAdd++;
                    textViewCount.setText("Timer: "+String.valueOf(positionAdd));
                    imageViewRemove.setClickable(true);
                    textViewCount.setTextColor(timerIntegerArrayList.get(position) / 1000 == positionAdd ? context.getResources().getColor(R.color.view_color): Color.BLACK);

                }
            });

            imageViewRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (positionAdd == 5)
                        imageViewRemove.setClickable(false);
                    else
                        positionAdd--;

                    textViewCount.setText("Timer: "+ String.valueOf(positionAdd));
                    textViewCount.setTextColor(timerIntegerArrayList.get(position) / 1000 == positionAdd ? context.getResources().getColor(R.color.view_color): Color.BLACK);
                }
            });

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewAdd.setClickable(true);

                    blog_DB.setA_positionDB(position);
                    blog_DB.setTimer_value_DB(Integer.parseInt(positionAdd+"000"));

                    // add to database..
                    dataBase_1.insertTimer(blog_DB);


                    Log.d("DM","First: " + timerIntegerArrayList.get(position)+"");
                    timerIntegerArrayList.set(position,Integer.parseInt(positionAdd+"000"));
                    textView1timer.setText(timerIntegerArrayList.get(position)/1000+" s ");
                    alertDialog.dismiss();
                    Log.d("DM","Sec: " + timerIntegerArrayList.get(position)+"");

                    notifyDataSetChanged();
                }
            });

            buttonReste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewAdd.setClickable(true);
                    textViewCount.setText("Timer: "+timerIntegerArrayList.get(position)/1000);
                    positionAdd = timerIntegerArrayList.get(position) / 1000;
                    textViewCount.setTextColor(context.getResources().getColor(R.color.view_color));

                }
            });
        }
    }

    public void updateDatafromDB(int adapter_position,int timer_value){
        timerIntegerArrayList.set(adapter_position,timer_value);
    }
}
