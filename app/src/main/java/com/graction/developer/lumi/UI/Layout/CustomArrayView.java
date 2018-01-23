package com.graction.developer.lumi.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.lumi.R;
import com.graction.developer.lumi.databinding.ItemArrayViewBinding;

/**
 * Created by Graction06 on 2018-01-23.
 */

public class CustomArrayView extends HareArrayView2<CustomArrayView.CustomItemView, CustomArrayView.CustomItemViewModel> {

    public CustomArrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void setTypeArray(TypedArray typedArray) {

    }

    @Override
    public CustomItemView createItemView(Context context, ViewGroup viewGroup, int resId) {
        return new CustomItemView(context, viewGroup, resId);
    }

    public class CustomItemView extends HareArrayView2.ItemView {
        private ItemArrayViewBinding itemArrayBinding;
        private Context context;

        public CustomItemView(Context context, ViewGroup viewGroup, int resId) {
            super(context, viewGroup, resId);
            this.context = context;
        }

        @Override
        public View setClickListener() {
            return itemArrayBinding.text;
        }

        @Override
        public View createView(Context context, ViewGroup viewGroup, int resId) {
            itemArrayBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resId, viewGroup, false);
            return itemArrayBinding.getRoot();
        }

        @Override
        public OnItemViewClickListener setFirstClick() {

            return (view, model) -> {
                itemArrayBinding.text.setTextColor(ContextCompat.getColor(context, R.color.item_array_view_text_second));
            };
        }

        @Override
        public OnItemViewClickListener setSecondClick() {
            return (view, model) -> {
                itemArrayBinding.text.setTextColor(ContextCompat.getColor(context, R.color.item_array_view_text_first));
            };
        }

        @Override
        public View toBind(ItemViewModel item) {
            super.toBind(item);
            itemArrayBinding.setItemModel((CustomItemViewModel) item);
            itemArrayBinding.executePendingBindings();
            return itemArrayBinding.getRoot();
        }
    }

    public class CustomItemViewModel extends HareArrayView2.ItemViewModel {
        private String text;
        private int index;

        public CustomItemViewModel(String text, int index) {
            this.text = text;
            this.index = index;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
