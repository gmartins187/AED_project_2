package App.Services;

public class ReviewClass implements Review{

    private final int numericRating;
    private final String textReview;


    /**
     * Constructor for ReviewClass.
     * @param numericRating the numeric rating of the review
     * @param textReview the tag of the review
     */
    public ReviewClass(int numericRating, String textReview) {
        this.numericRating = numericRating;
        this.textReview = textReview;
    }

    @Override
    public int getNumRate() {
        return numericRating;
    }

    @Override
    public String getTag() {
        return textReview;
    }
}
