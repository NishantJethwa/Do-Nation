package com.donation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerAdapterBooks extends RecyclerView.Adapter<RecyclerAdapterBooks.ViewHolder> {

    private Context context;
    private List<TextUtils> personUtils;
    static int count;

    public RecyclerAdapterBooks(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    private final int limit=6;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
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

        public TextView pName;
        public ImageView pJobProfile;

        public ViewHolder(final View itemView) {
            super(itemView);

            context = itemView.getContext();
            pName = itemView.findViewById(R.id.tv1);
            pJobProfile = itemView.findViewById(R.id.img1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextUtils cpu = (TextUtils) view.getTag();

                    if(cpu.getText1() == "Share At Doorstep India") {
                        Intent intent = new Intent(view.getContext(), Success.class);
                        context.startActivity(intent);
                    }
                    if(cpu.getText1() == "Shanti Avedna Cancer Hospice") {
                        Intent intent=new Intent(context, Success.class);
                        context.startActivity(intent);
                    }

                    if(cpu.getText1() == "Sneha Sadan") {
                        Intent intent=new Intent(context, Success.class);
                        context.startActivity(intent);
                    }

                    if(cpu.getText1() == "Wishing Well") {
                        Intent intent=new Intent(context, Success.class);
                        context.startActivity(intent);
                    }

                    if(cpu.getText1() == "Goonj") {
                        Intent intent=new Intent(context, Success.class);
                        context.startActivity(intent);
                    }

                    if(cpu.getText1() == "Green Yatra") {
                        Intent intent=new Intent(context, Success.class);
                        context.startActivity(intent);
                    }

                }
            });

        }
    }

    public static Integer getData(){
        return count;
    }
}