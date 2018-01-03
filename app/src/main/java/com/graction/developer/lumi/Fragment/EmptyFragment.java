package com.graction.developer.lumi.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.FragmentHomeBinding;

public class EmptyFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private HLogger logger;

    public static Fragment getInstance() {
        Fragment fragment = new EmptyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
        return binding.getRoot();
    }

    @Override
    void init(View view) {
        logger = new HLogger(getClass());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
