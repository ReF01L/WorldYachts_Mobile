package com.txt.worldyachts_mobile.ui.additional;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdditionalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdditionalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}