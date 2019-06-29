package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class ChaptersViewModel extends MyViewModel<Chapter> {

    /*todo remove{
        _MODEL_REF = Chapter._CHAPTERS_REF;
        _PARENT_ID_NAME = Chapter._PARENT_ID;
    }*/

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new FirebaseRepository<>(Chapter._CHAPTERS_REF, Lesson._LESSONS_REF, parentId, Chapter.class);
        listLiveData = repository.getAll();
    }
}
