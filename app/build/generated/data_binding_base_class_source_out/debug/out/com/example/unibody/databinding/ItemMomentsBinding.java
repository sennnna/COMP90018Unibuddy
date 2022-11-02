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
import com.example.unibody.me.fragment.view.GridViewForScrollView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemMomentsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final GridViewForScrollView gridPhoto;

  @NonNull
  public final ImageView ivDel;

  @NonNull
  public final CircleImageView ivHead;

  @NonNull
  public final LinearLayout llContent;

  @NonNull
  public final TextView tvDesc;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvTime;

  private ItemMomentsBinding(@NonNull LinearLayout rootView,
      @NonNull GridViewForScrollView gridPhoto, @NonNull ImageView ivDel,
      @NonNull CircleImageView ivHead, @NonNull LinearLayout llContent, @NonNull TextView tvDesc,
      @NonNull TextView tvName, @NonNull TextView tvTime) {
    this.rootView = rootView;
    this.gridPhoto = gridPhoto;
    this.ivDel = ivDel;
    this.ivHead = ivHead;
    this.llContent = llContent;
    this.tvDesc = tvDesc;
    this.tvName = tvName;
    this.tvTime = tvTime;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMomentsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMomentsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_moments, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMomentsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.grid_photo;
      GridViewForScrollView gridPhoto = ViewBindings.findChildViewById(rootView, id);
      if (gridPhoto == null) {
        break missingId;
      }

      id = R.id.iv_del;
      ImageView ivDel = ViewBindings.findChildViewById(rootView, id);
      if (ivDel == null) {
        break missingId;
      }

      id = R.id.iv_head;
      CircleImageView ivHead = ViewBindings.findChildViewById(rootView, id);
      if (ivHead == null) {
        break missingId;
      }

      id = R.id.ll_content;
      LinearLayout llContent = ViewBindings.findChildViewById(rootView, id);
      if (llContent == null) {
        break missingId;
      }

      id = R.id.tv_desc;
      TextView tvDesc = ViewBindings.findChildViewById(rootView, id);
      if (tvDesc == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_time;
      TextView tvTime = ViewBindings.findChildViewById(rootView, id);
      if (tvTime == null) {
        break missingId;
      }

      return new ItemMomentsBinding((LinearLayout) rootView, gridPhoto, ivDel, ivHead, llContent,
          tvDesc, tvName, tvTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
