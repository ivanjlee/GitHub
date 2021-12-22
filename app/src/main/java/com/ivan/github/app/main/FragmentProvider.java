package com.ivan.github.app.main;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.ivan.github.R;
import com.ivan.github.app.feed.FeedFragment;
import com.ivan.github.app.notification.NotificationFragment;
import com.ivan.github.app.settings.SettingsFragment;

/**
 * com.ivan.github.app.main
 * <p>
 *
 * @author lijun on 2021-12-23 00:29
 * @since v1.0
 */
public class FragmentProvider {

    static Fragment provideFragment(@IdRes int id) {
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
