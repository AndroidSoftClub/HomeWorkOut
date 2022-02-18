package com.example.RV;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Data.Blog;
import com.example.work.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.Utils.INTENT_CODE_1;
import static com.example.Utils.RESTDAY_CODE_1;
import static com.example.Utils.openDayInnerActivityFromRV;
import static com.example.Utils.openRestTimeActivityFromRV;


public class RecyclerViewAdapter_1 extends RecyclerView.Adapter<RecyclerViewAdapter_1.VH> {
    private Activity context;
    private List<Blog> blogArrayList = new ArrayList<>();

    public RecyclerViewAdapter_1(Activity context, List<Blog> blogArrayList) {
        this.context = context;
        this.blogArrayList = blogArrayList;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.context_list,parent,false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {


        holder.itemView.setTag(position);
        holder.progressBar.setMax(blogArrayList.get(position).isChek() ? blogArrayList.get(position).getSize() : 0);
        holder.progressBar.setProgressDrawable(blogArrayList.get(position).isChek() ? blogArrayList.get(position).getExerciseName().size() == blogArrayList.get(position).getSeekbar() ? context.getResources().getDrawable(R.drawable.congo) : context.getResources().getDrawable(R.drawable.circular_progress_bar) : position == 6 || position == 12 || position == 18 || position == 24 ? blogArrayList.get(position).ischekDay ? context.getResources().getDrawable(R.drawable.greentikck_image) : context.getResources().getDrawable(R.drawable.ic_rest_day_future) : blogArrayList.get(position).getExerciseName().size() == blogArrayList.get(position).getSeekbar() ? context.getResources().getDrawable(R.drawable.congo) : context.getResources().getDrawable(R.drawable.circular_progress_bar));
        holder.progressBar.setProgress(blogArrayList.get(position).isChek() ? blogArrayList.get(position).getSeekbar() : 0);

        holder.textViewProgress.setText(blogArrayList.get(position).isChek() ? blogArrayList.get(position).getSeekbar() * 100 / blogArrayList.get(position).getSize()+"%" : position == 6 || position == 12 || position == 18 || position == 24 ? blogArrayList.get(position).ischekDay? "" : "0%" : "0%");
        holder.textViewProgress.setTextColor(blogArrayList.get(position).isIsrestDay()? Color.WHITE : context.getResources().getColor(R.color.view_color));

        holder.cardView.setBackgroundColor(blogArrayList.get(position).isIsrestDay() ? context.getResources().getColor(R.color.cardcolor) : context.getResources().getColor(R.color.ll_color));

        holder.textViewEXERCISE.setTextColor(blogArrayList.get(position).isIsrestDay() ? Color.WHITE : context.getResources().getColor(R.color.view_color));
        holder.textViewEXERCISE.setText(blogArrayList.get(position).getExerciseTotal());

        holder.textViewDAYNAME.setTextColor(blogArrayList.get(position).isIsrestDay() ? Color.WHITE : context.getResources().getColor(R.color.view_color));
        holder.textViewDAYNAME.setText(blogArrayList.get(position).getDayName());


    }

    @Override
    public int getItemCount() {
        return blogArrayList == null ? 0 : blogArrayList.size();
    }


    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewDAYNAME,textViewEXERCISE,textViewProgress;
        ProgressBar progressBar;
        LinearLayout linearLayout;
        CardView cardView;
        public VH(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardID);
            progressBar = itemView.findViewById(R.id.progressBar);
            textViewDAYNAME = itemView.findViewById(R.id.dayTVID);
            linearLayout = itemView.findViewById(R.id.llID);
            textViewEXERCISE = itemView.findViewById(R.id.exerxiseTVID);
            textViewProgress = itemView.findViewById(R.id.tvprogresID);

            itemView.setOnClickListener(this);


        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {

            int position= (int) itemView.getTag();

            if (position == 6 || position == 12 || position == 18 || position == 24)
                openRestTimeActivityFromRV(position,context,blogArrayList,RESTDAY_CODE_1);
            else
                openDayInnerActivityFromRV(position,context,blogArrayList,INTENT_CODE_1);
        }
    }


    //todo: From onActivity Result Function..

    public void setCondition(int position,int seek,int size, int daycode){

        if (daycode == 0) {
            for (int i = 0; i < blogArrayList.size(); i++) {
                if (blogArrayList.get(i).isIsrestDay())
                    blogArrayList.get(i).setIsrestDay(false);
            }

            for (int i = 0; i < blogArrayList.size(); i++) {
                if (i == position) {
                    blogArrayList.get(position).setChek(true);
                    blogArrayList.get(position).setSeekbar(seek);
                    blogArrayList.get(position).setSize(size);
                    blogArrayList.get(size == seek ? position == blogArrayList.size() - 1 ? 0 : i + 1 : position == blogArrayList.size() - 1 ? 0 : i).setIsrestDay(true);
                    break;
                }
            }
        }else {
            for (int i=0;i<blogArrayList.size();i++){
                if (blogArrayList.get(i).isIsrestDay())
                    blogArrayList.get(i).setIsrestDay(false);
            }

            for (int i=0;i<blogArrayList.size();i++){
                if (daycode == i){
                    blogArrayList.get(i).setIschekDay(true);
                    blogArrayList.get(i+1).setIsrestDay(true);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

}
