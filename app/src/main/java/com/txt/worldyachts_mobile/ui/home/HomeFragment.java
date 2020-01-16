package com.txt.worldyachts_mobile.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.txt.worldyachts_mobile.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout layout;

    private ArrayList<LinearLayout> carts = new ArrayList<LinearLayout>();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        loadElementsToForm(activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        layout = root.findViewById(R.id.mainLayout);

        for (LinearLayout cart : carts) {
            layout.addView(cart);
        }

        return root;
    }


    private void loadElementsToForm(Context context) {
        for (int i = 0; i < 3; i++) {
            LinearLayout mainLayout = new LinearLayout(context);
            LinearLayout topLevel = new LinearLayout(context);
            LinearLayout midLevel = new LinearLayout(context);
            LinearLayout botLevel = new LinearLayout(context);
            TextView yachtName = new TextView(context);
            ImageView image = new ImageView(context);
            Button binBtn = new Button(context);
            Button detailBtn = new Button(context);
            TextView yacthCost = new TextView(context);
            TextView yachtCount = new TextView(context);


            // Default border with margin = 8dp
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            params.setMarginStart(8);

            // Count of the yacht settings
            yachtCount.setGravity(Gravity.END);
            yachtCount.setText("Кол-во: 4");
            yachtCount.setLayoutParams(params);

            // Cost of the yacht settings
            yacthCost.setGravity(Gravity.START);
            yacthCost.setText("25 000 000р");
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
            image.setLayoutParams(params);
            image.setContentDescription("Image");
            image.setImageResource(R.mipmap.ic_launcher);

            // Button border with margin = 10dp
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            btnParams.setMargins(10, 10, 10, 10);

            // Bin Button settings
            binBtn.setText(R.string.yacht_to_bin);
            binBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            binBtn.setLayoutParams(btnParams);
            binBtn.setGravity(Gravity.START);

            // Detail Button settings
            detailBtn.setText(R.string.yacht_detail);
            detailBtn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            detailBtn.setLayoutParams(btnParams);
            detailBtn.setGravity(Gravity.END);

            // Middle LinearLayout settings
            midLevel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1));
            midLevel.setOrientation(LinearLayout.HORIZONTAL);

            midLevel.addView(image);
            midLevel.addView(binBtn);
            midLevel.addView(detailBtn);

            // Name of the yacht settings
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            yachtName.setGravity(Gravity.CENTER);
            yachtName.setLayoutParams(nameParams);
            yachtName.setText(R.string.yacht_name);

            // Top LinearLayout settings
            LinearLayout.LayoutParams topLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            topLayoutParams.setMargins(0, 16, 0, 0);
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

            carts.add(mainLayout);
        }
    }
}