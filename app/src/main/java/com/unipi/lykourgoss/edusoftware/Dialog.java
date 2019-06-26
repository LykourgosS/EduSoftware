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

import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

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
        if (isEditEnabled){
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(dialog, lesson);
                }
            });
        }
        editButton.setEnabled(isEditEnabled);

        dialog.show();
        return dialog;
    }

    public interface OnEditClickListener<Model extends EduEntity>{
        void onEditClick(AlertDialog dialog, Model model);
    }
}
