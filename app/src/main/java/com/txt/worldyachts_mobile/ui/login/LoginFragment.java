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
import androidx.lifecycle.ViewModelProviders;

import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.ui.home.HomeViewModel;

public class LoginFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button btn = root.findViewById(R.id.login_sign_in);
        btn.setOnClickListener(v -> {
            Sender.Companion.checkAuth(
                    ((EditText) root.findViewById(R.id.login_input_name)).getText().toString(),
                    ((EditText) root.findViewById(R.id.login_input_password)).getText().toString());
        });


        return root;
    }
}
