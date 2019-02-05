package com.julia.tag.records;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.julia.tag.R;

public class RecordsActivity extends AppCompatActivity {

    private RecordsAdapter recordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        final Animation animClick = AnimationUtils.loadAnimation(this, R.anim.click);
        findViewById(R.id.activity_records_btn_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animClick);
                onBackPressed();
            }
        });

        findViewById(R.id.activity_records_btn_delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animClick);
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);
                builder.setMessage(getResources().getString(R.string.records_dialog_delete_message))
                            .setPositiveButton(getResources().getString(R.string.dialog_yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recordsAdapter.deleteAll();
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.dialog_no),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create();
                builder.show();
                }
        });

        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.activity_records_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordsAdapter = new RecordsAdapter(this);
        recyclerView.setAdapter(recordsAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.records_divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }

    @Override
    protected void onDestroy() {
        recordsAdapter.onDestroyActivity();
        super.onDestroy();
    }
}
