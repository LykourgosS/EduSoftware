package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.unipi.lykourgoss.edusoftware.adapters.ChapterAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.MyAdapter;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.viewmodels.ChaptersViewModel;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 26,June,2019.
 */

public class ChaptersFragment extends MyFragment<Chapter, ChaptersViewModel> {


    public ChaptersFragment() {
        super(new ChapterAdapter());
    }

    @Override
    protected void startActivityToCreateNew() {

    }

    @Override
    protected void startActivityToEdit(Chapter chapter) {

    }

    @Override
    protected void createNew(Intent data, int resultCode) {

    }

    @Override
    protected void update(Intent data, int resultCode) {

    }

    @Override
    public void onEditClick(AlertDialog dialog, Chapter chapter) {

    }

    @Override
    public void onItemClick(Chapter chapter) {

    }

    @Override
    public void onItemLongClick(Chapter chapter) {

    }
}
