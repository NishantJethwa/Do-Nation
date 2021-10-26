package com.donation;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TextUtils> personUtils;
    Intent i;
    static int count;

    public RecyclerAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    private final int limit=2;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewclothes, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));

        TextUtils pu = personUtils.get(position);

        holder.pName.setText(pu.getText1());
        holder.pJobProfile.setImageResource(pu.getImages1());

    }

    @Override
    public int getItemCount() {
        if(personUtils.size() > limit) {
            return limit;
        }
        else {
            return personUtils.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference reference;

        public TextView pName;
        public ImageView pJobProfile;
        String number;

        public ViewHolder(final View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.tv1);
            pJobProfile = itemView.findViewById(R.id.img1);

            reference = FirebaseDatabase.getInstance().getReference().child("users/").child(userId);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Number").getValue().toString() != null) {
                        number = dataSnapshot.child("Number").getValue().toString();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextUtils cpu = (TextUtils) view.getTag();

                    if(cpu.getText1() == "Share At Doorstep India") {

                        //Getting intent and PendingIntent instance
                        Intent intent=new Intent(view.getContext(), RecyclerAdapter.class);
                        PendingIntent pi=PendingIntent.getActivity(view.getContext(), 0, intent,0);

                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms=SmsManager.getDefault();
                        sms.sendTextMessage(number, null, "https://docs.google.com/forms/d/e/1FAIpQLSf6ackTKXl1jZAIxZuuIxM5QGKu0-lwAtgWZzP1ZuSltWX5Sw/viewform?usp=sf_link", pi,null);

                        Toast.makeText(view.getContext(), "You will be Sent a Message for Further Details",
                                Toast.LENGTH_LONG).show();

                    }
                    if(cpu.getText1() == "Shanti Avedna Cancer Hospice") {

                        //Getting intent and PendingIntent instance
                        Intent intent=new Intent(view.getContext(), RecyclerAdapter.class);
                        PendingIntent pi=PendingIntent.getActivity(view.getContext(), 0, intent,0);

                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms=SmsManager.getDefault();
                        sms.sendTextMessage(number, null, "https://docs.google.com/forms/d/e/1FAIpQLSetZfOGYiXeXkz-Ekc_VvloClBo0tRKd4AGKv0_j88IIreSSg/viewform?usp=sf_link", pi,null);

                        Toast.makeText(view.getContext(), "You will be Sent a Message for Further Details",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }

    public static Integer getData(){
        return count;
    }
}