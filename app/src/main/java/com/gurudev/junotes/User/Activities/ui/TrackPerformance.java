package com.gurudev.junotes.User.Activities.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gurudev.junotes.User.Adapter.TrackPerformanceAdapter;
import com.gurudev.junotes.Model.TrackPerfromanceModule;
import com.gurudev.junotes.R;

import java.util.ArrayList;
import java.util.List;

public class TrackPerformance extends Fragment {

    private ViewPager viewPager;
    private TrackPerformanceAdapter adapter;
    private List<TrackPerfromanceModule> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.track_performance, container, false);

        list = new ArrayList<>();
        list.add(new TrackPerfromanceModule("Track Performance", "Enter your class test marks", "Class Test Makrs / 150", "0", "GOOD", "PASS"));
        list.add(new TrackPerfromanceModule("Convert CGPA To Percentages", "Enter your CGPA marks", "Enter Your CGPA ","0", "GOOD", "PASS"));

        adapter = new TrackPerformanceAdapter(getContext(),list);

        viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);


        return view;
    }
}
