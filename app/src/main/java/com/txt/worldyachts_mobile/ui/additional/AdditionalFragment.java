package com.txt.worldyachts_mobile.ui.additional;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.AccessoryId;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.Fit;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.ui.home.HomeFragment;

import java.util.ArrayList;

public class AdditionalFragment extends Fragment {

    private AdditionalViewModel additionalViewModel;
    private TextView totalCost;
    private Boat boat;
    private long cost;
    private ArrayList<LinearLayout> layouts = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        additionalViewModel =
                ViewModelProviders.of(this).get(AdditionalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_additional, container, false);

        LinearLayout mainLayout = root.findViewById(R.id.accessoryMainLayout);

        boat = HomeFragment.chosenYacht;
        cost = boat.getBasePrice();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Thread thread = new Thread(() -> {
                ArrayList<Fit> fits = new ArrayList<>();
                ArrayList<AccessoryId> accessories = new ArrayList<>();

                Sender.Companion.getTable(Tables.fit, null);
                String json = Sender.Companion.getTable(Tables.fit, null);
                ArrayList<Fit> fullFit = new Gson().fromJson(json, new TypeToken<ArrayList<Fit>>() {
                }.getType());
                for (Fit fit : fullFit)
                    if (fit.getBoatId() == boat.getBoatId())
                        fits.add(fit);

                Sender.Companion.getTable(Tables.accessoryId, null);
                json = Sender.Companion.getTable(Tables.accessoryId, null);
                ArrayList<AccessoryId> accessoryList = new Gson().fromJson(json, new TypeToken<ArrayList<AccessoryId>>() {
                }.getType());

                for (AccessoryId accessory : accessoryList)
                    for (Fit fit : fits)
                        if (!accessories.contains(accessory) && accessory.getAccessoryId1() == fit.getAccessoryId()) {
                            loadAccessory(accessory);
                            accessories.add(accessory);
                        }

            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (LinearLayout layout : layouts) {
            if (layout.getParent() != null)
                ((ViewGroup) layout.getParent()).removeView(layout);
            mainLayout.addView(layout);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);

        totalCost = new TextView(getContext());
        totalCost.setText(String.valueOf(cost));
        totalCost.setLayoutParams(params);
        totalCost.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalCost.setGravity(Gravity.BOTTOM);

        Button buyBtn = new Button(getContext());
        buyBtn.setLayoutParams(params);
        buyBtn.setText(R.string.yacht_to_bin);
        buyBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        buyBtn.setGravity(Gravity.BOTTOM);

        mainLayout.addView(totalCost);
        mainLayout.addView(buyBtn);

        return root;
    }

    private void loadAccessory(AccessoryId accessory) {
        LinearLayout layout = new LinearLayout(getContext());
        CheckBox checkBox = new CheckBox(getContext());
        TextView textViewCost = new TextView(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        checkBox.setText(accessory.getAccName());
        checkBox.setOnClickListener(v -> {
            cost += ((CheckBox) v).isChecked() ? Integer.parseInt((String) textViewCost.getText()) : -Integer.parseInt((String) textViewCost.getText());
            totalCost.setText(String.valueOf(cost));
        });

        textViewCost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        textViewCost.setText(String.valueOf(accessory.getPrice()));
        textViewCost.setGravity(Gravity.END);

        params.setMarginEnd(24);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(params);
        layout.addView(checkBox);
        layout.addView(textViewCost);

        layouts.add(layout);
    }
}