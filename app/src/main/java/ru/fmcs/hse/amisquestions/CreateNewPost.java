package ru.fmcs.hse.amisquestions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.fmcs.hse.amisquestions.databinding.FragmentCreateNewPostBinding;

public class CreateNewPost extends Fragment {

    private FragmentCreateNewPostBinding mBinding;
    Toolbar mToolbar;
    private Drawer mDrawer;
    MarkdownTextView MTV;
    Button postButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_create_new_post, container, false);
        mBinding = FragmentCreateNewPostBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar2);
        MTV = view.findViewById(R.id.markdown_text);

        // mBinding.toolbar2;
        // mToolbar.setDisplayHomeAsUpEnabled(true);
        // mToolbar.dismissPopupMenus();
        mToolbar.setTitle("Simple Sample");
        //getActivity().setActionBar(mToolbar);
        postButton = view.findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String post = MTV.getText();
                System.out.println(post);
            }
        });


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        // mToolbar.setNavigationIcon(android.R.drawable.);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setTitle("MyTitle");

        // createDrawer();
    }

    private ActionBar getSupportActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void createDrawer() {

        mDrawer = new DrawerBuilder()
                .withActivity(((AppCompatActivity) getActivity()))
                .withToolbar(mToolbar)
                /*
                .withActionBarDrawerToggle(true)
                .withSelectedItem(-1)
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
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            ((AppCompatActivity)getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    // .replace(R.id.dataContainer, newPostsFragment)
                                    .commit();
                            Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        } else if (position == 2) {
                            ((AppCompatActivity)getActivity()).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, new SettingsFragment())
                                    .commit();
                            Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        }
                        // Toast.makeText(getApplicationContext(), Integer.toString(position + 5), Toast.LENGTH_LONG - 1).show();
                        return false;
                    }
                })

                 */
                .build();
    }
}