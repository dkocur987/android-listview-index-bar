package com.dominikk.sidebar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dominikk.sidebar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominik K on 2017-02-07.
 */

public class IdTextAdapter extends ArrayAdapter<IdTextInterface> implements SectionIndexer {

    private int resId;
    private static LayoutInflater inflater = null;
    private ArrayList<IdTextInterface> list;
    private Context context;

    private HashMap<String, Integer> map;
    private String[] sections;

    public IdTextAdapter(Context context, int resource, ArrayList<IdTextInterface> objects) {
        super(context, resource, objects);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        this.list = objects;
        this.resId = resource;

        refreshIndexMap();
    }

    public void refreshIndexMap() {
        map = new LinkedHashMap<>();

        for (int i = 0; i < list.size(); i++) {
            IdTextInterface obj = list.get(i);
            String fstChar = obj.getText().substring(0, 1);
            fstChar = fstChar.toUpperCase(Locale.getDefault());
            if(!map.containsKey(fstChar)) {
                map.put(fstChar, i);
            }
        }

        Set<String> letters = map.keySet();
        sections = new String[letters.size()];
        letters.toArray(sections);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public IdTextInterface getItem(int position) {
        if(list.size() > 0 && position < list.size())
            return list.get(position);
        else
            return null;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return map.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public static class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.id_row_value)
        TextView idText;
        @BindView(R.id.text_row_value)
        TextView descText;

        public boolean areViewsNull() {
            return (idText == null || descText == null);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (convertView == null)
            view = inflater.inflate(resId, null, false);

        holder = new ViewHolder(view);

        if(!holder.areViewsNull()) {
            IdTextInterface item = list.get(position);
            holder.idText.setText(String.format(Locale.getDefault(), "%d", item.getId()));
            holder.descText.setText(item.getText());
        }

        return view;
    }
}