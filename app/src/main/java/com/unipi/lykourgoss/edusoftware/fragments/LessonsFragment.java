package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity;
import com.unipi.lykourgoss.edusoftware.adapters.LessonAdapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.User;
import com.unipi.lykourgoss.edusoftware.viewmodels.CurrentViewModel;
import com.unipi.lykourgoss.edusoftware.viewmodels.LessonsViewModel;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 24,June,2019.
 */

public class LessonsFragment extends MyFragment<Lesson, LessonsViewModel> {

    public LessonsFragment() {
        super(new LessonAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currentViewModel = ViewModelProviders.of(getActivity()).get(CurrentViewModel.class);
        String userId = currentViewModel.getUser().getId();
        getActivity().setTitle("My Lessons");
        if (!isEditEnabled) {
            userId = null;
            getActivity().setTitle("All Lessons");
        }
        setUpViewModel(LessonsViewModel.class, userId);
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditLessonActivity.class);
        /*put in extras: the index for new lesson (hypothesis: will be added at the end
        of the lessons) -> used to set up numberPicker choices (for selecting index)*/
        intent.putExtra(EXTRA_LAST_INDEX, viewModel.getChildCount() + 1);
        startActivityForResult(intent, CREATE_NEW_REQUEST);
    }

    @Override
    protected void startActivityToEdit(Lesson lesson) {
        Intent intent = lesson.putToIntent(getActivity());
        // put in extras the index of the last lesson (so the user can't set the lesson's
        // index at maximum same as the last lesson) -> used to set up numberPicker choices
        // (for selecting index)
        intent.putExtra(EXTRA_LAST_INDEX, viewModel.getChildCount());
        startActivityForResult(intent, EDIT_REQUEST);
    }

    @Override
    protected void createNew(Intent data, int defaultIndex) {
        Lesson lesson = Lesson.getFromIntent(data, false, defaultIndex);
        // add user's info
        User user = currentViewModel.getUser();
        lesson.setAuthorId(user.getId());
        lesson.setAuthorEmail(user.getEmail());
        viewModel.create(lesson);
    }

    @Override
    protected void update(Intent data, int defaultIndex) {
        viewModel.update(Lesson.getFromIntent(data, true, defaultIndex));
    }

    @Override
    public void onItemClick(Lesson lesson) {
        currentViewModel.setLesson(lesson);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ChaptersFragment()).commit();
        Toast.makeText(getActivity(), "Open Chapters", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(final Lesson lesson) {
        Dialog.showLessonDetails(getActivity(), isEditEnabled, lesson, this);
    }

    @Override
    public void onEditClick(AlertDialog dialog, Lesson lesson) {
        startActivityToEdit(lesson);
        dialog.cancel();
    }
}
