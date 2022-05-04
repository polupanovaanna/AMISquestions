package ru.fmcs.hse.amisquestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// import com.example.myapplication.databinding.FragmentNewPostsBinding;

import java.util.Objects;

import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;

public class NewPostsFragment extends Fragment {
    private FragmentNewPostsBinding mBinding;
    private RecyclerView list;
    private PostPreviewAdapter adapter;

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
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        list.setLayoutManager(manager);

        list.setHasFixedSize(false);
        adapter = new PostPreviewAdapter();
        list.setAdapter(adapter);

        System.out.println("here: " + (list == null));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}