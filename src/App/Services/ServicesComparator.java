package App.Services;

import dataStructures.Comparator;

public class ServicesComparator implements Comparator<Service>{

    /**
     * Compares two services by rate and order in which got the rating average
     * @param x the element to compare
     * @param y the element to compare
     * @return a comparison on average and longer time with that average
     */
    @Override
    public int compare(Service x, Service y) {
        int ratingCompare = Double.compare(y.getAverageRating(), x.getAverageRating());
        if (ratingCompare != 0) {
            return ratingCompare;
        }
        return Integer.compare(x.getOrder(), y.getOrder());
    }
}
