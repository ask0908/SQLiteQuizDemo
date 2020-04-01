package com.example.sqlitequizdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitequizdemo.Common;
import com.example.sqlitequizdemo.Model.Category;
import com.example.sqlitequizdemo.QuestionActivity;
import com.example.sqlitequizdemo.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories)
    {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position)
    {
        holder.txt_category_name.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_category;
        TextView txt_category_name;

        public MyViewHolder(View view)
        {
            super(view);
            card_category = (CardView) view.findViewById(R.id.card_category);
            txt_category_name = (TextView) view.findViewById(R.id.txt_category_name);

            card_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "Click at category : " + categories.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                    Common.selectedCategory = categories.get(getAdapterPosition()); // Assign current Category
                    Intent intent = new Intent(context, QuestionActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

}
