package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.fmcs.hse.database.Post;

public class PostItemView extends LinearLayout {
    private TextView postText;
    public TextView authorsName;
    public ImageView avatarImage;
    private Post postValue;
    private TextView date;
    public PostItemView(Context context, Post post) {
        super(context);
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
        postText.setMovementMethod(new ScrollingMovementMethod());
        authorsName = findViewById(R.id.NameView);
        avatarImage = findViewById(R.id.Avatar);
        date = findViewById(R.id.post_date);
        //хочу получить текст поста
        //postText.setText(postValue.getText());
        //хочу получить имя автора
        //authorsName.setText(postValue.getAuthor());
        //хочу получить аватарку...
        //хочу поличить дату
        //date.setText(postValue.getDate());
        date.setText("15 сентября 2022");

    }

    public void setPostText(String text) {
        postText.setText(text);
    }

    public void setAuthor(String author) {
        authorsName.setText(author);
    }
}
