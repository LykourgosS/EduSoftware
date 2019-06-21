package com.unipi.lykourgoss.edusoftware.adapters;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 19,June,2019.
 */

public interface OnItemClickListener<Model extends EduEntity> {
    void onItemClick(Model model);
    void onItemLongClick(Model model);
}
