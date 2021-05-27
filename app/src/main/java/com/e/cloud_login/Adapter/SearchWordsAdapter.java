package com.e.cloud_login.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Data.SearchWords;
import com.e.cloud_login.R;

import java.util.List;

public class SearchWordsAdapter extends ArrayAdapter<SearchWords> {
    public SearchWordsAdapter(@NonNull Context context, int resource, List<SearchWords> list) {
        super(context, resource,list);
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SearchWords searchWords = getItem(position);
        View view = View.inflate(getContext(), R.layout.bean_search,null);
        TextView word = view.findViewById(R.id.bean_search_tv);
        return view;
    }
    
}
