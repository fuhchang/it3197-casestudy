package com.example.it3197_casestudy.ar.data;

import java.util.List;

import com.example.it3197_casestudy.ar.ui.Marker;


/**
 * This abstract class should be extended for new data sources.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class DataSource {

    public abstract List<Marker> getMarkers();
}
