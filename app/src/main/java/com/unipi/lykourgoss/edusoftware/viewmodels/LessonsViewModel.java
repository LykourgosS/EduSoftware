package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class LessonsViewModel extends MyViewModel <Lesson> {

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new FirebaseRepository<>(parentId, Lesson.class);
        listLiveData = repository.getAll();
    }
}
