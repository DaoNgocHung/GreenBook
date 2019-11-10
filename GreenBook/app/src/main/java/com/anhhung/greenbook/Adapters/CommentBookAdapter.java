package com.anhhung.greenbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhhung.greenbook.Models.CommentsModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CommentBookAdapter extends FirestoreRecyclerAdapter<CommentsModel, CommentBookAdapter.CommentBookViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CommentBookAdapter(@NonNull FirestoreRecyclerOptions<CommentsModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull CommentBookViewHolder holder, int position, @NonNull CommentsModel model) {
        holder.txtUserCommentBook.setText(model.getHoTen());
        holder.txtContentCommentBook.setText(model.getNoidungBL());
        holder.txtTimeCommentBook.setText(model.getTgBinhLuan().toString());
        Glide.with(holder.itemView)
                .load(model.getHinhDaiDien())
                .into(holder.imgAvatarCommentBook);
    }

    @NonNull
    @Override
    public CommentBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_book,parent,false);
        return new CommentBookAdapter.CommentBookViewHolder(view);
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
