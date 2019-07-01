package com.unipi.lykourgoss.edusoftware.statistics.SubsectionStatistic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.unipi.lykourgoss.edusoftware.statistics.StatisticViewModel;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

public class SubsectionStatisticViewModel extends StatisticViewModel<SubsectionStatistic> {

    private SubsectionStatisticRepository repository;

    public SubsectionStatisticViewModel(@NonNull Application application) {
        super(application);
        repository = new SubsectionStatisticRepository(application);
        listLiveData = repository.getAll();
    }

    @Override
    public void insert(SubsectionStatistic subsectionStatistic) {
        repository.insert(subsectionStatistic);
    }

    @Override
    public LiveData<List<SubsectionStatistic>> getAll() {
        return listLiveData;
    }

    @Override
    public void update(SubsectionStatistic subsectionStatistic) {
        repository.update(subsectionStatistic);
    }

    @Override
    public void delete(SubsectionStatistic subsectionStatistic) {
        repository.delete(subsectionStatistic);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    public int getTotalTimesOpen(String pdfFilename){
        return repository.getTotalTimesOpen(pdfFilename);
    }

    public long getTotalTimeInMin(String pdfFilename){
        return repository.getTotalTimeInMin(pdfFilename);
    }
}
