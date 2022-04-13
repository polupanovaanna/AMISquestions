package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.CommentViewHolder> {

    int numberItems;
    public static int adapterNumber = 0;

    public CommentViewAdapter(int cnt) {
        numberItems = cnt;
        adapterNumber += 1;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.fragment_post_comments;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        CommentViewHolder holder = new CommentViewHolder(view);
        // TODO holder id, not

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView CommentView;
        int id = -1;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentView = itemView.findViewById(R.id.comment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), Integer.toString(id), Toast.LENGTH_LONG - 1).show();
                }
            });
        }

        void bind(int position) {
            id = position;
            CommentView.setText(String.valueOf(position) + " Добавим еще краткого описания в пару слов");
        }
    }
}
