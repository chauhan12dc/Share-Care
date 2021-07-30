package com.example.portfolio.ui.get;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.portfolio.ConfirmationActivity;
import com.example.portfolio.DatabaseHelper;
import com.example.portfolio.R;

import java.util.ArrayList;

public class GetFragment extends Fragment {

    private GetViewModel galleryViewModel;

    ArrayList<String> listitem;
    ArrayAdapter adapter;
    DatabaseHelper db;
    ListView listView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_get_ride, container, false);

        db = new DatabaseHelper(root.getContext());
        listitem = new ArrayList<>();
        listView = root.findViewById(R.id.listView);

        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(root.getContext(), ConfirmationActivity.class);
                startActivity(intent);
            }
        });

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void viewData() {
        ArrayList<String> sAdd =new ArrayList<String>();
        ArrayList<String> dAdd=new ArrayList<String>();
        ArrayList<String> date=new ArrayList<String>();
        ArrayList<String> amount=new ArrayList<String>();
        ArrayList<String> type=new ArrayList<String>();
        ArrayList<String> seat=new ArrayList<String>();
        ArrayList<String> car_no=new ArrayList<String>();

        Cursor cursor = db.ReadShare();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this.getContext(), "No records found", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                listitem.add(cursor.getString(1));
                sAdd.add(cursor.getString(1));
                dAdd.add(cursor.getString(2));
                date.add(cursor.getString(3));
                amount.add(cursor.getString(4));
                type.add(cursor.getString(5));
                seat.add(cursor.getString(6));
                car_no.add(cursor.getString(7));


            }
            MyAdapter adapter = new MyAdapter(this.getContext(),sAdd,dAdd,date,amount,type,seat,car_no);
            listView.setAdapter(adapter);


        }
    }
    class MyAdapter extends ArrayAdapter<String>{

        Context c;
        //String sAdd[],dAdd[],date[],amount[],type[],seat[],car_no[];
ArrayList<String> sAdd,dAdd,date,amount,type,seat,car_no;
        MyAdapter(Context context,ArrayList<String> sAdd,ArrayList<String> dAdd,ArrayList<String> date,ArrayList<String> amount, ArrayList<String> type,ArrayList<String> seat,ArrayList<String> car_no)
        {
            super(context,R.layout.custom_list_view,R.id.stAdd,sAdd);
            this.c = context;
            this.sAdd = sAdd;
            this.dAdd = dAdd;
            this.date = date;
            this.amount = amount;
            this.type = type;
            this.seat = seat;
            this.car_no = car_no;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View custom = layoutInflater.inflate(R.layout.custom_list_view,parent,false);


            ((TextView) custom.findViewById(R.id.stAdd)).append(sAdd.get(position));
            ((TextView) custom.findViewById(R.id.dtAdd)).append(dAdd.get(position));
            ((TextView)  custom.findViewById(R.id.dtTime)).append(date.get(position));
            ((TextView)  custom.findViewById(R.id.amountShare)).append(amount.get(position));
            ((TextView)  custom.findViewById(R.id.carNo)).append(car_no.get(position));
            ((TextView) custom.findViewById(R.id.typeCar)).append(type.get(position));
            ((TextView)  custom.findViewById(R.id.seats)).append(seat.get(position));

            return custom;
        }
    }
}