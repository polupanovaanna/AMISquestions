package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.fmcs.hse.database.Post;

public class PostItemView extends LinearLayout {
    private TextView postText;
    private TextView authorsName;
    private ImageView avatarImage; //это пусть будет потом, пока не трогаю :(
    //private Post postValue;
    public PostItemView(Context context, Post post) {
        super(context);
        //postValue = post;
        initializeViews(context);
    }

    public PostItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post_item_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        postText = findViewById(R.id.postTextView);
        authorsName = findViewById(R.id.NameView);
        avatarImage = findViewById(R.id.Avatar);
        //хочу получить текст поста
        //postText.setText(postValue.getText());
        //хочу получить имя автора
        //authorsName.setText(postValue.getAuthor());
        //хочу получить аватарку...
    }

    public void setPostText(String text) {
        postText.setText(text);
    }

    public void setAuthor(String author) {
        authorsName.setText(author);
    }
}
