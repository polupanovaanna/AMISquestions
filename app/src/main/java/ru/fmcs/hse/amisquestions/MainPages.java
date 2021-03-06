package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ru.fmcs.hse.amisquestions.databinding.FragmentMainPagesBinding;

public class MainPages extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    private FragmentMainPagesBinding mBinding;
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private Toolbar mToolbar;
    private NewPostsFragment newPostsFragment;
    private FloatingActionButton FAB;
    private NavController navigation;
    View.OnClickListener s;
    private FirebaseAuth mFirebaseAuth;

    public String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return "err";
    }

    public String getMail() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getEmail();
        }
        return "err";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainPagesBinding.inflate(getLayoutInflater());
        newPostsFragment = new NewPostsFragment();
        mFirebaseAuth = FirebaseAuth.getInstance();
        s = Navigation.createNavigateOnClickListener(R.id.mainPages_to_createNewPost);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initFields();
        initFunc();
    }

    private void initFunc() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbar);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataContainer, newPostsFragment)
                .commit();

        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(getActivity())
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withSelectedItem(-1)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(100)
                                .withIconTintingEnabled(true)
                                .withName("?????? ??????????????")
                                .withSelectable(false),

                        new PrimaryDrawerItem()
                                .withIdentifier(101)
                                .withIconTintingEnabled(true)
                                .withName("?????????? ??????????")
                                .withSelectable(false),

                        new PrimaryDrawerItem()
                                .withIdentifier(102)
                                .withIconTintingEnabled(true)
                                .withName("????????????????")
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (position == 1) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.dataContainer, new MyProfileFragment())
                                .commit();
                        // Toast.makeText(getActivity().getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                    } else if (position == 2) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.dataContainer, newPostsFragment)
                                .commit();
                        // Toast.makeText(getActivity().getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                    } else if (position == 3) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.dataContainer, new SubscribeFragment())
                                .commit();
                        // Toast.makeText(getActivity().getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                    } else if (position == 4) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.dataContainer, new SettingsFragment())
                                .commit();
                        // Toast.makeText(getActivity().getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();
                    }
                    return false;
                })
                .build();
    }

    private void createHeader() {
        mHeader = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(getUserName())
                                .withEmail(getMail())
                ).withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.dataContainer, new MyProfileFragment())
                                .commit();
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();
    }

    private void initFields() {
        mToolbar = mBinding.mainToolbar;

        FAB = mBinding.getRoot().findViewById(R.id.create_new_post);
        FAB.setOnClickListener(s);
    }
}