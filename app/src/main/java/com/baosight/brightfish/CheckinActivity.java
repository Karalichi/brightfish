package com.baosight.brightfish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CheckinActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int msgKey1 = 1;
    private static final String TAG = "CheckinActivity";
    private String content="";
    private boolean isFocus;
    Toolbar toolbar;
    TextView currentTime;
    Button commit;
    EditText goodsSku, goodsName,price, amount,description,supplierSku,supplierName;
    ImageButton goodsMenu,goodsRefesh,goodsPhoto,supplierMenu,supplierRefesh;
    List<EditText> editTexts;
    public static void startCheckinActivity(Context context){
        Intent intent = new Intent(context,CheckinActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        currentTime=(TextView) findViewById(R.id.current_time);
        new TimeThread().start();
        commit=(Button)findViewById(R.id.checkin_commit);
        assert commit != null;
        commit.setOnClickListener(this);
        goodsSku= (EditText) findViewById(R.id.goods_sku_checkin);
        goodsName= (EditText) findViewById(R.id.goods_name_checkin);
        price= (EditText) findViewById(R.id.price_checkin);
        amount= (EditText) findViewById(R.id.amount_checkin);
        description= (EditText) findViewById(R.id.descripe_checkin);
        supplierName= (EditText) findViewById(R.id.supplier_name_checkin);
        supplierSku= (EditText) findViewById(R.id.supplier_sku_checkin);
        goodsMenu=(ImageButton) findViewById(R.id.check_good_menu);
        goodsRefesh=(ImageButton) findViewById(R.id.check_good_refesh);
        supplierMenu=(ImageButton) findViewById(R.id.check_supplier_menu);
        supplierRefesh=(ImageButton)findViewById(R.id.check_good_refesh);
        goodsPhoto=(ImageButton) findViewById(R.id.photo_goods);
        editTexts=new ArrayList<>();
        editTexts.add(goodsSku);
        editTexts.add(supplierSku);
        editTexts.add(price);
        editTexts.add(amount);
        editTexts.add(description);
        editTexts.add(supplierName);
        editTexts.add(goodsName);

        goodsPhoto.setOnClickListener(this);
        goodsMenu.setOnClickListener(this);
        goodsRefesh.setOnClickListener(this);
        supplierMenu.setOnClickListener(this);
        supplierRefesh.setOnClickListener(this);
        for (EditText editText:editTexts) {
         editText.setOnClickListener(this);
          /*  editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        isFocus=true;
                        invalidateOptionsMenu();
                    }else {
                        isFocus=false;
                        invalidateOptionsMenu();
                    }
                }
            });*/

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getClass()==android.support.v7.widget.AppCompatEditText.class){
            content+=goodsSku.getText();
            isFocus=true;
            invalidateOptionsMenu();
        }
        switch (v.getId()){
            case R.id.check_good_menu:
                ChooseGoodsActivity.startChooseGoodsActivity(CheckinActivity.this);
                break;
            case R.id.check_good_refesh:
                 goodsSku.setText("");
                 goodsName.setText("");
                break;
            case R.id.check_supplier_menu:
                ChooseSupplierActivity.startChooseSupplierActivity(CheckinActivity.this);
                break;
            case R.id.check_suplier_refesh:
                supplierSku.setText("");
                supplierName.setText("");
                break;
            case R.id.photo_goods:
                CheckinAblumActivity.startCheckinAblumActivity(CheckinActivity.this);
                break;
            case R.id.checkin_commit:
                setAlertDialog();
                break;

        }
    }
    private void setAlertDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(CheckinActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(TextUtils.isEmpty(content)) {
            dialog.setMessage("内容不能为空");
        }else if(TextUtils.isEmpty(goodsName.getText())){
            dialog.setMessage("货物不能为空");
        }else if(TextUtils.isEmpty(price.getText())){
            dialog.setMessage("价格不能为空");
        }else if(TextUtils.isEmpty(amount.getText())){
            dialog.setMessage("数量不能为空");
        }
        dialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isFocus){
            menu.findItem(R.id.check_mark).setVisible(false);
        }else{
            menu.findItem(R.id.check_mark).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.check_mark:
                setAlertDialog();
                break;
            case R.id.blueTooth:
                Toast.makeText(CheckinActivity.this,"开启蓝牙扫码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.default_param:
                Toast.makeText(CheckinActivity.this,"货品默认参数",Toast.LENGTH_SHORT).show();
                break;
            case R.id.kuaiSao:
                Toast.makeText(CheckinActivity.this,"开启快扫模式",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_bar_menu,menu);
        return true;
    }

    //显示时间线程
    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    currentTime.setText(getTime());
                    break;
                default:
                    break;
            }
        }
    };
    //获得当前年月日时分秒星期
    public String getTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mYear + "年" + mMonth + "月" + mDay+"日"+" "+"星期"+mWay+" "+mHour+":"+mMinute+":"+mSecond;
    }

}
