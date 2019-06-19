package com.unipi.lykourgoss.edusoftware.CodingFlowExample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipi.lykourgoss.edusoftware.Dialog;
import com.unipi.lykourgoss.edusoftware.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 17,June,2019.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessons = new ArrayList<>();
    private static OnItemClickListener listener;
    private static Context context;

    public LessonAdapter(Context context) {
        super();
        this.context = context;
    }

    @NonNull
    @Override
    public LessonAdapter.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson_room, parent, false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        Lesson currentLesson = lessons.get(position);
        holder.setLesson(currentLesson);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    public Lesson getLessonAt(int position) {
        return lessons.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Lesson lesson);
    }

    class LessonViewHolder extends RecyclerView.ViewHolder {

        private Lesson lesson;

        private TextView textViewTitle;
        private TextView textViewIndex;
        private TextView textViewDescription;
        private TextView textViewLastModifiedBy;

        public LessonViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_lesson_title);
            textViewIndex = itemView.findViewById(R.id.text_view_lesson_index);
            textViewDescription = itemView.findViewById(R.id.text_view_lesson_description);
            textViewLastModifiedBy = itemView.findViewById(R.id.text_view_lesson_lastModifiedBy);

            textViewDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog.showDescription(context, lesson.getTitle(), lesson.getDescription());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // compare it to RecyclerView.NO_POSITION to make sure that it is still valid
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(lessons.get(position));
                    }
                }
            });
        }

        public void setLesson(Lesson lesson) {
            this.lesson = lesson;
            textViewTitle.setText(lesson.getTitle());
            textViewIndex.setText(String.valueOf(lesson.getIndex()));
            textViewDescription.setText(lesson.getDescription());
            textViewLastModifiedBy.setText(lesson.getLastModifiedBy());
        }
    }
}
