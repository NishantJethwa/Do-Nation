package com.donation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChooseMoney extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterMoney adapterMoney;

    List<TextUtils> textUtilsList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choosemoney);

        recyclerView = findViewById(R.id.rc1);
        layoutManager = new LinearLayoutManager(ChooseMoney.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        textUtilsList1 = new ArrayList<>();
        textUtilsList1.add(new TextUtils("Share At Doorstep India", R.drawable.pic1));
        textUtilsList1.add(new TextUtils("Shanti Avedna Cancer Hospice", R.drawable.pic2));
        textUtilsList1.add(new TextUtils("Sneha Sadan", R.drawable.pic3));
        textUtilsList1.add(new TextUtils("Wishing Well", R.drawable.pic4));
        textUtilsList1.add(new TextUtils("Goonj", R.drawable.pic5));
        textUtilsList1.add(new TextUtils("Green Yatra", R.drawable.pic6));

        adapterMoney = new RecyclerAdapterMoney(getApplicationContext(), textUtilsList1);
        recyclerView.setAdapter(adapterMoney);

    }

}
