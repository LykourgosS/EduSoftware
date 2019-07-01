package com.unipi.lykourgoss.edusoftware.statistics.ExamStatistic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.unipi.lykourgoss.edusoftware.statistics.StatisticViewModel;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

public class ExamStatisticViewModel extends StatisticViewModel<ExamStatistic> {

    private ExamStatisticRepository repository;

    public ExamStatisticViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamStatisticRepository(application);
        listLiveData = repository.getAll();
    }

    @Override
    public void insert(ExamStatistic examStatistic) {
        repository.insert(examStatistic);
    }

    @Override
    public LiveData<List<ExamStatistic>> getAll() {
        return listLiveData;
    }

    @Override
    public void update(ExamStatistic examStatistic) {
        repository.update(examStatistic);
    }

    @Override
    public void delete(ExamStatistic examStatistic) {
        repository.delete(examStatistic);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    public int getTotalTries(String chapterId){
        return repository.getTotalTries(chapterId);
    }

    public int getTotalFailOrPass(String chapterId, boolean pass){
        return repository.getTotalFailOrPass(chapterId, pass);
    }

    public long getTotalTimeInMin(String chapterId){
        return repository.getTotalTimeInMin(chapterId);
    }
}
