package com.txt.worldyachts_mobile.ui.order;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.MainActivity;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.Contract;
import com.txt.worldyachts_mobile.api.Orders;
import com.txt.worldyachts_mobile.api.ProductionProcess;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.ui.detail.DetailFragment;
import com.txt.worldyachts_mobile.ui.login.LoginFragment;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    LinearLayout mainLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        mainLayout = root.findViewById(R.id.orderMainLayout);

        if (MainActivity.isGuest)
        {
            Fragment fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
            return root;
        }

        Thread thread = new Thread(() -> {
            String orderJsoin = Sender.Companion.getTable(Tables.orders, null);
            ArrayList<Orders> orderList = new Gson().fromJson(orderJsoin, new TypeToken<ArrayList<Orders>>() {
            }.getType());

            ArrayList<Orders> finalOrders = new ArrayList<Orders>();
            for (Orders order : orderList)
                if (order.getCustomerId() == MainActivity.user.getCustomerId())
                    finalOrders.add(order);

            String contractJson = Sender.Companion.getTable(Tables.contract, null);
            ArrayList<Contract> contractList = new Gson().fromJson(contractJson, new TypeToken<ArrayList<Contract>>() {
            }.getType());


            ArrayList<Contract> finalContracts = new ArrayList<Contract>();
            for (Contract contract : contractList) {
                for (Orders order : finalOrders) {
                    if (contract.getOrderId() == order.getOrderId()) {
                        String boatJson = Sender.Companion.getTable(Tables.boat, order.getBoatId());
                        Boat boat = new Gson().fromJson(boatJson, new TypeToken<Boat>(){}.getType());

                        String processJson = Sender.Companion.getTable(Tables.productionProcess, contract.getProductionProcess());
                        ProductionProcess process = new Gson().fromJson(processJson, new TypeToken<ProductionProcess>(){}.getType());

                        finalContracts.add(contract);
                        loadElems(contract, process, boat);
                    }
                }
            }

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return root;
    }

    private void loadElems(Contract contract, ProductionProcess process, Boat boat) {
        LinearLayout layout = new LinearLayout(getContext());
        TextView name = new TextView(getContext());
        TextView proccess = new TextView(getContext());
        TextView cost = new TextView(getContext());
        ImageView image = new ImageView(getContext());

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, pxToDp(15), 0, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(params);

        params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        name.setLayoutParams(params);
        name.setText(boat.getModel());
        name.setTypeface(null, Typeface.BOLD);

        cost.setText("Цена: " + contract.getContractTotalPrice() + "руб");
        cost.setLayoutParams(params);

        image.setImageResource(R.mipmap.y301_1);
        image.setLayoutParams(params);

        params.setMargins(0, pxToDp(15), 0, pxToDp(10));
        proccess.setText("В процессе: " + process.getProductionProcess1());
        proccess.setLayoutParams(params);

        layout.addView(name);
        layout.addView(image);
        layout.addView(proccess);
        layout.addView(cost);

        mainLayout.addView(layout);
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}