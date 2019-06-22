package com.unipi.lykourgoss.edusoftware.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 21,June,2019.
 */

public interface FirebaseRepository <Model> {

    void create(Model model);

    void update(Model model);

    boolean delete(Model model);

    void deleteAll();

    LiveData<List<Model>> getAll();
}