package com.mad.whoshomefordinner.fragments.group.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.model.Group;

import java.util.List;

/**
 * Created by Megan on 30/5/18.
 *
 * Adapter for RecyclerView implemented in view to create and bind views which related
 * to each data set
 */

public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.ViewHolder>  {

    private Context mContext;
    private List<Group> mGroupsList;

    /**
     * Constructor used to create and initialise the adapter
     * @param context
     * @param groups
     */

    public GroupViewAdapter (Context context, List<Group> groups) {
        mContext = context;
        mGroupsList = groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.group_items, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = mGroupsList.get(position);
        holder.groupName.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return mGroupsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName;
        public LinearLayout holder;

        public ViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.title_group_name);
            holder = itemView.findViewById(R.id.group_holder);

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }





}
