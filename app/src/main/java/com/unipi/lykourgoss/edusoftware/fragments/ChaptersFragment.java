package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditChapterActivity;
import com.unipi.lykourgoss.edusoftware.adapters.ChapterAdapter;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.ChaptersViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 26,June,2019.
 */

public class ChaptersFragment extends MyFragment<Chapter, ChaptersViewModel> {


    public ChaptersFragment() {
        super(new ChapterAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title and subtitle */
        getActivity().setTitle("Chapters");

        Lesson lesson = currentViewModel.getLesson().getValue();
        String subtitle =  "/" + lesson.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);

        /* Set up viewModel */
        setUpViewModel(ChaptersViewModel.class, lesson.getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Handle result when creating new or updating an existing one */

        // default value for model's index is the last available index
        int chapterCount = viewModel.getChildCount();

        if (requestCode == CREATE_NEW_REQUEST) {
            if (resultCode == RESULT_OK) {

                /* !ATTENTION! Creating chapter object and adding lessons's info and childCount */
                Chapter chapter = Chapter.getFromIntent(data, false, chapterCount + 1);
                chapter.setParentId(currentViewModel.getLesson().getValue().getId());
                chapter.setExamQuestionCount(0);
                chapter.setChildCount(0);
                viewModel.create(chapter, chapterCount);

                Toast.makeText(getActivity(), "Chapter created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(getActivity(), "Chapter not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {

                viewModel.update(Chapter.getFromIntent(data, true, chapterCount));

                Toast.makeText(getActivity(), "Chapter updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Chapter not updated", Toast.LENGTH_SHORT).show();
            }
        }
        
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditChapterActivity.class);
        /*put in extras: the index for new chapter (hypothesis: will be added at the end
        of the chapters) -> used to set up numberPicker choices (for selecting index)*/
        intent.putExtra(EXTRA_LAST_INDEX, viewModel.getChildCount() + 1);
        startActivityForResult(intent, CREATE_NEW_REQUEST);
    }

    @Override
    protected void startActivityToEdit(Chapter chapter) {
        Intent intent = chapter.putToIntent(getActivity());
        /*put in extras: the index for new chapter (hypothesis: will be added at the end
        of the chapters) -> used to set up numberPicker choices (for selecting index)*/
        intent.putExtra(EXTRA_LAST_INDEX, viewModel.getChildCount());
        startActivityForResult(intent, EDIT_REQUEST);
    }

    @Override
    protected void delete(Chapter chapter) {
        String deleteMsg = "Chapter deleted";
        if (!viewModel.delete(chapter, viewModel.getChildCount())){
            deleteMsg = "Chapter cannot be deleted, because it is not empty";
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(getActivity(), deleteMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void deleteAll() {
        viewModel.deleteAll(viewModel.getChildCount());
        Toast.makeText(getActivity(), "All empty chapters deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Chapter chapter) {
        /* Open fragment displaying chapter's sections */
        currentViewModel.setChapter(chapter);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SectionsFragment()).commit();
    }

    @Override
    public void onItemLongClick(Chapter chapter) {
        Dialog.showChapterDetails(getActivity(), isEditEnabled, chapter, this);
    }

    @Override
    public void onEditClick(AlertDialog dialog, Chapter chapter) {
        startActivityToEdit(chapter);
        dialog.cancel();
    }
}
