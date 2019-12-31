package com.example.architectures_practise.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.architectures_practise.model.CountriesService;
import com.example.architectures_practise.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends ViewModel {

    private MutableLiveData<List<String>> countries = new MutableLiveData<>();
    private MutableLiveData<Boolean> countryError = new MutableLiveData<>();

    private CountriesService service;

    public CountriesViewModel() {
        service = new CountriesService();
        fetchCountries();
    }

    public LiveData<List<String>> getCountries() {
        return countries;
    }

    public LiveData<Boolean> getCountryError() {
        return countryError;
    }

    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> value) {
                        List<String> countriesNames = new ArrayList<>();
                        for (Country country: value) {
                            countriesNames.add(country.countryName);
                        }
                        countries.setValue(countriesNames);
                        countryError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryError.setValue(true);
                    }
                });
    }

    /**
     * もう一度データを読み込み
     */
    public void doRefresh() {
        fetchCountries();
    }
}