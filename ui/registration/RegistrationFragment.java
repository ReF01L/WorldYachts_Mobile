package com.txt.worldyachts_mobile.ui.registration;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txt.worldyachts_mobile.R;
import com.txt.worldyachts_mobile.api.Auth;
import com.txt.worldyachts_mobile.api.Boat;
import com.txt.worldyachts_mobile.api.Customers;
import com.txt.worldyachts_mobile.api.DocumentName;
import com.txt.worldyachts_mobile.api.Sender;
import com.txt.worldyachts_mobile.api.Tables;
import com.txt.worldyachts_mobile.ui.additional.AdditionalFragment;
import com.txt.worldyachts_mobile.ui.home.HomeFragment;

import java.util.ArrayList;

public class RegistrationFragment extends Fragment {

    private String[] passports = {
            "Паспорт РФ",
            "Загранпаспорт"
    };
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_registration, container, false);
        Spinner spinner = root.findViewById(R.id.rg_passport);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, passports);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button btn = root.findViewById(R.id.rg_sign_up);
        btn.setOnClickListener(this::tryRegister);

        return root;
    }

    public void tryRegister(View view) {

        Auth auth = new Auth();
        auth.setUsername            (((TextView)root.findViewById(R.id.rg_login)).getText().toString());
        auth.setPassword            (((TextView)root.findViewById(R.id.rg_password)).getText().toString());

        Customers customers = new Customers();
        customers.setAddress        (((TextView)root.findViewById(R.id.rg_address)).getText().toString());
        customers.setCity           (((TextView)root.findViewById(R.id.rg_city)).getText().toString());
        customers.setDateOfBirth    (((TextView)root.findViewById(R.id.rg_date)).getText().toString());
        customers.setEmail          (((TextView)root.findViewById(R.id.rg_mail)).getText().toString());
        customers.setFirstName      (((TextView)root.findViewById(R.id.rg_name)).getText().toString());
        customers.setPhoneNumber    (((TextView)root.findViewById(R.id.rg_phone)).getText().toString());
        customers.setSecondName     (((TextView)root.findViewById(R.id.rg_family_name)).getText().toString());
        customers.setIdDocumentName (((Spinner)root.findViewById(R.id.rg_passport)).getSelectedItemPosition());

        Thread thread = new Thread(() -> Sender.Companion.registration(auth, customers));
        thread.start();
        try {
            thread.join();
            getFragmentManager().popBackStack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
