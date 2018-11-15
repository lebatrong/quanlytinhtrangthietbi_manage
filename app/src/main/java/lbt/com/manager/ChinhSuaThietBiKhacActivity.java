package lbt.com.manager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kongzue.dialog.v2.WaitDialog;

import java.util.ArrayList;

import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.App.objthongkemaytinh_app;
import lbt.com.manager.Models.Firebase.objPhongMay;
import lbt.com.manager.Models.Firebase.objthietbikhacs;
import lbt.com.manager.Presenter.iPhongMay;
import lbt.com.manager.Presenter.lPhongMay;

public class ChinhSuaThietBiKhacActivity extends AppCompatActivity implements iPhongMay {
    lPhongMay mLogicPhongMay;

    Toolbar toolbar;

    TextView tvban, tvbanadd,tvbansub;
    TextView tvghe,tvgheadd,tvghesub;
    TextView tvtivi,tvtiviadd,tvtivisub;
    TextView tvquat,tvquatadd,tvquatsub;
    TextView tvden,tvdenadd,tvdensub;
    TextView tvmodem,tvmodemadd,tvmodemsub;
    TextView tvswitch,tvswitchadd,tvswitchsub;
    TextView tvdieuhoa,tvdieuhoaadd,tvdieuhoasub;

    Button btnCapNhat;

    objthietbikhacs mThietbi_default;
    objPhongMay mObjPhong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thiet_bi_khac);
        getData();
        initView();
        setupDefault();
        actionAddSub();
        actionCapNhat();
    }

    private void actionCapNhat() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objthietbikhacs capnhat = new objthietbikhacs();

                capnhat.setBan(Long.parseLong(tvban.getText().toString()));
                capnhat.setDen(Long.parseLong(tvden.getText().toString()));
                capnhat.setGhe(Long.parseLong(tvghe.getText().toString()));
                capnhat.setMaydieuhoa(Long.parseLong(tvdieuhoa.getText().toString()));
                capnhat.setQuat(Long.parseLong(tvquat.getText().toString()));
                capnhat.setModemwifi(Long.parseLong(tvmodem.getText().toString()));
                capnhat.setSwitchmang(Long.parseLong(tvswitch.getText().toString()));
                capnhat.setTivi(Long.parseLong(tvtivi.getText().toString()));
                capnhat.setKhac("");

                WaitDialog.show(ChinhSuaThietBiKhacActivity.this,getText(R.string.dangtaidulieu).toString());
                mLogicPhongMay.capnhattinhtrangthietbi(capnhat,mObjPhong);

            }
        });
    }

    private void setupDefault() {
        tvban.setText(String.valueOf(mThietbi_default.getBan()));
        tvden.setText(String.valueOf(mThietbi_default.getDen()));
        tvdieuhoa.setText(String.valueOf(mThietbi_default.getMaydieuhoa()));
        tvmodem.setText(String.valueOf(mThietbi_default.getModemwifi()));
        tvghe.setText(String.valueOf(mThietbi_default.getGhe()));
        tvquat.setText(String.valueOf(mThietbi_default.getQuat()));
        tvswitch.setText(String.valueOf(mThietbi_default.getSwitchmang()));
        tvtivi.setText(String.valueOf(mThietbi_default.getTivi()));
    }

    private void getData() {
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle!=null){
            mThietbi_default = (objthietbikhacs) bundle.getSerializable("thietbi");
            mObjPhong = (objPhongMay) bundle.getSerializable("phongmay");
        }else {
            finish();
        }
    }


    private void actionAddSub(){

//        BÀN
        tvbanadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvban.getText().toString());
                tvban.setText(String.valueOf(count+1));
            }
        });

        tvbansub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvban.getText().toString());
                if(count>0)
                    tvban.setText(String.valueOf(count-1));
            }
        });

//        GHẾ
        tvgheadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvghe.getText().toString());
                tvghe.setText(String.valueOf(count+1));
            }
        });

        tvghesub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvghe.getText().toString());
                if(count>0)
                    tvghe.setText(String.valueOf(count-1));
            }
        });

//        TIVI
        tvtiviadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvtivi.getText().toString());
                tvtivi.setText(String.valueOf(count+1));
            }
        });

        tvtivisub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvtivi.getText().toString());
                if(count>0)
                    tvtivi.setText(String.valueOf(count-1));
            }
        });

//        SWITCH
        tvswitchadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvswitch.getText().toString());
                tvswitch.setText(String.valueOf(count+1));
            }
        });

        tvswitchsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvswitch.getText().toString());
                if(count>0)
                    tvswitch.setText(String.valueOf(count-1));
            }
        });

//        MODEM
        tvmodemadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvmodem.getText().toString());
                tvmodem.setText(String.valueOf(count+1));
            }
        });

        tvmodemsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvmodem.getText().toString());
                if(count>0)
                    tvmodem.setText(String.valueOf(count-1));
            }
        });

//        QUẠT
        tvquatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvquat.getText().toString());
                tvquat.setText(String.valueOf(count+1));
            }
        });

        tvquatsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvquat.getText().toString());
                if(count>0)
                    tvquat.setText(String.valueOf(count-1));
            }
        });

//        ĐÈN
        tvdenadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvden.getText().toString());
                tvden.setText(String.valueOf(count+1));
            }
        });

        tvdensub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvden.getText().toString());
                if(count>0)
                    tvden.setText(String.valueOf(count-1));
            }
        });

//        ĐIỀU HÒA
        tvdieuhoaadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvdieuhoa.getText().toString());
                tvdieuhoa.setText(String.valueOf(count+1));
            }
        });

        tvdieuhoasub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvdieuhoa.getText().toString());
                if(count>0)
                    tvdieuhoa.setText(String.valueOf(count-1));
            }
        });
    }

    private void initView() {
        //SETUP TOOLBAR
        mLogicPhongMay = new lPhongMay(this);

        toolbar = findViewById(R.id.toolbarchinhsuathietbikhac);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        btnCapNhat = findViewById(R.id.btncapnhat_edit);

        tvban = findViewById(R.id.tvtongban_edit);
        tvbansub = findViewById(R.id.tvgiamban_edit);
        tvbanadd = findViewById(R.id.tvthemban_edit);

        tvghe = findViewById(R.id.tvtongghe_edit);
        tvghesub = findViewById(R.id.tvgiamghe_edit);
        tvgheadd = findViewById(R.id.tvthemghe_edit);

        tvden = findViewById(R.id.tvtongden_edit);
        tvdensub = findViewById(R.id.tvgiamden_edit);
        tvdenadd = findViewById(R.id.tvthemden_edit);

        tvtivi = findViewById(R.id.tvtongtivi_edit);
        tvtivisub = findViewById(R.id.tvgiamtivi_edit);
        tvtiviadd = findViewById(R.id.tvthemtivi_edit);

        tvswitch = findViewById(R.id.tvtongswitchmang_edit);
        tvswitchsub = findViewById(R.id.tvgiamswitchmang_edit);
        tvswitchadd = findViewById(R.id.tvthemswitchmang_edit);

        tvmodem = findViewById(R.id.tvtongmodemwifi_edit);
        tvmodemsub = findViewById(R.id.tvgiammodemwifi_edit);
        tvmodemadd = findViewById(R.id.tvthemmodemwifi_edit);

        tvdieuhoa = findViewById(R.id.tvtongdieuhoa_edit);
        tvdieuhoasub = findViewById(R.id.tvgiamdieuhoa_edit);
        tvdieuhoaadd = findViewById(R.id.tvthemdieuhoa_edit);

        tvquat = findViewById(R.id.tvtongquat_edit);
        tvquatsub = findViewById(R.id.tvgiamquat_edit);
        tvquatadd = findViewById(R.id.tvthemquat_edit);
    }

    @Override
    public void loitaidulieu() {

    }

    @Override
    public void phongmaydangxaydung() {

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
        WaitDialog.dismiss();
        if(isSuccess){
            Toast.makeText(this, getText(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getText(R.string.capnhatkhongthanhcong), Toast.LENGTH_SHORT).show();
        }
    }
}
