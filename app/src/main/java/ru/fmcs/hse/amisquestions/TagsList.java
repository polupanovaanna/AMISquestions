package ru.fmcs.hse.amisquestions;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import ru.fmcs.hse.database.Tags;

public class TagsList extends LinearLayout {

    TextView textView;
    boolean[] selectedPosition;

    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray;


    public TagsList(Context context) {
        super(context);
        initializeViews(context);
    }

    public TagsList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public void setTags(String[] langArray) {
        this.langArray = langArray;
        selectedPosition = new boolean[langArray.length];
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tags_list, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TextView textView = findViewById(R.id.textView);
        //selectedPosition = new boolean[langArray.length]; //TODO переделать

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select tags");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedPosition, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            langList.add(i);
                            Collections.sort(langList);
                        } else {
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < langList.size(); j++) {
                            stringBuilder.append(langArray[langList.get(j)]);
                            if (j != langList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        textView.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedPosition.length; j++) {
                            selectedPosition[j] = false;
                            langList.clear();
                            textView.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

    }

    HashSet<String> getMarkedTags() {
        HashSet<String> ans = new HashSet<>();
        for (int i = 0; i < selectedPosition.length; i++) {
            if (selectedPosition[i]) {
                ans.add(langArray[i]);
            }
        }
        return ans;
    }

}
