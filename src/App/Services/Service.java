package App.Services;

import App.Students.Student;

import java.io.Serializable;

public interface Service extends Serializable {

    /**
     * @return the name of the service
     */
    String getName();

    /**
     * @return the type of the service
     */
    String getType();

    /**
     * @return the latitude of the service
     */
    long getLatitude();

    /**
     * @return the longitude of the service
     */
    long getLongitude();

    /**
     * @return the price of the service
     */
    int getPrice();

    /**
     * @return if the service is full
     */
    boolean isFull();

    /**
     * adds a student to the service
     * @param student the student to add
     */
    void addStudent(Student student);

    /**
     * removes student from the service in the service
     * @param student the student to remove
     */
    void removeStudent(Student student);

    /**
     * adds a review to the service
     * @param review the one to add to the service
     */
    void addReview(Review review);

    /**
     * @return the average rating
     */
    long getAverageRating();

    /**
     * @return the order in which they got the average rating
     */
    int getOrder();

    /**
     * @param tag the tag to check for
     * return if the service is tagged with the tag
     */
    boolean isTagged(String tag);

    /**
     * @param student the student
     * @return the distance from the service to the student
     */
    long getDistance(Student student);

    /**
     * @return yes if it has student in service
     */
    boolean hasStudent(Student student);

    /**
     * @return true if there is no student in the service
     */
    boolean isEmpty();
}
