package lbt.com.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
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

public class LichSuSuaChuaMayTinhActivity extends AppCompatActivity implements iLichSu {

    Toolbar toolbar;

    ExpandableListView explistmaytinh;
    aExplvLichSu adapter;
    List<objLichSu> mList;
    lLichSu mLichSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_sua_chua_may_tinh);

        initView();
        mLichSu.getLichSuMayTinh();
        actionExpListView();
    }

    private void actionExpListView() {
        explistmaytinh.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String mamay = mList.get(groupPosition).getMamay();
                objlichsu_maytinhs mLs = mList.get(groupPosition).getLichsu().get(childPosition);

                Bundle bundle = new Bundle();
                bundle.putSerializable("mls",  mLs);
                bundle.putString("mamay",mamay);
                Intent intent = new Intent(LichSuSuaChuaMayTinhActivity.this,TinhTrangThietBiActivity.class);
                intent.putExtra("data",bundle);
                startActivity(intent);

                return false;
            }
        });
    }

    private void initView() {
        mLichSu = new lLichSu(this,this);
        toolbar = findViewById(R.id.tblichsusuachuamaytinh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        explistmaytinh = findViewById(R.id.expLvLichSu);

        WaitDialog.show(this,getText(R.string.loading).toString());


    }

    @Override
    public void loidulieu() {
        Toast.makeText(this, getText(R.string.loitaidulieu), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lichsutrong() {
        Toast.makeText(this, getText(R.string.khongcolichsu), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getlistlichsu(List<objLichSu> list) {
        WaitDialog.dismiss();
        mList = new ArrayList<>();
        mList = list;
        adapter = new aExplvLichSu(this,list);
        explistmaytinh.setAdapter(adapter);
    }

    @Override
    public void getListLichSuCapNhatMayTinh(List<objlichsucapnhatmaytinh> list) {

    }

    @Override
    public void getListLichSuCapNhatThietBiKhac(List<objlichsucapnhatthietbikhac> list) {

    }
}
