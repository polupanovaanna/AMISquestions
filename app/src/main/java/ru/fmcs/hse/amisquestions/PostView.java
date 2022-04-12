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
import android.widget.TextView;

import ru.fmcs.hse.amisquestions.databinding.FragmentCreateNewPostBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentMainPagesBinding;
import ru.fmcs.hse.amisquestions.databinding.FragmentPostViewBinding;

public class PostView<CustomAdapter> extends Fragment {

    private FragmentPostViewBinding mBinding;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PostItemView[] mDataset;
    private PostViewAdapter adapter;
    //PostItemView post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //тут хочется получить пост и комментарии к нему
        mDataset = new PostItemView[1];
        mDataset[0] = new PostItemView(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPostViewBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.postList);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setHasFixedSize(true);
        adapter = new PostViewAdapter(100);
        mRecyclerView.setAdapter(adapter);
    }

}