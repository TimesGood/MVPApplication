package com.example.ext.adapter.common;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.expand.R;
import com.example.ext.adapter.AppAdapter;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * 图片预览适配器
 */
public final class ImagePreviewAdapter extends AppAdapter<String> {

    public ImagePreviewAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(R.layout.adapter_image_preview);
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {
        private final PhotoView mPhotoView;

        private ViewHolder(int id) {
            super(id);
            mPhotoView = (PhotoView) getItemView();
        }

        @Override
        public void onBindView(int position) {
            Glide.with(getContext())
                    .load(getItemData(position))
                    .into(mPhotoView);
        }
    }
}