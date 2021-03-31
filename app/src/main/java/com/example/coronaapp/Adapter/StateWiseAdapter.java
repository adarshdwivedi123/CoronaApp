package com.example.coronaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronaapp.Model.StateWiseModel;
import com.example.coronaapp.R;

import java.util.ArrayList;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder>{
Context mContext;
private ArrayList<StateWiseModel> arrayList;


    public StateWiseAdapter(Context mContext, ArrayList<StateWiseModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stateName,stateTotalCases;
        LinearLayout lin_state_wise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName=itemView.findViewById(R.id.statewise_layout_name_textview);
            stateTotalCases=itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            lin_state_wise=itemView.findViewById(R.id.layout_state_wise_lin);

        }
    }

    @NonNull
    @Override
    public StateWiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise,parent,false);
            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StateWiseAdapter.ViewHolder holder, int position) {

        StateWiseModel currentItem=arrayList.get(position);
        String stateName=currentItem.getState();
        String stateTotal=currentItem.getConfirmed();
        holder.stateTotalCases.setText(stateTotal);
        holder.stateName.setText(stateName);
    }

public void filter(ArrayList<StateWiseModel>filteredList)
{
    arrayList=filteredList;
    notifyDataSetChanged();
}

    @Override
    public int getItemCount() {
        return arrayList==null?0:arrayList.size();
    }
}
