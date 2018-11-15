package lbt.com.manager.customAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import lbt.com.manager.CustomInterface.iDeleteRclvDanhSachMay;
import lbt.com.manager.Models.App.objmaytinh_app;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.Models.Firebase.objmaytinhs;
import lbt.com.manager.R;

public class aRclvDanhSachMay_Add extends RecyclerView.Adapter<aRclvDanhSachMay_Add.ViewHolder> {

    ArrayList<objmaytinh_app> mList;
    Context context;

    boolean isChecked;

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onClick(View v, int pos);
    }

    public ArrayList<objmaytinh_app> getmList() {
        return mList;
    }

    public void setmList(ArrayList<objmaytinh_app> mList) {
        this.mList = mList;
        notifyItemRangeChanged(0, mList.size());
    }

    public objmaytinh_app getItem(int position){
        return mList.get(position);
    }


    public void setOnClickListener(OnItemClickListener ls){
        this.listener = ls;
    }


    public aRclvDanhSachMay_Add(ArrayList<objmaytinh_app> mList, Context context) {
        this.mList = mList;
        this.context = context;
        this.isChecked = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.row_maytinh,parent,false);
        return new ViewHolder(v);
    }

    public void setChecked(boolean checked){
        this.isChecked = checked;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.e("kiemtra", "position "+ position + " - " + mList.get(position).isChecked());

        holder.tvMaThietBi.setText("("+mList.get(position).getMaytinh().getMamay()+")");
        holder.tvTenThietBi.setText(mList.get(position).getMaytinh().getTenmay());

        holder.chkCheck.setChecked(mList.get(position).isChecked());

        if(isChecked) {
            holder.chkCheck.setVisibility(View.VISIBLE);
        }else {
            holder.chkCheck.setVisibility(View.GONE);
        }

        holder.chkCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkCheck.isChecked())
                    mList.get(position).setChecked(true);
                else
                    mList.get(position).setChecked(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMaThietBi;
        TextView tvTenThietBi;
        CheckBox chkCheck;
        LinearLayout lnlThietBi;

        public ViewHolder(final View itemView) {
            super(itemView);
            lnlThietBi = itemView.findViewById(R.id.lnlmaytinh_add);
            tvMaThietBi = itemView.findViewById(R.id.tvmamayrclv_edit);
            tvTenThietBi = itemView.findViewById(R.id.tvtenmayrclv_edit);
            chkCheck = itemView.findViewById(R.id.imvCheck_edit);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_recyclerview);
            itemView.setAnimation(animation);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        listener.onClick(itemView,getLayoutPosition());
                    }
                }
            });



        }
    }

}
