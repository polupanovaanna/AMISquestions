package ru.fmcs.hse.amisquestions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

// import com.example.myapplication.databinding.FragmentNewPostsBinding;

import ru.fmcs.hse.amisquestions.databinding.FragmentNewPostsBinding;

public class NewPostsFragment extends Fragment {
    private FragmentNewPostsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNewPostsBinding.inflate(getLayoutInflater());
        // return inflater.inflate(R.layout.fragment_new_posts, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}