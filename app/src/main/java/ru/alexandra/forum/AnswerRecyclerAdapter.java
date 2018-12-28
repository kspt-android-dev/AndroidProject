package ru.alexandra.forum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.alexandra.forum.data.DBConnector;
import ru.alexandra.forum.objects.Answer;
import ru.alexandra.forum.objects.Theme;

public class AnswerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DBConnector dbConnector;

    private Theme theme;
    private List<Answer> answerList;

    public static final int TYPE_THEME = 1;
    public static final int TYPE_ANSWER = 2;

    public AnswerRecyclerAdapter(DBConnector dbConnector, Theme theme) {
        this.dbConnector = dbConnector;
        this.theme = theme;
    }

    public void loadData() {
        answerList = dbConnector.loadAnswers(theme.getId());
        notifyDataSetChanged();
    }

    public void addAnswer(Answer answer){
        answerList.add(answer);
        dbConnector.insertAnswer(answer);
        notifyItemInserted(getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_THEME;
        else
            return TYPE_ANSWER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case TYPE_THEME:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.main_recycler_theme, viewGroup, false);
                vh = new ThemeViewHolder(view);
                break;
            case TYPE_ANSWER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.theme_recycler_answer, viewGroup, false);
                vh = new AnswerViewHolder(view);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_THEME:
                ((ThemeViewHolder) viewHolder).bind(theme);
                break;
            case TYPE_ANSWER:
                ((AnswerViewHolder) viewHolder).bind(answerList.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size() + 1;
    }

    private class AnswerViewHolder extends RecyclerView.ViewHolder {

        TextView loginView, textView;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            loginView = itemView.findViewById(R.id.theme_recycler_answer_login);
            textView = itemView.findViewById(R.id.theme_recycler_answer_text);
        }

        public void bind(Answer answer){
            loginView.setText(answer.getUser().getName());
            textView.setText(answer.getText());
        }

    }

    private class ThemeViewHolder extends RecyclerView.ViewHolder {

        TextView loginView, headerView, textView;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            loginView = itemView.findViewById(R.id.main_recycler_theme_login);
            headerView = itemView.findViewById(R.id.main_recycler_theme_header);
            textView = itemView.findViewById(R.id.main_recycler_theme_text);
        }

        public void bind(Theme theme) {
            loginView.setText(theme.getUser().getName());
            headerView.setText(theme.getHeader());
            textView.setText(theme.getText());
        }

    }
}
