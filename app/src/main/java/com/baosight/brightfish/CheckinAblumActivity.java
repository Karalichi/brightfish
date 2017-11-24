package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CheckinAblumActivity extends AppCompatActivity {
    List<AblumItem> ablumItemList;
    Toolbar toolbar;
    RecyclerView recyclerView;
    private static final String TAG = "CheckinAblumActivity";

    public static void startCheckinAblumActivity(Context context){
        Intent intent = new Intent(context,CheckinAblumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ==================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_ablum);
        initControls();
        initAblum();
        recyclerView=(RecyclerView) findViewById(R.id.add_Ablum_rec);
        GridLayoutManager layoutManager=new GridLayoutManager(this,3);
        AblumAdapter adapter=new AblumAdapter(ablumItemList);
        Log.d(TAG, "onCreate: ==========adapter============");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


    }
    private void initControls(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initAblum(){
        ablumItemList=new ArrayList<>();
        AblumItem addPhotoItem=new AblumItem();
        ablumItemList.add(addPhotoItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_ablum,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
