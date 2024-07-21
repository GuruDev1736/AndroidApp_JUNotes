package com.gurudev.junotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gurudev.junotes.Model.TrackPerfromanceModule;
import com.gurudev.junotes.R;

import java.util.List;

public class TrackPerformanceAdapter extends PagerAdapter {
    private Context context;
    private List<TrackPerfromanceModule> list;

    public TrackPerformanceAdapter(Context context, List<TrackPerfromanceModule> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.track_performance_item, container, false);

        TextView title,description,ct1,percetageText,commentText,statusText;

        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        ct1 = view.findViewById(R.id.ct1);
        percetageText =view.findViewById(R.id.percentageText);
        commentText = view.findViewById(R.id.commentText);
        statusText = view.findViewById(R.id.statusText);


        title.setText(list.get(position).getTitle());
        description.setText(list.get(position).getDescription());
        ct1.setText(list.get(position).getCt1());
        percetageText.setText(list.get(position).getPercetageText());
        commentText.setText(list.get(position).getCommentText());
        statusText.setText(list.get(position).getStatusText());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
