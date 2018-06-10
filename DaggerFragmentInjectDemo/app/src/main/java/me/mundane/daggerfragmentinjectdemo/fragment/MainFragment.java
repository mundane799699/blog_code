package me.mundane.daggerfragmentinjectdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;
import me.mundane.daggerfragmentinjectdemo.R;
import me.mundane.daggerfragmentinjectdemo.bean.FragmentUser;

public class MainFragment extends Fragment {
    
    private static final String TAG = "MainFragment";
    
    @Inject
    FragmentUser mFragmentUser;
    
    public MainFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "context = " + context);
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "mFragmentUser = " + mFragmentUser);
    }
}
