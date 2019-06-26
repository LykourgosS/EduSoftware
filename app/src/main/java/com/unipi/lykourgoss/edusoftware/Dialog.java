package com.unipi.lykourgoss.edusoftware;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.unipi.lykourgoss.edusoftware.models.Chapter;
import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.models.Lesson;
import com.unipi.lykourgoss.edusoftware.models.Section;

public class Dialog<Model extends EduEntity<Model>> {

    //for CRUD operations (Create, Load, Update, Delete)
    public static final String CREATING = "Creating...";
    public static final String LOADING = "Loading...";
    public static final String UPDATING = "Updating...";
    public static final String DELETING = "Deleting...";
    public static final String DOWNLOADING = "Downloading...";

//    private OnEditClickListener<Model> listener;

    //generic progressbar dialog, with custom action each time
    //(i.e. Loading..., Uploading..., Saving...)
    public static AlertDialog progressbarAction(Context context, String action) {
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_progressbar, null);

        //set Action text
        ((TextView) dialogView.findViewById(R.id.textView_dialog_action)).setText(action);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.show();
        return dialog;
    }

    // show description dialog method
    public static AlertDialog showLessonDetails(Context context, boolean isEditEnabled, final Lesson lesson, final OnEditClickListener listener) {
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_details_lesson, null);

        /* fill textViews with lesson properties */
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_title)).setText(lesson.getTitle());
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_index)).setText(String.valueOf(lesson.getIndex()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_child_count)).setText(String.valueOf(lesson.getChildCount()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_description)).setText(lesson.getDescription());
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_author_email)).setText(lesson.getAuthorEmail());

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        ImageButton editButton = dialogView.findViewById(R.id.dialog_details_lesson_button_edit);
        editButton.setVisibility(View.GONE);
        if (isEditEnabled){
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(dialog, lesson);
                }
            });
        }

        dialog.show();
        return dialog;
    }

    public static AlertDialog showChapterDetails(Context context, boolean isEditEnabled, final Chapter chapter, final OnEditClickListener listener) {
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_details_chapter, null);

        /* fill textViews with chapter properties */
        ((TextView) dialogView.findViewById(R.id.dialog_details_chapter_title)).setText(chapter.getTitle());
        ((TextView) dialogView.findViewById(R.id.dialog_details_chapter_index)).setText(String.valueOf(chapter.getIndex()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_chapter_child_count)).setText(String.valueOf(chapter.getChildCount()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_chapter_description)).setText(chapter.getDescription());

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        ImageButton editButton = dialogView.findViewById(R.id.dialog_details_chapter_button_edit);
        editButton.setVisibility(View.GONE);
        if (isEditEnabled){
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(dialog, chapter);
                }
            });
        }

        dialog.show();
        return dialog;
    }

    public static AlertDialog showSectionDetails(Context context, boolean isEditEnabled, final Section section, final OnEditClickListener listener){
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_details_section, null);

        /* fill textViews with section properties */
        ((TextView) dialogView.findViewById(R.id.dialog_details_section_title)).setText(section.getTitle());
        ((TextView) dialogView.findViewById(R.id.dialog_details_section_index)).setText(String.valueOf(section.getIndex()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_section_child_count)).setText(String.valueOf(section.getChildCount()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_section_description)).setText(section.getDescription());

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        ImageButton editButton = dialogView.findViewById(R.id.dialog_details_section_button_edit);
        editButton.setVisibility(View.GONE);
        if (isEditEnabled){
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(dialog, section);
                }
            });
        }

        dialog.show();
        return dialog;
    }

    public interface OnEditClickListener<Model extends EduEntity>{
        void onEditClick(AlertDialog dialog, Model model);
    }
}
