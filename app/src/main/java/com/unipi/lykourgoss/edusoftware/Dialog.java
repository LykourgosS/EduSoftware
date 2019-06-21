package com.unipi.lykourgoss.edusoftware;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;
import com.unipi.lykourgoss.edusoftware.models.Lesson;

public class Dialog {

    //for CRUD operations (Create, Load, Update, Delete)
    public static final String CREATING = "Creating...";
    public static final String LOADING = "Loading...";
    public static final String UPDATING = "Updating...";
    public static final String DELETING = "Deleting...";

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
    public static AlertDialog showLessonDetails(Context context, Lesson lesson) {
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_details_lesson, null);

        /*fill textViews with lesson properties*/
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_title)).setText(lesson.getTitle());
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_index)).setText(String.valueOf(lesson.getIndex()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_child_count)).setText(String.valueOf(lesson.getChildCount()));
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_description)).setText(lesson.getDescription());
        ((TextView) dialogView.findViewById(R.id.dialog_details_lesson_author_email)).setText(lesson.getAuthorEmail());

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dialog.show();
        return dialog;
    }

    //generic dialog for creating a new EduEntity (i.e. lesson, chapter, section etc.) dialog
    public static void createEduEntity(final Context context, final EduEntity eduEntity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //use create eduEntity dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_create_edu_entity, null);
        builder.setView(dialogView);
        //in placeholder linearLayout use appropriate fields layout,
        //depends on the type of the EduEntity
        LinearLayout layout = dialogView.findViewById(R.id.linearLayout_placeholder);

        //save button -> creates and uploads to firebase the new EduEntity
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                eduEntity.create(context);
            }
        });
        //cancel button -> closes the dialog
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.cancel();
            }
        });
        builder.setCancelable(false);
//        dialog = builder.create();
//        dialog.show();
    }


    //common show alert dialogue method
    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
//        dialog = builder.create();
//        dialog.show();
    }
}
