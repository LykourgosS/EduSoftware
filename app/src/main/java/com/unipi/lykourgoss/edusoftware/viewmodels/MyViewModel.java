package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public abstract class MyViewModel<Model extends EduEntity> extends ViewModel {

    protected String _MODEL_REF;

    protected String _PARENT_ID_NAME;

    protected FirebaseRepository<Model> repository;

    protected LiveData<List<Model>> listLiveData;

    protected String parentId;

    public void create(Model model, int parentChildCount) {
        repository.create(model, parentChildCount);
    }

    public LiveData<List<Model>> getAll() {
        return listLiveData;
    }

    public void update(Model model) {
        repository.update(model);
    }

    public boolean delete(Model model, int parentChildCount) {
        return repository.delete(model, parentChildCount);
    }

    public void deleteAll(int parentChildCount) {
        repository.deleteAll(parentChildCount);
    }

    /* don't need that use childCount property*/
    public int getChildCount() {
        return listLiveData.getValue().size();
    }

    public LiveData<Model> getModel(String id){
        List<Model> models = listLiveData.getValue();
        for (Model model : models){
            if (model.getId() == id){
                MutableLiveData<Model> modelLiveData = new MutableLiveData<Model>();
                modelLiveData.setValue(model);
                return modelLiveData;
            }
        }
        return null;
    }

    /* used to load entities that belong to an entity (i.e lessons belong to users (authors),
    if null means all lessons. But for chapters, sections, subsections loads those that belong
    to the clicked, selected item).
    !ATTENTION! should be called right after initialization of this */
    public abstract void setParentId(String parentId);
}
