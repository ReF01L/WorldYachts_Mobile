package com.txt.worldyachts_mobile.ui.home;

import com.google.gson.Gson;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.MainActivity;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.ui.additional.AdditionalFragment;
import com.txt.worldyachts_mobile.ui.additional.AdditionalViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static Boat chosenYacht;
    private ArrayList<LinearLayout> cards = new ArrayList<LinearLayout>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout layout = root.findViewById(R.id.homeMainLayout);

        Sender.Companion.getTable(Tables.boat, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Thread thread = new Thread(() -> {
                String json = Sender.Companion.getTable(Tables.boat, null);
                ArrayList<Boat> List = new Gson().fromJson(json, new TypeToken<ArrayList<Boat>>() {
                }.getType());

                if (cards.isEmpty()) {
                    for (Boat boat : List) {
                        loadElementsToForm(getContext(), boat);
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (LinearLayout card : cards) {
            if (card.getParent() != null)
                ((ViewGroup) card.getParent()).removeView(card);
            layout.addView(card);
        }


        return root;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadElementsToForm(Context context, Boat boat) {
        LinearLayout mainLayout = new LinearLayout(context);
        LinearLayout topLevel = new LinearLayout(context);
        LinearLayout midLevel = new LinearLayout(context);
        LinearLayout botLevel = new LinearLayout(context);
        TextView yachtName = new TextView(context);
        ImageView image = new ImageView(context);
        final Button binBtn = new Button(context);
        Button detailBtn = new Button(context);
        TextView yacthCost = new TextView(context);
        TextView yachtCount = new TextView(context);


        // Default border with margin = 8dp
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.setMarginStart(8);

        // Count of the yacht settings
        yachtCount.setGravity(Gravity.END);
        yachtCount.setText("Кол-во гребцов: " + boat.getNumberOfRowers());
        yachtCount.setLayoutParams(params);

        // Cost of the yacht settings
        yacthCost.setGravity(Gravity.START);
        yacthCost.setText(boat.getBasePrice() + "р");
        yacthCost.setLayoutParams(params);

        // Bottom LinearLayout settings
        LinearLayout.LayoutParams bottomLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        bottomLayoutParams.setMargins(16, 0, 16, 0);

        botLevel.setLayoutParams(bottomLayoutParams);
        botLevel.setOrientation(LinearLayout.HORIZONTAL);
        botLevel.addView(yacthCost);
        botLevel.addView(yachtCount);

        // Image settings
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        image.setLayoutParams(imageParams);
        image.setContentDescription("Image");
        image.setForegroundGravity(Gravity.TOP);
        image.setImageResource(R.mipmap.y301_1);

        // Button border with margin = 10dp
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        btnParams.setMargins(50, 10, 20, 20);

        // Bin Button settings
        binBtn.setText(R.string.yacht_to_bin);
        binBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        binBtn.setLayoutParams(btnParams);
        binBtn.setGravity(Gravity.BOTTOM);
        binBtn.setOnClickListener(view -> {
            chosenYacht = boat;

            Fragment fragment = new AdditionalFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();

        });

        // Detail Button settings
        detailBtn.setText(R.string.yacht_detail);
        detailBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        detailBtn.setLayoutParams(btnParams);
        detailBtn.setGravity(Gravity.BOTTOM);

        // Middle LinearLayout settings
        midLevel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1));
        midLevel.setOrientation(LinearLayout.HORIZONTAL);
        midLevel.setVerticalGravity(Gravity.BOTTOM);

        midLevel.addView(image);
        midLevel.addView(binBtn);
        midLevel.addView(detailBtn);

        // Name of the yacht settings
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        yachtName.setGravity(Gravity.CENTER);
        yachtName.setLayoutParams(nameParams);
        yachtName.setText(boat.getModel());
        yachtName.setTypeface(null, Typeface.BOLD);

        // Top LinearLayout settings
        LinearLayout.LayoutParams topLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        topLayoutParams.setMargins(0, 64, 0, 0);
        topLevel.setOrientation(LinearLayout.HORIZONTAL);
        topLevel.setLayoutParams(topLayoutParams);

        topLevel.addView(yachtName);

        // Main LinearLayout settings
        LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        mainLayoutParams.setMargins(8, 0, 8, 0);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        mainLayout.addView(topLevel);
        mainLayout.addView(midLevel);
        mainLayout.addView(botLevel);

        cards.add(mainLayout);
    }
}