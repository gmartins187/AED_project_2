package App;

import App.Services.Service;
import App.Students.Student;
import dataStructures.Iterator;

import java.io.Serializable;

public interface Region extends Serializable {


    /**
     * @return the name of the region
     */
    String getName();

    /**
     * @param latitude  the latitude of the service
     * @param longitude the longitude of the service
     * @return if a service is in the region
     */
    boolean isValid(long latitude, long longitude);

    /**
     * @param name the name of the region
     * @return if the service exists in the region
     */
    Service getService(String name);

    /**
     * adds a service to the region
     *
     * @param service the service to add to the region
     */
    void addService(Service service);

    /**
     * @return if there is any service
     */
    boolean isEmpty();

    /**
     * Iterates all the services in the current region loaded
     */
    Iterator<Service> listAllServices();

    /**
     * adds a student to the region
     *
     * @param student the student to add to the region
     */
    void addStudent(Student student);

    /**
     * @return the ethnicity object
     */

    boolean hasEthnicity(String country);

    /**
     * @param name the name of the service
     * @return if a service is full
     */
    boolean isServiceFull(String name);

    /**
     * @return the student ub the region by name
     */
    Student getStudent(String name);

    /**
     * removes student from the current region
     * @param name the name of the student to remove
     */
    void removeStudent(String name);

    /**
     * @param lodgingName the name of the lodge
     * @return true if it exists
     */
    boolean hasLodging(String lodgingName);

    /**
     * @return true if it has students
     */
    boolean hasStudents();

    /**
     * @param country the 'ethnicity' to add
     */
    void addEthnicity(String country);

    /**
     * list all the students from the region
     * @param from the ethnicity of the students to iterate
     */
    Iterator<Student> listStudents(String from);

    /**
     * lists all the users in a service by a specific order
     * @param service the users must be in this service
     * @param order the specific order to iterate(from oldest to newest or reverse)
     */
    Iterator<Student> listUsersIn(Service service, String order);

    /**
     * prints to the console where is the student
     * @param student the student to search for
     */
    String whereStudent(Student student);

    /**
     * list the services by the review. in case of the same review
     * @return the iterator for the main to iterate for
     */
    Iterator<Service> listServicesByReview();

    /**
     * @param type the type to check for
     * @return yes if it has any service in the region with the current type
     */
    boolean hasServicesType(String type);

    /**
     * @param type the type
     * @param numericRate the rating average
     * @return if it has any services of a certain type and rating average
     */
    boolean hasServicesTypeRate(String type, int numericRate);

    /**
     * @param tag the tag to check for
     * @return if is there any service with a certain tag
     */
    boolean hasServicesWithTag(String tag);

    /**
     * @param numericRate the numeric rating of the service
     * @param type the type of the service
     * @param student the student
     * @return an iterator sorted by ranking and distance to the student
     */
    Iterator<Service> getRankedServices(int numericRate, String type, Student student);

    /**
     * @param tag the tag the services must be tagged with
     * @return an iterator with the services by rating
     */
    Iterator<Service> listServicesWithTag(String tag);

    /**
     * @param student the student
     * @return the most relevant service to the student
     */
    String findMostRelevantService(Student student, String type);

    /**
     * removes this service
     * @param loc service to remove
     */
    void removeServiceFromSorted(Service loc);

    /**
     * adds service back to the sorted list
     * @param loc the service to add
     */
    void addServiceToSorted(Service loc);

    /**
     * save the global counter when the region is saved
     * @param counter the global watch
     */
    void setSavedOrderCounter(int counter);

    /**
     * @return the stored value
     */
    int getSavedOrderCounter();
}
