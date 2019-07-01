package com.unipi.lykourgoss.edusoftware.statistics.ExamStatistic;

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

public class ExamStatisticRepository implements StatisticRepository<ExamStatistic> {

    private ExamStatisticDao statisticDao;

    private LiveData<List<ExamStatistic>> listLiveData;

    public ExamStatisticRepository(Application application) {
        StatisticDatabase database = StatisticDatabase.getInstance(application);
        this.statisticDao = database.ExamStatisticDao();
        this.listLiveData = statisticDao.getAll();
    }

    @Override
    public void insert(ExamStatistic examStatistic) {
        new InsertAsyncTask(statisticDao).execute(examStatistic);
    }

    @Override
    public LiveData<List<ExamStatistic>> getAll() {
        return listLiveData;
    }

    @Override
    public void update(ExamStatistic examStatistic) {
        new UpdateAsyncTask(statisticDao).execute(examStatistic);
    }

    @Override
    public void delete(ExamStatistic examStatistic) {
        new DeleteAsyncTask(statisticDao).execute(examStatistic);
    }

    @Override
    public void deleteAll() {
        new DeleteAllAsyncTask(statisticDao).execute();
    }

    public int getTotalTries(String chapterId){
        return statisticDao.getTotalTries(chapterId);
    }

    public int getTotalFailOrPass(String chapterId, boolean pass){
        return statisticDao.getTotalFailOrPass(chapterId, pass);
    }

    public long getTotalTimeInMin(String chapterId){
        return statisticDao.getTotalTimeInMin(chapterId);
    }

    private static class InsertAsyncTask extends AsyncTask<ExamStatistic, Void, Void> {
        private ExamStatisticDao statisticDao;

        public InsertAsyncTask(ExamStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(ExamStatistic... examStatistics) {
            statisticDao.insert(examStatistics[0]);
            return null;
        }
    }



    private static class UpdateAsyncTask extends AsyncTask<ExamStatistic, Void, Void> {
        private ExamStatisticDao statisticDao;

        public UpdateAsyncTask(ExamStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(ExamStatistic... examStatistics) {
            statisticDao.update(examStatistics[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ExamStatistic, Void, Void> {
        private ExamStatisticDao statisticDao;

        public DeleteAsyncTask(ExamStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(ExamStatistic... examStatistics) {
            statisticDao.delete(examStatistics[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<ExamStatistic, Void, Void> {
        private ExamStatisticDao statisticDao;

        public DeleteAllAsyncTask(ExamStatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(ExamStatistic... examStatistics) {
            statisticDao.deleteAll();
            return null;
        }
    }
}
