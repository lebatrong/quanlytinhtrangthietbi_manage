package lbt.com.manager;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.CustomInterface.iDeleteRclvDanhSachMay;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.iTaiKhoan;
import lbt.com.manager.Presenter.icapnhatmaytinh;
import lbt.com.manager.Presenter.lPhongMay;
import lbt.com.manager.Presenter.lTaiKhoan;
import lbt.com.manager.Presenter.lcapnhatmaytinh;
import lbt.com.manager.customAdapter.aRclvDanhSachMayTinh;
import lbt.com.manager.customAdapter.aRclvDanhSachMay_Add;

public class ChinhSuaPhongMayActivity extends AppCompatActivity implements iPhongMay, icapnhatmaytinh, iTaiKhoan, iDeleteRclvDanhSachMay {

    ArrayList<String> mList;
    Toolbar toolbar;
    RecyclerView rclvDanhSachMay;
    objPhongMay mObjPhong;
    LinearLayout lnlAddMayTinh, lnlDelMayTinh;
    aRclvDanhSachMay_Add adapter;
    lPhongMay mphongmay;
    lcapnhatmaytinh mcapnhat;
    lTaiKhoan mTaiKhoan;
    objNguoiDung mNguoiDung;

    Button btnDel, btnHuy;

    ArrayList<Integer> listChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_phong_may);
        initView();
        mcapnhat.getListMayTinh(mObjPhong.getMaphong());
        actionAddMayTinh();
        actionDel();
    }

    private void actionDel() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnlAddMayTinh.setVisibility(View.VISIBLE);
                lnlDelMayTinh.setVisibility(View.GONE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ChinhSuaPhongMayActivity.this,LinearLayoutManager.VERTICAL,false);
                rclvDanhSachMay.setLayoutManager(layoutManager);
                adapter = new aRclvDanhSachMay_Add(mList,ChinhSuaPhongMayActivity.this, ChinhSuaPhongMayActivity.this);
                rclvDanhSachMay.setAdapter(adapter);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listmay = new ArrayList<>();
                for(int i: listChecked){
                    listmay.add(mList.get(i));
                }
                mcapnhat.delMayTinh(listmay,mObjPhong.getMaphong(),mNguoiDung);
            }
        });
    }

    private void actionAddMayTinh() {
        lnlAddMayTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ChinhSuaPhongMayActivity.this);
                dialog.setContentView(R.layout.dialog_add_maytinh);
                dialog.setCancelable(false);
                Button btnAdd = dialog.findViewById(R.id.btnThemmaytinh_dialog);
                Button btnHuy = dialog.findViewById(R.id.btnhuy_dialog);
                final TextInputLayout tilsoluong = dialog.findViewById(R.id.tilsoluong_dialog);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!tilsoluong.getEditText().getText().toString().matches("") && Integer.parseInt(tilsoluong.getEditText().getText().toString())<50){
                            int soluong = Integer.parseInt(tilsoluong.getEditText().getText().toString());
                            tilsoluong.setError("");
                            tilsoluong.setErrorEnabled(false);
                            mcapnhat.addMayTinh(mObjPhong.getMaphong(), soluong, mNguoiDung);
                            dialog.dismiss();
                        }else {
                            if(tilsoluong.getEditText().getText().toString().matches("")){
                                tilsoluong.setError(getText(R.string.soluongkhonghople));
                                tilsoluong.setErrorEnabled(true);
                            }else {
                                if(Integer.parseInt(tilsoluong.getEditText().getText().toString())>50){
                                    tilsoluong.setError(getText(R.string.soluongqualon));
                                    tilsoluong.setErrorEnabled(true);
                                }else {
                                    tilsoluong.setError("");
                                    tilsoluong.setErrorEnabled(false);
                                }
                            }


                        }
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });

    }

    private void initView() {
        String title = getText(R.string.chinhsuamaytinh).toString();
        toolbar = findViewById(R.id.toolbarchinhsuaphongmay);
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


        rclvDanhSachMay = findViewById(R.id.rclvDanhSachMayEdit);
        lnlAddMayTinh = findViewById(R.id.lnladdmaytinh);
        lnlDelMayTinh = findViewById(R.id.lnldelmaytinh);

        btnDel = findViewById(R.id.btnxoamay_suaphongmay);
        btnHuy = findViewById(R.id.btnhuy_suaphongmay);

        mphongmay = new lPhongMay(this);
        mcapnhat = new lcapnhatmaytinh(this);
        mTaiKhoan = new lTaiKhoan(this,this);
        mNguoiDung = mTaiKhoan.getDataUser();

        listChecked = new ArrayList<>();
    }

    @Override
    public void loitaidulieu() {
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void phongmaydangxaydung() {
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

    }

    @Override
    public void capnhatthanhcong(boolean isSuccess) {

    }

    @Override
    public void results_capnhatthietbikhac(boolean isSuccess) {

    }

    @Override
    public void result_addmaytinh(boolean isSuccess) {
        if(isSuccess){
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getText(R.string.capnhatkhongthanhcong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void result_lichsucapnhatmaytinh(boolean isSuccess) {

    }

    @Override
    public void danhsachmaytinh(List<String> mamay) {
        mList = new ArrayList<>();
        this.mList = (ArrayList<String>) mamay;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclvDanhSachMay.setLayoutManager(layoutManager);
        adapter = new aRclvDanhSachMay_Add((ArrayList<String>) mamay,this, this);
        rclvDanhSachMay.setAdapter(adapter);
    }

    @Override
    public void loitaidanhsachmay() {
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void thongtintaikhoan(objNguoiDung user, Drawable hinhnen) {

    }

    @Override
    public void capnhatthongtin(boolean isSuccess) {

    }

    @Override
    public void laythongtinkhongthanhcong() {

    }

    @Override
    public void bankhongconquyenquanly() {

    }

    @Override
    public void deleteItems(ArrayList<Integer> listChecked) {
        this.listChecked = listChecked;
        if(listChecked.size()!=0){
            lnlAddMayTinh.setVisibility(View.GONE);
            lnlDelMayTinh.setVisibility(View.VISIBLE);

        }else {
            lnlAddMayTinh.setVisibility(View.VISIBLE);
            lnlDelMayTinh.setVisibility(View.GONE);
        }
    }
}
