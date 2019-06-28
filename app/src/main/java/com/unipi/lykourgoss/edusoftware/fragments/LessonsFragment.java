package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditLessonActivity;
import com.unipi.lykourgoss.edusoftware.adapters.LessonAdapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.viewmodels.LessonsViewModel;

import static android.app.Activity.RESULT_OK;

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

        /* Set title and subtitle */

        String title = "My Lessons";
        String userId = currentViewModel.getUser().getId();
        if (!isEditEnabled) {
            userId = null;
            title = "All Lessons";
        }
        getActivity().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");

        /* Set up viewModel */
        setUpViewModel(LessonsViewModel.class, userId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /* Handle result when creating new or updating an existing one */

        /* default value for model's index is the last available index */
        int lessonCount = viewModel.getChildCount();

        if (requestCode == Constant.CREATE_NEW_REQUEST) {
            if (resultCode == RESULT_OK) {

                /* !ATTENTION! Creating lesson object and adding user's info and childCount */
                Lesson lesson = Lesson.getFromIntent(data, false, lessonCount + 1);
                lesson.setParentId(currentViewModel.getUser().getId());
                lesson.setAuthorEmail(currentViewModel.getUser().getEmail());
                lesson.setChildCount(0);
                viewModel.create(lesson, lessonCount);

                Toast.makeText(getActivity(), "Lesson created", Toast.LENGTH_SHORT).show();
            } else {// something went wrong or user clicked to go back
                Toast.makeText(getActivity(), "Lesson not created", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {

                viewModel.update(Lesson.getFromIntent(data, true, lessonCount));

                Toast.makeText(getActivity(), "Lesson updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Lesson not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditLessonActivity.class);
        intent.putExtra(Constant.EXTRA_LAST_INDEX, viewModel.getChildCount() + 1);
        startActivityForResult(intent, Constant.CREATE_NEW_REQUEST);
    }

    @Override
    protected void startActivityToEdit(Lesson lesson) {
        Intent intent = lesson.putToIntent(getActivity());
        intent.putExtra(Constant.EXTRA_LAST_INDEX, viewModel.getChildCount());
        startActivityForResult(intent, Constant.EDIT_REQUEST);
    }

    @Override
    protected void delete(Lesson lesson) {
        String deleteMsg = "Lesson deleted";
        if (!viewModel.delete(lesson, viewModel.getChildCount())){
            deleteMsg = "Lesson cannot be deleted, because it is not empty";
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(getActivity(), deleteMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void deleteAll() {
        viewModel.deleteAll(viewModel.getChildCount());
        Toast.makeText(getActivity(), "All empty lessons deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Lesson lesson) {
        /* Open fragment displaying chapter's sections */
        currentViewModel.setLesson(lesson);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ChaptersFragment()).commit();
    }

    @Override
    public void onItemLongClick(final Lesson lesson) {
        Dialog.showLessonDetails(getActivity(), isEditEnabled, lesson, this);
    }
}
