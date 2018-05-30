package com.mad.whoshomefordinner.fragments.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String tempNameCook = "";


    public UserGroupSumAdapter(Context context, List<Group> groups, User user) {
        mGroupList = groups;
        mContext = context;
        mUser = user;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupName, cookingStatus, whoCooking, meal, deadline, homeQuestionOrNumber,
        noPeopleHome, homeStaus;
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
            homeStaus = itemView.findViewById(R.id.home_status);
            recycleGroup = itemView.findViewById(R.id.home_body);
            recycleGroup.setOnClickListener(new View.OnClickListener() {
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
        if (mUser.getId() == groups.getAllocatedCook()) {
            //current user is cooking tonight
            holder.cookingStatus.setText("You are cooking tonight");
            holder.whoCooking.setText("You are making ");
            holder.deadline.setText("Your deadline is" + " " + groups.getDeadline());
            holder.noPeopleHome.setText(groups.getGroupMembers().size());
            holder.homeQuestionOrNumber.setText("Number of people RSVD'd: ");
        } else {
            //current user is not cooking
            holder.noPeopleHome.setVisibility(View.GONE);
            holder.homeQuestionOrNumber.setText("Are you home? ");
            holder.cookingStatus.setText("You are not cooking tonight");

            boolean home = false;
            List<String> tempMembers = groups.getGroupMembers();

            for (String string : tempMembers) {
                if (string == mUser.getId()) {
                    home = true;
                }
            }

            if (home) {
                holder.homeStaus.setText("Yes");
            } else {
                holder.homeStaus.setText("No");
            }
            DatabaseReference cookRef = FirebaseDatabase.getInstance().getReference().child("User's").child(groups.getAllocatedCook().toString());
            cookRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.getKey().equals("Name")) {
                                tempNameCook = item.getValue().toString();
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            holder.whoCooking.setText(tempNameCook + " " + "is making ");
            holder.deadline.setText("Let them know by " + groups.getDeadline());
        }
        holder.meal.setText(groups.getMeal());


    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }



}
