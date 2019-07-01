package com.unipi.lykourgoss.edusoftware.statistics.SubsectionStatistic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.unipi.lykourgoss.edusoftware.statistics.StatisticDatabase;
import com.unipi.lykourgoss.edusoftware.statistics.StatisticRepository;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 01,July,2019.
 */

public class SubsectionStatisticRepository implements StatisticRepository<SubsectionStatistic> {

    private SubsectionStatisticDao statisticDao;

    private LiveData<List<SubsectionStatistic>> listLiveData;

    public SubsectionStatisticRepository(Application application) {
        StatisticDatabase database = StatisticDatabase.getInstance(application);
        this.statisticDao = database.SubsectionStatisticDao();
        this.listLiveData = statisticDao.getAll();
    }

    @Override
    public void insert(SubsectionStatistic subsectionStatistic) {
        new InsertAsyncTask(statisticDao).execute(subsectionStatistic);
    }

    @Override
    public LiveData<List<SubsectionStatistic>> getAll() {
        return listLiveData;
    }

    @Override
    public void update(SubsectionStatistic subsectionStatistic) {
        new UpdateAsyncTask(statisticDao).execute(subsectionStatistic);
    }

    @Override
    public void delete(SubsectionStatistic subsectionStatistic) {
        new DeleteAsyncTask(statisticDao).execute(subsectionStatistic);
    }

    public int getTotalTimesOpen(String pdfFilename){
        return statisticDao.getTotalTimesOpen(pdfFilename);
    }

    public long getTotalTimeInMin(String pdfFilename){
        return statisticDao.getTotalTimeInMin(pdfFilename);
    }

    @Override
    public void deleteAll() {
        new DeleteAllAsyncTask(statisticDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<SubsectionStatistic, Void, Void> {
        private SubsectionStatisticDao statisticDao;

        public InsertAsyncTask(SubsectionStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(SubsectionStatistic... subsectionStatistics) {
            statisticDao.insert(subsectionStatistics[0]);
            return null;
        }
    }



    private static class UpdateAsyncTask extends AsyncTask<SubsectionStatistic, Void, Void> {
        private SubsectionStatisticDao statisticDao;

        public UpdateAsyncTask(SubsectionStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(SubsectionStatistic... subsectionStatistics) {
            statisticDao.update(subsectionStatistics[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SubsectionStatistic, Void, Void> {
        private SubsectionStatisticDao statisticDao;

        public DeleteAsyncTask(SubsectionStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(SubsectionStatistic... subsectionStatistics) {
            statisticDao.delete(subsectionStatistics[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<SubsectionStatistic, Void, Void> {
        private SubsectionStatisticDao statisticDao;

        public DeleteAllAsyncTask(SubsectionStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(SubsectionStatistic... subsectionStatistics) {
            statisticDao.deleteAll();
            return null;
        }
    }
}
