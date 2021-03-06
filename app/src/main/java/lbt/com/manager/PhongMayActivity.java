package lbt.com.manager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.WaitDialog;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.zip.Inflater;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.lPhongMay;
import lbt.com.manager.customAdapter.aRclvDanhSachMayTinh;

public class PhongMayActivity extends AppCompatActivity implements iPhongMay {

    Toolbar toolbar;
    objPhongMay mObjPhong;
    lPhongMay mphongmay;

    LinearLayout lnlMain;


    EditText edtKhac;
    TextView tvban, tvbanhu,tvbanbinhthuong;
    TextView tvghe,tvghehu,tvghebinhthuong;
    TextView tvtivi,tvtivihu,tvtivibinhthuong;
    TextView tvquat,tvquathu,tvquatbinhthuong;
    TextView tvden,tvdenhu,tvdenbinhthuong;
    TextView tvmodem,tvmodemhu,tvmodembinhthuong;
    TextView tvswitch,tvswitchhu,tvswitchbinhthuong;
    TextView tvdieuhoa,tvdieuhoahu,tvdieuhoabinhthuong;

    TextView tvtongmaytinh, tvbtmaytinh,tvhumaytinh;
    TextView tvtongchuot,tvbtchuot,tvhuchuot;
    TextView tvtongbanphim,tvbtbanphim,tvhubanphim;
    TextView tvtongmanhinh,tvbtmanhinh,tvhumanhinh;
    TextView tvtongcpu,tvbtcpu,tvhucpu;



    objthietbikhacs mThietbi_default,mThietBi_Hu;

    LinearLayout lnlpro;
    Button btndanhsachmaytinh,btndasuaxong;

    ExpandableLayout explMayTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_may);
        initView();


        actionChiTietMayTinh();
        actiondasuxong();
    }

    private void actiondasuxong() {
        btndasuaxong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phongmay,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ichinhsuaphongmay:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("phong",mObjPhong);
                Intent intent1 = new Intent(this,ChinhSuaPhongMayActivity.class);
                intent1.putExtra("dataphong",bundle1);
                startActivity(intent1);
                break;
            case R.id.ichinhsuathietbikhac:
                if(mThietbi_default!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("thietbi", mThietbi_default);
                    bundle.putSerializable("phongmay",mObjPhong);
                    Intent intent = new Intent(this, ChinhSuaThietBiKhacActivity.class);
                    intent.putExtra("data",bundle);
                    startActivity(intent);
                }
                break;
        }

        return false;
    }

    private void showAlert(){

        SelectDialog.show(this,
                getText(R.string.bandansuaxong).toString(),
                null, getText(R.string.xacnhan).toString(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mphongmay.suaxongtatcathietbikhac(mObjPhong.getMaphong());
                        WaitDialog.show(PhongMayActivity.this,getText(R.string.dangtaidulieu).toString());
                        dialog.dismiss();
                    }
                }, getText(R.string.huy).toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    private void initView() {

        mphongmay = new lPhongMay(this);

        String title = "Chi tiết phong máy";
        Gson gson = new Gson();
        SharedPreferences spf = getSharedPreferences("data", Context.MODE_PRIVATE);
        String strPhong = spf.getString("phongmay",null);
        if(strPhong==null)
            finish();
        mObjPhong = gson.fromJson(strPhong,objPhongMay.class);
        title = mObjPhong.getTenphong();

        mphongmay.getChiTietPhongMay(mObjPhong.getMaphong());


        //SETUP TOOLBAR
        toolbar = findViewById(R.id.toolbarchitietphongmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        lnlpro  = findViewById(R.id.lnlproctp);
        lnlMain = findViewById(R.id.lnlMain_ctp);

        tvban = findViewById(R.id.tvtongban_ctp);
        tvbanhu = findViewById(R.id.tvhuban_ctp);
        tvbanbinhthuong = findViewById(R.id.tvbinhthuongban_ctp);

        tvghe = findViewById(R.id.tvtongghe_ctp);
        tvghehu = findViewById(R.id.tvhughe_ctp);
        tvghebinhthuong = findViewById(R.id.tvbinhthuongghe_ctp);

        tvden = findViewById(R.id.tvtongden_ctp);
        tvdenhu = findViewById(R.id.tvhuden_ctp);
        tvdenbinhthuong = findViewById(R.id.tvbinhthuongden_ctp);

        tvtivi = findViewById(R.id.tvtongtivi_ctp);
        tvtivihu = findViewById(R.id.tvhutivi_ctp);
        tvtivibinhthuong = findViewById(R.id.tvbinhthuongtivi_ctp);

        tvswitch = findViewById(R.id.tvtongswitchmang_ctp);
        tvswitchhu = findViewById(R.id.tvhuswitchmang_ctp);
        tvswitchbinhthuong = findViewById(R.id.tvbinhthuongswitchmang_ctp);

        tvmodem = findViewById(R.id.tvtongmodemwifi_ctp);
        tvmodemhu = findViewById(R.id.tvhumodemwifi_ctp);
        tvmodembinhthuong = findViewById(R.id.tvbinhthuongmodemwifi_ctp);

        tvdieuhoa = findViewById(R.id.tvtongdieuhoa_ctp);
        tvdieuhoahu = findViewById(R.id.tvhudieuhoa_ctp);
        tvdieuhoabinhthuong = findViewById(R.id.tvbinhthuongdieuhoa_ctp);

        tvquat = findViewById(R.id.tvtongquat_ctp);
        tvquathu = findViewById(R.id.tvhuquat_ctp);
        tvquatbinhthuong = findViewById(R.id.tvbinhthuongquat_ctp);

        edtKhac = findViewById(R.id.edtKhac_ctp);

        btndanhsachmaytinh = findViewById(R.id.btnchitietmaytinh_ctp);
        btndasuaxong = findViewById(R.id.btndasuaxongtatca);

        tvtongmaytinh = findViewById(R.id.tvtongmaytinh_ctp);
        tvhumaytinh = findViewById(R.id.tvhumaytinh_ctp);
        tvbtmaytinh = findViewById(R.id.tvbinhthuongmaytinh_ctp);

        tvtongmanhinh = findViewById(R.id.tvtongmanhinh_ctp);
        tvhumanhinh = findViewById(R.id.tvhumanhinh_ctp);
        tvbtmanhinh = findViewById(R.id.tvbinhthuongmanhinh_ctp);

        tvtongbanphim = findViewById(R.id.tvtongbanphim_ctp);
        tvhubanphim = findViewById(R.id.tvhubanphim_ctp);
        tvbtbanphim = findViewById(R.id.tvbinhthuongbanphim_ctp);

        tvtongcpu = findViewById(R.id.tvtongcpu_ctp);
        tvhucpu = findViewById(R.id.tvhucpu_ctp);
        tvbtcpu = findViewById(R.id.tvbinhthuongcpu_ctp);

        tvtongchuot = findViewById(R.id.tvtongchuot_ctp);
        tvhuchuot = findViewById(R.id.tvhuchuot_ctp);
        tvbtchuot = findViewById(R.id.tvbinhthuongchuot_ctp);


        //SETUP PROGRESS BAR
        lnlpro.setVisibility(View.VISIBLE);
        lnlMain.setVisibility(View.GONE);


        explMayTinh = findViewById(R.id.exdlMayTinh_ctp);




    }


    @Override
    public void loitaidulieu() {
        WaitDialog.dismiss();
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void actionChiTietMayTinh() {
        btndanhsachmaytinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phong",mObjPhong);
                Intent intent = new Intent(PhongMayActivity.this,DanhSachMayTinhActivity.class);
                intent.putExtra("dataphong",bundle);
                startActivity(intent);
            }
        });
    }



    @Override
    public void phongmaydangxaydung() {
        Toast.makeText(this, getText(R.string.phongmaydangxaydung), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void thongkethietbikhac(objthietbikhacs default_values, objthietbikhacs hu) {

        mThietbi_default = default_values;
        mThietBi_Hu = hu;

        tvban.setText(String.valueOf(default_values.getBan()));
        tvbanhu.setText(String.valueOf(hu.getBan()));
        tvbanbinhthuong.setText(String.valueOf(default_values.getBan() - hu.getBan()));

        tvghe.setText(String.valueOf(default_values.getGhe()));
        tvghehu.setText(String.valueOf(hu.getGhe()));
        tvghebinhthuong.setText(String.valueOf(default_values.getGhe() - hu.getGhe()));

        tvden.setText(String.valueOf(default_values.getDen()));
        tvdenhu.setText(String.valueOf(hu.getDen()));
        tvdenbinhthuong.setText(String.valueOf(default_values.getDen() - hu.getDen()));

        tvtivi.setText(String.valueOf(default_values.getTivi()));
        tvtivihu.setText(String.valueOf(hu.getTivi()));
        tvtivibinhthuong.setText(String.valueOf(default_values.getTivi() - hu.getTivi()));

        tvquat.setText(String.valueOf(default_values.getQuat()));
        tvquathu.setText(String.valueOf(hu.getQuat()));
        tvquatbinhthuong.setText(String.valueOf(default_values.getQuat() - hu.getQuat()));

        tvdieuhoa.setText(String.valueOf(default_values.getMaydieuhoa()));
        tvdieuhoahu.setText(String.valueOf(hu.getMaydieuhoa()));
        tvdieuhoabinhthuong.setText(String.valueOf(default_values.getMaydieuhoa() - hu.getMaydieuhoa()));

        tvmodem.setText(String.valueOf(default_values.getModemwifi()));
        tvmodemhu.setText(String.valueOf(hu.getModemwifi()));
        tvmodembinhthuong.setText(String.valueOf(default_values.getModemwifi() - hu.getModemwifi()));

        tvswitch.setText(String.valueOf(default_values.getSwitchmang()));
        tvswitchhu.setText(String.valueOf(hu.getSwitchmang()));
        tvswitchbinhthuong.setText(String.valueOf(default_values.getSwitchmang() - hu.getSwitchmang()));

        edtKhac.setText(hu.getKhac());

        //SETUP PROGRESS BAR
        lnlpro.setVisibility(View.GONE);
        lnlMain.setVisibility(View.VISIBLE);



        if(!tvbanhu.getText().toString().matches("0")||
                !tvghehu.getText().toString().matches("0")||
                !tvtivihu.getText().toString().matches("0")||
                !tvdenhu.getText().toString().matches("0")||
                !tvdieuhoahu.getText().toString().matches("0")||
                !tvquathu.getText().toString().matches("0")||
                !tvmodemhu.getText().toString().matches("0")||
                !tvswitchhu.getText().toString().matches("0"))
        {
                btndasuaxong.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "VISIBLE", Toast.LENGTH_SHORT).show();

        }else{
            if(!edtKhac.getText().toString().matches(""))
                btndasuaxong.setVisibility(View.VISIBLE);
            else
                btndasuaxong.setVisibility(View.GONE);
        }

    }

    @Override
    public void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke) {
        if(phongmayhu){
            tvtongmaytinh.setText(String.valueOf(thongke.getMaytinh()));
            tvtongbanphim.setText(String.valueOf(thongke.getBanphim()));
            tvtongcpu.setText(String.valueOf(thongke.getCpu()));
            tvtongmanhinh.setText(String.valueOf(thongke.getManhinh()));
            tvtongchuot.setText(String.valueOf(thongke.getChuot()));

            tvhumaytinh.setText(String.valueOf(thongke.getMaytinhhu()));
            tvhubanphim.setText(String.valueOf(thongke.getBanphimhu()));
            tvhuchuot.setText(String.valueOf(thongke.getChuothu()));
            tvhucpu.setText(String.valueOf(thongke.getCpuhu()));
            tvhumanhinh.setText(String.valueOf(thongke.getManhinhhu()));

            tvbtmaytinh.setText(String.valueOf(thongke.getMaytinhbt()));
            tvbtbanphim.setText(String.valueOf(thongke.getBanphimbt()));
            tvbtchuot.setText(String.valueOf(thongke.getChuotbt()));
            tvbtcpu.setText(String.valueOf(thongke.getCpubt()));
            tvbtmanhinh.setText(String.valueOf(thongke.getManhinhbt()));

        }else{

            tvtongmaytinh.setText(String.valueOf(thongke.getMaytinh()));
            tvtongbanphim.setText(String.valueOf(thongke.getBanphim()));
            tvtongcpu.setText(String.valueOf(thongke.getCpu()));
            tvtongmanhinh.setText(String.valueOf(thongke.getManhinh()));
            tvtongchuot.setText(String.valueOf(thongke.getChuot()));

            tvhumaytinh.setText("0");
            tvhubanphim.setText("0");
            tvhuchuot.setText("0");
            tvhucpu.setText("0");
            tvhumanhinh.setText("0");

            tvbtmaytinh.setText("0");
            tvbtbanphim.setText("0");
            tvbtchuot.setText("0");
            tvbtcpu.setText("0");
            tvbtmanhinh.setText("0");

        }
    }

    @Override
    public void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list) {

    }

    @Override
    public void capnhatthanhcong(boolean isSuccess) {
        WaitDialog.dismiss();
        if(isSuccess)
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getText(R.string.capnhatkhongthanhcong), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void results_capnhatthietbikhac(boolean isSuccess) {

    }
}
