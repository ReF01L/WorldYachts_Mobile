package com.txt.worldyachts_mobile.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.MainActivity;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.Customers;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.ui.detail.DetailFragment;
import com.txt.worldyachts_mobile.ui.home.HomeFragment;
import com.txt.worldyachts_mobile.ui.home.HomeViewModel;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_login, container, false);

        Button btn = root.findViewById(R.id.login_sign_in);
        btn.setOnClickListener(v -> {
            final int[] trial = {0};
            Thread thread = new Thread(() -> {
                trial[0] = Sender.Companion.checkAuth(
                        ((EditText) root.findViewById(R.id.login_input_name)).getText().toString(),
                        ((EditText) root.findViewById(R.id.login_input_password)).getText().toString());

                if (trial[0] != -1) {
                    String json = Sender.Companion.getTable(Tables.customers, trial[0]);
                    Customers customers = new Gson().fromJson(json, new TypeToken<Customers>() {
                    }.getType());
                    MainActivity.isGuest = false;
                    MainActivity.user = customers;
                }
            });
            thread.start();
            try {
                thread.join();
                if (trial[0] != -1) {
                    getFragmentManager().popBackStack();
                } else
                    ((TextView) root.findViewById(R.id.error_message)).setText("Неверный логин или пароль");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return root;
    }
}
