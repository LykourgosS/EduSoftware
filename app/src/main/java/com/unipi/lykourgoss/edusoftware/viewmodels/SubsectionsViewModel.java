package com.unipi.lykourgoss.edusoftware.viewmodels;

import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.repositories.SubsectionRepository;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class SubsectionsViewModel extends MyViewModel<Subsection> {

    /*todo remove{
        _MODEL_REF = Subsection._SUBSECTIONS_REF;
        _PARENT_ID_NAME = Subsection._PARENT_ID;
    }*/

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new SubsectionRepository(Subsection._SUBSECTIONS_REF, Section._SECTIONS_REF, parentId, Subsection.class);
        listLiveData = repository.getAll();
    }

    public String getNewId() {
        return repository.getNewId();
    }
}
