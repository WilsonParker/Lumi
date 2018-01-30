package com.graction.developer.lumi.Net;

import com.graction.developer.lumi.Model.Address.PostcodifyModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PostcodifyFactoryIm {

	// Search Address
	@GET("post/search.php")
	Call<PostcodifyModel> searchAddress(@QueryMap Map<String, String>map);
}
