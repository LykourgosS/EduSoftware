package com.unipi.lykourgoss.edusoftware.repositories;

import android.app.Application;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.FirebaseQueryLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 21,June,2019.
 */

public class LessonRepository implements FirebaseRepository<Lesson> {

    private FirebaseQueryLiveData firebaseQueryLiveData;
    private final MediatorLiveData<List<Lesson>> lessonsLiveData;

    private static final DatabaseReference LESSONS_REF =
            FirebaseDatabase.getInstance().getReference("/lessons");

    /*userId used to fetch all the lessons that user's has made (My Lessons case)*/
    public LessonRepository() {
        String userId = null;
        if (userId == null) { // display All Lessons
            firebaseQueryLiveData = new FirebaseQueryLiveData(LESSONS_REF);
        } else { // display only My (user's) Lessons
            Query queryMyLessons = LESSONS_REF.orderByChild(Lesson._AUTHOR_ID).equalTo(userId);
            firebaseQueryLiveData = new FirebaseQueryLiveData(queryMyLessons);
        }
        // lessonsLiveData is used to store either all lessons or my lesson
        lessonsLiveData = new MediatorLiveData<>();
        lessonsLiveData.addSource(firebaseQueryLiveData, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Lesson> lessons = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                lessons.add(snapshot.getValue(Lesson.class));
                            }
                            lessonsLiveData.postValue(lessons);
                        }
                    }).start();
                } else {
                    lessonsLiveData.setValue(null);
                }
            }
        });
    }

    @Override
    public void insert(Lesson lesson) {
        String key = LESSONS_REF.push().getKey();
        lesson.setId(key);
        LESSONS_REF.child(key).setValue(lesson);
    }

    @Override
    public void update(Lesson lesson) {
        String key = lesson.getId();
        LESSONS_REF.child(key).setValue(lesson);
    }

    // a lesson cannot be deleted, unless it has no chapters
    @Override
    public boolean delete(Lesson lesson) {
        if (lesson.getChildCount() == 0) {
            String key = lesson.getId();
            LESSONS_REF.child(key).setValue(null);
            return true;
        } else {
            return false;
        }
    }

    // not needed
    @Override
    public void deleteAll() {
        for (Lesson lesson: getAll().getValue()){
            delete(lesson);
        }

    }

    @Override
    public MutableLiveData<List<Lesson>> getAll() {
        return lessonsLiveData;
    }
}
