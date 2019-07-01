package com.unipi.lykourgoss.edusoftware.statistics;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

public abstract class StatisticViewModel<Statistic> extends AndroidViewModel {

    protected LiveData<List<Statistic>> listLiveData;

    public StatisticViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract void insert(Statistic statistic);

    public abstract LiveData<List<Statistic>> getAll();

    public abstract void update(Statistic statistic);

    public abstract void delete(Statistic statistic);

    public abstract void deleteAll();
}
