package com.graction.developer.lumi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.lumi.Model.Item.AlarmData;
import com.graction.developer.lumi.UI.UIFactory;
import com.graction.developer.lumi.databinding.ItemAlarmBinding;

import java.util.ArrayList;

import static com.graction.developer.lumi.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {
    private ArrayList<AlarmData.AlarmItem> items;

    public AlarmListAdapter(ArrayList<AlarmData.AlarmItem> items) {
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
            UIFactory.setViewWithRateParams(binding.itemAlarmRoot, TYPE_BASIC);
        }

        public void onBind(AlarmData.AlarmItem item){
            binding.setItem(item);
            binding.executePendingBindings();
            if(!item.isSpeaker())
                binding.itemAlarmSBVolume.setOnTouchListener((v, e)->true);
        }
    }
}
