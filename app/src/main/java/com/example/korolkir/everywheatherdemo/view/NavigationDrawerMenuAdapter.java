package com.example.korolkir.everywheatherdemo.view;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.korolkir.everywheatherdemo.R;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by korolkir on 22.08.16.
 */
public class NavigationDrawerMenuAdapter extends RecyclerView.Adapter<NavigationDrawerMenuAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Override
    public NavigationDrawerMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
           //TO DO add menu items
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerMenuAdapter.ViewHolder holder, int position) {
        if(holder.holderId == 1) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://youtube.com"))
                    .build();
            holder.shareButton.setShareContent(content);
        } else {
            if(AccessToken.getCurrentAccessToken() != null) {
                holder.name.setVisibility(View.VISIBLE);
                holder.shareButton.setVisibility(View.VISIBLE);
                holder.profilePictureView.setProfileId(AccessToken.getCurrentAccessToken().getUserId());
                Profile.fetchProfileForCurrentAccessToken();
                if(Profile.getCurrentProfile() != null) {
                    holder.name.setText(Profile.getCurrentProfile().getName());
                }
            } else {
                holder.shareButton.setVisibility(View.INVISIBLE);
                holder.name.setVisibility(View.GONE);
                holder.profilePictureView.setProfileId(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;
        @Nullable
        @BindView(R.id.login_button)
        LoginButton loginButton;
        @Nullable
        @BindView(R.id.share_button)
        ShareButton shareButton;
        @Nullable
        @BindView(R.id.profile_picture)
        ProfilePictureView profilePictureView;
        @Nullable
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(View itemView,int ViewType) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            if(ViewType == TYPE_ITEM) {
                holderId = 1;
            } else {
                holderId = 0;
            }
        }
    }
}
