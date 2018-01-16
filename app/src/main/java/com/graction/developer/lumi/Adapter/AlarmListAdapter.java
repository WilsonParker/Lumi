package com.graction.developer.lumi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.lumi.Model.Item.AlarmItem;
import com.graction.developer.lumi.databinding.ItemAlarmBinding;

import java.util.ArrayList;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
    private ArrayList<AlarmItem> items;

    public AlarmListAdapter(ArrayList<AlarmItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, null));
        return new ViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position));
        Log.i("TAG", "binding : " + items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAlarmBinding binding;

        public ViewHolder(ItemAlarmBinding binding) {
            super(binding.getRoot());
//            binding = DataBindingUtil.bind(itemView);
            this.binding = binding;
        }

        public void onBind(AlarmItem item){
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
