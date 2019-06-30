package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;

import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Question;
import com.unipi.lykourgoss.edusoftware.repositories.QuestionRepository;

import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 29,June,2019.
 */

public class QuestionViewModel extends MyViewModel<Question> {


    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
        repository = new QuestionRepository(Question._QUESTIONS_REF, Chapter._CHAPTERS_REF, parentId);
        listLiveData = repository.getAll();
    }

    public Question getQuestionById(String id, List<Question> questionList) {
        for (Question question : questionList) {
            if (question.getId().equals(id)) {
                return question;
            }
        }
        return null;
    }
}
