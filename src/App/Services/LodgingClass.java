package App.Services;

import dataStructures.DoublyLinkedList;
import dataStructures.List;
import App.Students.Student;
import dataStructures.ListInArray;

public class LodgingClass extends ServiceAbstractClass implements Lodging {

    private final int capacity;

    private final List<Student> visited;
    private final List<Student> inService;

    private int numOfPeople;

    /**
     * Constructor for ServiceAbstractClass.
     *
     * @param latitude  the latitude of the service
     * @param longitude the longitude of the service
     * @param price     the price of the service
     * @param name      the name of the service
     * @param type      the type of the service
     */
    public LodgingClass(long latitude, long longitude, int price, int capacity, String name, String type) {
        super(latitude, longitude, price, name, type);

        this.capacity = capacity;

        visited = new DoublyLinkedList<>();
        inService = new DoublyLinkedList<>();

        numOfPeople = 0;
    }

    @Override
    public boolean isFull() {
        return numOfPeople == capacity;
    }

    @Override
    public void addStudent(Student student) {
        visited.addLast(student);
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
}
