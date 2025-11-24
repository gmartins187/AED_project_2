package App.Services;

import App.Students.Student;
import dataStructures.TwoWayList;

public class LeisureClass extends ServiceAbstractClass implements Leisure{

    private final int priceWithDiscount;

    /**
     * Constructor for ServiceAbstractClass.
     *
     * @param latitude  the latitude of the service
     * @param longitude the longitude of the service
     * @param price     the price of the service
     * @param name      the name of the service
     * @param type      the type of the service
     */
    public LeisureClass(long latitude, long longitude, int price, int value, String name, String type) {
        super(latitude, longitude, price, name, type);
        priceWithDiscount = price * (100 - value) / 100;
    }



    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void addStudent(Student student) {
    }

    @Override
    public void removeStudent(Student student) {
    }

    @Override
    public boolean hasStudent(Student stu) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public TwoWayList<Student> getStudents() {
        return null;
    }

    @Override
    public int getPrice(){
        return this.priceWithDiscount;
    }
}
