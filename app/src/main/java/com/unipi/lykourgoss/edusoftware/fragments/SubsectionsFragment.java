package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.PdfViewerActivity;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditSubsectionActivity;
import com.unipi.lykourgoss.edusoftware.adapters.SubsectionAdapter;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.viewmodels.SubsectionsViewModel;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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

        /* Set title and subtitle */
        getActivity().setTitle("Subsections");

        Lesson lesson = currentViewModel.getLesson().getValue();
        Chapter chapter = currentViewModel.getChapter().getValue();
        Section section = currentViewModel.getSection().getValue();
        String subtitle = "/" + lesson.getTitle() + "/" + chapter.getTitle() + "/" + section.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);

        /* Set up viewModel */
        setUpViewModel(SubsectionsViewModel.class, section.getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Handle result when creating new or updating an existing one */

        // default value for model's index is the last available index
        int subsectionCount = viewModel.getChildCount();

        if (requestCode == Constant.CREATE_NEW_REQUEST) {
            if (resultCode == RESULT_OK) {

                /* !ATTENTION! Creating subsection object and adding section's info and childCount */

                // ! -> hasId = true (because subsection is the only entity that its id is created
                // at the time user clicked to add one, and it is passed to CreateEditSubsection
                // activity in Extras)
                Subsection subsection = Subsection.getFromIntent(data, true, subsectionCount + 1);
                subsection.setParentId(currentViewModel.getSection().getValue().getId());
                subsection.setQuestionCount(0);
                subsection.setChildCount(0);
                viewModel.create(subsection, subsectionCount);

                Toast.makeText(getActivity(), "Subsection created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(getActivity(), "Subsection not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {

                viewModel.update(Subsection.getFromIntent(data, true, subsectionCount));

                Toast.makeText(getActivity(), "Subsection updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Subsection not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditSubsectionActivity.class);
        String newId = viewModel.getNewId();
        intent.putExtra(Constant.EXTRA_NEW_ID, newId);
        String sectionId = currentViewModel.getSection().getValue().getId();
        intent.putExtra(Constant.EXTRA_PARENT_ID, sectionId);
        intent.putExtra(Constant.EXTRA_LAST_INDEX, viewModel.getChildCount() + 1);
        startActivityForResult(intent, Constant.CREATE_NEW_REQUEST);
    }

    @Override
    protected void startActivityToEdit(Subsection subsection) {
        Intent intent = subsection.putToIntent(getActivity());
        intent.putExtra(Constant.EXTRA_LAST_INDEX, viewModel.getChildCount());
        startActivityForResult(intent, Constant.EDIT_REQUEST);
    }

    @Override
    protected void delete(Subsection subsection) {
        String deleteMsg = "Subsection deleted";
        if (!viewModel.delete(subsection, viewModel.getChildCount())) {
            deleteMsg = "Subsection cannot be deleted, because it is not empty";
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
    public void onItemClick(Subsection subsection) {
        if (fileInAssets(subsection)){ // todo not in Assets.. if is downloaded!
            Intent intent = new Intent(getActivity(), PdfViewerActivity.class);
            intent.putExtra(Constant.EXTRA_ID, subsection.getId());
            intent.putExtra(Constant.EXTRA_PARENT_ID, subsection.getParentId());
            startActivity(intent);
        } else {
            // todo dialogDownload pdf (if ok execute downloadPdf)
            // todo make downloadPdf() method!
        }
    }

    @Override
    public void onItemLongClick(Subsection subsection) {
        if (isEditEnabled) {
            startActivityToEdit(subsection);
        }
    }

    private boolean fileInAssets(Subsection subsection) {
        String path = "subsections/" + subsection.getParentId();
        try {
            for (String filename : getActivity().getAssets().list(path)){
                if (filename.equals(subsection.getPdfFilename())){
                    return true;
                }
            }
        } catch (final IOException e) {
            return false;
        }
        return false;
    }
}
