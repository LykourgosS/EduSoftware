package com.unipi.lykourgoss.edusoftware.filestoremove.codingflowexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

public class LessonRepository {

    private LessonDao lessonDao;
    private LiveData<List<Lesson>> allLessons;

    public LessonRepository(Application application){
        LessonDatabase database = LessonDatabase.getInstance(application);
        lessonDao = database.lessonDao();
        allLessons = lessonDao.getAllLessons();
    }

    // create
    private static class InsertLessonAsyncTask extends AsyncTask<Lesson, Void, Void>{
        private LessonDao lessonDao;

        public InsertLessonAsyncTask(LessonDao lessonDao){
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.insert(lessons[0]);
            return null;
        }
    }

    public void insert(Lesson lesson){
        new InsertLessonAsyncTask(lessonDao).execute(lesson);
    }

    // update
    private static class UpdateLessonAsyncTask extends AsyncTask<Lesson, Void, Void>{
        private LessonDao lessonDao;

        public UpdateLessonAsyncTask(LessonDao lessonDao){
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.update(lessons[0]);
            return null;
        }
    }

    public void update(Lesson lesson){
        new UpdateLessonAsyncTask(lessonDao).execute(lesson);
    }

    // delete a lesson
    private static class DeleteLessonAsyncTask extends AsyncTask<Lesson, Void, Void>{
        private LessonDao lessonDao;

        public DeleteLessonAsyncTask(LessonDao lessonDao){
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(Lesson... lessons) {
            lessonDao.delete(lessons[0]);
            return null;
        }
    }

    public void delete(Lesson lesson){
        new DeleteLessonAsyncTask(lessonDao).execute(lesson);
    }

    // delete all lessons
    private static class DeleteAllLessonsLessonAsyncTask extends AsyncTask<Void, Void, Void>{
        private LessonDao lessonDao;

        public DeleteAllLessonsLessonAsyncTask(LessonDao lessonDao){
            this.lessonDao = lessonDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            lessonDao.deleteAllLessons();
            return null;
        }
    }

    public void deleteAllLessons(){
        new DeleteAllLessonsLessonAsyncTask(lessonDao).execute();
    }

    public LiveData<List<Lesson>> getAllLessons() {
        return allLessons;
    }
}
