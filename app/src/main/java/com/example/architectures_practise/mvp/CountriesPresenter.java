package com.example.architectures_practise.mvp;

import com.example.architectures_practise.model.CountriesService;
import com.example.architectures_practise.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenter {
    private View view;
    private CountriesService service;

    public CountriesPresenter(View view) {
        this.view = view;
        service = new CountriesService();
        fetchCountries();
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
                        view.setListValues(countriesNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });
    }

    /**
     * もう一度データを読み込み
     */
    public void doRefresh() {
        fetchCountries();
    }

    public interface View {
        void setListValues(List<String> countries);
        void onError();
    }
}
