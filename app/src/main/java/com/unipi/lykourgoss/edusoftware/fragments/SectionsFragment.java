package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSectionActivity;
import com.unipi.lykourgoss.edusoftware.adapters.MyAdapter;
import com.unipi.lykourgoss.edusoftware.adapters.SectionAdapter;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.viewmodels.SectionsViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 26,June,2019.
 */

public class SectionsFragment extends MyFragment<Section, SectionsViewModel> {

    public SectionsFragment() {
        super(new SectionAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title and subtitle */
        getActivity().setTitle("Sections");

        Lesson lesson = currentViewModel.getLesson().getValue();
        Chapter chapter = currentViewModel.getChapter().getValue();
        String subtitle =  "/" + lesson.getTitle() + "/" + chapter.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);

        /* Set up viewModel */
        setUpViewModel(SectionsViewModel.class, chapter.getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Handle result when creating new or updating an existing one */

        // default value for model's index is the last available index
        int sectionCount = viewModel.getChildCount();

        if (requestCode == CREATE_NEW_REQUEST) {
            if (resultCode == RESULT_OK) {

                /* !ATTENTION! Creating section object and adding chapter's info and childCount */
                Section section = Section.getFromIntent(data, false, sectionCount + 1);
                section.setParentId(currentViewModel.getChapter().getValue().getId());
                section.setChildCount(0);
                viewModel.create(section, sectionCount);

                Toast.makeText(getActivity(), "Section created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(getActivity(), "Section not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {

                viewModel.update(Section.getFromIntent(data, true, sectionCount));

                Toast.makeText(getActivity(), "Section updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Section not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditSectionActivity.class);
        intent.putExtra(EXTRA_LAST_INDEX, viewModel.getChildCount() + 1);
        startActivityForResult(intent, CREATE_NEW_REQUEST);
    }

    @Override
    protected void delete(Section section) {
        String deleteMsg = "Section deleted";
        if (!viewModel.delete(section, viewModel.getChildCount())){
            deleteMsg = "Section cannot be deleted, because it is not empty";
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(getActivity(), deleteMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void deleteAll() {
        viewModel.deleteAll(viewModel.getChildCount());
        Toast.makeText(getActivity(), "All empty sections deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Section section) {
        currentViewModel.setSection(section);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SubsectionsFragment()).commit();
    }

    @Override
    public void onItemLongClick(Section section) {
        Dialog.showSectionDetails(getActivity(), isEditEnabled, section, this);
    }
}
