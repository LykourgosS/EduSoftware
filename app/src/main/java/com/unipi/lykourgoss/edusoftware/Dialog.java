package com.unipi.lykourgoss.edusoftware;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unipi.lykourgoss.edusoftware.models.EduEntity;

public class Dialog {

    //for CRUD operations (Create, Load, Update, Delete)
    public static final String CREATING = "Creating...";
    public static final String LOADING = "Loading...";
    public static final String UPDATING = "Updating...";
    public static final String DELETING = "Deleting...";

    private static AlertDialog dialog;

    //generic progressbar dialog, with custom action each time
    //(i.e. Loading..., Uploading..., Saving...)
    public static void progressbarAction(Context context, String action){
        //use custom dialog layout theme
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_progressbar, null);

        //set Action text
        TextView textViewDialogAction = dialogView.findViewById(R.id.textView_dialog_action);
        textViewDialogAction.setText(action);

        AlertDialog.Builder builder= new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    // show description dialog method
    public static void showDescription(Context context, String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    //generic dialog for creating a new EduEntity (i.e. lesson, chapter, section etc.) dialog
    public static void createEduEntity(final Context context, final EduEntity eduEntity){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
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
                eduEntity.create(context);
            }
        });
        //cancel button -> closes the dialog
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
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
        dialog = builder.create();
        dialog.show();
    }

    public static void dismiss(){
        dialog.cancel();
        dialog = null;
    }
}
