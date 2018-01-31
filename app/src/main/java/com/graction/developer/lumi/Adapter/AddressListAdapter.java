package com.graction.developer.lumi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.lumi.Model.Address.PostcodifyModel;
import com.graction.developer.lumi.UI.UIFactory;
import com.graction.developer.lumi.databinding.ItemSearchAddressBinding;

import java.util.ArrayList;

import static com.graction.developer.lumi.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private ArrayList<PostcodifyModel.ItemModel> items;
    private ItemOnClickListener itemOnClickListener;

    public AddressListAdapter(ArrayList<PostcodifyModel.ItemModel> items, ItemOnClickListener itemOnClickListener) {
        this.items = items;
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSearchAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), items.size() == position+1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchAddressBinding binding;

        public ViewHolder(ItemSearchAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        public void onBind(PostcodifyModel.ItemModel item, boolean isLast){
            binding.setItem(item);
            binding.setViewHolder(this);
            binding.setIsLast(isLast);
            binding.executePendingBindings();
        }

        public void onClick(PostcodifyModel.ItemModel item){
            itemOnClickListener.onClick(item);
        }
    }

    public interface ItemOnClickListener{
        void onClick(PostcodifyModel.ItemModel item);
    }
}
