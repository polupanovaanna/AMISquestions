package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PostItemView extends LinearLayout {
    private TextView postText;
    private TextView authorsName;
    private ImageView avatarImage; //это пусть будет потом, пока не трогаю :(

    public PostItemView(Context context) {
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
        inflater.inflate(R.layout.markdown_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        postText = findViewById(R.id.postTextView);
        authorsName = findViewById(R.id.NameView);
        avatarImage = findViewById(R.id.Avatar);
        //хочу получить текст поста
        postText.setText("Тестирую текст у поста");
        //хочу получить имя автора
        authorsName.setText("Идеал Идеалов");
        //хочу получить аватарку...

    }
}
