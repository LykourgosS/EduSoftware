package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.viewmodels.FirebaseQueryLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 21,June,2019.
 */

public class FirebaseRepository <Model extends EduEntity> {

    protected MediatorLiveData<List<Model>> listMediatorLiveData;

    protected static DatabaseReference MODEL_REF;

    private String parentId;

    private String parentIdName;

    protected FirebaseRepository(String modelRef, String parentId){

    }

    public FirebaseRepository(String modelRef, String parentIdName, String parentId, final Class<Model> klass) {
        this.parentId = parentId;
        this.parentIdName = parentIdName;

        MODEL_REF = FirebaseDatabase.getInstance().getReference().child(modelRef);
        /*Query query = MODEL_REF.orderByChild(Model._INDEX);
        if (parentId != null) { // display only EduEntities that belong to specific parent
            query = MODEL_REF.orderByChild(Model._PARENT_ID).equalTo(parentId);
        }*/
        Query query = MODEL_REF.orderByChild(Model._PARENT_ID).equalTo(parentId);

        // listMediatorLiveData is used to store either all or my
        listMediatorLiveData = new MediatorLiveData<>();
        listMediatorLiveData.addSource(new FirebaseQueryLiveData(query), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(final DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Model> models = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                models.add(snapshot.getValue(klass));
                            }
                            listMediatorLiveData.postValue(models);
                        }
                    }).start();
                } else {
                    listMediatorLiveData.setValue(null);
                }
            }
        });
    }

    public void create(Model model, int parentChildCount){
        String key = MODEL_REF.push().getKey();
        model.setId(key);
        //MODEL_REF.child(key).setValue(model);
        //MODEL_REF.child(parentIdName).child(EduEntity._CHILD_COUNT).setValue(parentChildCount + 1);

        Map<String, Object> childUpdates = new HashMap<>();
        // ex. of newModelPath: /chapters/-LiJUUfXtIJqlGv6a2RE
        String newModelPath = MODEL_REF.getKey() + "/" + key;
        // ex. of parentChildCountPath /lessons/-LiJUUfXtIJqlGv6a2RE/childCount
        String parentChildCountPath = parentIdName + "/" + parentId + "/" + EduEntity._CHILD_COUNT;

        childUpdates.put(newModelPath, model);
        childUpdates.put(parentChildCountPath, parentChildCount + 1);

        MODEL_REF.getRoot().updateChildren(childUpdates);

    }

    public LiveData<List<Model>> getAll(){
        return listMediatorLiveData;
    }

    public void update(Model model){
        String key = model.getId();
        MODEL_REF.child(key).setValue(model);
    }

    /* model cannot be deleted, unless it has no children */
    public boolean delete(Model model, int parentChildCount){
        if (model.getChildCount() == 0) {
            String key = model.getId();
            // todo the same as create
            //MODEL_REF.child(key).setValue(null);

            Map<String, Object> childUpdates = new HashMap<>();
            // ex. of newModelPath: /chapters/-LiJUUfXtIJqlGv6a2RE
            String newModelPath = MODEL_REF.getKey() + "/" + key;
            // ex. of parentChildCountPath /lessons/-LiJUUfXtIJqlGv6a2RE/childCount
            String parentChildCountPath = parentIdName + "/" + parentId + "/" + EduEntity._CHILD_COUNT;

            childUpdates.put(newModelPath, null);
            childUpdates.put(parentChildCountPath, parentChildCount - 1);

            MODEL_REF.getRoot().updateChildren(childUpdates);

            return true;
        } else {
            return false;
        }
    }

    public void deleteAll(int parentChildCount){
        for (Model model: getAll().getValue()){
            delete(model, parentChildCount);
        }
    }
}