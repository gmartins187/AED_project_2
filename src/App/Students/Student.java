package App.Students;

import App.Services.Lodging;
import App.Services.Service;
import dataStructures.Iterator;

import java.io.Serializable;

public interface Student extends Serializable {

    /**
     * @return the name of the student
     */
    String getName();

    /**
     * @return the type of student
     */
    String getType();

    /**
     * @return the lodging in which the student lives
     */
    Service getLodging();

    /**
     * @return the current location of the student
     */
    Service getLocation();

    /**
     * Sets the new location which the student moved to
     * @param service the service which he changes to
     */
    void setLocation(Service service);

    /**
     * @return the home of the student
     */
    Lodging getHome();

    /**
     * moves the student to a new home
     * @param service the new home of the student
     */
    void setHome(Lodging service);

    /**
     * @return the ethnicity from which the student is
     */
    String getEthnicity();

    /**
     * @return if the student has visited any places
     */
    boolean hasnotVisited();

    /**
     * iterates all the places visited by the student
     */
    Iterator<Service> getVisitedPlaces();

    /**
     * register that I have visited this place
     */
    void pingService(Service service);
}

