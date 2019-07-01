package com.unipi.lykourgoss.edusoftware.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.Section;
import com.unipi.lykourgoss.edusoftware.models.Subsection;
import com.unipi.lykourgoss.edusoftware.models.User;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 24,June,2019.
 */

public class CurrentViewModel extends ViewModel {

    private MutableLiveData<Boolean> editEnabled = new MutableLiveData<>();

    private MutableLiveData<User> user = new MutableLiveData<>();
    
    private MutableLiveData<Lesson> lesson = new MutableLiveData<>();

    private MutableLiveData<Chapter> chapter = new MutableLiveData<>();

    private MutableLiveData<Section> section = new MutableLiveData<>();

    private MutableLiveData<Subsection> subsection = new MutableLiveData<>();

    /* Getters and Setters for every  entity */

    /* editEnabled: If user clicked My Lessons = true, else = false*/
    public void setEditEnabled(boolean editEnabled) {
        this.editEnabled.setValue(editEnabled);
    }

    public boolean isEditEnabled() {
        return editEnabled.getValue();
    }

    /* Current User */
    public void setUser(User user){
        this.user.setValue(user);
    }

    public LiveData<User> getUser(){
        return user;
    }

    /* Current Lesson */
    public void setLesson(Lesson lesson) {
        this.lesson.setValue(lesson);
    }

    public LiveData<Lesson> getLesson() {
        return lesson;
    }

    /* Current Chapter */
    public void setChapter(Chapter chapter) {
        this.chapter.setValue(chapter);
    }

    public LiveData<Chapter> getChapter() {
        return chapter;
    }

    /* Current Section */
    public void setSection(Section section) {
        this.section.setValue(section);
    }

    public LiveData<Section> getSection() {
        return section;
    }

    /* Current Subsection */
    public void setSubsection(Subsection subsection) {
        this.subsection.setValue(subsection);
    }

    public LiveData<Subsection> getSubsection() {
        return subsection;
    }
}
