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
import lbt.com.manager.Models.Firebase.objmaytinhs;

public class lcapnhatmaytinh {

    icapnhatmaytinh mInterface;
    static final String TAG = "lcapnhatmaytinh";
    FirebaseDatabase mDatabase;


    List<objmaytinhs> mamay;

    public lcapnhatmaytinh(icapnhatmaytinh mInterface) {
        this.mInterface = mInterface;
        mDatabase = FirebaseDatabase.getInstance();

    }

    public void getListMayTinh(final String maphong){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    GenericTypeIndicator<List<objmaytinhs>> gen = new GenericTypeIndicator<List<objmaytinhs>>(){};
                    List<objmaytinhs> listmay = new ArrayList<>();
                    //Kiểm tra máy nào consudung mới add vào list
                    for(objmaytinhs maytinh :  dataSnapshot.getValue(gen)){
                        if(maytinh.isConsudung())
                            listmay.add(maytinh);
                    }
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

    public void capnhatTenMay(final objmaytinhs maytinh, final String newName, String maphong){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot val : dataSnapshot.getChildren()){
                        if(val.child("mamay").getValue().toString().matches(maytinh.getMamay())){
                            maytinh.setTenmay(newName);
                            mRef.child(val.getKey())
                                    .setValue(maytinh)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mInterface.ketquacapnhat(true);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mInterface.ketquacapnhat(false);
                                        }
                                    });
                            break;
                        }
                    }
                }else
                    mInterface.ketquacapnhat(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.ketquacapnhat(false);
            }
        });
    }

    public void delMayTinh(final ArrayList<objmaytinhs> mList, final String maphong, final objNguoiDung nguoidung){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");
        //Lấy danh sách máy tính hiện tại
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){

                    GenericTypeIndicator<List<objmaytinhs>> gen = new GenericTypeIndicator<List<objmaytinhs>>(){};
                    List<objmaytinhs> listValues = dataSnapshot.getValue(gen);

                    int size_root = listValues.size();
                    int size_hu = mList.size();
                    //duyệt qua các máy tính tìm máy cần delete sửa consudung về false
                    for (int i=0; i<size_root; i++) {
                        for(int j=0; j<size_hu; j++){
                            if(listValues.get(i).getMamay().matches(mList.get(j).getMamay())){
                                listValues.get(i).setConsudung(false);
                            }
                        }
                    }

                    //Cập nhật lại giá trị
                    mRef.setValue(listValues);

                    //Ghi vào kho
                    luukho(mList);

                    //Cập nhật vào lịch sử
                    capnhatlichsudel(maphong, mList,nguoidung,"delete");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.toString());
                mInterface.ketquadelete(false);
            }
        });
    }

    public void luukho(ArrayList<objmaytinhs> mList){
        for(objmaytinhs maytinhhu : mList){

            DatabaseReference mRef = mDatabase.getReference().child("khochuas").child(maytinhhu.getMamay());
            mRef.setValue(Calendar.getInstance().getTimeInMillis());

        }

    }

    public void addMayTinh(final String maphong, final int soluongmay, final objNguoiDung nguoidung){
        mamay = new ArrayList<>();
        final DatabaseReference mRef = mDatabase.getReference()
                .child("thietbis")
                .child(maphong)
                .child("maytinhs");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){

                    //Lấy danh sách máy tính
                    GenericTypeIndicator<List<objmaytinhs>> gen = new GenericTypeIndicator<List<objmaytinhs>>(){};
                    mamay=  dataSnapshot.getValue(gen);

                    String mm = "";
                    int count = mamay.size();
                    for(int i=count+1; i<count+soluongmay; i++){
                        if(i<10)
                            mm = "0"+String.valueOf(i);
                        else
                            mm = String.valueOf(i);
                        mamay.add(new objmaytinhs(
                                true,
                                maphong+"PC"+mm,
                                "Máy "+ i));
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

    private void capnhatlichsuadd(String maphong, final List<objmaytinhs> mamay, final int soluongmay, final objNguoiDung nguoidung, final  String loai){
        final DatabaseReference mRef = mDatabase.getReference()
                .child("lichsucapnhats")
                .child(maphong)
                .child("maytinh");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Lấy ra danh sách máy tính còn sử dụng trong tất cả máy tính của phòng
                ArrayList<objmaytinhs> listMayTinhConSuDung = new ArrayList<>();
                for(objmaytinhs mt_consudung :  mamay){
                    if(mt_consudung.isConsudung())
                        listMayTinhConSuDung.add(mt_consudung);
                }

                if(dataSnapshot.getValue()!=null){

                    GenericTypeIndicator<List<objlichsucapnhatmaytinh>> gen = new GenericTypeIndicator<List<objlichsucapnhatmaytinh>>(){};
                    List<objlichsucapnhatmaytinh> mlistcapnhat = dataSnapshot.getValue(gen);

                    objlichsucapnhatmaytinh mls = new objlichsucapnhatmaytinh();
                    mls.setEmailnguoicapnhat(nguoidung.getTendangnhap());
                    Calendar calendar = Calendar.getInstance();
                    mls.setNgaycapnhat(calendar.getTimeInMillis());
                    mls.setLoai(loai);

                    List<String> mamayupdate = new ArrayList<>();
                    int count = listMayTinhConSuDung.size() - soluongmay +1;
                    for(int i=count; i<listMayTinhConSuDung.size(); i++){
                        mamayupdate.add(listMayTinhConSuDung.get(i).getMamay());
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

                    //tính vị trí của item add vào
                    int count = listMayTinhConSuDung.size() - soluongmay +1;
                    for(int i=count; i<listMayTinhConSuDung.size(); i++){
                        mamayupdate.add(listMayTinhConSuDung.get(i).getMamay());
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

    private void capnhatlichsudel(String maphong, final ArrayList<objmaytinhs> mList, final objNguoiDung nguoidung, final  String loai){
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
                        mamayupdate.add(mList.get(i).getMamay());
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
                        mamayupdate.add(mList.get(i).getMamay());
                    }
                    mls.setChitiet(mamayupdate);

                    mRef.child("0").setValue(mls);
                }

                mInterface.ketquadelete(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mInterface.result_lichsucapnhatmaytinh(false);
                Log.e(TAG,databaseError.toString());
            }
        });
    }
}
