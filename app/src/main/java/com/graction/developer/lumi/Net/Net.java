package com.graction.developer.lumi.Net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net {
	private static final Net instance = new Net();
//	private static final String BASE_URL = "http://10.0.2.2:8101/lumi/";
	public static final String BASE_URL = "http://192.168.0.8:8101/lumi/"
								, POSTCODIFY_URL = "https://api.poesis.kr/";
	private NetFactoryIm netFactoryIm;
	private PostcodifyFactoryIm postcodifyFactoryIm;
	private Retrofit retrofit, postcodifyRetrofit;
	
	{
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		postcodifyRetrofit = new Retrofit.Builder()
				.baseUrl(POSTCODIFY_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	public static Net getInstance() {
		return instance;
	}

	public NetFactoryIm getFactoryIm() {
		if (netFactoryIm == null)
			netFactoryIm = retrofit.create(NetFactoryIm.class);
		return netFactoryIm;
	}

	public PostcodifyFactoryIm getFactoryImPostcodifyFactoryIm() {
		if (postcodifyFactoryIm== null)
			postcodifyFactoryIm = postcodifyRetrofit.create(PostcodifyFactoryIm.class);
		return postcodifyFactoryIm;
	}
}
