package com.example.tomandjerry.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tomandjerry.R;
import com.example.tomandjerry.Utilities.MyScore;
import com.example.tomandjerry.Utilities.MySharedPreferences;

import java.util.ArrayList;

public class ScoreFragment extends Fragment {

    private OnScoreSelectedListener callback;

    public interface OnScoreSelectedListener {
        void onScoreSelected(double lat, double lng);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (OnScoreSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnScoreSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.score_list);
        MySharedPreferences mySharedPreferences=MySharedPreferences.getInstance();;
        MyScore[] bundle=new MyScore[10];
        for(int x=0;x<10;x++) {
            bundle[x] = new MyScore(mySharedPreferences.readString("SCORE" + x, "-1/0/0"));
        }
        ArrayList<String> scores= new ArrayList<>();
        for(int x=0;x< bundle.length;x++)
        {
            if(bundle[x].getScore()!=-1)
            {
                scores.add("Score "+(x+1)+" :"+bundle[x].getScore());
            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double lat = bundle[position].getLat();
                double lng = bundle[position].getLng();
                callback.onScoreSelected(lat, lng);
            }
        });

        return view;
    }
}