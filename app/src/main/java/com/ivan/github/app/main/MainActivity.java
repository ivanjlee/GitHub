package com.ivan.github.app.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.log.Logan;
import com.ivan.github.R;
import com.ivan.github.account.Account;
import com.ivan.github.account.model.User;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.feed.FeedFragment;
import com.ivan.github.app.login.SplashActivity;
import com.ivan.github.app.notification.NotificationFragment;
import com.ivan.github.app.settings.SettingsFragment;
import com.ivan.github.util.BitmapUtils;
import com.ivan.github.widget.BridgeActionProvider;

/**
 * Home Page of the App
 * @author Ivan
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private ViewGroup mProfileBackground;
    private ImageView mIVAvatar;
    private TextView mTVUsername;
    private TextView mTVEmail;
    private BridgeActionProvider mBridgeActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = this.findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mProfileBackground = headerView.findViewById(R.id.ll_profile_background);
        mIVAvatar = headerView.findViewById(R.id.iv_avatar);
        mTVUsername = headerView.findViewById(R.id.tv_username);
        mTVEmail = headerView.findViewById(R.id.tv_email);

        User user = Account.getInstance().getUser();
        if (user != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(user.getAvatarUrl())
                    .into(new CustomViewTarget<View, Bitmap>(mProfileBackground) {
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {

                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Bitmap bitmap;
                            try {
                                bitmap = BitmapUtils.renderScriptBlur(MainActivity.this, resource, 5, 1/64f);
                            } catch (Exception exception) {
                                Logan.e(TAG, "failed to blur image", exception);
                                return;
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                mProfileBackground.setBackground(new BitmapDrawable(getResources(), bitmap));
                            } else {
                                mProfileBackground.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                            }
                        }

                        @Override
                        protected void onResourceCleared(@Nullable Drawable placeholder) {

                        }
                    });
            Glide.with(this)
                    .load(user.getAvatarUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mIVAvatar);
            mTVUsername.setText(user.getName());
            mTVEmail.setText(user.getEmail());
        } else {
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            this.finish();
        }
        switchFragment(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_notification);
        mBridgeActionProvider = (BridgeActionProvider) MenuItemCompat.getActionProvider(menuItem);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mBridgeActionProvider.setIcon(R.drawable.ic_menu_notification);
        mBridgeActionProvider.setBridgeRes(R.drawable.ic_dot_blue);
//        mBridgeActionProvider.setBridgeNum(99);
        mBridgeActionProvider.setOnClickListener(v -> Toast.makeText(MainActivity.this, "notification action clicked", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switchFragment(id);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(@IdRes int id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(id);
        if (fragment == null) {
            fragment = newFragment(id);
        }
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }

    private @Nullable Fragment newFragment(@IdRes int id) {
        Fragment fragment = null;
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_home) {
            fragment = FeedFragment.newInstance();
        } else if (id == R.id.nav_notification) {
            fragment = NotificationFragment.newInstance();
        } else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.newInstance();
        }
        return fragment;
    }
}
