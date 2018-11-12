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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import lbt.com.manager.CustomInterface.iDeleteRclvDanhSachMay;
import lbt.com.manager.Models.App.objthietbimaytinh_app;
import lbt.com.manager.R;

public class aRclvDanhSachMay_Add extends RecyclerView.Adapter<aRclvDanhSachMay_Add.ViewHolder> {

    ArrayList<String> mList;
    Context context;
    ArrayList<Integer> listChecked;

    iDeleteRclvDanhSachMay mInterface;

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onClick(View v, int pos);
    }

    public ArrayList<Integer> getListChecked() {
        return listChecked;
    }

    public void setOnClickListener(OnItemClickListener ls){
        this.listener = ls;
    }


    public aRclvDanhSachMay_Add(ArrayList<String> mList, Context context, iDeleteRclvDanhSachMay mInterface) {
        this.mList = mList;
        this.context = context;
        this.mInterface = mInterface;
        this.listChecked = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.row_maytinh,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvMaThietBi.setText(mList.get(position));

        holder.lnlThietBi.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onLongClick(View v) {
                if(!listChecked.contains(position)) {
                    holder.lnlThietBi.setBackground(context.getDrawable(R.drawable.background_check));
                    listChecked.add(position);
                    holder.imvCheck.setVisibility(View.VISIBLE);
                }else {
                    int size = listChecked.size();
                    for(int i=0; i<size; i++){
                        if(listChecked.get(i)==position) {
                            listChecked.remove(i);
                            break;
                        }
                    }
                    holder.lnlThietBi.setBackground(context.getDrawable(R.drawable.background_nocheck));
                    holder.imvCheck.setVisibility(View.GONE);
                }

                mInterface.deleteItems(listChecked);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMaThietBi;
        ImageView imvCheck;
        LinearLayout lnlThietBi;

        public ViewHolder(final View itemView) {
            super(itemView);
            lnlThietBi = itemView.findViewById(R.id.lnlmaytinh_add);
            tvMaThietBi = itemView.findViewById(R.id.tvmamayrclv_edit);
            imvCheck = itemView.findViewById(R.id.imvCheck_edit);
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
