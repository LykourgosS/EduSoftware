package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.FirebaseQueryLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 26,June,2019.
 */

public class LessonRepository extends FirebaseRepository<Lesson> {

    public LessonRepository(String modelRef, String parentId) {
        super();

        MODEL_REF = FirebaseDatabase.getInstance().getReference().child(modelRef);

        Query query = MODEL_REF.orderByChild(Lesson._TITLE); // all lessons sorted by Title

        if (parentId != null) { // my lessons sorted by default firebase order (sorted by id)
            query = MODEL_REF.orderByChild(Lesson._PARENT_ID).equalTo(parentId);
        }
        // listMediatorLiveData is used to store either all or my
        listMediatorLiveData = new MediatorLiveData<>();
        listMediatorLiveData.addSource(new FirebaseQueryLiveData(query), new Observer<DataSnapshot>() {
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
                            listMediatorLiveData.postValue(sortByTitle(lessons));
                        }
                    }).start();
                } else {
                    listMediatorLiveData.setValue(null);
                }
            }
        });
    }

    @Override
    public void create(Lesson lesson, int parentChildCount) {
        String key = MODEL_REF.push().getKey();
        lesson.setId(key);
        MODEL_REF.child(key).setValue(lesson);
    }

    @Override
    public boolean delete(Lesson lesson, int parentChildCount) {
        if (lesson.getChildCount() == 0) {
            String key = lesson.getId();
            MODEL_REF.child(key).setValue(null);
            return true;
        } else {
            return false;
        }
    }
}
