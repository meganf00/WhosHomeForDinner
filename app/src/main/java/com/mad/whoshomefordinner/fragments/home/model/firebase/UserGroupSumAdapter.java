package com.mad.whoshomefordinner.fragments.home.model.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Megan on 25/5/18.
 */

public class UserGroupSumAdapter extends RecyclerView.Adapter<UserGroupSumAdapter.ViewHolder>  {

    private Context mContext;
    private List<Group> mGroupList;
    private User mUser;
    private List<String> mAllocatedCookName;


    public UserGroupSumAdapter(Context context, List<Group> groups, User user, List<String> allocatedCookName) {
        mGroupList = groups;
        mContext = context;
        mUser = user;
        mAllocatedCookName = allocatedCookName;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName, cookingStatus, whoCooking, meal, deadline, homeQuestionOrNumber,
        noPeopleHome, homeStatus;
        public LinearLayout recycleGroup;
        public ViewHolder(View itemView) {
            super(itemView);
            groupName =  itemView.findViewById(R.id.group_name);
            cookingStatus = itemView.findViewById(R.id.cooking_status);
            whoCooking = itemView.findViewById(R.id.who_cooking);
            meal = itemView.findViewById(R.id.meal);
            deadline = itemView.findViewById(R.id.deadline_txt);
            homeQuestionOrNumber = itemView.findViewById(R.id.home_question);
            noPeopleHome = itemView.findViewById(R.id.no_people_home);
            homeStatus = itemView.findViewById(R.id.home_status);
            recycleGroup = itemView.findViewById(R.id.home_body);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_items, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Group groups = mGroupList.get(position);
        String allocatedCook = mAllocatedCookName.get(position);
        holder.groupName.setText(groups.getName());
        if (mUser.getId().equals(groups.getAllocatedCook())) {
            //current user is cooking tonight
            holder.cookingStatus.setText(R.string.you_are_cooking);
            holder.whoCooking.setText(R.string.making);
            holder.deadline.setText(mContext.getString(R.string.deadline_is) + groups.getDeadline());
            holder.noPeopleHome.setText(Integer.toString(groups.getGroupMembers().size()));
            holder.homeQuestionOrNumber.setText(R.string.RSVDd);
            holder.homeStatus.setVisibility(View.GONE);
        } else {
            //current user is not cooking
            holder.noPeopleHome.setVisibility(View.GONE);
            holder.homeQuestionOrNumber.setText(R.string.home_q);
            holder.cookingStatus.setText(R.string.not_cooking);

            boolean home = false;
            List<String> tempMembers = groups.getGroupMembers();

            List<String> groupMembers = new ArrayList<>();
            groupMembers = groups.getGroupMembers();

            for (String member : groupMembers) {
                if (member.equals(mUser.getId())) {
                    home = true;
                }
            }

            if (home) {
                holder.homeStatus.setText(R.string.yes_txt);
            } else {
                holder.homeStatus.setText(R.string.no_txt);
            }
            holder.whoCooking.setText(allocatedCook + mContext.getString(R.string.is_making));
            holder.deadline.setText(mContext.getString(R.string.let_them_know) + groups.getDeadline());
        }
        holder.meal.setText(groups.getMeal());

        holder.recycleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    @Override
    public int getItemCount() {
        return mGroupList.size();
    }



}
