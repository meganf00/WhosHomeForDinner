package com.mad.whoshomefordinner.fragments.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mad.whoshomefordinner.model.Group;
import com.mad.whoshomefordinner.R;
import com.mad.whoshomefordinner.model.User;

import java.util.List;

/**
 * Created by Megan on 25/5/18.
 */

public class UserGroupSumAdapter extends RecyclerView.Adapter<UserGroupSumAdapter.ViewHolder>  {

    private Context mContext;
    private List<Group> mGroupList;
    private User mUser;


    public UserGroupSumAdapter(Context context, List<Group> groups, User user) {
        mGroupList = groups;
        mContext = context;
        mUser = user;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName, cookingStatus, whoCooking, meal, deadline, homeQuestionNumber;
        public RadioGroup areYouHome;
        public ViewHolder(View itemView) {
            super(itemView);
            groupName =  itemView.findViewById(R.id.group_name);
            cookingStatus = itemView.findViewById(R.id.cooking_status);
            whoCooking = itemView.findViewById(R.id.who_cooking);
            meal = itemView.findViewById(R.id.meal);
            deadline = itemView.findViewById(R.id.deadline_txt);
            homeQuestionNumber = itemView.findViewById(R.id.home_question);
            areYouHome = itemView.findViewById(R.id.are_you_home);

            areYouHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_items, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group groups = mGroupList.get(position);
        holder.groupName.setText(groups.getName());
        if (mUser.getId() == groups.getAllocatedCook().getId()) {
            //current user is cooking tonight
            holder.cookingStatus.setText("You are cooking tonight");
            holder.whoCooking.setText("You are making ");
            holder.deadline.setText("Your deadline is  " + groups.getDeadline());
        } else {
            //current user is not cooking
            holder.cookingStatus.setText(groups.getAllocatedCook().getName() + "You are not cooking tonight");
            holder.whoCooking.setText(groups.getAllocatedCook().getName().toString() + "is making ");
            holder.deadline.setText("Let them know by " + groups.getDeadline());
        }
        holder.meal.setText(groups.getMeal());


    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }



}
