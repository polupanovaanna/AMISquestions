package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executors;

import io.noties.markwon.Markwon;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;
import io.noties.markwon.ext.latex.JLatexMathPlugin;
import io.noties.markwon.inlineparser.MarkwonInlineParserPlugin;

public class MarkdownTextView extends LinearLayout {

    private EditText markdownEditText;
    private TextView markdownTextView;
    Markwon markwon;
    MarkwonEditor editor;

    public MarkdownTextView(Context context) {
        super(context);
        initializeViews(context);
    }

    public void setText(String text) {
        markdownEditText.setText(text);
    }

    public String getText() {
        return markdownEditText.getText().toString();
    }

    public MarkdownTextView(Context context, @Nullable AttributeSet attrs) {
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

        markdownTextView = findViewById(R.id.textView);
        markdownEditText = findViewById(R.id.editText);

        markwon = Markwon.builder(getContext())
                .usePlugin(MarkwonInlineParserPlugin.create())
                .usePlugin(JLatexMathPlugin.create(markdownEditText.getTextSize(), new JLatexMathPlugin.BuilderConfigure() {
                    @Override
                    public void configureBuilder(@NonNull JLatexMathPlugin.Builder builder) {
                        builder.inlinesEnabled(true);
                    }
                }))
                .build();

        editor = MarkwonEditor.create(markwon);

        markdownEditText.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(editor));
        JLatexMathPlugin.create(markdownTextView.getTextSize());

        JLatexMathPlugin.create(markdownEditText.getTextSize(), builder -> {
            builder.inlinesEnabled(true);
            builder.blocksLegacy(true);
            builder.blocksEnabled(true);
            builder.errorHandler((latex, error) -> null);
            builder.executorService(Executors.newCachedThreadPool());
        });

        markdownEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                markwon.setMarkdown(markdownTextView, markdownEditText.getText().toString());
            }
        });

    }

}
