package com.unipi.lykourgoss.edusoftware.viewholders;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 19,June,2019.
 */

public interface OnItemClickListener<T> {
    void onItemClick(T t);
    void onItemLongClick(T t);
}
