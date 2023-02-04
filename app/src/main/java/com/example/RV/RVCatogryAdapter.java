package com.example.RV;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Activity.DayInnerActivity;
import com.example.Data.Blog;
import com.example.Data.PageData;
import com.example.Utils;
import com.example.work.R;

import java.util.ArrayList;
import java.util.List;


public class RVCatogryAdapter extends RecyclerView.Adapter<RVCatogryAdapter.VH> {
    private List<PageData> pageDataList = new ArrayList<>();
    private List<Blog> blogList = new ArrayList<>();
    private Activity activity;


    public RVCatogryAdapter(List<PageData> pageDataList, Activity activity, List<Blog> blogs) {
        this.pageDataList = pageDataList;
        this.activity = activity;
        this.blogList = blogs;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(activity).inflate(R.layout.rv_catagory_layout,null));
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        PageData pageData = pageDataList.get(position);

        holder.itemView.setTag(position);
        holder.linearLayout.setBackgroundResource(pageData.getImage());
        holder.progressBar.setProgress(pageData.getCat_progress());
        holder.imageView_rate.setImageResource(pageData.getCat_steg());
        holder.textView_day.setText(pageData.getCat_day());
        holder.textView_description.setText(pageData.getCat_discription());
        holder.textView_name.setText(pageData.getCat_tital());
        holder.textView_taka.setText(pageData.getCat_persanag());

    }

    @Override
    public int getItemCount() {
        return pageDataList == null ? 0 : pageDataList.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        TextView textView_name,textView_description,textView_day,textView_taka;
        ImageView imageView_rate;
        ProgressBar progressBar;

        public VH(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.rv_cat_lliD);
            textView_name = itemView.findViewById(R.id.tv_cat_catID);
            textView_description = itemView.findViewById(R.id.tv_subtext_catID);
            textView_day = itemView.findViewById(R.id.tv_day_id);
            textView_taka = itemView.findViewById(R.id.tv_persentag_id);
            imageView_rate = itemView.findViewById(R.id.iv_rate_catID);
            progressBar = itemView.findViewById(R.id.progress_cat_id);


            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //Open inner Activity
            int position = (int) itemView.getTag();
//            Utils.openDayInnerActivityFromRV(position,activity,blogList,);
        }
    }


    public void setCat_UPdate_Data(int position,int seek_progress,String persentage,String catDay){
        for (int i=0;i<pageDataList.size();i++){
            if (i == position){
                pageDataList.get(i).setCat_progress(seek_progress);
                pageDataList.get(i).setCat_persanag(persentage);
                pageDataList.get(i).setCat_day(catDay);
                Log.d("MMM","Position: "+position + " Seek_progress: "+seek_progress+ " Prcesntag: "+persentage + " Cat_Day: "+catDay);
            }
        }
        notifyDataSetChanged();
    }
}
