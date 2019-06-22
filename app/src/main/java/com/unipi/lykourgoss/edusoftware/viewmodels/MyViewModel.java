package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public abstract class MyViewModel<Model extends EduEntity> extends ViewModel {

    protected FirebaseRepository<Model> repository;

    protected LiveData<List<Model>> listLiveData;

    protected String userId;

    protected String parentId;

    public void create(Model model) {
        repository.create(model);
    }

    public LiveData<List<Model>> getAll() {
        return listLiveData;
    }

    public void update(Model model) {
        repository.update(model);
    }

    public void delete(Model model) {
        repository.delete(model);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public int getChildrenCount() {
        return listLiveData.getValue().size();
    }

    // todo make UserViewModel
    public void setUserId(String userId){
        this.userId = userId;
    }

    /* used to load entities that belong to an entity (i.e lessons belong to users (authors),
    if null means all lessons. But for chapters, sections, subsections loads those that belong
    to the clicked, selected item.) */
    public abstract void setParentId(String parentId);
}
