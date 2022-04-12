package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostViewHolder> {

    int numberItems;
    public static int adapterNumber = 0;

    public PostViewAdapter(int cnt) {
        numberItems = cnt;
        adapterNumber += 1;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.fragment_post_preview;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);

        PostViewHolder holder = new PostViewHolder(view);
        // TODO holder id, not

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewAdapter.PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView PostPreview;
        int id = -1;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            PostPreview = itemView.findViewById(R.id.number_of_post);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), Integer.toString(id), Toast.LENGTH_LONG - 1).show();
                }
            });
        }

        void bind(int position) {
            id = position;
            PostPreview.setText(String.valueOf(position) + " Добавим еще краткого описания в пару слов");
        }
    }
}
