package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.repositories.LessonRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 22,June,2019.
 */

public class LessonsViewModel extends MyViewModel <Lesson> {

    @Override
    public void setParentId(String parentId) {
        repository = new LessonRepository(parentId);
        listLiveData = repository.getAll();
    }
}
