package lbt.com.manager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;
import com.kongzue.dialog.v2.WaitDialog;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Presenter.iCapNhatTinhTrang;
import lbt.com.manager.Presenter.lCapNhatTinhTrang;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerQRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler, iCapNhatTinhTrang {
    private ZXingScannerView mScannerView;
    private lCapNhatTinhTrang mTinhTrang;
    private String mQRcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view

        mTinhTrang = new lCapNhatTinhTrang(this);
        mQRcode = "";

        ActivityCompat.requestPermissions(ScannerQRCode.this,new String[] {Manifest.permission.CAMERA},1);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void handleResult(Result rawResult) {
        mQRcode = rawResult.getText();
        mTinhTrang.kiemtratinhtrangmay(rawResult.getText());
        WaitDialog.show(this,getText(R.string.dangxuly).toString());

        //PHÁT ÂM BÁO KHI QUÉT THÀNH CÔNG
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.beep);
        mediaPlayer.start();

    }



    @Override
    public void laythongtinmay(boolean isSuccess) {
        WaitDialog.dismiss();
        if(isSuccess){
            Intent intent = new Intent(this,TinhTrangThietBiActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("qrcode",mQRcode);
            intent.putExtra("data",bundle);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, getText(R.string.loi), Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScannerView.resumeCameraPreview(ScannerQRCode.this);
                }
            },1500);
        }

    }

    @Override
    public void capnhatthanhcong(boolean isthanhcong) {

    }

    @Override
    public void maycontot(boolean isTot, objlichsu_maytinhs mls, String mamay) {
        WaitDialog.dismiss();
        //NẾU MÁY HƯ SẼ XEM THÔNG TIN HƯ
        if(!isTot){
            showtinhtrangthietbi(mls,mamay);
        }else{
            showaler();
        }
    }

    @Override
    public void maqrkhonghopke() {
        Toast.makeText(this, getText(R.string.maqrkhonghople), Toast.LENGTH_SHORT).show();
    }

    public void showaler(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getText(R.string.mayhoatdongtot));
        builder.setNegativeButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showtinhtrangthietbi(objlichsu_maytinhs mls, String mamay){
        Bundle bundle = new Bundle();
        bundle.putSerializable("mls",  mls);
        bundle.putString("mamay",mamay);
        Intent intent = new Intent(this,TinhTrangThietBiActivity.class);
        intent.putExtra("data",bundle);
        startActivity(intent);

    }
}
