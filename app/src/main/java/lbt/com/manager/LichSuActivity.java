package lbt.com.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kongzue.dialog.v2.WaitDialog;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Models.Firebase.objlichsucapnhatmaytinh;
import lbt.com.manager.Models.Firebase.objlichsucapnhatthietbikhac;
import lbt.com.manager.Presenter.iLichSu;
import lbt.com.manager.Presenter.lLichSu;
import lbt.com.manager.customAdapter.aExplvLichSu;

public class LichSuActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvCapNhatMayTinh, tvCapNhatTBK, tvSuaChuaMayTinh, tvSuaChuaTBK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        initView();
        setupmenu();
    }

    private void setupmenu() {
       tvCapNhatTBK.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(LichSuActivity.this, LichSuCapNhatTBKActivity.class));
           }
       });

        tvCapNhatMayTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LichSuActivity.this, LichSuCapNhatMayTinhActivity.class));
            }
        });

        tvSuaChuaTBK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LichSuActivity.this, LichSuSuaChuaTBKActivity.class));
            }
        });

        tvSuaChuaMayTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LichSuActivity.this, LichSuSuaChuaMayTinhActivity.class));
            }
        });
    }




    private void initView() {
        toolbar = findViewById(R.id.toolbarlichsu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvCapNhatMayTinh = findViewById(R.id.tvmaytinh_lscapnhat);
        tvCapNhatTBK = findViewById(R.id.tvthietbikhac_lscapnhat);

        tvSuaChuaMayTinh = findViewById(R.id.tvmaytinh_lssuachua);
        tvSuaChuaTBK = findViewById(R.id.tvthietbikhac_lssuachua);


    }


}
