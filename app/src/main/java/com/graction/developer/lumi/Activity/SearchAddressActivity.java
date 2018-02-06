package com.graction.developer.lumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.graction.developer.lumi.Adapter.AddressListAdapter;
import com.graction.developer.lumi.Adapter.Listener.ItemOnClickListener;
import com.graction.developer.lumi.Adapter.Listener.OnScrollEndListener;
import com.graction.developer.lumi.Data.DataStorage;
import com.graction.developer.lumi.Model.Address.AddressModelResult;
import com.graction.developer.lumi.Net.Net;
import com.graction.developer.lumi.R;
import com.graction.developer.lumi.UI.ViewAttributeManager;
import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.databinding.ActivitySearchAddressBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressActivity extends BaseActivity {
    private final int CREATE = 1, ADD = 0;
    private ActivitySearchAddressBinding binding;
    private Intent intent = new Intent();
    private AddressListAdapter adapter;
    private ItemOnClickListener itemOnClickListener = item -> {
        intent.putExtra(DataStorage.Key.KEY_ADDRESS_ITEM, item);
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_OK);
    };

    private OnScrollEndListener onScrollEndListener = new OnScrollEndListener() {
        @Override
        public void scroll() {
            onSearch(ADD);
        }
    };

    private int currentPage = 1;

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_address);
        binding.activitySearchAddressRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.setActivity(this);
        ViewAttributeManager.getInstance().setDoneOption(this, binding.activitySearchAddressETKeyword, (v, actionId, event) -> {
            onSearch(CREATE);
            return false;
        });
    }

    /*
    Used PostcodifyModel
    public void onSearch(View view) {
        logger.log(HLogger.LogType.INFO, "onSearch(View view)", "onSearch");
        Net.getInstance().getAddressFactoryIm().searchAddress(PostcodifyModel.getParameter(binding.activitySearchAddressETKeyword.getText() + "")).enqueue(new Callback<PostcodifyModel>() {
            @Override
            public void onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response) {
                if(response.isSuccessful()){
                    PostCodifyListAdapter adapter = new PostCodifyListAdapter(response.body().getResults(), itemOnClickListener);
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
    }*/

    public void onSearch(int stat) {
        logger.log(HLogger.LogType.INFO, "onSearch(View view)", "onSearch");
        Net.getInstance().getAddressFactoryIm().searchAddress(AddressModelResult.getParameter(binding.activitySearchAddressETKeyword.getText() + "", currentPage)).enqueue(new Callback<AddressModelResult>() {
            @Override
            public void onResponse(Call<AddressModelResult> call, Response<AddressModelResult> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response)", "isSuccess");
                    logger.log(HLogger.LogType.INFO, "onSearch(View view) - onResponse(Call<PostcodifyModel> call, Response<PostcodifyModel> response)", "" + response.body());
                    if (stat == CREATE) {
                        adapter = new AddressListAdapter(response.body(), currentPage++, itemOnClickListener, onScrollEndListener);
                        binding.activitySearchAddressRV.setAdapter(adapter);
                    } else if (stat == ADD) {
                        adapter.addItems(response.body().getResults().getJuso());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AddressModelResult> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onSearch(View view) - onFailure(Call<PostcodifyModel> call, Throwable t)", t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityEnd(DataStorage.Request.SEARCH_ADDRESS_NONE_SELECTED);
    }

    private void activityEnd(int code) {
        setResult(code, intent);
        finish();
    }
}
