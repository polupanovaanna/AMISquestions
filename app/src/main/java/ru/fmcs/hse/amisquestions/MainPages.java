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
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainPagesBinding.inflate(getLayoutInflater());
        newPostsFragment = new NewPostsFragment();

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
        ((AppCompatActivity) requireActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataContainer, new SettingsFragment())
                .commit();
        /*
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataContainer, new NewPostsFragment())
                .commit();
         */


        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(((AppCompatActivity) getActivity()))
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withSelectedItem(-1)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(100)
                                .withIconTintingEnabled(true)
                                .withName("Новые посты")
                                .withSelectable(false),

                        new PrimaryDrawerItem()
                                .withIdentifier(101)
                                .withIconTintingEnabled(true)
                                .withName("Настройки")
                                .withSelectable(false),

                        new PrimaryDrawerItem()
                                .withIdentifier(102)
                                .withIconTintingEnabled(true)
                                .withName("Кнопочка")
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            ((AppCompatActivity) getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, newPostsFragment)
                                    .commit();
                            Toast.makeText(((AppCompatActivity) getActivity()).getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        } else if (position == 2) {
                            ((AppCompatActivity) getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, new SettingsFragment())
                                    .commit();
                            Toast.makeText(((AppCompatActivity) getActivity()).getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        } else if (position == 3) {
                            ((AppCompatActivity) getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, new TestButton())
                                    .commit();
                        }
                        // Toast.makeText(getApplicationContext(), Integer.toString(position + 5), Toast.LENGTH_LONG - 1).show();
                        return false;
                    }
                })
                .build();
    }

    private void createHeader() {
        mHeader = new AccountHeaderBuilder()
                .withActivity(((AppCompatActivity) getActivity()))
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Test Name")
                                .withEmail("mail@mail.ru")
                ).build();
    }

    private void initFields() {
        mToolbar = mBinding.mainToolbar;

        //navigation = Navigation.findNavController(this, R.id.main_toolbar);


        FAB = mBinding.getRoot().findViewById(R.id.create_new_post);
        FAB.setOnClickListener(s);
    }
}