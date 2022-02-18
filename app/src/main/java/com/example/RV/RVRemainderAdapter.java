package com.example.RV;

import android.app.Activity;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Data.PageData;
import com.example.Utils;
import com.example.work.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.Utils.showToast;

public class RVRemainderAdapter extends RecyclerView.Adapter<RVRemainderAdapter.VH> {
    Activity activity;
    List<PageData> pageDataList = new ArrayList<>();

    public RVRemainderAdapter(Activity activity, List<PageData> pageDataList) {
        this.activity = activity;
        this.pageDataList = pageDataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_remainder_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PageData pageData = pageDataList.get(position);
        holder.itemView.setTag(position);
        holder.tv_day.setText(pageData.getDay());
        holder.aSwitch.setChecked(pageData.isIsswitch());
        holder.tv_timer.setText(pageData.getTimer_Remainder());
    }

    @Override
    public int getItemCount() {
        return pageDataList == null ? 0 : pageDataList.size();
    }

    public class VH extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        TextView tv_timer,tv_day;
        Switch aSwitch;
        ImageView imageView;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv_timer = itemView.findViewById(R.id.rv_remainder_tv__ID);
            tv_day = itemView.findViewById(R.id.rv_week_id);
            aSwitch = itemView.findViewById(R.id.rv_remainder_switchID);
            imageView = itemView.findViewById(R.id.rv_iv_remainder_id);

            aSwitch.setOnCheckedChangeListener(this);
            aSwitch.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                aSwitch.setChecked(true);
            else
                aSwitch.setChecked(false);
        }

        @Override
        public void onClick(View view) {
            int position = (int) itemView.getTag();
            PageData pageData = pageDataList.get(position);

            switch (view.getId()){
                case R.id.rv_iv_remainder_id:

                    showToast(activity,"Delete Click" + pageData.getTimer_Remainder());
                    break;

                case R.id.rv_remainder_switchID:
                    showToast(activity,"Cheak Clicked"+pageData.isIsswitch());
                    break;

            }
        }
    }
}
