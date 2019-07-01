package com.unipi.lykourgoss.edusoftware;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 28,June,2019.
 */

public class Constant {

    // for EduEntity
    public static final String EXTRA_ID =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_TITLE";
    public static final String EXTRA_INDEX =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_INDEX";
    public static final String EXTRA_DESCRIPTION =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_DESCRIPTION";
    public static final String EXTRA_CHILD_COUNT =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_CHILD_COUNT";
    public static final String EXTRA_PARENT_ID =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_PARENT_ID";

    // for Lesson
    public static final String EXTRA_AUTHOR_EMAIL =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_AUTHOR_EMAIL";

    // for Chapters, and Subsection
    public static final String EXTRA_QUESTION_COUNT =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_QUESTION_COUNT";

    // for Subsection
    public static final String EXTRA_PDF_URL =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_PDF_URL";
    public static final String EXTRA_PDF_FILENAME =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_PDF_FILENAME";
    // (used for creating new subsection)
    public static final String EXTRA_NEW_ID =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_NEW_ID";

    // used for creating new exam questions
    public static final String EXTRA_PARENT_TITLE =
            "com.unipi.lykourgoss.edusoftware.constants.EXTRA_PARENT_TITLE";

    // for CreateEdit requests
    public static final int CREATE_NEW_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;
    public static final int SELECT_PDF_REQUEST = 3;

    // for passing last available index to CreateEditActivity
    // (i.e. EXTRA_LAST_INDEX = how many chapters there are in a lesson)
    public static final String EXTRA_LAST_INDEX = "com.unipi.lykourgoss.edusoftware.constants.EXTRA_LAST_INDEX";

    // used for the statistics
    /**
     *
     * @return yyyy-MM-dd HH:mm:ss format date as string
     */
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find today's date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
