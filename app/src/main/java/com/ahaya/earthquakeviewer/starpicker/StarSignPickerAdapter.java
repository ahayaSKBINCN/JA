package com.ahaya.earthquakeviewer.starpicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahaya.earthquakeviewer.R;

public class StarSignPickerAdapter extends RecyclerView.Adapter<StarSignPickerAdapter.ViewHolder> {
    private String[] mStarSigns = {"Aries", "Taurus", "Gemini", "Cancer",
            "Leo", "Virgo", "Libra", "Scorpio",
            "Sagittarius", "Capricorn", "Aquarius", "Pisces"};

    public interface IAdapterItemClickListener {
        void onItemClicked(String selectedItem);
    }

    IAdapterItemClickListener mAdapterItemClickListener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_sign_list_item,parent,false);
        return new ViewHolder(view, null);
    }
    public StarSignPickerAdapter(){}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(mStarSigns[position]);
        holder.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mAdapterItemClickListener){
                    mAdapterItemClickListener.onItemClicked(mStarSigns[position]);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return mStarSigns == null ? 0 : mStarSigns.length;
    }




    public void setOnAdapterItemClick(IAdapterItemClickListener adpaterItemClickHandler ){
        mAdapterItemClickListener = adpaterItemClickHandler;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView textView;
        public View.OnClickListener listener;



        public ViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.star_item_text);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(listener != null)
            listener.onClick(v);
        }
    }
}
