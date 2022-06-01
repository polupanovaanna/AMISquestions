package ru.fmcs.hse.amisquestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;
import ru.fmcs.hse.database.Controller;
import ru.fmcs.hse.database.Tags;

public class NewPostsFragment extends Fragment {
    private FragmentNewPostsBinding mBinding;
    private RecyclerView list;
    private PostPreviewAdapter adapter;
    private TagsList tagsList;
    //private Tags tagsClass;
    private CheckBox sbd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentNewPostsBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.postList);
        tagsList = view.findViewById(R.id.tags_list);
        Controller.getAllTags((list) -> {
            tagsList.setTagsAdapter(list, adapter);
        });

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        list.setLayoutManager(manager);
        sbd = view.findViewById(R.id.sort_by_date);

        list.setHasFixedSize(false);
        sbd.setOnClickListener(view1 -> {
           adapter.sortChanged();
        });


        adapter = new PostPreviewAdapter(sbd);
        list.setAdapter(adapter);

        System.out.println("here: " + (list == null));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}