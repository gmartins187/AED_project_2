package App.Students;

import dataStructures.Comparator;

public class StudentsComparator implements Comparator<Student> {
    /**
     * @param x one of the students
     * @param y one of the students
     * @return comparator to put the order in alphabetical order in this case by the name
     */
    @Override
    public int compare(Student x, Student y) {
        return x.getName().compareTo(y.getName());
    }
}
