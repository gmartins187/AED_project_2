package App.Services;

import App.Students.Student;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

public abstract class ServiceAbstractClass implements Service {

    /**
     * Is only public because is used in the app method save to serialize and save ich time it loads the area to get the number where it left of
     */
    public static int orderOfInsertion;


    private int myOrder;

    private final long latitude;
    private final long longitude;
    private final String type;


    private final int price;
    private final String name;

    private final List<Review> reviews;
    private int reviewCounter;

    private long averageRating;
    private long totalRatingSum;

    /**
     * Constructor for ServiceAbstractClass.
     * @param latitude the latitude of the service
     * @param longitude the longitude of the service
     * @param price the price of the service
     * @param name the name of the service
     */
    public ServiceAbstractClass(long latitude, long longitude, int price, String name, String type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.name = name;
        this.type = type;

        this.myOrder = orderOfInsertion++;

        reviews = new DoublyLinkedList<>();

        reviewCounter = 1;
        averageRating = 4;
        totalRatingSum = 4;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public long getLatitude(){
        return this.latitude;
    }

    @Override
    public long getLongitude(){
        return this.longitude;
    }

    @Override
    public int getPrice(){
        return this.price;
    }

    @Override
    public void addReview(Review review){
        reviews.addLast(review);
        calculateAverage(review);
    }

    @Override
    public int getOrder(){
        return this.myOrder;
    }

    @Override
    public long getAverageRating(){
        return this.averageRating;
    }

    /**
     * Adds a new review and recalculates the average rating.
     * Updates the insertion order only if the average changes by a significant amount.
     */
    public void calculateAverage(Review review) {

        this.totalRatingSum += review.getNumRate();
        this.reviewCounter++;

        double trueAverage = (double) this.totalRatingSum / this.reviewCounter;

        if(Math.round(trueAverage) != this.averageRating){
            this.myOrder = orderOfInsertion++;
            this.averageRating = Math.round(trueAverage);
        }

    }

    @Override
    public boolean isTagged(String tag){
        Iterator<Review> it = reviews.iterator();
        while(it.hasNext()) {

            String reviewText = it.next().getTag();

            if (myContainsIgnoreCase(reviewText, tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param reviewText the review text to compare
     * @param tag the word to search
     * @return true if the tag is contained in some other tag from this service
     */
    private boolean myContainsIgnoreCase(String reviewText, String tag){
        return kmpIgnoreCase(reviewText, tag);
    }

    /**
     * Case-insensitive KMP that matches WHOLE WORDS only (simulating split + equals).
     */
    private boolean kmpIgnoreCase(String text, String pattern) {
        if (pattern.length() == 0) return true;
        if (text.length() < pattern.length()) return false;

        char[] txt = toLowerCaseCharArray(text);
        char[] pat = toLowerCaseCharArray(pattern);

        int[] lps = buildLPS(pat);

        int i = 0, j = 0;
        while (i < txt.length) {
            if (txt[i] == pat[j]) {
                i++;
                j++;
            }

            if (j == pat.length) {

                boolean startOk = false;
                boolean endOk = false;

                int matchStartIndex = i - j;
                int matchEndIndex = i;

                if (matchStartIndex == 0) {
                    startOk = true;
                } else if (txt[matchStartIndex - 1] == ' ') {
                    startOk = true;
                }

                if (matchEndIndex == txt.length) {
                    endOk = true;
                } else if (txt[matchEndIndex] == ' ') {
                    endOk = true;
                }

                if (startOk && endOk) {
                    return true;
                }

                j = lps[j - 1];
            }

            else if (i < txt.length && txt[i] != pat[j]) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }
        return false;
    }

    /**
     * Build LPS array (from PDF).
     */
    private int[] buildLPS(char[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern[i] == pattern[len]) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Converts string to lowercase char array without using String methods.
     */
    private char[] toLowerCaseCharArray(String s) {
        char[] arr = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') c = (char)(c + 32);
            arr[i] = c;
        }
        return arr;
    }

    @Override
    public long getDistance(Student student){
        return Math.abs(this.latitude - student.getLocation().getLatitude())
                + Math.abs(this.longitude - student.getLocation().getLongitude());
    }
}
