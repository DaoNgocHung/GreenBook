package com.anhhung.greenbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.CommentItem;

import com.anhhung.greenbook.R;

import java.util.List;

public class CommentBookAdapter extends RecyclerView.Adapter<CommentBookAdapter.CommentBookViewHolder> {

    Context mContext;
    List<CommentItem> mData;

    public CommentBookAdapter(Context mContext, List<CommentItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;

        layout = LayoutInflater.from(mContext).inflate(R.layout.item_comment_book,parent,false);

        return new CommentBookViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentBookViewHolder holder, int position) {

        //bind Data here

        holder.txtUserCommentBook.setText(mData.get(position).getTitle());
        holder.txtContentCommentBook.setText(mData.get(position).getContent());
        holder.txtTimeCommentBook.setText(mData.get(position).getDate());
        holder.imgAvatarCommentBook.setImageResource(mData.get(position).getAvatar());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class CommentBookViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserCommentBook, txtContentCommentBook, txtTimeCommentBook;
        ImageView imgAvatarCommentBook;

        public CommentBookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserCommentBook = itemView.findViewById(R.id.txtUserCommentBook);
            txtContentCommentBook = itemView.findViewById(R.id.txtContentCommentBook);
            txtTimeCommentBook = itemView.findViewById(R.id.txtTimeCommentBook);
            imgAvatarCommentBook = itemView.findViewById(R.id.imgAvatarCommentBook);

        }
    }
}
