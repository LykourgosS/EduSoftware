package com.unipi.lykourgoss.edusoftware.repositories;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.models.Subsection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 28,June,2019.
 */

public class SubsectionRepository extends FirebaseRepository<Subsection> {
    public SubsectionRepository(String modelRef, String parentIdName, String parentId, Class<Subsection> klass) {
        super(modelRef, parentIdName, parentId, klass);
    }

    /*// only SubsectionRepository use getNewId()
    @Override
    public String getNewId() {
        return MODEL_REF.push().getKey();
    }

    // overrides because object already have an id
    @Override
    public void create(Subsection subsection, int parentChildCount) {
        Map<String, Object> childUpdates = new HashMap<>();
        // ex. of newModelPath: /subsections/-LiJUUfXtIJqlGv6a2RE
        String newModelPath = MODEL_REF.getKey() + "/" + subsection.getId();
        // ex. of parentChildCountPath /sections/-LiJUUfXtIJqlGv6a2RE/childCount
        String parentChildCountPath = parentIdName + "/" + parentId + "/" + EduEntity._CHILD_COUNT;

        childUpdates.put(newModelPath, subsection);
        childUpdates.put(parentChildCountPath, parentChildCount + 1);

        MODEL_REF.getRoot().updateChildren(childUpdates);
    }*/

    @Override
    public boolean delete(Subsection subsection, int parentChildCount) {
        if (super.delete(subsection, parentChildCount)){
            // delete pdf file from firebase storage as well
            StorageReference subsectionsRef = FirebaseStorage.getInstance().getReference()
                    .child("subsections").child(subsection.getId()).child(subsection.getPdfFilename());;
            subsectionsRef.delete();
            return true;
        }
        return false;
    }
}
