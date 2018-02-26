package com.example.popovov.newyorktimes.Fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.popovov.newyorktimes.Adapter.MyAdapter;
import com.example.popovov.newyorktimes.R;
import com.example.popovov.newyorktimes.model.Answer;
import com.example.popovov.newyorktimes.model.Result;
import com.example.popovov.newyorktimes.network.ApiService;
import com.example.popovov.newyorktimes.network.NetworkStatusChecker;
import com.example.popovov.newyorktimes.network.RetroClient;
import com.example.popovov.newyorktimes.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MostFragment extends Fragment {
  private RecyclerView recyclerView;
  private ProgressBar progressBar;
  private List<Result> res;
  private String path;
    public MostFragment() {
        this.res=new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(NetworkStatusChecker.isNetworkAvailable(getActivity())){
            progressBar.setVisibility(ProgressBar.VISIBLE);
            res.clear();
            MyAdapter adapter = new MyAdapter(res,getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            RetroGet(AppConfig.mostemailed);
        }else{
            ShowMessage("Ошибка подключения к интернету",getView());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_most, container, false);

        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)view.findViewById(R.id.Recycler);

        recyclerView.setHasFixedSize(false);

        return view;
    }

    public void ShowMessage(String msg,View view){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("", null).show();
    }

    public void RetroGet(String str){
        RetroClient retroClient=new RetroClient();

        ApiService api= retroClient.getApiService();
        retrofit2.Call<Answer> call=api.getMyJson(str);
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(retrofit2.Call<Answer> call, Response<Answer> response) {
                if(response.isSuccessful()){
                    if(response.code()==200){
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        res.clear();
                        res.addAll(response.body().getResults());

                        recyclerView.getAdapter().notifyDataSetChanged();
                    }else{
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        ShowMessage("Все пропало",getView());

                    }
                }else{
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    ShowMessage("Что то пошло не так !!!",getView());

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Answer> call, Throwable t) {
                Log.println(Log.DEBUG,t.getMessage(),"ss");
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                ShowMessage(t.getMessage(),getView());
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView.getAdapter().notifyItemRangeRemoved(0,res.size());
        recyclerView.removeAllViewsInLayout();
    }
}
