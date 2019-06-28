package com.unipi.lykourgoss.edusoftware.activities.createedit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unipi.lykourgoss.edusoftware.Constant;
import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;
import com.unipi.lykourgoss.edusoftware.models.Subsection;

import java.io.File;

public class CreateEditSubsectionActivity extends CreateEditActivity implements View.OnClickListener {

    private StorageReference mStorageRef;

    private Uri pdfFilepath;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPdfFilename;
    private Button buttonSelectPdf;
    private NumberPicker numberPickerIndex;

    private Subsection subsection;

    private String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_subsection);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        editTextTitle = findViewById(R.id.create_edit_title);
        editTextDescription = findViewById(R.id.create_edit_description);
        editTextPdfFilename = findViewById(R.id.create_edit_pdf_filename);
        editTextPdfFilename.setEnabled(false);

        buttonSelectPdf = findViewById(R.id.button_select_pdf);
        buttonSelectPdf.setOnClickListener(this);

        numberPickerIndex = findViewById(R.id.create_edit_index);

        Intent intent = getIntent();

        int lastIndex = intent.getIntExtra(Constant.EXTRA_LAST_INDEX, 1);

        numberPickerIndex.setMinValue(1);
        numberPickerIndex.setMaxValue(lastIndex);

        subsection = null;
        if (intent.hasExtra(Constant.EXTRA_ID)) { // update situation

            subsection = Subsection.getFromIntent(intent, true, 0);

            setTitle("Edit Subsection");
            // fill editTexts with subsection values for editing
            editTextTitle.setText(intent.getStringExtra(Constant.EXTRA_TITLE));
            editTextPdfFilename.setText(intent.getStringExtra(Constant.EXTRA_PDF_FILENAME));
            numberPickerIndex.setValue(intent.getIntExtra(Constant.EXTRA_INDEX, 1));
        } else { // create new situation
            setTitle("Create Νew Subsection");
            //default is to add it as last...
            numberPickerIndex.setValue(lastIndex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.upload_subsection_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_upload_pdf:
                uploadPdf();
                return true;
            case R.id.item_save_edu_entity:
                saveEntity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_select_pdf:
                // open file chooser
                buttonSelectPdf.clearFocus();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), Constant.SELECT_PDF_REQUEST);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.SELECT_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfFilepath = data.getData();
            editTextPdfFilename.setText(new File(pdfFilepath.getPath()).getName());
            pdfUrl = null;
        }
    }

    @Override
    protected void saveEntity() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String pdfFilename = editTextPdfFilename.getText().toString().trim();
        int index = numberPickerIndex.getValue();

        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            editTextTitle.requestFocus();
            return;
        } else if (pdfFilename.isEmpty()) {
            Toast.makeText(this, "Pdf file has not been selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (pdfUrl == null) {
            Toast.makeText(this, "Upload pdf first", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data;

        if (subsection != null) {
            // set every field that might have change to update
            subsection.setTitle(title);
            subsection.setDescription(description);
            subsection.setIndex(index);

            subsection.setPdfUrl(pdfUrl);
            subsection.setPdfFilename(pdfFilename);

            data = subsection.putToIntent();
        } else {
            // put extras for creating new
            data = new Intent();
            data.putExtra(Constant.EXTRA_TITLE, title);
            data.putExtra(Constant.EXTRA_DESCRIPTION, description);
            data.putExtra(Constant.EXTRA_INDEX, index);

            data.putExtra(Constant.EXTRA_PDF_URL, pdfUrl);
            data.putExtra(Constant.EXTRA_PDF_FILENAME, pdfFilename);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    // upload file to firebase storage
    private void uploadPdf() {
        if (pdfFilepath != null){
            String filename = editTextPdfFilename.getText().toString().trim();
            String chapterId = getIntent().getStringExtra(Constant.EXTRA_PARENT_ID);
            final StorageReference subsectionsRef = mStorageRef.child("subsections/").child(chapterId).child(filename);
            final AlertDialog uploadingDialog = Dialog.progressbarAction(this, Dialog.UPLOADING);
            subsectionsRef.putFile(pdfFilepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        subsectionsRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                uploadingDialog.cancel();
                                if (task.isSuccessful()) {
                                    pdfUrl = task.getResult().toString();
                                    Toast.makeText(CreateEditSubsectionActivity.this, "Pdf uploaded", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CreateEditSubsectionActivity.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        uploadingDialog.cancel();
                        Toast.makeText(CreateEditSubsectionActivity.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Pdf file has not been selected", Toast.LENGTH_SHORT).show();
        }
    }
}
