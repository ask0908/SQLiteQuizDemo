package com.example.sqlitequizdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sqlitequizdemo.Adapter.AnswerSheetAdapter;
import com.example.sqlitequizdemo.DBHelper.DBHelper;
import com.example.sqlitequizdemo.Model.CurrentQuestion;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int time_play = Common.TOTAL_TIME;
    boolean isAnswerModeView = false;

    TextView txt_right_answer, txt_timer;

    RecyclerView answer_sheet_view;
    AnswerSheetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selectedCategory.getName());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                                 R.string.open,
                                                                 R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // First, we need take question from DB
        takeQuestion();

        if (Common.questionList.size() > 0)
        {
            // show Textview right answer and Textview Timer
            txt_right_answer = findViewById(R.id.txt_question_right);
            txt_timer = findViewById(R.id.txt_timer);

            txt_timer.setVisibility(View.VISIBLE);
            txt_right_answer.setVisibility(View.VISIBLE);

            txt_right_answer.setText(new StringBuilder(String.format("%d/%d", Common.right_answer_count,
                                                                     Common.questionList.size())));

            countTimer();

            // View
            answer_sheet_view = findViewById(R.id.grid_answer);
            answer_sheet_view.setHasFixedSize(true);
            if (Common.questionList.size() > 5) // If question List have size > 5, we will sperate 2 rows
            {
                answer_sheet_view.setLayoutManager(new GridLayoutManager(this, Common.questionList.size() / 2));
            }
            adapter = new AnswerSheetAdapter(this, Common.answerSheetList);
            answer_sheet_view.setAdapter(adapter);
        }

    }

    private void countTimer()
    {
        if (Common.countDownTimer == null)
        {
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long l)
                {
                    txt_timer.setText(String.format("%02d:%02d",
                                                    TimeUnit.MILLISECONDS.toMinutes(l),
                                                    TimeUnit.MILLISECONDS.toSeconds(l) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                    time_play -= 1000;
                }

                @Override
                public void onFinish()
                {
                    // finish game
                }
            }.start();
        }
        else
        {
            Common.countDownTimer.cancel();
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long l)
                {
                    txt_timer.setText(String.format("%02d:%02d",
                                                    TimeUnit.MILLISECONDS.toMinutes(l),
                                                    TimeUnit.MILLISECONDS.toSeconds(l) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                    time_play -= 1000;
                }

                @Override
                public void onFinish()
                {
                    // finish game
                }
            }.start();
        }
    }

    private void takeQuestion()
    {
        Common.questionList = DBHelper.getInstance(this).getQuestionByCategory(Common.selectedCategory.getId());
        if (Common.questionList.size() == 0)
        {
            // If no question
            new MaterialStyledDialog.Builder(this)
                    .setTitle("이런")
                    .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                    .setDescription("We don't have any question in this " + Common.selectedCategory.getName() + " category")
                    .setPositiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                        {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }
        else
        {
            if (Common.answerSheetList.size() > 0)
            {
                Common.answerSheetList.clear();
            }
            // Gen answerSheet item from question
            // 30 question = 30 answer sheet item
            // 1 question = 1 answer sheet item

            for (int i = 0; i < Common.questionList.size(); i++)
            {
                // Because we need take index of question in list, so we will use for i

                // Default all answer is no answer
                Common.answerSheetList.add(new CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return false;
    }

    @Override
    protected void onDestroy()
    {
        if (Common.countDownTimer != null)
        {
            Common.countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
