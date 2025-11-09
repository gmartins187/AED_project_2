package App.Services;

import java.io.Serializable;

public interface Review extends Serializable {

    /**
     * @return the numeric rating stored in the review
     */
    int getNumRate();

    /**
     * @return the tag of the review
     */
    String getTag();
}
