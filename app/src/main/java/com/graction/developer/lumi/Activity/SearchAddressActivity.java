package com.graction.developer.lumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.graction.developer.lumi.Adapter.AddressListAdapter;
import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Address.PostcodifyModel;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.ActivitySearchAddressBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressActivity extends BaseActivity {
    private ActivitySearchAddressBinding binding;
    private Intent intent = new Intent();
    private AddressListAdapter.ItemOnClickListener itemOnClickListener = item -> {
        intent.putExtra(DataStorage.Intent.KEY_ADDRESS_ITEM, item);
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_OK);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address);
    }

    @Override
    protected void init() {
        binding.activitySearchAddressRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.setActivity(this);
    }

    public void onSearch(View view) {
        logger.log(HLogger.LogType.INFO, "onSearch(View view)", "onSearch");
        Net.getInstance().getFactoryImPostcodifyFactoryIm().searchAddress(PostcodifyModel.getParameter(binding.activitySearchAddressETKeyword.getText() + "")).enqueue(new Callback<PostcodifyModel>() {
            @Override
            public void onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response) {
                if(response.isSuccessful()){
                    AddressListAdapter adapter = new AddressListAdapter(response.body().getResults(), itemOnClickListener);
                    binding.activitySearchAddressRV.setAdapter(adapter);
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response)", "isSuccess");
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response)", ""+response.body());
                }
            }

            @Override
            public void onFailure(Call<PostcodifyModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onSearch(View view) - onFailure(Call<PostcodifyModel> call, Throwable t)", t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_NONE_SELECTED);
    }

    private void activityEnd(int code){
        setResult(code, intent);
        finish();
    }
}
