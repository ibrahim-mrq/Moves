package com.moves.network.operations;

import com.moves.network.retrofit.RetrofitClient;
import com.moves.network.retrofit.RetrofitInterface;

public class BaseOperation {

    final static RetrofitInterface apiService = RetrofitClient.getApiService();
}
