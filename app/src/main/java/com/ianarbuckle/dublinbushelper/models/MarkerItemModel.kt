package com.ianarbuckle.dublinbushelper.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by Ian Arbuckle on 20/02/2017.

 */

data class MarkerItemModel(private val position: LatLng?, val displayStopId: String?, val shortName: String?, val shortNameLocalised: String?, val lastUpdated: String?, val routes: String?) : ClusterItem {

    override fun getPosition(): LatLng? {
        return position
    }

}
