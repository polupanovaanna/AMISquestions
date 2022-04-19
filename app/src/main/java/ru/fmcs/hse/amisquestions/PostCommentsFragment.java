package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentPostCommentsBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentPostViewBinding;


public class PostCommentsFragment<CustomAdapter> extends Fragment {

    private FragmentPostCommentsBinding mBinding;

    protected RecyclerView mRecyclerView;
    //protected RecyclerView.LayoutManager mLayoutManager;
    //protected PostItemView[] mDataset;
    private CommentViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPostCommentsBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.RecyclerViewComments);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new CommentViewAdapter(100);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}