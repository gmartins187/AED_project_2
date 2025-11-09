import App.*;
import App.Exceptions.*;
import App.Services.Service;
import App.Students.Student;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.File;
import java.util.Scanner;


public class Main {

    private static final String EATING = "eating";
    private static final String LODGING = "lodging";
    private static final String LEISURE = "leisure";

    private static final String EXIT_TEXT = "Bye!";
    private static final String SAVED = "%s saved.\n";
    private static final String NOT_COMMAND_TEXT = "Unknown command. Type help to see available commands.";
    private static final String STUDENT_ADDED = "%s added.\n";
    private static final String INVALID_BOUNDS = "Invalid bounds.";
    private static final String AREA_EXISTS_ALREADY = "Bounds already exists. Please load it!";
    private static final String NO_CURRENT_AREA = "System bounds not defined.";
    private static final String INVALID_TYPE = "Invalid service type!";
    private static final String INVALID_LOCATION = "Invalid location!";
    private static final String INVALID_MENU_PRICE = "Invalid menu price!";
    private static final String INVALID_DISCOUNT_PRICE = "Invalid discount price!";
    private static final String ALREADY_EXISTS = " already exists!";
    private static final String NO_SERVICES = "No services yet!";
    private static final String INVALID_STU_TYPE = "Invalid student type!";
    private static final String INVALID_LODGING = "lodging %s does not exist!\n";
    private static final String SERVICE_FULL = "lodging %s is full!\n";
    private static final String EATING_FULL = "eating %s is full!\n";
    private static final String UNKNOWN = "Unknown %s!\n";
    private static final String DOES_NOT_EXIST = "%s does not exist!\n";
    private static final String INVALID_SERVICE = "%s is not a valid service!\n";
    private static final String ALREADY_THERE = "Already there!";
    private static final String NO_STUDENTS = "No students yet!";
    private static final String NO_STUDENTS_FROM = "No students from %s!\n";
    private static final String CURRENT_HOME = "That is %s's home!\n";
    private static final String LODGING_FULL = "lodging %s is full!\n";
    private static final String INVALID_MOVE = "Move is not acceptable for %s!\n";
    private static final String INVALID_CAPACITY = "Invalid capacity!";
    private static final String INVALID_ROOM_PRICE = "Invalid room price!";
    private static final String INVALID_TICKET_PRICE = "Invalid ticket price!";
    private static final String DOES_NOT_CONTROL = "%s does not control student entry and exit!\n";
    private static final String HAS_NOT_VISITED = "%s has not visited any locations!\n";
    private static final String IS_THRIFTY = "%s is thrifty!\n";
    private static final String INVALID_RATE = "Invalid evaluation!";
    private static final String NO_SERVICES_SYS = "No services in the system.";
    private static final String INVALID_STARS = "Invalid stars!";
    private static final String NO_TYPE_SERVICES = "No %s services!\n";
    private static final String NO_TYPE_SERVICES_AVG = "No %s services with average!\n";
    private static final String NO_TAG = "There are no services with this tag!";
    private static final String HAS_LEFT = " has left.";
    private static final String RANKED = "Services sorted in descending order";
    private static final String RANKING = "%s services closer with %d average\n";
    private static final String SERVICE_RATED = "Your evaluation has been registered!";
    private static final String IS_AT = "%s is now at %s.";
    private static final String NEW_HOME = "lodging %s is now %s's home. %s is at home.\n";
    private static final String NEW_SERVICE = "%s %s added.\n";
    private static final String AREA_LOADED = "%s loaded.\n";
    private static final String IS_DISTRACTED = " %s is distracted!";
    private static final String NO_SYSTEM_BOUNDS = "System bounds not defined.";
    private static final String NO_STUDENTS_IN = "No students on %s!\n";
    private static final String NO_ORDER = "This order does not exists!";


    public static void main(String[] args){commands();}


    /**
     * Handles all commands.
     */
    private static void commands() {
        Scanner in = new Scanner(System.in);
        HomeAwayApp App = new homeAwayAppClass();

        Command command;
        do{
            command = getCommand(in);
            switch (command){
                case EXIT -> System.out.println(EXIT_TEXT);
                case HELP -> executeHelp();
                case BOUNDS -> newArea(App, in);
                case SAVE -> saveArea(App);
                case LOAD -> loadArea(App, in);
                case SERVICE -> newService(App, in);
                case SERVICES -> allServices(App);
                case STUDENT -> newStudent(App, in);
                case LEAVE -> removeStudent(App, in);
                case STUDENTS -> listStudents(App, in);
                case GO -> changeStudentLocation(App, in);
                case MOVE -> changeStudentHome(App, in);
                case USERS -> listUsersInService(App, in);
                case WHERE -> locateStudent(App, in);
                case VISITED -> listVisitedLocations(App, in);
                case STAR -> rateService(App, in);
                case RANKING -> listServicesByRating(App);
                case RANKED -> listServicesByTypeAndRating(App, in);
                case TAG -> allServicesWithTag(App, in);
                case FIND -> mostRelevantService(App, in);
                case UNKNOWN -> {System.out.println(NOT_COMMAND_TEXT);
                    in.nextLine();}
            }
        } while(!command.equals(Command.EXIT));
        App.saveArea();
        //clearDataFolder();
        in.close();
    }


    /**
     * Reads the command from input (taken from source code of problem A from moodle)
     * @param input the scanner to read input from
     * @return the command
     */
    private static Command getCommand(Scanner input) {
        try {
            String comm = input.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }

    }


    /**
     * Prints all available commands (taken from source code of problem A from moodle)
     */
    private static void executeHelp() {
        Command[] help=Command.values();
        for(int i=0; i<help.length-1;i++)
            System.out.println(help[i].getMsg());
    }


    /**
     * Creates a new region in the app
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void newArea(HomeAwayApp app, Scanner in){
        try {
            long top = in.nextLong();
            long left = in.nextLong();
            long bottom = in.nextLong();
            long right = in.nextLong();

            String areaName = in.nextLine().trim();

            app.newArea(top,left,bottom,right, areaName);

            System.out.println(areaName + " created.");
        } catch (InvalidArea e){
            System.out.println(INVALID_BOUNDS);
        } catch (AlreadyExists e){
            System.out.println(AREA_EXISTS_ALREADY);
        }
    }


    /**
     * Saves the current region to a text file
     * @param app the region manager (app object)
     */
    private static void saveArea(HomeAwayApp app) {
        try{

            String areaName = app.saveArea();
            System.out.printf(SAVED, areaName);
        } catch (NoCurrentArea e){
            System.out.println(NO_CURRENT_AREA);
        }
    }


    /**
     * Loads the region from a text file
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void loadArea(HomeAwayApp app, Scanner in) {
        String areaName = in.nextLine().trim();
        try{;
            System.out.printf(AREA_LOADED, app.loadArea(areaName.toLowerCase()));
        } catch (InvalidArea e){
            System.out.println("Bounds " + areaName + " does not exists.");
        }
    }


    /**
     * Creates and adds a new service in the app
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void newService(HomeAwayApp app, Scanner in) {
        String name = "";
        String type = "";
        try {
            type = in.next().trim().toLowerCase();
            long latitude = in.nextLong();
            long longitude = in.nextLong();
            int value1 = in.nextInt();
            int value2 = in.nextInt();
            name = in.nextLine().trim();
            app.newService(type, latitude, longitude, value1, value2, name);
            System.out.printf(NEW_SERVICE, type, app.getServiceName(name));
        } catch (InvalidService e){
            System.out.println(INVALID_TYPE);
        } catch (InvalidLocation e){
            System.out.println(INVALID_LOCATION);
        } catch (InvalidPrice e){
            switch(type){
                case EATING -> System.out.println(INVALID_MENU_PRICE);
                case LODGING -> System.out.println(INVALID_ROOM_PRICE);
                case LEISURE -> System.out.println(INVALID_TICKET_PRICE);
            }
        } catch (InvalidValue e){
            System.out.println(INVALID_DISCOUNT_PRICE);
        } catch (ServiceFull e) {
            System.out.println(INVALID_CAPACITY);
        } catch (AlreadyExists e){
            System.out.println(app.getServiceName(name) + ALREADY_EXISTS); }
    }


    /**
     * Lists all services in the app
     * @param app the region manager (app object)
     */
    private static void allServices(HomeAwayApp app) {
        try{
            Iterator<Service> it = app.listAllServices();
            while(it.hasNext()){
                Service next = it.next();
                System.out.println(next.getName() + ": "
                        + next.getType() + " ("
                        + next.getLatitude()
                        + ", " + next.getLongitude() + ").");
            }
        } catch (DoesNotExist e){
            System.out.println(NO_SERVICES);
        } catch (NoCurrentArea e){
            System.out.println(NO_CURRENT_AREA);
        }
    }


    /**
     * Creates and adds a new student in the app
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void newStudent(HomeAwayApp app, Scanner in) {
        String name = "";
        String lodgingName = "";
        try{
            String type = in.nextLine().trim().toLowerCase();
            name = in.nextLine();
            String country = in.nextLine().trim();
            lodgingName = in.nextLine();

            app.newStudent(type, name, country, lodgingName);
            System.out.printf(STUDENT_ADDED, name);
        } catch (InvalidType e){
            System.out.println(INVALID_STU_TYPE);
        } catch (InvalidLocation e){
            System.out.printf(INVALID_LODGING, lodgingName);
        } catch (ServiceFull e){
            System.out.printf(SERVICE_FULL, lodgingName);
        } catch (AlreadyExists e){
            System.out.println(app.getStudentName(name) + ALREADY_EXISTS);
        }
    }


    /**
     * Removes a student from the app
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void removeStudent(HomeAwayApp app, Scanner in) {
        String name = in.nextLine().trim();
        try{
            name = app.getStudentName(name);
            app.removeStudent(name);
            System.out.println(name + HAS_LEFT);
        } catch (DoesNotExist e){
            System.out.println(name + " does not exist!");
        }
    }


    /**
     * Lists all students in the app
     * @param app the region manager (app object)
     */
    private static void listStudents(HomeAwayApp app, Scanner in) {
        String from = "";
        try{
            from = in.nextLine().trim();

            Iterator<Student> it = app.listStudents(from);
            while (it.hasNext()){
                Student next = it.next();
                System.out.println(next.getName() + ": "
                        + next.getType() + " at "
                        + next.getLocation().getName() + ".");
            }
        } catch (DoesNotExist e){
            System.out.println(NO_STUDENTS);
        } catch (InvalidArea e){
            System.out.printf(NO_STUDENTS_FROM, from);
        } catch (NoCurrentArea e){
            System.out.println(NO_SYSTEM_BOUNDS);
        }
    }


    /**
     * change the location of a student to an eating or leisure place
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void changeStudentLocation(HomeAwayApp app, Scanner in)  {
        String name = "";
        String locationName = "";
        try{
            name = in.nextLine().trim();
            locationName = in.nextLine().trim();

            boolean isDistracted = app.isStudentDistracted(name, locationName);
            app.changeStudentLocation(name, locationName);

            System.out.printf(IS_AT, app.getStudentName(name),
                    app.getServiceName(locationName));

            if(isDistracted) System.out.printf(IS_DISTRACTED, name);

            System.out.println();
        } catch (InvalidLocation e){
            System.out.printf(UNKNOWN, locationName);
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, name);
        } catch (InvalidService e){
            System.out.printf(INVALID_SERVICE, locationName);
        } catch (AlreadyThere e){
            System.out.println(ALREADY_THERE);
        } catch (ServiceFull e){
            System.out.printf(EATING_FULL, locationName);
        }
    }


    /**
     * change the home of a student to a lodging place
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void changeStudentHome(HomeAwayApp app, Scanner in) {
        String name = "";
        String lodgingName = "";
        try{
            name = in.nextLine().trim();
            lodgingName = in.nextLine().trim();

            app.changeStudentHome(name, lodgingName);
            String newName = app.getStudentName(name);
            System.out.printf(NEW_HOME, app.getServiceName(lodgingName), newName, newName);
        } catch (InvalidLocation e){
            System.out.printf(INVALID_LODGING, lodgingName);
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, name);
        } catch (AlreadyThere e){
            System.out.printf(CURRENT_HOME, app.getStudentName(name));
        } catch (ServiceFull e){
            System.out.printf(LODGING_FULL, lodgingName);
        } catch (InvalidService e){
            System.out.printf(INVALID_MOVE, app.getStudentName(name));
        }
    }


    /**
     * List users in a service (eating or lodging)
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void listUsersInService(HomeAwayApp app, Scanner in) {
        String serviceName = "";
        try{
            String order = in.next().trim();
            serviceName = in.nextLine().trim();

            Iterator<Student> it = app.listUsersInService(order, serviceName);

            if(order.equals("<")){
                TwoWayIterator<Student> newIt = (TwoWayIterator<Student>) it;
                while(newIt.hasPrevious()){
                    Student next = newIt.previous();
                    System.out.println(next.getName() + ": " + next.getType());
                }
            } else{
                while(it.hasNext()){
                    Student next = it.next();
                    System.out.println(next.getName() + ": " + next.getType());
                }
            }
        } catch (InvalidOrder e){
            System.out.println(NO_ORDER);
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, serviceName);
        } catch (InvalidType e){
            System.out.printf(DOES_NOT_CONTROL, app.getServiceName(serviceName));
        } catch (InvalidValue e){
            System.out.printf(NO_STUDENTS_IN, app.getServiceName(serviceName));
        }
    }


    /**
     * Locates a student (current service location)
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void locateStudent(HomeAwayApp app, Scanner in) {
        String name = "";
        try{
            name = in.nextLine().trim();

            System.out.println(app.locateStudent(name));
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, name);
        }
    }


    /**
     * Lists locations visited by one student
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void listVisitedLocations(HomeAwayApp app, Scanner in) {
        String name = "";
        try{
            name = in.nextLine().trim();

            Iterator<Service> it = app.listVisitedLocations(name);
            while(it.hasNext())
                System.out.println(it.next().getName());
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, name);
        } catch (InvalidType e){
            System.out.printf(IS_THRIFTY, app.getStudentName(name));
        } catch (Untouched e){
            System.out.printf(HAS_NOT_VISITED, app.getStudentName(name));
        }
    }


    /**
     * Rates a service with a star and a comment
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void rateService(HomeAwayApp app, Scanner in) {
        String name = "";
        try {
            int numericRate = in.nextInt();
            name = in.nextLine().trim();
            String tag = in.nextLine().trim();

            app.rateService(name, numericRate, tag);
            System.out.println(SERVICE_RATED);
        } catch (InvalidType e){
            System.out.println(INVALID_RATE);
        } catch (DoesNotExist e){
            System.out.printf(DOES_NOT_EXIST, name);
        }
    }


    /**
     * Lists services ordered by rating
     * @param app the region manager (app object)
     */
    private static void listServicesByRating(HomeAwayApp app) {
        try {
            Iterator<Service> it = app.getServicesIteratorByRating();
            System.out.println(RANKED);
            while(it.hasNext()){
                Service next = it.next();
                System.out.println(next.getName() + ": " + next.getAverageRating());
            }
        } catch (DoesNotExist e){
            System.out.println(NO_SERVICES_SYS);
        }
    }


    /**
     * Lists the service(s) of the indicated type with the given score that are closer to the student location.
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void listServicesByTypeAndRating(HomeAwayApp app, Scanner in) {
        String type = "";
        String name = "";
        try {
            type = in.next().trim();
            int numericRate = in.nextInt();
            name = in.nextLine().trim();

            Iterator<Service> it = app.listServicesByTypeAndRating(numericRate, type, name);
            System.out.printf(RANKING, type, numericRate);
            while(it.hasNext()){
                System.out.println(it.next().getName());
            }
        } catch (InvalidValue e) {
            System.out.println(INVALID_STARS);
        } catch (DoesNotExist e) {
            System.out.printf(DOES_NOT_EXIST, name);
        } catch (InvalidType e) {
            System.out.println(INVALID_TYPE);
        } catch (Untouched e) {
            System.out.printf(NO_TYPE_SERVICES, type);
        } catch (ServiceFull e) {
            System.out.printf(NO_TYPE_SERVICES_AVG, type);
        }
    }


    /**
     * Lists all services that have at least one review whose description contains the specified word.
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void allServicesWithTag(HomeAwayApp app, Scanner in) {
        try {
            String tag = in.nextLine().trim();

            Iterator<Service> it = app.getServicesWithTag(tag);
            while(it.hasNext()){
                Service next = it.next();
                System.out.println(next.getType().toLowerCase() + " " +next.getName());
            }
        } catch (Untouched e) {
            System.out.println(NO_TAG);
        }
    }


    /**
     * Displays the most relevant service of a certain type, for a specific student
     * @param app the region manager (app object)
     * @param in the scanner to read input from
     */
    private static void mostRelevantService(HomeAwayApp app, Scanner in) {
        String name = "";
        String type = "";
        try {
            name = in.nextLine().trim();
            type = in.nextLine().trim();

            System.out.println(app.mostRelevantService(name, type));
        } catch (InvalidType e) {
            System.out.println(INVALID_TYPE);
        } catch (DoesNotExist e) {
            System.out.printf(DOES_NOT_EXIST, name);
        } catch (Untouched e) {
            System.out.printf(NO_TYPE_SERVICES, type);
        }
    }


    /**
     * Deletes all the folder info after the app is exited.
     */
    public static void clearDataFolder() {
        File dataFolder = new File("data");

        File[] files = dataFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deleted = file.delete();
                }
            }
        }
    }
}
