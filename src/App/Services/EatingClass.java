package App.Services;

import App.Students.Student;
import dataStructures.*;

public class EatingClass extends ServiceAbstractClass implements Eating{

    private final TwoWayList<Student> inService;

    private final int capacity;

    private int numOfPeople;

    /**
     * Constructor for ServiceAbstractClass.
     *
     * @param latitude  the latitude of the service
     * @param longitude the longitude of the service
     * @param price     the price of the service
     * @param capacity     the capacity of the service
     * @param name      the name of the service
     * @param type      the type of the service
     */
    public EatingClass(long latitude, long longitude, int price, int capacity, String name, String type) {
        super(latitude, longitude, price, name, type);

        this.capacity = capacity;

        inService = new DoublyLinkedList<>();

        numOfPeople = 0;
    }

    @Override
    public boolean isFull() {
        return numOfPeople == capacity;
    }

    @Override
    public void addStudent(Student student) {
        inService.addLast(student);
        numOfPeople++;
    }

    @Override
    public void removeStudent(Student student) {
        numOfPeople--;
        inService.remove(inService.indexOf(student));
    }

    @Override
    public boolean hasStudent(Student stu) {
        return inService.indexOf(stu) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return inService.isEmpty();
    }

    @Override
    public TwoWayList<Student> getStudents() {
        return inService;
    }
}
