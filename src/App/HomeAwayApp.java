package App;
import App.Exceptions.*;
import App.Services.Service;
import App.Students.Student;
import dataStructures.Iterator;


public interface HomeAwayApp {


    /**
     * Introduces a new region to the app
     * @param top the top boundary
     * @param left the left boundary
     * @param bottom the bottom boundary
     * @param right the right boundary
     * @param name the name of the area
     * @throws InvalidArea if the area is invalid
     * @throws AlreadyExists if the area already exists
     */
    void newArea(long top, long left, long bottom, long right, String name)
        throws InvalidArea, AlreadyExists;

    /**
     * Saves the current region to a text file
     * @throws NoCurrentArea if there is no current area defined
     * @return the name of the area
     */
    String saveArea();


    /**
     * Loads the region from a text file
     * @param areaName the name of the area to load
     * @throws InvalidArea if the area does not exist
     */
    String loadArea(String areaName);


    /**
     * Lists all services in the current area
     * @throws NoCurrentArea if there is no current area defined
     */
    Iterator<Service> listAllServices();

    /**
     * Creates and adds a new student in the app
     * @param type the type of lodging (hostel, hotel, apartment)
     * @param name the name of the student
     * @param country the country of the student
     * @param lodgingName the name of the lodging
     * @throws InvalidType if there is no type that matches
     * @throws InvalidLocation if the location is invalid
     * @throws AlreadyExists if the student already exists
     */
    void newStudent(String type, String name, String country, String lodgingName);

    /**
     * Removes a student from the app
     * @param name the name of the student to remove
     * @throws DoesNotExist if the student does not exist
     */
    void removeStudent(String name);


    /**
     * Change the location of a student
     * @param name the name of the student
     * @param locationName the name of the new location
     * @throws DoesNotExist if the student or location do not exist
     */
    void changeStudentLocation(String name, String locationName);

    /**
     * Lists all students in the app, or only those from a specific country if provided
     * @param from the country to filter by, or null to list all students
     * @throws DoesNotExist if there are no students
     * @throws InvalidArea if there are no students in the app from that specific country
     */
    Iterator<Student> listStudents(String from);

    /**
     * Change the home of a student
     * @param name the name of the student
     * @param lodgingName the name of the new lodging
     * @throws InvalidLocation if the lodging does not exist or is not a lodging
     * @throws DoesNotExist if the student do not exist
     * @throws AlreadyThere if the student is already at that lodging
     * @throws ServiceFull if the lodging is full
     * @throws InvalidService if the lodging is not valid for the student type
     */
    void changeStudentHome(String name, String lodgingName);


    /**
     * Creates and adds a new service in the app
     * @param type the type of service
     * @param latitude the latitude of the service
     * @param longitude the longitude of the service
     * @param value1 the price of the service
     * @param value2 the discount or the capacity of the service
     * @param name the name of the service
     * @throws InvalidService if there is no type that matches
     * @throws InvalidLocation if the location is invalid
     * @throws InvalidPrice if the price is invalid
     * @throws InvalidValue if the value is invalid
     * @throws ServiceFull if the service is full
     * @throws AlreadyExists if the service already exists
     */
    void newService(String type, long latitude, long longitude, int value1, int value2, String name);


    /**
     * Lists all users that have used a specific service, ordered by first or last.
     * @param order the order to list the users (name or times)
     * @param serviceName the name of the service
     * @throws InvalidOrder if the sign order is not valid
     * @throws DoesNotExist if the service does not exist or has no users
     * @throws InvalidType if the order is not valid
     * @return an iterator with all the users in the service
     */
    Iterator<Student> listUsersInService(String order, String serviceName);

    /**
     * Locates a student and shows their details
     * @param name the name of the student
     * @throws DoesNotExist if the student does not exist
     */
    String locateStudent(String name);

    /**
     * Lists all locations a student has visited
     * @param name the name of the student
     * @throws DoesNotExist if the student does not exist or has not visited any locationsq
     * @throws InvalidType if the student is thrifty
     * @throws Untouched if the student has not visited any locations
     */
    Iterator<Service> listVisitedLocations(String name);

    /**
     * Rates a service
     * @param name the name of the service
     * @param numericRate the numeric rate (1-5)
     * @param tag the tag to add to the service along the numeric rate
     */
    void rateService(String name, int numericRate, String tag);

    /**
     * List all services ordered by their average rating (from highest to lowest).
     * If two services have the same average rating, order them by the time they were updated to the system.
     * @throws DoesNotExist if there are no services in the system
     */
    Iterator<Service> getServicesIteratorByRating()
            throws DoesNotExist;

    /**
     * Lists the service(s) of the indicated type with the given score that are closer to the student location.
     *
     * @param numericRate The desired average star rating (between 1 and 5).
     * @param type The service type to search for.
     * @param studentName The student's name to calculate the distance from.
     * @throws InvalidValue If the number of stars is invalid.
     * @throws DoesNotExist If the student does not exist.
     * @throws InvalidType If the service type is invalid.
     * @throws Untouched If no services of that type exist.
     * @throws ServiceFull If no services of that type with the specified average rating exist.
     */
    Iterator<Service> listServicesByTypeAndRating(int numericRate, String type, String studentName)
            throws InvalidValue, DoesNotExist, InvalidType, Untouched, ServiceFull;

    /**
     * Lists all services that have the specified 'tag' in their reviews.
     *
     * @param tag The word (case-insensitive) to search for in the review descriptions.
     * @throws Untouched If there are no services with reviews containing the 'tag'.
     */
    Iterator<Service> getServicesWithTag(String tag) throws Untouched;

    /**
     * Finds the most relevant service of a certain type for a specific student.
     * Relevance is determined by the student's type (best average for 'bookish'/'outgoing', least expensive for 'thrifty').
     *
     * @param studentName The student's name.
     * @param type The service type (eating, lodging, or leisure).
     * @throws InvalidType If the service type is invalid.
     * @throws DoesNotExist If the student does not exist.
     * @throws Untouched If no services of the specified type exist.
     * @return the most relevant service to the student
     */
    String mostRelevantService(String studentName, String type)
            throws InvalidType, DoesNotExist, Untouched;

    /**
     * @return the name of the current region.
     */
    String getAreaName();

    /**
     * @param name the name
     * @return the stored name of the service
     */
    String getServiceName(String name);

    /**
     * @param name the name received in the input
     * @return the name of the student registered to the region
     */
    String getStudentName(String name);

    /**
     * Checks if a thrifty student is distracted changing location
     * @param name the name of the student
     * @param locationName the location of the student
     * @return yes if it is
     */
    boolean isStudentDistracted(String name, String locationName);
}
