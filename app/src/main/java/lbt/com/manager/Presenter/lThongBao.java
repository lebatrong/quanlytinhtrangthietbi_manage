package lbt.com.manager.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Models.Firebase.objlichsu_thietbikhacs;

public class lThongBao {
    public static final String TAG = "lThongBao";


    FirebaseDatabase mDatabase;
    iThongBao mInterface;
    Context context;



    public lThongBao(iThongBao mInterface,Context context) {
        this.mInterface = mInterface;
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
    }


    public void LangNgeThongBao(final String maphong){

        //LẮNG NGHE SỰ KIỆN CỦA PHÒNG MÁY DO USER QUẢN LÝ
        DatabaseReference mTB = mDatabase.getReference().child("lichsusuachuas").child(maphong);
        mTB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {

                    List<objThietBi> mTB = new ArrayList<>();
                    objlichsu_thietbikhacs mThietBiKhac = null;

                    //PHÂN TÁCH DATA
                    //===========================================
                    // NODE MAYTINHS
                    if(dataSnapshot.child("maytinhs").getValue()!=null) {
                        GenericTypeIndicator<List<objlichsu_maytinhs>> gen = new GenericTypeIndicator<List<objlichsu_maytinhs>>() {
                        };
                        List<objLichSu> mList = new ArrayList<>();
                        for (DataSnapshot i : dataSnapshot.child("maytinhs").getChildren()) {
                            List<objlichsu_maytinhs> list = i.getValue(gen);
                            mList.add(new objLichSu(i.getKey(), list));
                        }


                        //GÁN LIST THIẾT BỊ HƯ
                        for (objLichSu j : mList) {
                            if (!j.getLichsu().get(j.getLichsu().size() - 1).isDasuachua()) {
                                objThietBi obj = new objThietBi();
                                obj.setMathietbi(j.getMamay());
                                obj.setLichsusuachua(j.getLichsu().get(j.getLichsu().size() - 1));
                                mTB.add(obj);
                            }
                        }
                    }


                    //NODE THIẾT BỊ KHÁC

                    if(dataSnapshot.child("thietbikhacs").getValue()!=null) {
                        GenericTypeIndicator<List<objlichsu_thietbikhacs>> gen = new GenericTypeIndicator<List<objlichsu_thietbikhacs>>() {};
                        List<objlichsu_thietbikhacs> mList = dataSnapshot.child("thietbikhacs").getValue(gen);

                        if(!mList.get(mList.size()-1).isDasuachua())
                            mThietBiKhac = mList.get(mList.size()-1);
                    }


                    if(mTB.size()!=0 || mThietBiKhac != null)
                        mInterface.pushthongbao(mTB, mThietBiKhac);
                    else
                        mInterface.phongmayhoatdongtot();




                }else{
                    mInterface.phongmayhoatdongtot();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.toString());
                mInterface.loiduongtruyen();
            }
        });
    }


}
