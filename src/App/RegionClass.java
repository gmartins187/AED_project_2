package App;

import App.Services.*;
import App.Students.Student;
import App.Students.Thrifty;
import dataStructures.*;

public class RegionClass implements Region {

    private int savedOrderCounter;

    private final long topBound;
    private final long bottomBound;
    private final long leftBound;
    private final long rightBound;

    private final String regionName;

    private final int DEFAULT_RATE = 4;

    //sorted by alphabetical order
    private final SortedMap<String, Student> students;
    //students Map
    private final Map<String,Student> studentMap;


    //sortedRating TAD's is this list?
    private final List<List<Service>> sortedRatingServices;
    //Insertion Order
    private final List<Service> services;
    //Map of services
    private final Map<String, Service> serviceMap;


    private final Map<String, List<Student>> ethnicityList;


    /**
     * Constructor for RegionClass
     * @param topBound the top boundary of the region
     * @param bottomBound the low boundary of the region
     * @param leftBound the left boundary of the region
     * @param rightBound the right boundary of the region
     * @param regionName the name of the region
     */
    public RegionClass(long topBound, long bottomBound, long leftBound, long rightBound, String regionName) {
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.regionName = regionName;

        this.ethnicityList = new SepChainHashTable<>();

        //AVL/redBlack
        this.students = new AVLSortedMap<>();
        this.studentMap = new SepChainHashTable<>();

        //MAP mais uma struct de data para consulta
        this.services = new DoublyLinkedList<>();

        sortedRatingServices = new ListInArray<>(5);
        for (int i=0; i<5; i++)
            sortedRatingServices.add(i, new DoublyLinkedList<>());

        serviceMap = new SepChainHashTable<>();
    }



    @Override
    public String getName() {
        return this.regionName;
    }

    @Override
    public boolean isValid(long latitude, long longitude) {
        return this.topBound >= latitude
                && latitude >= this.bottomBound
                && this.leftBound <= longitude
                && longitude <= this.rightBound;
    }

    @Override
    public Service getService(String name) {
        return this.serviceMap.get(name.toLowerCase());
    }

    @Override
    public void addService(Service service) {
        if(!hasService(service)){
            services.addLast(service);
            sortedRatingServices.get(DEFAULT_RATE-1).addLast(service);
            serviceMap.put(service.getName().toLowerCase(), service);
        }
    }

    /**
     *
     * @param service the service to check
     * @return true if the service is in the system
     */
    private boolean hasService(Service service) {
        return serviceMap.get(service.getName()) != null;
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
        String stuName = student.getName().toLowerCase();

        if(studentMap.get(stuName) == null){
            students.put(stuName, student);
            studentMap.put(stuName, student);
        }

        //adds ethnicity
        String stuEthnicity = student.getEthnicity().trim().toLowerCase();
        if(ethnicityList.get(stuEthnicity) == null){
            ethnicityList.put(stuEthnicity, new DoublyLinkedList<>());
            List<Student> list = ethnicityList.get(stuEthnicity);
            list.addLast(student);
        } else{
            List<Student> list = ethnicityList.get(stuEthnicity);
            list.addLast(student);
        }
    }

    @Override
    public boolean hasEthnicity(String country) {
        if(country.equalsIgnoreCase("all"))
            return !isEmpty();

        return ethnicityList.get(country.toLowerCase()) != null;
    }

    @Override
    public boolean isServiceFull(String name) {
        Lodging lodgingService = (Lodging) getService(name);
        return lodgingService.isFull();
    }

    @Override
    public Student getStudent(String name) {
        return studentMap.get(name.toLowerCase());
    }

    @Override
    public String removeStudent(String name) {
        //remover da sorted list e da ethnicity list
        name = name.trim().toLowerCase();

        String ret = students.get(name).getName();
        Student stu = students.get(name);

        Service toRemove = stu.getLocation();
        toRemove.removeStudent(stu);

        students.remove(name);
        studentMap.remove(name);

        List<Student> list = ethnicityList.get(stu.getEthnicity().toLowerCase());
        //remove student form ethnicity list
        list.remove(list.indexOf(stu));
        if(list.isEmpty())
            ethnicityList.remove(stu.getEthnicity());

        return ret;
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
    public Iterator<Student> listStudents(String from) {
        if(from.equalsIgnoreCase("all"))
            return students.values();
        else{
            List<Student> list = ethnicityList.get(from.toLowerCase());
            return list.iterator();
        }
    }

    @Override
    public Iterator<Student> listUsersIn(Service service, String order) {
        TwoWayList<Student> ret = service.getStudents();

        if(order.equalsIgnoreCase("<")){
            return ret.twoWayiterator();
        } else{ //(equals(">"))
            return ret.iterator();
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
        List<Service> ret = mergeLists();
        return ret.iterator();
    }

    /**
     * private method that merges 5 lists in 1 in this case used for the rating lists
     *
     * @return the merged list
     */
    private List<Service> mergeLists() {
        List<Service> ret = new DoublyLinkedList<>();

        List<List<Service>> lists = new ListInArray<>(5);
        lists.addLast(sortedRatingServices.get(4));
        lists.addLast(sortedRatingServices.get(3));
        lists.addLast(sortedRatingServices.get(2));
        lists.addLast(sortedRatingServices.get(1));
        lists.addLast(sortedRatingServices.get(0));

        for(int i=0; i<lists.size(); i++){
            Iterator<Service> it = lists.get(i).iterator();
            while(it.hasNext()){
                ret.addLast(it.next());
            }
        }

        return ret;
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
            return findThriftyMostRelevant(type);
        } else {
            return findOtherMostRelevant(type);
        }
    }

    private String findOtherMostRelevant(String type) {
        Iterator<Service> it = mergeLists().iterator();
        while(it.hasNext()){
            Service next = it.next();
            if(next.getType().equals(type)) return next.getName();
        }
        return null;
    }

    private String findThriftyMostRelevant(String type) {
        Iterator<Service> it = services.iterator();
        Service ret = null;

        int MinPrice = Integer.MAX_VALUE;
        while(it.hasNext()){
            Service next = it.next();
            if(next.getType().equalsIgnoreCase(type) && next.getPrice() < MinPrice) {
                MinPrice = next.getPrice();
            }
        }
        it.rewind();

        while(it.hasNext()) {
            Service next = it.next();
            if(next.getType().equalsIgnoreCase(type) && next.getPrice() == MinPrice) {
                ret = next;
                break;
            }
        }

        return ret.getName();
    }

    @Override
    public void removeServiceFromSorted(Service loc, int rate) {
        switch (rate){
            case 1 -> sortedRatingServices.get(0).remove(sortedRatingServices.get(0).indexOf(loc));
            case 2 -> sortedRatingServices.get(1).remove(sortedRatingServices.get(1).indexOf(loc));
            case 3 -> sortedRatingServices.get(2).remove(sortedRatingServices.get(2).indexOf(loc));
            case 4 -> sortedRatingServices.get(3).remove(sortedRatingServices.get(3).indexOf(loc));
            case 5 -> sortedRatingServices.get(4).remove(sortedRatingServices.get(4).indexOf(loc));
        }
    }

    @Override
    public void addServiceToSorted(Service loc, int rate) {
        switch (rate){
            case 1 -> sortedRatingServices.get(0).addLast(loc);
            case 2 -> sortedRatingServices.get(1).addLast(loc);
            case 3 -> sortedRatingServices.get(2).addLast(loc);
            case 4 -> sortedRatingServices.get(3).addLast(loc);
            case 5 -> sortedRatingServices.get(4).addLast(loc);
        }
    }

    @Override
    public void setSavedOrderCounter(int counter) {
        this.savedOrderCounter = counter;
    }

    @Override
    public int getSavedOrderCounter() {
        return this.savedOrderCounter;
    }

    @Override
    public void addReview(Service service, Review review) {
        int oldAvg = (int) service.getAverageRating();

        service.addReview(review);

        int newAvg = (int) service.getAverageRating();

        if(oldAvg != newAvg){
            removeServiceFromSorted(service, oldAvg);
            addServiceToSorted(service, newAvg);
        }
    }

    @Override
    public boolean hasStudentsFrom(String from) {
        if(from.equalsIgnoreCase("all")) //ignore 'all' case
            return true;

        List<Student> ret = ethnicityList.get(from.toLowerCase());
        if(ret!=null)
            return !ret.isEmpty();
        else return false;
    }
}
