package App.Services;

import App.Students.Student;
import dataStructures.Comparator;

public class DistanceComparator implements Comparator<Service> {


    private final Student student;

    public DistanceComparator(Student student){
        this.student = student;
    }


    @Override
    public int compare(Service x, Service y) {
        if(x.getDistance(student) == y.getDistance(student)) return 0;
        else if(x.getDistance(student) >= y.getDistance(student)) return 1;
        else if(x.getDistance(student) <= y.getDistance(student)) return -1;

        return -2;
    }
}
