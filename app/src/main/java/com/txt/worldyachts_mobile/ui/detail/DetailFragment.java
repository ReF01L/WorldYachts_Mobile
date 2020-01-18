package com.txt.worldyachts_mobile.ui.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.MainActivity;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.BoatType;
import com.txt.worldyachts_mobile.api.Colours;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.api.Wood;
import com.txt.worldyachts_mobile.ui.additional.AdditionalFragment;
import com.txt.worldyachts_mobile.ui.home.HomeFragment;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    View root;
    Boat boat;
    LinearLayout mainLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_detail, container, false);
        boat = HomeFragment.chosenYacht;

        mainLayout = root.findViewById(R.id.detailMainLayout);

        Thread thread = new Thread(() -> {
            String json = Sender.Companion.getTable(Tables.colours, boat.getColour());
            Colours color = new Gson().fromJson(json, new TypeToken<Colours>() {}.getType());

            json = Sender.Companion.getTable(Tables.wood, boat.getWood());
            Wood wood = new Gson().fromJson(json, new TypeToken<Wood>() {}.getType());

            json = Sender.Companion.getTable(Tables.boatType, boat.getBoatType());
            BoatType type = new Gson().fromJson(json, new TypeToken<BoatType>() {}.getType());

            loadForm(color, wood, type);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void loadForm(Colours colour, Wood wood, BoatType bType) {
        ImageView image1 = new ImageView(getContext());
        ImageView image2 = new ImageView(getContext());
        ImageView image3 = new ImageView(getContext());
        TextView name = new TextView(getContext());
        TextView type = new TextView(getContext());
        TextView material = new TextView(getContext());
        TextView color = new TextView(getContext());
        TextView places = new TextView(getContext());
        Button btn = new Button(getContext());

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 296);
        params.setMarginStart(16);

        image1.setImageResource(MainActivity.getImageById(boat.getBoatId(), 1));
        image2.setImageResource(MainActivity.getImageById(boat.getBoatId(), 2));
        image3.setImageResource(MainActivity.getImageById(boat.getBoatId(), 3));
        image1.setLayoutParams(params);
        image2.setLayoutParams(params);
        image3.setLayoutParams(params);

        params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        name.setText(boat.getModel());
        name.setLayoutParams(params);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        params.setMargins(16, 35, 0, 0);

        type.setText("Тип: " + bType.getBoatType1());
        type.setLayoutParams(params);
        type.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        params.setMargins(16, 15, 0, 0);
        material.setText("Материал: " + wood.getWood1());
        material.setLayoutParams(params);
        material.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        color.setText("Цвет: " + colour.getColour());
        color.setLayoutParams(params);
        color.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        places.setText("Кол-во мест для гребли: " + boat.getNumberOfRowers());
        places.setLayoutParams(params);
        places.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        btn.setText("Оформить заказ");
        params.setMargins(16, 32, 16, 0);
        btn.setLayoutParams(params);
        btn.setOnClickListener(v -> {
            Fragment fragment = new AdditionalFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });
        btn.setLayoutParams(params);

        LinearLayout imageLayout = new LinearLayout(getContext());
        imageLayout.setOrientation(LinearLayout.HORIZONTAL);

        imageLayout.addView(image1);
        imageLayout.addView(image2);
        imageLayout.addView(image3);

        mainLayout.addView(imageLayout);
        mainLayout.addView(name);
        mainLayout.addView(type);
        mainLayout.addView(material);
        mainLayout.addView(color);
        mainLayout.addView(places);
        mainLayout.addView(btn);
    }
}
