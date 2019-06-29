package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public interface MyRepository<T> {

    /**
     * only SubsectionRepository use getNewId()
     */
    String getNewId();

    void create(T t, int parentChildCount);

    LiveData<List<T>> getAll();

    void update(T t);

    boolean delete(T t, int parentChildCount);

    void deleteAll(int parentChildCount);
}
