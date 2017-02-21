package com.example.mammam.cowchat.ui.fragm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.ui.custom.ZoomableImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Mam  Mam on 12/28/2016.
 */

public class ImageFragment extends BaseFragment implements IConstand{
    private ZoomableImageView view;
    private String url;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_image,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        view = (ZoomableImageView) view.findViewById(R.id.ivFgImage);
        Picasso.with(getContext()).load(url).into((ImageView) view);
    }

    @Override
    public void initComponents() {
        url = getArguments().getString(URL_DOWNLOAD);

    }

    @Override
    public void setEventViews() {

    }
}
