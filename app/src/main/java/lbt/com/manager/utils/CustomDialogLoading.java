package lbt.com.manager.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import lbt.com.manager.R;

public class CustomDialogLoading {
    private Dialog progressDialog;
    private Context context;

    public CustomDialogLoading(Context context) {
        this.context = context;
        this.progressDialog = new Dialog(context,R.style.progress_dialog);
    }

    public void showDialog(String mess){
        progressDialog.setContentView(R.layout.custom_dialog_layout);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(mess);
        progressDialog.show();
    }

    public void dismissDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
}