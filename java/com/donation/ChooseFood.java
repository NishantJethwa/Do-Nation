package com.donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChooseFood extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterFood adapterFood;

    List<TextUtils> textUtilsList, textUtilsList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choosefood);

        recyclerView = findViewById(R.id.rc1);
        layoutManager = new LinearLayoutManager(ChooseFood.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        textUtilsList1 = new ArrayList<>();
        textUtilsList1.add(new TextUtils("Share At Doorstep India", R.drawable.pic1));
        textUtilsList1.add(new TextUtils("Shanti Avedna Cancer Hospice", R.drawable.pic2));
        textUtilsList1.add(new TextUtils("Sneha Sadan", R.drawable.pic3));
        textUtilsList1.add(new TextUtils("Wishing Well", R.drawable.pic4));
        textUtilsList1.add(new TextUtils("Goonj", R.drawable.pic5));
        textUtilsList1.add(new TextUtils("Green Yatra", R.drawable.pic6));

        adapterFood = new RecyclerAdapterFood(getApplicationContext(), textUtilsList1);
        recyclerView.setAdapter(adapterFood);

    }

}
