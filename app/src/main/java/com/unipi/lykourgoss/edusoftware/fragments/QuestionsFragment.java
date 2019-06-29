package com.unipi.lykourgoss.edusoftware.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.activities.createedit.CreateEditQuestionActivity;
import com.unipi.lykourgoss.edusoftware.adapters.QuestionAdapter;
import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.viewmodels.QuestionViewModel;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class QuestionsFragment extends MyFragment<Question, QuestionViewModel> {


    protected QuestionsFragment() {
        super(new QuestionAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title and subtitle */
        getActivity().setTitle("Questions");

        Lesson lesson = currentViewModel.getLesson().getValue();
        Chapter chapter = currentViewModel.getChapter().getValue();
        String subtitle =  "/" + lesson.getTitle() + "/" + chapter.getTitle();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);

        /* Set up viewModel */
        setUpViewModel(QuestionViewModel.class, chapter.getId());
    }

    @Override
    protected void startActivityToCreateNew() {
        Intent intent = new Intent(getActivity(), CreateEditQuestionActivity.class);
        Chapter chapter = currentViewModel.getChapter().getValue();
        intent.putExtra(Constant.EXTRA_PARENT_ID, chapter.getId());
        intent.putExtra(Constant.EXTRA_PARENT_TITLE, chapter.getTitle());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, viewModel.getChildCount());
        startActivity(intent);
    }

    @Override
    protected void startActivityToEdit(Question question) {
        Intent intent = new Intent(getActivity(), CreateEditQuestionActivity.class);
        intent.putExtra(Constant.EXTRA_ID, question.getId());
        Chapter chapter = currentViewModel.getChapter().getValue();
        intent.putExtra(Constant.EXTRA_PARENT_ID, chapter.getId());
        intent.putExtra(Constant.EXTRA_PARENT_TITLE, chapter.getTitle());
        intent.putExtra(Constant.EXTRA_CHILD_COUNT, viewModel.getChildCount());
        startActivity(intent);
    }

    @Override
    protected void delete(Question question) {
        viewModel.delete(question, viewModel.getChildCount());
        Toast.makeText(getActivity(), "Section deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void deleteAll() {
        viewModel.deleteAll(viewModel.getChildCount());
        Toast.makeText(getActivity(), "All questions deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Question question) {

    }

    @Override
    public void onItemLongClick(Question question) {
        startActivityToEdit(question);
    }
}
