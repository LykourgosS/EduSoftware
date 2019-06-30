package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.viewmodels.FirebaseQueryLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class QuestionRepository implements MyRepository<Question> {

    protected MediatorLiveData<List<Question>> listMediatorLiveData;

    protected static DatabaseReference MODEL_REF;

    protected String parentId;

    protected String parentIdName;

    public QuestionRepository(String modelRef, String parentIdName, String parentId) {
        this.parentId = parentId;
        this.parentIdName = parentIdName;

        MODEL_REF = FirebaseDatabase.getInstance().getReference().child(modelRef);
        Query query = MODEL_REF.orderByChild(Question._PARENT_ID).equalTo(parentId);

        // listMediatorLiveData is used to store either all or my
        listMediatorLiveData = new MediatorLiveData<>();
        listMediatorLiveData.addSource(new FirebaseQueryLiveData(query), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Question> questions = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                questions.add(snapshot.getValue(Question.class));
                            }
                            listMediatorLiveData.postValue(questions);
                        }
                    }).start();
                } else {
                    listMediatorLiveData.setValue(null);
                }
            }
        });
    }

    @Override
    public String getNewId() {
        return MODEL_REF.push().getKey();
    }

    @Override
    public void create(Question question, int parentQuestionCount) {
        if (question.getId() == null){
            String key = MODEL_REF.push().getKey();
            question.setId(key);
        }
        Map<String, Object> childUpdates = new HashMap<>();
        // ex. of newModelPath: /questions/-LiJUUfXtIJqlGv6a2RE
        String newModelPath = MODEL_REF.getKey() + "/" + question.getId();
        // ex. of parentChildCountPath /chapters/-LiJUUfXtIJqlGv6a2RE/questionCount
        String parentChildCountPath = parentIdName + "/" + parentId + "/" + Chapter._QUESTION_COUNT;

        childUpdates.put(newModelPath, question);
        childUpdates.put(parentChildCountPath, parentQuestionCount + 1);

        MODEL_REF.getRoot().updateChildren(childUpdates);
    }

    @Override
    public LiveData<List<Question>> getAll() {
        return listMediatorLiveData;
    }

    @Override
    public void update(Question question) {
        String key = question.getId();
        MODEL_REF.child(key).setValue(question);
    }

    @Override
    public boolean delete(Question question, int parentQuestionCount) {
        String key = question.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        // ex. of newModelPath: /question/-LiJUUfXtIJqlGv6a2RE
        String newModelPath = MODEL_REF.getKey() + "/" + key;
        // ex. of parentChildCountPath /chapters/-LiJUUfXtIJqlGv6a2RE/questionCount
        String parentChildCountPath = parentIdName + "/" + parentId + "/" + Chapter._QUESTION_COUNT;

        childUpdates.put(newModelPath, null);
        childUpdates.put(parentChildCountPath, parentQuestionCount - 1);

        MODEL_REF.getRoot().updateChildren(childUpdates);
        return true;
    }

    @Override
    public void deleteAll(int parentChildCount) {
        for (Question question : getAll().getValue()) {
            delete(question, parentChildCount);
        }
    }

    private List<Question> shuffleList(List<Question> questions){
        Collections.shuffle(questions);
        return questions;
    }
}
