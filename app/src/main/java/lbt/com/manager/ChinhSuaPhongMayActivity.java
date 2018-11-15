package lbt.com.manager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
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
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.WaitDialog;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.CustomInterface.iDeleteRclvDanhSachMay;
import lbt.com.manager.Models.App.objmaytinh_app;
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
import lbt.com.manager.customAdapter.aRclvDanhSachMay_Add;

public class ChinhSuaPhongMayActivity extends AppCompatActivity implements iPhongMay, icapnhatmaytinh, iTaiKhoan {

    ArrayList<objmaytinh_app> mList;
    Toolbar toolbar;
    RecyclerView rclvDanhSachMay;
    objPhongMay mObjPhong;
    LinearLayout lnlDelMayTinh;
    aRclvDanhSachMay_Add adapter;
    lPhongMay mphongmay;
    lcapnhatmaytinh mcapnhat;
    lTaiKhoan mTaiKhoan;
    objNguoiDung mNguoiDung;



    Button btnDel, btnHuy;


    private SpeedDialView mSpeedDialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chinh_sua_phong_may);
        initView();
        mcapnhat.getListMayTinh(mObjPhong.getMaphong());

        initSpeedDial(savedInstanceState == null);

        capnhatMayTinh(false);
    }

    @Override
    public void onBackPressed() {
        //Closes menu if its opened.
        if (mSpeedDialView.isOpen()) {
            mSpeedDialView.close();
        } else {
            super.onBackPressed();
        }

    }

    private void initSpeedDial(boolean addActionItems) {
        mSpeedDialView = findViewById(R.id.speedDial);

        if (addActionItems) {
            mSpeedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_addMayTinh, R.drawable.ic_add_circle_outline_white_24dp)
                            .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorGreen, getTheme()))
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorWhite, getTheme()))
                            .setLabel(getString(R.string.themmaytinh))
                            .setLabelColor(Color.WHITE)
                            .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorGreen, getTheme()))
                            .setLabelClickable(false)
                            .create()
            );


            mSpeedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_DeleteMayTinh, R.drawable.ic_delete_black_24dp)
                            .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, getTheme()))
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorWhite, getTheme()))
                            .setLabel(getString(R.string.xoamaytinh))
                            .setLabelColor(Color.WHITE)
                            .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, getTheme()))
                            .setLabelClickable(false)
                            .create()
            );

            mSpeedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_UpdateMayTinh, R.drawable.ic_edit)
                            .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colortong, getTheme()))
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorWhite, getTheme()))
                            .setLabel(getString(R.string.capnhatmaytinh))
                            .setLabelColor(Color.WHITE)
                            .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colortong, getTheme()))
                            .setLabelClickable(false)
                            .create()
            );

        }

        //Set option fabs clicklisteners.
        mSpeedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_addMayTinh:
                        addMayTinh();
                        mSpeedDialView.close(); // To close the Speed Dial with animation
                        return true; // false will close it without animation
                    case R.id.fab_DeleteMayTinh:
                        deleteMayTinh();
                        mSpeedDialView.close();
                        return true;
                    case R.id.fab_UpdateMayTinh:
                        capnhatMayTinh(true);
                        mSpeedDialView.close();
                        return true; // closes without animation (same as mSpeedDialView.close(false); return false;)
                    default:
                        break;
                }
                return true; // To keep the Speed Dial open
            }
        });

    }

    private void deleteMayTinh() {
        if(adapter!=null){
            mSpeedDialView.setVisibility(View.GONE);
            lnlDelMayTinh.setVisibility(View.VISIBLE);
            adapter.setChecked(true);


            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lnlDelMayTinh.setVisibility(View.GONE);
                    mSpeedDialView.setVisibility(View.VISIBLE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChinhSuaPhongMayActivity.this,LinearLayoutManager.VERTICAL,false);
                    rclvDanhSachMay.setLayoutManager(layoutManager);
                    //reset giá trị checked về false
                    for(objmaytinh_app mt : mList){
                        mt.setChecked(false);
                    }
                    adapter = new aRclvDanhSachMay_Add(mList,ChinhSuaPhongMayActivity.this);
                    rclvDanhSachMay.setAdapter(adapter);
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SelectDialog.show(ChinhSuaPhongMayActivity.this,
                            getText(R.string.xoamaytinh) + "?",
                            null, getText(R.string.xacnhan).toString(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ArrayList<objmaytinhs> listMayDelete = new ArrayList<>();
                                    for(objmaytinh_app i: adapter.getmList()){
                                        if(i.isChecked())
                                            listMayDelete.add(i.getMaytinh());
                                    }
                                    mcapnhat.delMayTinh(listMayDelete,mObjPhong.getMaphong(),mNguoiDung);
                                    WaitDialog.show(ChinhSuaPhongMayActivity.this,getText(R.string.loading).toString());
                                    dialog.dismiss();
                                }
                            }, getText(R.string.huy).toString(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                }
            });

        }
    }

    private void addMayTinh(){
        InputDialog.show(ChinhSuaPhongMayActivity.this,
                getText(R.string.nhapsoluong).toString(),
                null,
                getText(R.string.ok).toString(),
                new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        if(!inputText.matches("") && Integer.parseInt(inputText.toString())<50){
                            int soluong = Integer.parseInt(inputText) +1;
                            mcapnhat.addMayTinh(mObjPhong.getMaphong(), soluong, mNguoiDung);
                            WaitDialog.show(ChinhSuaPhongMayActivity.this,getText(R.string.loading).toString());
                            dialog.dismiss();
                        }else {
                            if(inputText.matches("")){
                                Toast.makeText(ChinhSuaPhongMayActivity.this, getText(R.string.soluongkhonghople), Toast.LENGTH_SHORT).show();
                            }else {
                                if(Integer.parseInt(inputText)>50){
                                    Toast.makeText(ChinhSuaPhongMayActivity.this, getText(R.string.soluongqualon), Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    }
                }, getText(R.string.huy).toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void capnhatMayTinh(final boolean isCapNhat){
        if(adapter != null){
            adapter.setOnClickListener(new aRclvDanhSachMay_Add.OnItemClickListener() {
                @Override
                public void onClick(View v, final int pos) {
                     if(isCapNhat == true){
                         InputDialog.show(ChinhSuaPhongMayActivity.this,
                                 getText(R.string.capnhattenmaytinh).toString(),
                                 getText(R.string.capnhattencho).toString()
                                         + adapter.getItem(pos).getMaytinh().getTenmay() +
                                         " (" +adapter.getItem(pos).getMaytinh().getMamay()+")",
                                 getText(R.string.ok).toString(),
                                 new InputDialogOkButtonClickListener() {
                                     @Override
                                     public void onClick(Dialog dialog, String inputText) {
                                         if(!inputText.matches("")){
                                             mcapnhat.capnhatTenMay(adapter.getItem(pos).getMaytinh(),inputText,mObjPhong.getMaphong());
                                             WaitDialog.show(ChinhSuaPhongMayActivity.this,getText(R.string.loading).toString());
                                             dialog.dismiss();
                                         }else {
                                             if(inputText.matches("")){
                                                 Toast.makeText(ChinhSuaPhongMayActivity.this, getText(R.string.tenmaykhonghople), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                 }, getText(R.string.huy).toString(), new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                     }
                                 });
                     }
                }
            });

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogSettings.unloadAllDialog();
    }

    private void initView() {
        String title = getText(R.string.chinhsuamaytinh).toString();
        toolbar = findViewById(R.id.toolbarchinhsuaphongmay);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting dialog
        DialogSettings.use_blur = true;
        DialogSettings.blur_alpha = 200;
        DialogSettings.type = DialogSettings.TYPE_IOS;

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
        lnlDelMayTinh = findViewById(R.id.lnldelmaytinh);

        btnDel = findViewById(R.id.btnxoamay_suaphongmay);
        btnHuy = findViewById(R.id.btnhuy_suaphongmay);

        mphongmay = new lPhongMay(this);
        mcapnhat = new lcapnhatmaytinh(this);
        mTaiKhoan = new lTaiKhoan(this,this);
        mNguoiDung = mTaiKhoan.getDataUser();


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
        WaitDialog.dismiss();
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
    public void danhsachmaytinh(List<objmaytinhs> mamay) {
        WaitDialog.dismiss();
        mList = new ArrayList<>();
        for(objmaytinhs mt : mamay){
            mList.add(new objmaytinh_app(mt,false));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclvDanhSachMay.setLayoutManager(layoutManager);
        adapter = new aRclvDanhSachMay_Add(mList,this);
        rclvDanhSachMay.setAdapter(adapter);
    }

    @Override
    public void loitaidanhsachmay() {
        WaitDialog.dismiss();
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void ketquadelete(boolean isSuccess) {
        WaitDialog.dismiss();
        if(isSuccess)
        {
            mSpeedDialView.setVisibility(View.VISIBLE);
            lnlDelMayTinh.setVisibility(View.GONE);
        }
    }

    @Override
    public void ketquacapnhat(boolean isSuccess) {
        WaitDialog.dismiss();
        if(isSuccess){
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getText(R.string.capnhatkhongthanhcong), Toast.LENGTH_SHORT).show();
        }
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


}
