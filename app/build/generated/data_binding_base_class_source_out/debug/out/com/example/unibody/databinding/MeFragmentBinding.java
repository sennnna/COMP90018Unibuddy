// Generated by view binder compiler. Do not edit!
package com.example.unibody.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unibody.R;
import com.example.unibody.me.fragment.view.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MeFragmentBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CircleImageView headIv;

  @NonNull
  public final TextView itemAlbums;

  @NonNull
  public final TextView itemFriends;

  @NonNull
  public final TextView itemSent;

  @NonNull
  public final ImageView ivStatus;

  @NonNull
  public final TextView tvAge;

  @NonNull
  public final TextView tvDesc;

  @NonNull
  public final TextView tvId;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvStatus;

  private MeFragmentBinding(@NonNull LinearLayout rootView, @NonNull CircleImageView headIv,
      @NonNull TextView itemAlbums, @NonNull TextView itemFriends, @NonNull TextView itemSent,
      @NonNull ImageView ivStatus, @NonNull TextView tvAge, @NonNull TextView tvDesc,
      @NonNull TextView tvId, @NonNull TextView tvName, @NonNull TextView tvStatus) {
    this.rootView = rootView;
    this.headIv = headIv;
    this.itemAlbums = itemAlbums;
    this.itemFriends = itemFriends;
    this.itemSent = itemSent;
    this.ivStatus = ivStatus;
    this.tvAge = tvAge;
    this.tvDesc = tvDesc;
    this.tvId = tvId;
    this.tvName = tvName;
    this.tvStatus = tvStatus;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MeFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MeFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.me_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MeFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.head_iv;
      CircleImageView headIv = ViewBindings.findChildViewById(rootView, id);
      if (headIv == null) {
        break missingId;
      }

      id = R.id.item_albums;
      TextView itemAlbums = ViewBindings.findChildViewById(rootView, id);
      if (itemAlbums == null) {
        break missingId;
      }

      id = R.id.item_friends;
      TextView itemFriends = ViewBindings.findChildViewById(rootView, id);
      if (itemFriends == null) {
        break missingId;
      }

      id = R.id.item_sent;
      TextView itemSent = ViewBindings.findChildViewById(rootView, id);
      if (itemSent == null) {
        break missingId;
      }

      id = R.id.iv_status;
      ImageView ivStatus = ViewBindings.findChildViewById(rootView, id);
      if (ivStatus == null) {
        break missingId;
      }

      id = R.id.tv_age;
      TextView tvAge = ViewBindings.findChildViewById(rootView, id);
      if (tvAge == null) {
        break missingId;
      }

      id = R.id.tv_desc;
      TextView tvDesc = ViewBindings.findChildViewById(rootView, id);
      if (tvDesc == null) {
        break missingId;
      }

      id = R.id.tv_id;
      TextView tvId = ViewBindings.findChildViewById(rootView, id);
      if (tvId == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_status;
      TextView tvStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvStatus == null) {
        break missingId;
      }

      return new MeFragmentBinding((LinearLayout) rootView, headIv, itemAlbums, itemFriends,
          itemSent, ivStatus, tvAge, tvDesc, tvId, tvName, tvStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
