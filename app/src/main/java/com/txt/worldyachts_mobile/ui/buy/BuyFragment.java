package com.txt.worldyachts_mobile.ui.buy;

import android.graphics.Typeface;
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
import androidx.fragment.app.Fragment;

import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.AccessoryId;
import com.txt.worldyachts_mobile.ui.additional.AdditionalFragment;

import java.util.ArrayList;

public class BuyFragment extends Fragment {
    private ArrayList<AccessoryId> list = new ArrayList<AccessoryId>();
    private LinearLayout mainLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_final, container, false);
        mainLayout = root.findViewById(R.id.finalMainLayout);
        list = AdditionalFragment.accessoryIdArrayList;

        loadElems();

        return root;
    }

    private void loadElems() {
        ImageView image = new ImageView(getContext());
        image.setImageResource(R.mipmap.y101_1);
        image.setLayoutParams(new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT, 407));

        mainLayout.addView(image);

        for (AccessoryId id : list) {
            TextView textView = new TextView(getContext());
            textView.setText(id.getAccName());
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mainLayout.addView(textView);
        }

        TextView textView = new TextView(getContext());
        textView.setText(String.valueOf(AdditionalFragment.cost));
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(null, Typeface.BOLD);

        Button btn = new Button(getContext());
        btn.setText(R.string.yacht_to_bin);
        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        mainLayout.addView(textView);
        mainLayout.addView(btn);
    }
}
