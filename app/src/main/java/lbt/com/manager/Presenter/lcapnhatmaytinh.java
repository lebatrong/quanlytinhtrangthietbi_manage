package lbt.com.manager.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.Firebase.objNguoiDung;
import lbt.com.manager.Models.Firebase.objlichsucapnhatmaytinh;

public class lcapnhatmaytinh {

    icapnhatmaytinh mInterface;
    static final String TAG = "lcapnhatmaytinh";
    FirebaseDatabase mDatabase;

    String mm = "";
    List<String> mamay;

    public lcapnhatmaytinh(icapnhatmaytinh mInterface) {
        this.mInterface = mInterface;
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void getListMayTinh( String maphong){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    GenericTypeIndicator<List<String>> gen = new GenericTypeIndicator<List<String>>(){};
                    List<String> listmay = dataSnapshot.getValue(gen);

                    mInterface.danhsachmaytinh(listmay);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.loitaidanhsachmay();
                Log.e(TAG,databaseError.toString());
            }
        });

    }

    public void delMayTinh(final ArrayList<String> mList, String maphong, final objNguoiDung nguoidung){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){

                    GenericTypeIndicator<List<String>> gen = new GenericTypeIndicator<List<String>>(){};
                    List<String> listValues = dataSnapshot.getValue(gen);

                    int count_remove = mList.size();

                    for(int rm=0; rm< count_remove; rm++){
                        listValues.remove(mList.get(rm));
                    }

                    mRef.setValue(listValues);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.toString());
            }
        });
        capnhatlichsudel(maphong, mList,nguoidung,"delete");

    }

    public void addMayTinh(final String maphong, final int soluongmay, final objNguoiDung nguoidung){

        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    GenericTypeIndicator<List<String>> gen = new GenericTypeIndicator<List<String>>(){};
                    mamay = dataSnapshot.getValue(gen);

                    int count = mamay.size();

                    for(int i=count+1; i<count+soluongmay; i++){
                        if(i<10)
                            mm = "0"+String.valueOf(i);
                        else
                            mm = String.valueOf(i);
                        mamay.add(maphong+"PC"+mm);
                    }

                    DatabaseReference ref = mDatabase.getReference()
                            .child("thietbis")
                            .child(maphong)
                            .child("maytinhs");
                    ref.setValue(mamay)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mInterface.result_addmaytinh(false);
                                    Log.e(TAG,e.toString());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //Cập nhật lịch sử cập nhật
                                    capnhatlichsuadd(maphong,mamay, soluongmay,nguoidung,"add");

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.result_addmaytinh(false);
                Log.e(TAG,databaseError.toString());
            }
        });
    }

    private void capnhatlichsuadd(String maphong, final List<String> mamay, final int soluongmay, final objNguoiDung nguoidung, final  String loai){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("lichsucapnhats")
                .child(maphong)
                .child("maytinh");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){

                    GenericTypeIndicator<List<objlichsucapnhatmaytinh>> gen = new GenericTypeIndicator<List<objlichsucapnhatmaytinh>>(){};
                    List<objlichsucapnhatmaytinh> mlistcapnhat = dataSnapshot.getValue(gen);

                    objlichsucapnhatmaytinh mls = new objlichsucapnhatmaytinh();
                    mls.setEmailnguoicapnhat(nguoidung.getTendangnhap());
                    Calendar calendar = Calendar.getInstance();
                    mls.setNgaycapnhat(calendar.getTimeInMillis());
                    mls.setLoai(loai);

                    List<String> mamayupdate = new ArrayList<>();
                    int count = mamay.size() - soluongmay-1;
                    for(int i=count; i<mamay.size(); i++){
                        mamayupdate.add(mamay.get(i));
                    }
                    mls.setChitiet(mamayupdate);

                    mRef.child(String.valueOf(mlistcapnhat.size())).setValue(mls);

                }else {
                    objlichsucapnhatmaytinh mls = new objlichsucapnhatmaytinh();
                    mls.setEmailnguoicapnhat(nguoidung.getTendangnhap());
                    Calendar calendar = Calendar.getInstance();
                    mls.setNgaycapnhat(calendar.getTimeInMillis());
                    mls.setLoai(loai);
                    List<String> mamayupdate = new ArrayList<>();
                    int count = mamay.size() - soluongmay;
                    for(int i=count; i<mamay.size(); i++){
                        mamayupdate.add(mamay.get(i));
                    }
                    mls.setChitiet(mamayupdate);

                    mRef.child("0").setValue(mls);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.result_lichsucapnhatmaytinh(false);
                Log.e(TAG,databaseError.toString());
            }
        });
    }

    private void capnhatlichsudel(String maphong, final ArrayList<String> mList, final objNguoiDung nguoidung, final  String loai){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("lichsucapnhats")
                .child(maphong)
                .child("maytinh");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){

                    GenericTypeIndicator<List<objlichsucapnhatmaytinh>> gen = new GenericTypeIndicator<List<objlichsucapnhatmaytinh>>(){};
                    List<objlichsucapnhatmaytinh> mlistcapnhat = dataSnapshot.getValue(gen);

                    objlichsucapnhatmaytinh mls = new objlichsucapnhatmaytinh();
                    mls.setEmailnguoicapnhat(nguoidung.getTendangnhap());
                    Calendar calendar = Calendar.getInstance();
                    mls.setNgaycapnhat(calendar.getTimeInMillis());
                    mls.setLoai(loai);

                    List<String> mamayupdate = new ArrayList<>();
                    int count = mList.size();
                    for(int i=0; i<count; i++){
                        mamayupdate.add(mList.get(i));
                    }
                    mls.setChitiet(mamayupdate);

                    mRef.child(String.valueOf(mlistcapnhat.size())).setValue(mls);

                }else {
                    objlichsucapnhatmaytinh mls = new objlichsucapnhatmaytinh();
                    mls.setEmailnguoicapnhat(nguoidung.getTendangnhap());
                    Calendar calendar = Calendar.getInstance();
                    mls.setNgaycapnhat(calendar.getTimeInMillis());
                    mls.setLoai(loai);
                    List<String> mamayupdate = new ArrayList<>();
                    int count = mList.size();
                    for(int i=0; i<count; i++){
                        mamayupdate.add(mList.get(i));
                    }
                    mls.setChitiet(mamayupdate);

                    mRef.child("0").setValue(mls);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.result_lichsucapnhatmaytinh(false);
                Log.e(TAG,databaseError.toString());
            }
        });
    }
}
