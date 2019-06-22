/*
package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

import java.util.List;

*/
/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 20,June,2019.
 *//*


public class LessonsFirebaseViewModel extends ViewModel {

    private static final DatabaseReference LESSONS_REF =
            FirebaseDatabase.getInstance().getReference("/lessons");

    private FirebaseQueryLiveData liveData;
    private final MediatorLiveData<List<Lesson>> lessonsLiveData = new MediatorLiveData<>();

    public LessonsFirebaseViewModel() {
        super();
        liveData = new FirebaseQueryLiveData(LESSONS_REF);
    }

    public LessonsFirebaseViewModel(String userId) {
        super();
        Query myLessonsQuery = LESSONS_REF.orderByChild("authorId").equalTo(userId);
        liveData = new FirebaseQueryLiveData(myLessonsQuery);
    }

    private LessonFirebaseRepository repository;
    private FirebaseQueryLiveData allLessons;

    public LessonsFirebaseViewModel() {
        super();
        repository = new LessonFirebaseRepository();
        allLessons = lessonRepository.getAll();
    }

    public void create(Lesson lesson){
        String lessonId = LESSONS_REF.push().getKey();
        lesson.setId(lessonId);
        lessonRepository.create(lesson);
    }

    public void update(Lesson lesson){
        lessonRepository.update(lesson);
    }

    public void delete(Lesson lesson){
        lessonRepository.delete(lesson);
    }

    public void deleteAll(){
        lessonRepository.deleteAll();
    }

    public LiveData<List<Lesson>> getAll() {
        return allLessons;
    }

    public int getCount(){
        return allLessons.getValue().size();
    }
}
*/
