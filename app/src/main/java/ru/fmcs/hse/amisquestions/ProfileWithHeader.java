package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fmcs.hse.amisquestions.databinding.FragmentProfileWithHeaderBinding;

public class ProfileWithHeader extends Fragment {
    private FragmentProfileWithHeaderBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileWithHeaderBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }
}