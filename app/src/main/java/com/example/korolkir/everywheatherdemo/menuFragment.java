package com.example.korolkir.everywheatherdemo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by korolkir on 06.08.16.
 */
public class MenuFragment extends BaseFragment {

    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.share_button)
    ShareButton shareButton;

    @Override
    protected int getViewId() {
        return R.layout.drawer_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.gameloft.android.ANMP.GloftDOHM"))
                .build();
        shareButton.setShareContent(content);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
