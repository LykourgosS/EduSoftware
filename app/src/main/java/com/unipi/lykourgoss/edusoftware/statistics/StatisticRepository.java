package com.unipi.lykourgoss.edusoftware.statistics;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

public interface StatisticRepository<Statistic> {

    void insert(Statistic statistic);

    LiveData<List<Statistic>> getAll();

    void update(Statistic statistic);

    void delete(Statistic statistic);

    void deleteAll();
}
