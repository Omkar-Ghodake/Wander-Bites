package com.foodtruck.foodtruck.calculations;

import org.springframework.stereotype.Component;

@Component
public class FindDistance {
    Double EARTH_RADIUS = 6371.;

    Double haversine(Double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public Double calculateDistance(Double startLat, Double startLong, Double endLat, Double endLong) {

        Double dLat = Math.toRadians((endLat - startLat));
        Double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        Double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
