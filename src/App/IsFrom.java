package App;

import App.Students.Student;
import dataStructures.Predicate;

public class IsFrom implements Predicate<Student>{

    private final String from;

    public IsFrom(String from){
        this.from=from;
    }

    @Override
    public boolean check(Student student) {
        return this.from.equalsIgnoreCase(student.getEthnicity());
    }
}
