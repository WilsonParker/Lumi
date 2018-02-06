package com.graction.developer.lumi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.graction.developer.lumi.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.lumi.Adapter.Listener.OnScrollEndListener;
import com.graction.developer.lumi.Model.Address.AddressModelResult;
import com.graction.developer.lumi.UI.UIFactory;
import com.graction.developer.lumi.Util.Parser.MathematicsManager;
import com.graction.developer.lumi.databinding.ItemSearchAddressBinding;

import java.util.ArrayList;

import static com.graction.developer.lumi.UI.UIFactory.TYPE_BASIC;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private ItemOnClickListener itemOnClickListener;
    private OnScrollEndListener onScrollEndListener;
    private AddressModelResult model;
    private ArrayList<AddressModelResult.AddressModel.Juso> items;
    private int totalCount, currentPage, maxPage;

    public AddressListAdapter(AddressModelResult model, int currentPage, ItemOnClickListener itemOnClickListener, OnScrollEndListener onScrollEndListener) {
        this.model = model;
        this.currentPage = currentPage;
        this.itemOnClickListener = itemOnClickListener;
        this.onScrollEndListener = onScrollEndListener;
        items = model.getResults().getJuso();
        totalCount = Integer.parseInt(model.getResults().getCommon().getTotalCount());
        maxPage = MathematicsManager.getInstance().rounds(model.getResults().getCommon().getTotalCount(), model.getResults().getCommon().getCountPerPage(), 2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSearchAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(items.get(position), totalCount == position + 1);

        if (onScrollEndListener != null && currentPage < maxPage && position + 1 == getItemCount()) {
            Log.i("AddressListAdapter",String.format("%d / %d / %d / %d", currentPage, maxPage, position, getItemCount()));
            //  1 / 10 / 0 / 1
            onScrollEndListener.scroll();
        }
    }

    public void addItems(ArrayList<AddressModelResult.AddressModel.Juso> items){
        this.items.addAll(items);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchAddressBinding binding;

        public ViewHolder(ItemSearchAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            UIFactory.setViewWithRateParams(binding.itemSearchAddressRoot, TYPE_BASIC);
        }

        public void onBind(AddressModelResult.AddressModel.Juso item, boolean isLast) {
            binding.setItem(item);
            binding.setViewHolder(this);
            binding.setIsLast(isLast);
            binding.executePendingBindings();
        }

        public void onClick(AddressModelResult.AddressModel.Juso item) {
            itemOnClickListener.onClick(item);
        }
    }
}
