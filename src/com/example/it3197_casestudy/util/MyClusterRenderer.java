package com.example.it3197_casestudy.util;

import android.content.Context;

import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {
    public MyClusterRenderer(Context context, GoogleMap map,ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item,
            MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getTitle()).snippet(item.getHyperLink());
    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem,
            Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }
}