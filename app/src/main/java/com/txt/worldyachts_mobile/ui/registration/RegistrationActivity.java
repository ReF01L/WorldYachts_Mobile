package com.txt.worldyachts_mobile.ui.registration;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.txt.worldyachts_mobile.R;

public class RegistrationActivity extends Activity {
    private String[] passports = {
            "Паспорт РФ",
            "Паспорт УК"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Spinner spinner = findViewById(R.id.rg_passport);

        // Создание адаптера ArrayAdapter с помощью строк и стандратной разметки Spinner'a
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, passports);

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
    }
}
