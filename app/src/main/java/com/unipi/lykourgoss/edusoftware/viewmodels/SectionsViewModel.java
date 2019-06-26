package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class SectionsViewModel extends MyViewModel<Section> {

    static {
        _MODEL_REF = Section._SECTIONS_REF;
        _PARENT_ID_NAME = Section._CHAPTER_ID;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new FirebaseRepository<>(_MODEL_REF, _PARENT_ID_NAME, parentId, Section.class);
        listLiveData = repository.getAll();
    }
}
