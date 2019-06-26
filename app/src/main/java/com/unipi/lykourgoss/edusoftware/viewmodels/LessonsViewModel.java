package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;
import com.unipi.lykourgoss.edusoftware.repositories.LessonsRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class LessonsViewModel extends MyViewModel<Lesson> {

    {
        _MODEL_REF = Lesson._LESSONS_REF;
        _PARENT_ID_NAME = Lesson._PARENT_ID;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new LessonsRepository(_MODEL_REF, parentId);
        listLiveData = repository.getAll();
    }
}
