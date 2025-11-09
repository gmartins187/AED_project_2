package App.Students;

import App.Services.Eating;
import App.Services.Service;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

public class ThriftyClass extends StudentAbstractClass implements Thrifty {

    private final List<Service> EatingVisited;

    /**
     * Constructor for StudentAbstractClass.
     *
     * @param name           the name of the student
     * @param ethnicity      the ethnicity of the student
     * @param currentService the current service the student is at and their home
     */
    public ThriftyClass(String name, String ethnicity, Service currentService, String type) {
        super(name, ethnicity, currentService, type);
        EatingVisited = new DoublyLinkedList<>();
    }


    @Override
    public boolean hasnotVisited() {
        return false;
    }

    @Override
    public Iterator<Service> getVisitedPlaces() {
        return null;
    }

    @Override
    public void pingService(Service service) {
        if(service instanceof Eating) EatingVisited.addLast(service);
    }

    @Override
    public boolean isDistracted(Service location) {
        Iterator<Service> it = EatingVisited.iterator();
        while(it.hasNext()){
            if(it.next().getPrice() < location.getPrice()) return true;
        }
        return false;
    }
}
