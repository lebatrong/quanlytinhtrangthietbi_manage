package lbt.com.manager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.WaitDialog;
import com.leinardi.android.speeddial.FabWithLabelView;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objmaytinhs;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.iTaiKhoan;
import lbt.com.manager.Presenter.icapnhatmaytinh;
import lbt.com.manager.Presenter.lPhongMay;
import lbt.com.manager.Presenter.lTaiKhoan;
import lbt.com.manager.Presenter.lcapnhatmaytinh;
import lbt.com.manager.customAdapter.aRclvDanhSachMayTinh;

public class DanhSachMayTinhActivity extends AppCompatActivity implements iPhongMay {

    aRclvDanhSachMayTinh adapter;
    ArrayList<objthietbimaytinh_app> mList;
    Toolbar toolbar;
    RecyclerView rclvDanhSachMay;
    objPhongMay mObjPhong;
    lPhongMay mphongmay;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_may_tinh);

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mphongmay.getDanhSachMay(mObjPhong.getMaphong());
    }

    private void initView() {
        String title = getText(R.string.danhsachmaytinh).toString();
        rclvDanhSachMay = findViewById(R.id.rclvDanhSachMay);
        //SETUP TOOLBAR
        toolbar = findViewById(R.id.toolbardanhsachmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("dataphong");
        if(bundle!=null){
            mObjPhong = (objPhongMay) bundle.getSerializable("phong");
            title = mObjPhong.getTenphong();
        }
        toolbar.setTitle(title);
        mphongmay = new lPhongMay(this);

    }

    private void clickChiTietMay() {
        adapter.setOnClickListener(new aRclvDanhSachMayTinh.OnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if( mList.get(pos).getLichsusuachua()!=null){
                    if(!mList.get(pos).getLichsusuachua().isDasuachua()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mamay", mList.get(pos).getThietbi().getMamay());
                        bundle.putSerializable("mls", mList.get(pos).getLichsusuachua());
                        Intent intent = new Intent(DanhSachMayTinhActivity.this, TinhTrangThietBiActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    public void loitaidulieu() {
        WaitDialog.dismiss();
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void phongmaydangxaydung() {
        WaitDialog.dismiss();
        Toast.makeText(this, getText(R.string.phongmaydangxaydung), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void thongkethietbikhac(objthietbikhacs default_values, objthietbikhacs hu) {

    }

    @Override
    public void thongkemaytinh(boolean phongmayhu, objthongkemaytinh_app thongke) {

    }

    @Override
    public void danhsachmaytinh(ArrayList<objthietbimaytinh_app> list) {
        WaitDialog.dismiss();
        mList = new ArrayList<>();
        this.mList = list;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclvDanhSachMay.setLayoutManager(layoutManager);
        adapter = new aRclvDanhSachMayTinh(list,this);
        rclvDanhSachMay.setAdapter(adapter);
        clickChiTietMay();
    }

    @Override
    public void capnhatthanhcong(boolean isSuccess) {

    }

    @Override
    public void results_capnhatthietbikhac(boolean isSuccess) {

    }




}
