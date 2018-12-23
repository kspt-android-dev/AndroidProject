package ru.alexandra.forum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import ru.alexandra.forum.objects.User;

public class ThemeRecyclerAdapter extends RecyclerView.Adapter {

    private List<Theme> themeList;
    private Context context;

    public ThemeRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void loadData() {
        themeList = DBConnector.loadThemes(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_recycler_theme, viewGroup, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ThemeViewHolder)viewHolder).bind(themeList.get(i));
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    private class ThemeViewHolder extends RecyclerView.ViewHolder {

        TextView loginView, headerView, textView;
        LinearLayout cardView;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.main_recycler_theme_main_space);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThemeActivity.class);
                    intent.putExtra("theme", themeList.get(getAdapterPosition()));
                    intent.putExtra("user", ((MainActivity)context).getUser());
                    context.startActivity(intent);
                }
            });
            loginView = itemView.findViewById(R.id.main_recycler_theme_login);
            headerView = itemView.findViewById(R.id.main_recycler_theme_header);
            textView = itemView.findViewById(R.id.main_recycler_theme_text);
        }

        public void bind(Theme theme){
            loginView.setText(theme.getUser().getName());
            headerView.setText(theme.getHeader());
            textView.setText(theme.getText());
        }

    }
}
