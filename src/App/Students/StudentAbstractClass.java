package App.Students;

import App.Services.*;

public abstract class StudentAbstractClass implements Student{

    private Lodging home;
    private Service Location;

    private final String type;
    private final String name;
    private final String ethnicity;


    /**
     * Constructor for StudentAbstractClass.
     * @param name the name of the student
     * @param ethnicity the ethnicity of the student
     * @param currentService the current service the student is at and their home
     */
    public StudentAbstractClass(String name, String ethnicity, Service currentService, String type){
        this.name = name;
        this.home = (Lodging) currentService;
        this.Location = currentService;
        this.ethnicity = ethnicity;
        this.type = type;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getType(){
        return this.type;
    }

    @Override
    public Service getLodging() {
        return home;
    }



    @Override
    public void setLocation(Service newLocation){
        if(this.home != this.Location)
            this.Location.removeStudent(this);
        this.Location = newLocation;
        this.Location.addStudent(this);
    }

    @Override
    public void setHome(Lodging home) {

        if (this.home == this.Location) {
            this.home.removeStudent(this);
        } else {
            this.home.removeStudent(this);
            this.Location.removeStudent(this);
        }

        home.addStudent(this);

        this.home = home;
        this.Location = home;
    }




    @Override
    public Lodging getHome(){
        return this.home;
    }

    @Override
    public Service getLocation(){
        return this.Location;
    }

    @Override
    public String getEthnicity(){
        return ethnicity;
    }
}
