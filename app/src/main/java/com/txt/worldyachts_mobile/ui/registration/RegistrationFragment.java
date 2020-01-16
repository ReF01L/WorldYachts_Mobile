package com.txt.worldyachts_mobile.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.txt.worldyachts_mobile.R;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private String[] passports = {
            "Паспорт РФ",
            "Паспорт УК"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        Spinner spinner = root.findViewById(R.id.rg_passport);

        // Создание адаптера ArrayAdapter с помощью строк и стандратной разметки Spinner'a
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, passports);

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

//        OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
//            // adapterView -> Объект, в котором произошло событие
//            // view -> Объект внутри, который представляет выбранный элемент
//            // i -> индекс выбранного элемента
//            // l -> идендификатор выбранной строки
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                // Получаем выбранный объект
//                String item = (String) adapterView.getItemAtPosition(i);
//                selection.setText(item);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        };
//        spinner.setOnItemSelectedListener(itemSelectedListener);


        return root;
    }
}
