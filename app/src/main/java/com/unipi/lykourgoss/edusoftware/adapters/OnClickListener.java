package com.unipi.lykourgoss.edusoftware.adapters;

import com.unipi.lykourgoss.edusoftware.codingflowexample.Lesson;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 19,June,2019.
 */

public interface OnClickListener {
    void onItemClick(Lesson lesson);
    void onDescriptionClick(Lesson lesson);
}
