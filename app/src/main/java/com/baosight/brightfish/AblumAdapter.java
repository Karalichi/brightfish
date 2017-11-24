package com.baosight.brightfish;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by saitama on 2017/11/24.
 */
public class AblumAdapter extends RecyclerView.Adapter<AblumAdapter.ViewHolder> implements View.OnClickListener{
    private static final String TAG = "AblumAdapter";
    List<AblumItem> mAblumItemList;
    public AblumAdapter(List<AblumItem> ablumItemList){
        mAblumItemList=ablumItemList;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ablum_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        if(holder.getAdapterPosition()==0){
            holder.ablumPhoto.setOnClickListener(this);
        }
        holder.ablumPhotoChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //选中照片后
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AblumItem ablumItem=mAblumItemList.get(position);
        holder.ablumPhoto.setImageResource(ablumItem.getAblumPhoto());
        if (position==0){
            holder.ablumPhotoChoose.setVisibility(View.INVISIBLE);
            holder.addPhotoIcon.setVisibility(View.VISIBLE);
            holder.addPhotoText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ==========="+mAblumItemList.size());
        return mAblumItemList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ablum_item_photo:
                //打开照相机
                break;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ablumPhoto;
        RadioButton ablumPhotoChoose;
        ImageView addPhotoIcon;
        TextView addPhotoText;

        public ViewHolder(View itemView) {
            super(itemView);
            ablumPhoto=(ImageView) itemView.findViewById(R.id.ablum_item_photo);
            ablumPhotoChoose=(RadioButton) itemView.findViewById(R.id.ablum_item_photo_choose);
            addPhotoIcon=(ImageView) itemView.findViewById(R.id.add_photo_icon);
            addPhotoText=(TextView) itemView.findViewById(R.id.add_photo_text);
        }
    }
}
