package com.example.sqlitequizdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitequizdemo.Common;
import com.example.sqlitequizdemo.Model.CurrentQuestion;
import com.example.sqlitequizdemo.R;

import java.util.List;

public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.MyViewHolder> {

    Context context;
    // CurrentQuestion : it contains Index of question in question list, Answer state of question(no Answer, wrong Answer,
    // right Answer)
    List<CurrentQuestion> currentQuestionList;

    public AnswerSheetAdapter(Context context, List<CurrentQuestion> currentQuestionList)
    {
        this.context = context;
        this.currentQuestionList = currentQuestionList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View question_item;

        public MyViewHolder(View view)
        {
            super(view);
            question_item = view.findViewById(R.id.question_item);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_grid_answer_sheet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        if (currentQuestionList.get(position).getType() == Common.ANSWER_TYPE.RIGHT_ANSWER)
        {
            holder.question_item.setBackgroundResource(R.drawable.grid_question_right_answer);
        }
        else if (currentQuestionList.get(position).getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
        {
            holder.question_item.setBackgroundResource(R.drawable.grid_question_wrong_answer);
        }
        else
        {
            holder.question_item.setBackgroundResource(R.drawable.grid_question_no_answer);
        }
    }

    @Override
    public int getItemCount()
    {
        return currentQuestionList.size();
    }

}
