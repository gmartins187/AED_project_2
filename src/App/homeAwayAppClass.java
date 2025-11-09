package App;

import App.Exceptions.*;
import App.Services.ReviewClass;
import App.Services.*;
import App.Students.*;
import dataStructures.Iterator;

import java.io.*;

public class homeAwayAppClass implements HomeAwayApp{

    private Region currentRegion;

    private final String EATING = "eating";
    private final String LODGING = "lodging";
    private final String LEISURE = "leisure";

    private final String THRIFTY = "thrifty";
    private final String BOOKISH = "bookish";
    private final String OUTGOING = "outgoing";


    /**
     * Constructor for homeAwayAppClass (App)
     */
    public homeAwayAppClass() {
        currentRegion = null;
    }





    @Override
    public void newArea(long top, long left, long bottom, long right, String name)
            throws InvalidArea, AlreadyExists{
        if(fileExists(name.toLowerCase())){
            throw new AlreadyExists();
        }else if(invalidArea(top,left,bottom,right)){
            throw new InvalidArea();
        }else {
            currentRegion = new RegionClass(top, bottom, left, right, name);
        }
    }

    @Override
    public String saveArea() throws NoCurrentArea {
        if(currentRegion == null)
            throw new NoCurrentArea();
        else {
            String areaName = currentRegion.getName().toLowerCase();
            try {
                File dataFolder = new File("data");

                if (!dataFolder.exists()) dataFolder.mkdir();

                currentRegion.setSavedOrderCounter(ServiceAbstractClass.orderOfInsertion);

                File file = new File(dataFolder, areaName.replace(" ",""));

                FileOutputStream output = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(output);

                out.writeObject(currentRegion);

                out.close();
                output.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return currentRegion.getName();
        }
    }

    @Override
    public String loadArea(String regionName) throws NoCurrentArea{
        if(currentRegion != null)
            saveArea();

        File file = new File("data", regionName.replace(" ", ""));

        if (!file.exists()) throw new NoCurrentArea();

        try (FileInputStream input = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(input)) {

            currentRegion = (Region) in.readObject();

            ServiceAbstractClass.orderOfInsertion = currentRegion.getSavedOrderCounter();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return currentRegion.getName();
    }

    @Override
    public void newService(String type, long latitude, long longitude, int price, int value2, String name) {
        if(!isSerTypeValid(type))
            throw new InvalidService();
        else if(!this.currentRegion.isValid(latitude,longitude))
            throw new InvalidLocation();
        else if(price <= 0)
            throw new InvalidPrice();
        else if(type.equals(LEISURE)) {
            if (!(0 <= value2 && value2 <= 100)) throw new InvalidValue();
        }
        else if(value2 <= 0)
            throw new ServiceFull();
        if(this.currentRegion.getService(name) != null)
            throw new AlreadyExists();
        else {
            switch (type) {
                case LEISURE -> this.currentRegion.addService(new LeisureClass(latitude, longitude, price, value2, name, type));
                case EATING -> this.currentRegion.addService(new EatingClass(latitude,longitude,price,value2,name, type));
                case LODGING -> this.currentRegion.addService(new LodgingClass(latitude,longitude,price,value2,name, type));
            }
        }
    }

    /**
     * @param type the type to check
     * @return if the type of service is valid
     */
    private boolean isSerTypeValid(String type) {
        return type.equals(EATING) || type.equals(LODGING) || type.equals(LEISURE);
    }

    @Override
    public Iterator<Service> listAllServices() {
        if(this.currentRegion == null)
            throw new NoCurrentArea();
        else if(this.currentRegion.isEmpty())
            throw new DoesNotExist();
        else
            return this.currentRegion.listAllServices();
    }

    @Override
    public void newStudent(String type, String name, String country, String lodgingName){
        if(!isStuTypeValid(type))
            throw new InvalidType();
        else if(!currentRegion.hasLodging(lodgingName))
            throw new InvalidLocation();
        else if(this.currentRegion.isServiceFull(lodgingName))
            throw new ServiceFull();
        else if(this.currentRegion.getStudent(name) != null)
            throw new AlreadyExists();
        else {
            Lodging lodgingService = (Lodging) this.currentRegion.getService(lodgingName);
            if(!this.currentRegion.hasEthnicity(country)) this.currentRegion.addEthnicity(country);
            switch (type){
                case OUTGOING -> this.currentRegion.addStudent(new OutgoingClass(name, country, lodgingService, type));
                case BOOKISH -> this.currentRegion.addStudent(new BookishClass(name, country, lodgingService, type));
                case THRIFTY -> this.currentRegion.addStudent(new ThriftyClass(name, country, lodgingService, type));
            }
            Student student = this.currentRegion.getStudent(name);
            lodgingService.addStudent(student);
            student.pingService(lodgingService);
        }
    }

    /**
     * @param type the type to check
     * @return if this student type is valid
     */
    private boolean isStuTypeValid(String type) {
        return type.equals(BOOKISH) || type.equals(OUTGOING) || type.equals(THRIFTY);
    }

    @Override
    public void removeStudent(String name) {
        Student student = this.currentRegion.getStudent(name);

        if(student == null)
            throw new DoesNotExist();

        this.currentRegion.removeStudent(name);

    }

    @Override
    public Iterator<Student> listStudents(String from) {
        if(this.currentRegion == null)
            throw new NoCurrentArea();
        if(!this.currentRegion.hasStudents()){
            throw new DoesNotExist();
        } else if(!this.currentRegion.hasEthnicity(from)){
            throw new InvalidArea();
        } else{
            return this.currentRegion.listStudents(from);
        }
    }

    @Override
    public void changeStudentLocation(String name, String locationName) {
        Service service = currentRegion.getService(locationName);
        Student student = currentRegion.getStudent(name);

        if (service == null)
            throw new InvalidLocation();
        else if (student == null)
            throw new DoesNotExist();
        else if (service instanceof LodgingClass)
            throw new InvalidService();
        else if (student.getLocation() == service)
            throw new AlreadyThere();
        if (service instanceof Eating && service.isFull())
            throw new ServiceFull();

        student.setLocation(service);
        student.pingService(service);
    }


    @Override
    public void changeStudentHome(String name, String lodgingName) {
        Service lodging = currentRegion.getService(lodgingName);
        Student stu = currentRegion.getStudent(name);

        if(lodging == null)
            throw new InvalidLocation();
        else if(stu == null)
            throw new DoesNotExist();
        else if(stu.getHome().equals(lodging))
            throw new AlreadyThere();
        else if(lodging.isFull())
            throw new ServiceFull();
        else if(stu instanceof Thrifty && lodging.getPrice() > stu.getHome().getPrice())
            throw new InvalidService();

        stu.setHome((Lodging) lodging);
        stu.pingService(lodging);

    }

    @Override
    public Iterator<Student> listUsersInService(String order, String serviceName) {
        Service location = this.currentRegion.getService(serviceName);


        if(!order.equals(">") && !order.equals("<"))
            throw new InvalidOrder();
        else if(location == null)
            throw new DoesNotExist();
        else if(location instanceof Leisure)
            throw new InvalidType();
        else if(location.isEmpty())
            throw new InvalidValue();

        return this.currentRegion.listUsersIn(location, order);

    }

    @Override
    public String locateStudent(String name) {
        Student stu = this.currentRegion.getStudent(name);


        if(stu == null) throw new DoesNotExist();

        else return this.currentRegion.whereStudent(stu);

    }

    @Override
    public Iterator<Service> listVisitedLocations(String name) {
        Student stu = this.currentRegion.getStudent(name);

        if(stu == null)
            throw new DoesNotExist();
        else if(stu instanceof Thrifty)
            throw new InvalidType();
        else if(stu.hasnotVisited())
            throw new Untouched();

        return this.currentRegion.getStudent(name).getVisitedPlaces();
    }

    @Override
    public void rateService(String name, int numericRate, String tag) {
        Service loc = this.currentRegion.getService(name);

        if (numericRate < 1 || numericRate > 5)
            throw new InvalidType();
        else if(loc == null)
            throw new DoesNotExist();

        currentRegion.removeServiceFromSorted(loc);
        loc.addReview(new ReviewClass(numericRate, tag));
        currentRegion.addServiceToSorted(loc);
    }

    @Override
    public Iterator<Service> getServicesIteratorByRating() {

        if(this.currentRegion.isEmpty())
            throw new DoesNotExist();


        return this.currentRegion.listServicesByReview();

    }

    @Override
    public Iterator<Service> listServicesByTypeAndRating(int numericRate, String type, String studentName)
            throws InvalidValue, DoesNotExist, InvalidType, Untouched, ServiceFull {

        Student student = this.currentRegion.getStudent(studentName);

        if(numericRate < 1 || numericRate > 5)
            throw new InvalidValue();
        else if(student == null)
            throw new DoesNotExist();
        else if(!(type.equalsIgnoreCase(LEISURE)
                || type.equalsIgnoreCase(LODGING) || type.equalsIgnoreCase(EATING)))
            throw new InvalidType();
        else if(!this.currentRegion.hasServicesType(type))
            throw new Untouched();
        else if(!this.currentRegion.hasServicesTypeRate(type, numericRate))
            throw new ServiceFull();

        return this.currentRegion.getRankedServices(numericRate, type,student);

    }

    @Override
    public Iterator<Service> getServicesWithTag(String tag) throws Untouched {

        if(!this.currentRegion.hasServicesWithTag(tag))
            throw new Untouched();


        return this.currentRegion.listServicesWithTag(tag);

    }

    @Override
    public String mostRelevantService(String studentName, String type) throws InvalidType, DoesNotExist, Untouched {

        Student student = this.currentRegion.getStudent(studentName);

        if(!(type.equals(LEISURE) || type.equals(LODGING) || type.equals(EATING)))
            throw new InvalidType();
        else if(student == null)
            throw new DoesNotExist();
        else if(!this.currentRegion.hasServicesType(type))
            throw new Untouched();


        return this.currentRegion.findMostRelevantService(student, type);

    }



    @Override
    public String getAreaName() {
        return currentRegion.getName();
    }

    @Override
    public String getServiceName(String name) {
        return this.currentRegion.getService(name).getName();
    }

    @Override
    public String getStudentName(String name) {
        return this.currentRegion.getStudent(name).getName();
    }

    @Override
    public boolean isStudentDistracted(String name, String locationName) {
        Student stu = this.currentRegion.getStudent(name);
        Service loc = this.currentRegion.getService(locationName);
        if(stu instanceof Thrifty && loc instanceof Eating) return ((Thrifty) stu).isDistracted(loc);
        else return false;
    }


    /**
     * Private function that checks if the file exists in the folder data
     * @param fileName the name of the file
     * @return if the file exists
     */
    private static boolean fileExists(String fileName) {
        File file = new File("data", fileName.replace(" ",""));
        return file.exists();
    }

    /**
     * @param top the top bound
     * @param left the left bound
     * @param bottom the bottom bound
     * @param right the right bound
     * @return if the area is valid
     */
    private boolean invalidArea(long top, long left, long bottom, long right) {
        return top <= bottom || right <= left;
    }
}
