package App.Students;

import App.Services.Service;

public interface Thrifty extends Student{

    /**
     * if the student is distracted by the location
     * @param location the location to check
     * @return true if the student is distracted, false otherwise
     */
    boolean isDistracted(Service location);
}
