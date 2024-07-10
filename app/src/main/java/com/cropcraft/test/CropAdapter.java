package com.cropcraft.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CropAdapter extends ArrayAdapter<CropItem> {

    private int layoutResource;
    private int nameTextViewResourceId;

    public CropAdapter(Context context, int layoutResource,
                       int nameTextViewResourceId, List<CropItem> items) {
        super(context, layoutResource, items);
        this.layoutResource = layoutResource;
        this.nameTextViewResourceId = nameTextViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResource, parent, false);
        }

        CropItem item = getItem(position);

        if (item != null) {
            TextView nameTextView = convertView.findViewById(nameTextViewResourceId);

            if (nameTextView != null) {
                nameTextView.setText(item.getCropName());
            }
            // Add a click listener to each item in the list
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsIntent = new Intent(getContext(), CropDetailsActivity.class);
                    detailsIntent.putExtra("cropName", item.getCropName());
                    getContext().startActivity(detailsIntent);
                }
            });

        }

        return convertView;
    }
}