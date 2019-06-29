package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.repositories.LessonRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class LessonsViewModel extends MyViewModel<Lesson> {

    /*todo remove{
        _MODEL_REF = Lesson._LESSONS_REF;
        _PARENT_ID_NAME = Lesson._PARENT_ID;
    }*/

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new LessonRepository(Lesson._LESSONS_REF, parentId);
        listLiveData = repository.getAll();
    }
}
