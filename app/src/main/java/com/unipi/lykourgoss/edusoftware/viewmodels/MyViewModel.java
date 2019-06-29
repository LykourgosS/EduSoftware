package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.unipi.lykourgoss.edusoftware.repositories.MyRepository;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public abstract class MyViewModel<T> extends ViewModel {

    protected MyRepository<T> repository;

    protected LiveData<List<T>> listLiveData;

    protected String parentId;

    public void create(T model, int parentChildCount) {
        repository.create(model, parentChildCount);
    }

    // cannot be implemented because T has not any method getId (can be fixed if T extends
    // superclass which will contain getId() method)
    public T getById(String id) {
        List<T> list = listLiveData.getValue();
        for (T t : list) {
            if (t./*getId().*/equals(id)) {
                return t;
            }
        }
        return null;
    }

    public LiveData<List<T>> getAll() {
        return listLiveData;
    }

    public void update(T model) {
        repository.update(model);
    }

    public boolean delete(T model, int parentChildCount) {
        return repository.delete(model, parentChildCount);
    }

    public void deleteAll(int parentChildCount) {
        repository.deleteAll(parentChildCount);
    }

    /* don't need that use childCount property*/
    public int getChildCount() {
        return listLiveData.getValue().size();
    }

    /* used to load entities that belong to an entity (i.e lessons belong to users (authors),
    if null means all lessons. But for chapters, sections, subsections loads those that belong
    to the clicked, selected item).
    !ATTENTION! should be called right after initialization of this */
    public abstract void setParentId(String parentId);
}
