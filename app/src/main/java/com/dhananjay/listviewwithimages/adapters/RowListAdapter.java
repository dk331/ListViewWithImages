package com.dhananjay.listviewwithimages.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhananjay.listviewwithimages.R;
import com.dhananjay.listviewwithimages.models.Row;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RowListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Row> rowList;

    public RowListAdapter(Context context, ArrayList<Row> rowList) {
        this.context = context;
        this.rowList = rowList;
    }

    @Override
    public int getCount() {
        return rowList.size();
    }

    @Override
    public Row getItem(int position) {
        return rowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(ArrayList<Row> rowList) {
        this.rowList = rowList;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();

        Row row = rowList.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (mInflater != null) {
                convertView = mInflater.inflate(R.layout.row_list_item, parent, false);

                holder = new ViewHolder();

                holder.imgProfile = convertView.findViewById(R.id.imgProfile);

                holder.txtTitle = convertView.findViewById(R.id.txtTitle);

                holder.txtDescription = convertView.findViewById(R.id.txtDescription);

                convertView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(row.getTitle());

        holder.txtDescription.setText(row.getDescription());

        if (row.getImageHref() != null && !row.getImageHref().isEmpty()) {
            ImageView image = holder.imgProfile;

            Picasso.get().load(row.getImageHref())
                    .placeholder(R.drawable.other)
                    .error(R.drawable.other)
                    .into(image);
        } else {
            holder.imgProfile.setImageResource(R.drawable.other);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imgProfile;
        TextView txtTitle;
        TextView txtDescription;
    }
}
