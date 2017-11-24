package com.baosight.brightfish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.Util.IconColor;

import java.util.List;

/**
 * Created by saitama on 2017/11/20.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeItem> mHomeItem;

    public HomeAdapter(List<HomeItem> homeItems){
        mHomeItem=homeItems;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                HomeItem homeItem=mHomeItem.get(position);
                startItemActivity(homeItem,parent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeItem homeItem=mHomeItem.get(position);
        holder.itemName.setText(homeItem.getItem_name());
        holder.itemIcon.setImageResource(homeItem.getIcon());
    }

    @Override
    public int getItemCount() {
        return mHomeItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View homeView;
        ImageView itemIcon;
        TextView itemName;

        public ViewHolder(View view){
            super(view);
            homeView=view;
            itemIcon=(ImageView)view.findViewById(R.id.item_image);
            itemName=(TextView)view.findViewById(R.id.item_name);
        }
    }
    private void startItemActivity(HomeItem homeItem,ViewGroup parent){
        Context context=parent.getContext();
        switch (homeItem.getId()){
            case MainActivity.CHECKIN_ITEM:
                CheckinActivity.startCheckinActivity(context);
                break;
            case MainActivity.CHECKOUT_ITEM:
                CheckoutActivity.startCheckoutActivity(context);
                break;
            case MainActivity.ACCOUNT_ITEM:
                AccountActivity.startAccountActivity(context);
                break;
            case MainActivity.ANALYZE_ITEM:
                AnalyzeActivity.startAnalyzeActivity(context);
                break;
            case MainActivity.BUYER_ITEM:
                BuyerActivity.startBuyerActivity(context);
                break;
            case MainActivity.DIANHUO_ITEM:
                DianhuoActivity.startDianhuoActivity(context);
                break;
            case MainActivity.GOOD_ITEM:
                GoodsActivity.startGoodsActivity(context);
                break;
            case MainActivity.SEARCH_ITEM:
                SearchActivity.startSearchActivity(context);
                break;
            case MainActivity.SETTING_ITEM:
                SettingActivity.startSettingActivity(context);
                break;
            case MainActivity.SUPPILER_ITEM:
                SupplierActivity.startSupplierActivity(context);
                break;
            default:
                break;
        }
    }

}
