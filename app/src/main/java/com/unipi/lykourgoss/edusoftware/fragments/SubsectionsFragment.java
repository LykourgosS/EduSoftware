package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.unipi.lykourgoss.edusoftware.adapters.MyAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.SubsectionAdapter;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 26,June,2019.
 */

public class SubsectionsFragment extends MyFragment<Subsection, SubsectionsViewModel> {

    public SubsectionsFragment() {
        super(new SubsectionAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void startActivityToCreateNew() {

    }

    @Override
    protected void startActivityToEdit(Subsection subsection) {

    }

    @Override
    protected void delete(Subsection subsection) {

    }

    @Override
    protected void deleteAll() {

    }

    @Override
    public void onItemClick(Subsection subsection) {

    }

    @Override
    public void onItemLongClick(Subsection subsection) {

    }

    @Override
    public void onEditClick(AlertDialog dialog, Subsection subsection) {

    }
}
