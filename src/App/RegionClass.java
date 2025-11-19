package App;

import App.Services.*;
import App.Students.Student;
import App.Students.Thrifty;
import dataStructures.*;

public class RegionClass implements Region {

    private int savedOrderCounter;

    private final long topBound;
    private final long lowBound;
    private final long leftBound;
    private final long rightBound;

    private final String regionName;

    //Insertion Order
    private final List<Service> services;
    //sorted by alphabetical order
    private final SortedMap<String, Student> students;


    //sortedRating TAD's is this list?
    private final List<Service> sortedRatingWith5Star;
    private final List<Service> sortedRatingWith4Star;
    private final List<Service> sortedRatingWith3Star;
    private final List<Service> sortedRatingWith2Star;
    private final List<Service> sortedRatingWith1Star;

    private final Map<String, List<Student>> ethnicityList;



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

        this.ethnicityList = new SepChainHashTable<>();

        //MAP
        this.services = new DoublyLinkedList<>();

        //AVL/redBlack
        this.students = new AVLSortedMap<>();

        this.sortedRatingWith5Star = new DoublyLinkedList<>();
        this.sortedRatingWith4Star = new DoublyLinkedList<>();
        this.sortedRatingWith3Star = new DoublyLinkedList<>();
        this.sortedRatingWith2Star = new DoublyLinkedList<>();
        this.sortedRatingWith1Star = new DoublyLinkedList<>();
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
        if(!hasService(service)){
            services.addLast(service);
            sortedRatingWith4Star.addLast(service);
        }
    }

    /**
     *
     * @param service the service to check
     * @return true if the service is in the system
     */
    private boolean hasService(Service service) {
        return services.indexOf(service)<0;
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
        if(students.get(student.getName()) == null)
            students.put(student.getName(), student);

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

        return ethnicityList.get(country) != null;
    }

    @Override
    public boolean isServiceFull(String name) {
        Lodging lodgingService = (Lodging) getService(name);
        return lodgingService.isFull();
    }

    @Override
    public Student getStudent(String name) {
        return students.get(name);
    }

    @Override
    public void removeStudent(String name) {
        //remover da sorted list e da ethnicity list
        name = name.trim().toLowerCase();
        Student stu = students.get(name);

        students.remove(name);

        List<Student> list = ethnicityList.get(stu.getEthnicity());
        //remove student form ethnicity list
        list.remove(list.indexOf(stu));
        if(list.isEmpty()) ethnicityList.remove(stu.getEthnicity());
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
            List<Student> list = ethnicityList.get(from);
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
        List<Service> ret = mergeLists(sortedRatingWith5Star,
                sortedRatingWith4Star,
                sortedRatingWith3Star,
                sortedRatingWith2Star,
                sortedRatingWith1Star);
        return ret.iterator();
    }

    private List<Service> mergeLists(List<Service> l1, List<Service> l2,
                                     List<Service> l3, List<Service> l4, List<Service> l5) {
        List<Service> ret = new DoublyLinkedList<>();
        List<List<Service>> lists = new ListInArray<>(5);
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
        Iterator<Service> it = mergeLists(sortedRatingWith5Star,
                sortedRatingWith4Star,
                sortedRatingWith3Star,
                sortedRatingWith2Star,
                sortedRatingWith1Star).iterator();
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
            if(next.getType().equals(type) && next.getPrice() < MinPrice) {
                MinPrice = next.getPrice();
            }
        }
        it.rewind();

        while(it.hasNext()) {
            Service next = it.next();
            if(next.getType().equalsIgnoreCase(type) && next.getPrice() == MinPrice)
                ret = next;
        }

        return ret.getName();
    }

    @Override
    public void removeServiceFromSorted(Service loc) {
        int rating = (int) loc.getAverageRating();
        switch (rating){
            case 1 -> sortedRatingWith1Star.remove(sortedRatingWith1Star.indexOf(loc));
            case 2 -> sortedRatingWith2Star.remove(sortedRatingWith2Star.indexOf(loc));
            case 3 -> sortedRatingWith3Star.remove(sortedRatingWith3Star.indexOf(loc));
            case 4 -> sortedRatingWith4Star.remove(sortedRatingWith4Star.indexOf(loc));
            case 5 -> sortedRatingWith5Star.remove(sortedRatingWith5Star.indexOf(loc));
        }
    }

    @Override
    public void addServiceToSorted(Service loc) {
        int rating = (int) loc.getAverageRating();
        switch (rating){
            case 1 -> sortedRatingWith1Star.addLast(loc);
            case 2 -> sortedRatingWith2Star.addLast(loc);
            case 3 -> sortedRatingWith3Star.addLast(loc);
            case 4 -> sortedRatingWith4Star.addLast(loc);
            case 5 -> sortedRatingWith5Star.addLast(loc);
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
}
