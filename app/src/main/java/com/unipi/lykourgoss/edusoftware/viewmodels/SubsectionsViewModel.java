package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.repositories.FirebaseRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class SubsectionsViewModel extends MyViewModel<Subsection> {

    static {
        _MODEL_REF = Subsection._SUBSECTIONS_REF;
        _PARENT_ID_NAME = Subsection._SECTION_ID;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new FirebaseRepository<>(_MODEL_REF, _PARENT_ID_NAME, parentId, Subsection.class);
        listLiveData = repository.getAll();
    }
}
