package com.example.sqlitequizdemo;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* We need write simple Decoration class for our RecyclerView to make it beautiful */
public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceDecoration(int space)
    {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.left = outRect.right = outRect.bottom = outRect.top = space;
    }
}
