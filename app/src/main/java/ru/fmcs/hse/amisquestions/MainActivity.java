package ru.fmcs.hse.amisquestions;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// import com.example.myapplication.databinding.ActivityMainBinding;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.fmcs.hse.amisquestions.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initFields();
        initFunc();
    }

    private void initFunc() {
        setSupportActionBar(mToolbar);
        getSupportFragmentManager()
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
                .withActivity(this)
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
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if( position == 1) {
                            // RecyclerView list = findViewById(R.id.postList);
                            // list.setHasFixedSize(true);
                            // PostPreviewAdapter adapter = new PostPreviewAdapter(100);
                            // list.setAdapter(adapter);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, new NewPostsFragment())
                                    .commit();
                            Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        }else if (position == 2) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dataContainer, new SettingsFragment())
                                    .commit();
                            Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG - 1).show();

                        }
                        // Toast.makeText(getApplicationContext(), Integer.toString(position + 5), Toast.LENGTH_LONG - 1).show();
                        return false;
                    }
                })
                .build();
    }

    private void createHeader() {
        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Test Name")
                                .withEmail("mail@mail.ru")
                ).build();
    }

    private void initFields() {
        mToolbar = mBinding.mainToolbar;
    }
}