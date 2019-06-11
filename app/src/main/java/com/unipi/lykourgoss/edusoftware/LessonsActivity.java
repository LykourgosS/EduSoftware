package com.unipi.lykourgoss.edusoftware;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.unipi.lykourgoss.edusoftware.Model.Lesson;
import com.unipi.lykourgoss.edusoftware.ViewHolder.LessonViewHolder;

public class LessonsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private DatabaseReference dbRef;

    //to display Lessons
    private FirebaseRecyclerOptions<Lesson> options;
    private FirebaseRecyclerAdapter<Lesson, LessonViewHolder> adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        //get database reference in firebase
        dbRef = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.fab_create_lesson).setOnClickListener(this);

        getSupportActionBar().setTitle("Lessons");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO make all lesson items
        //editTextTime.setFocusable(false);
        //editTextTime.setClickable(true);

        //Init RecyclerView
        recyclerView = findViewById(R.id.recyclerView_lessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_lesson:
                startActivity(new Intent(getApplicationContext(), CreateLessonActivity.class));
//                createLessonDialog();
                break;
        }
    }


    private void loadListFromFirebase() {

        Query keyQuery = dbRef.child(Lesson.LESSONS_REF).orderByChild(Lesson.PROP_ID);

        DatabaseReference dataRef = dbRef.child(Lesson.LESSONS_REF);

        // >>> initialize the query for the recyclerView's adapter
        // keyQuery - the Firebase location containing the list of keys to be found in dataRef
        // dataRef - the Firebase location to watch for data changes. Each key found at
        // keyRef's location represents a list item.
        options = new FirebaseRecyclerOptions.Builder<Lesson>()
                .setIndexedQuery(keyQuery, dataRef, Lesson.class)
                .build();

        //Initialize the adapter
        adapter = new FirebaseRecyclerAdapter<Lesson, LessonViewHolder>(options) {

            @NonNull
            @Override
            public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_lesson, viewGroup, false);
                return new LessonViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull LessonViewHolder holder, int position, @NonNull final Lesson model) {
                holder.textViewName.setText(model.getName());
                holder.textViewDateTime.setText(model.getDateTime());
                holder.textViewDuration.setText(durationOptions[model.getDuration()]);
                holder.textViewNotificationStatus.setText(model.notificationStatus());
                holder.textViewClickForUsersList.setText(Html.fromHtml("<u>Meeting's participants</u>"));

                loadClickListenerForParticipants(model.getMeetingId(), model.getName());

                //onClick method for meeting's participants
                holder.textViewClickForUsersList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO see if ok without the following line
//                        loadClickListenerForParticipants(model.getMeetingId(), model.getName());
                        showMeetingParticipants(model.getName(), emailListOfClickedMeeting);
                    }
                });

                //onClick method to edit meeting
                holder.textViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadClickListenerForParticipants(model.getMeetingId(), model.getName());
                        editOrAddMeeting(model.getMeetingId());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @Override
            public void onDataChanged() {
                progressBar.setVisibility(View.VISIBLE);
                super.onDataChanged();
                //when there are no meetings show a TextView "No meetings to show" instead of the RecyclerView
                if (adapter.getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoMeetings.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoMeetings.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void createLessonDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(LessonsActivity.this);

        //use create eduEntity dialog layout
        LayoutInflater inflater = LayoutInflater.from(LessonsActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_create_edu_entity, null);
        builder.setView(dialogView);

        //in placeholder linearLayout use appropriate fields layout,
        //depends on the type of the EduEntity
        LinearLayout layout = dialogView.findViewById(R.id.linearLayout_placeholder);
        View fields = inflater.inflate(R.layout.fields_lesson, (ViewGroup) findViewById(R.id.linearLayout_placeholder));

        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        //save button -> creates and uploads to firebase the new EduEntity
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lesson lesson = new Lesson();
                lesson.create(LessonsActivity.this);
                dialog.cancel();
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
        dialog.show();
    }

//    private void loadLessonsFromFirebase() {
//
//        Query query = databaseReference.child(Lesson.LESSONS_REF);
//
////        DatabaseReference dataRef = databaseReference.child(Lesson.LESSONS_REF);
//
//        // >>> initialize the query for the recyclerView's adapter
//        // keyQuery - the Firebase location containing the list of keys to be found in dataRef
//        // dataRef - the Firebase location to watch for data changes. Each key found at
//        // keyRef's location represents a list item.
//        FirebaseRecyclerOptions<Lesson> options = new FirebaseRecyclerOptions.Builder<Lesson>()
//                .setQuery(query, Lesson.class)
//                .build();
//
//        //Initialize the adapter
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Lesson, LessonViewHolder>(options){
//
//            @NonNull
//            @Override
//            public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.message for each item
//                View view = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.item_lesson, viewGroup, false);
//
//                return new LessonViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull LessonViewHolder holder, int position, @NonNull Lesson model) {
//
//            }
//        };
//
//
//        adapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(options) {
//
//            @NonNull
//            @Override
//            public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View itemView = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.meeting_item, viewGroup, false);
//                return new MeetingViewHolder(itemView);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull MeetingViewHolder holder, int position, @NonNull final Meeting model) {
//                holder.textViewName.setText(model.getName());
//                holder.textViewDateTime.setText(model.getDateTime());
//                holder.textViewDuration.setText(durationOptions[model.getDuration()]);
//                holder.textViewNotificationStatus.setText(model.notificationStatus());
//                holder.textViewClickForUsersList.setText(Html.fromHtml("<u>Meeting's participants</u>"));
//
//                loadClickListenerForParticipants(model.getMeetingId(), model.getName());
//
//                //onClick method for meeting's participants
//                holder.textViewClickForUsersList.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //TODO see if ok without the following line
////                        loadClickListenerForParticipants(model.getMeetingId(), model.getName());
//                        showMeetingParticipants(model.getName(), emailListOfClickedMeeting);
//                    }
//                });
//
//                //onClick method to edit meeting
//                holder.textViewEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loadClickListenerForParticipants(model.getMeetingId(), model.getName());
//                        editOrAddMeeting(model.getMeetingId());
//                    }
//                });
//            }
//
//            @Override
//            public int getItemCount() {
//                return super.getItemCount();
//            }
//
//            @Override
//            public void onDataChanged() {
//                progressBar.setVisibility(View.VISIBLE);
//                super.onDataChanged();
//                //when there are no meetings show a TextView "No meetings to show" instead of the RecyclerView
//                if (adapter.getItemCount() == 0) {
//                    recyclerView.setVisibility(View.GONE);
//                    textViewNoMeetings.setVisibility(View.VISIBLE);
//                } else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    textViewNoMeetings.setVisibility(View.GONE);
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(@NonNull DatabaseError error) {
//                super.onError(error);
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
}
