package App;

import App.Services.*;
import App.Students.Student;
import App.Students.StudentsComparator;
import App.Students.Thrifty;
import dataStructures.*;

public class RegionClass implements Region {

    private int savedOrderCounter;

    private final long topBound;
    private final long lowBound;
    private final long leftBound;
    private final long rightBound;

    private final String regionName;

    private final List<Student> students;
    private final List<Service> services;

    private final SortedList<Student> sortedStudents;
    private final SortedList<Service> sortedRatingServices;

    private int numOfEthnicities;
    private final List<String> ethnicityList;



    /**
     * Constructor for RegionClass
     * @param topBound the top boundary of the region
     * @param lowBound the low boundary of the region
     * @param leftBound the left boundary of the region
     * @param rightBound the right boundary of the region
     * @param regionName the name of the region
     */
    public RegionClass(long topBound, long lowBound, long leftBound, long rightBound, String regionName) {
        this.topBound = topBound;
        this.lowBound = lowBound;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.regionName = regionName;

        this.numOfEthnicities = 30;
        this.ethnicityList = new ListInArray<>(numOfEthnicities);

        this.students = new DoublyLinkedList<>();
        this.services = new DoublyLinkedList<>();

        this.sortedStudents = new SortedDoublyLinkedList<>(new StudentsComparator());
        this.sortedRatingServices = new SortedDoublyLinkedList<>(new ServicesComparator());
    }



    @Override
    public String getName() {
        return this.regionName;
    }

    @Override
    public boolean isValid(long latitude, long longitude) {
        return this.topBound >= latitude
                && latitude >= this.lowBound
                && this.leftBound <= longitude
                && longitude <= this.rightBound;
    }

    @Override
    public Service getService(String name) {
        Iterator<Service> iterator = services.iterator();
        while(iterator.hasNext()) {
            Service next = iterator.next();
            if (next.getName().equalsIgnoreCase(name)) return next;
        }

        return null;
    }

    @Override
    public void addService(Service service) {
        if(services.indexOf(service) < 0)
            services.addLast(service);
        if(sortedRatingServices.get(service) == null)
            sortedRatingServices.add(service);
    }

    @Override
    public boolean isEmpty() {
        return services.isEmpty();
    }

    @Override
    public Iterator<Service> listAllServices() {
        return services.iterator();
    }

    @Override
    public void addStudent(Student student) {
        students.addLast(student);
        if(!sortedStudents.contains(student))sortedStudents.add(student);
    }

    @Override
    public boolean hasEthnicity(String country) {
        if(country.equalsIgnoreCase("all")) return true;
        return ethnicityList.indexOf(country.trim().toLowerCase()) >= 0;
    }

    @Override
    public boolean isServiceFull(String name) {
        Lodging lodgingService = (Lodging) getService(name);
        return lodgingService.isFull();
    }

    @Override
    public Student getStudent(String name) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student next = iterator.next();
            if (next.getName().equalsIgnoreCase(name)) return next;
        }

        return null;
    }

    @Override
    public void removeStudent(String name) {
        Student student = getStudent(name);
        String ethnicity = getStudent(name).getEthnicity();
        getService(student.getHome().getName()).removeStudent(student);
        sortedStudents.remove(student);
        students.remove(students.indexOf(student));
        if(!containsEthnicity(ethnicity))
            ethnicityList.remove(ethnicityList.indexOf(ethnicity.toLowerCase()));
    }

    private boolean containsEthnicity(String ethnicity) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext())
            if(it.next().getEthnicity().equalsIgnoreCase(ethnicity)) return true;

        return false;
    }

    @Override
    public boolean hasLodging(String lodgingName) {
        Iterator<Service> iterator = services.iterator();
        while(iterator.hasNext()) {
            Service next = iterator.next();
            if (next instanceof Lodging && next.getName().equalsIgnoreCase(lodgingName))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasStudents() {
        return !students.isEmpty();
    }

    @Override
    public void addEthnicity(String country) {
        if(numOfEthnicities == 0)
            ethnicityList.addFirst(country.trim().toLowerCase());
        else ethnicityList.addLast(country.trim().toLowerCase());
        numOfEthnicities++;
    }

    @Override
    public Iterator<Student> listStudents(String from) {
        if(from.equalsIgnoreCase("all"))
            return sortedStudents.iterator();
        else
            return new FilterIterator<>(students.iterator(), new IsFrom(from));
    }

    @Override
    public Iterator<Student> listUsersIn(Service service, String order) {

        TwoWayList<Student> ret = new DoublyLinkedList<>();
        Iterator<Student> it = students.iterator();
        while(it.hasNext()){
            Student next = it.next();
            if(next.getLocation().equals(service)) ret.addLast(next);
        }
        if(order.equals(">")){
            return ret.iterator();
        } else {
            return ret.twoWayiterator();
        }
    }

    @Override
    public String whereStudent(Student student) {
        Service location = student.getLocation();
        return String.format("%s is at %s %s (%d, %d).",
                student.getName(), location.getName(),
                location.getType(), location.getLatitude(),
                location.getLongitude());
    }

    @Override
    public Iterator<Service> listServicesByReview() {
        return sortedRatingServices.iterator();
    }

    @Override
    public boolean hasServicesType(String type) {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()) {
            if (it.next().getType().equalsIgnoreCase(type))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasServicesTypeRate(String type, int numericRate) {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            if (next.getType().equals(type))
                if (next.getAverageRating() == numericRate) return true;
        }
        return false;
    }

    @Override
    public boolean hasServicesWithTag(String tag) {
        Iterator<Service> it = services.iterator();
        while (it.hasNext())
            if(it.next().isTagged(tag)) return true;

        return false;
    }

    @Override
    public Iterator<Service> getRankedServices(int numericRate, String type, Student student) {
        Comparator<Service> comparator = new DistanceComparator(student);

        SortedList<Service> ret = new SortedDoublyLinkedList<>(comparator);

        Iterator<Service> it = services.iterator();

        long minDistance = Integer.MAX_VALUE;

        while(it.hasNext()) {
            Service next = it.next();
            if (next.getAverageRating() == numericRate && next.getType().equalsIgnoreCase(type))
                if(next.getDistance(student) < minDistance)
                    minDistance = next.getDistance(student);
        }

        it.rewind();

        while(it.hasNext()){
            Service next = it.next();
            if(next.getAverageRating() == numericRate
                    && next.getDistance(student) == minDistance
                    && next.getType().equalsIgnoreCase(type))
                ret.add(next);
        }

        return ret.iterator();
    }

    @Override
    public Iterator<Service> listServicesWithTag(String tag) {
        List<Service> ret = new DoublyLinkedList<>();
        Iterator<Service> it = services.iterator();
        while (it.hasNext()){
            Service next = it.next();
            if(next.isTagged(tag)) ret.addLast(next);
        }

        return ret.iterator();
    }

    @Override
    public String findMostRelevantService(Student student, String type) {
        if(student instanceof Thrifty){
            Iterator<Service> it = services.iterator();
            Service ret = null;
            int MinPrice = Integer.MAX_VALUE;
            while(it.hasNext()){
                Service next = it.next();
                if(next.getType().equals(type) && next.getPrice() < MinPrice) {
                    MinPrice = next.getPrice();
                    ret = next;
                }
            }
            return ret.getName();
        } else {
            Iterator<Service> it = sortedRatingServices.iterator();
            while(it.hasNext()){
                Service next = it.next();
                if(next.getType().equals(type)) return next.getName();
            }
            return null;
        }
    }

    @Override
    public void removeServiceFromSorted(Service loc) {
        sortedRatingServices.remove(loc);
    }

    @Override
    public void addServiceToSorted(Service loc) {
        sortedRatingServices.add(loc);
    }


    @Override
    public void setSavedOrderCounter(int counter) {
        this.savedOrderCounter = counter;
    }

    @Override
    public int getSavedOrderCounter() {
        return this.savedOrderCounter;
    }
}
