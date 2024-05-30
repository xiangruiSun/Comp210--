package assn05;

import java.util.ArrayList;
import java.util.List;

public class SimpleEmergencyRoom {
    private List<Patient> patients;

    public SimpleEmergencyRoom() {
        patients = new ArrayList<>();
    }

    // TODO (Task 1): dequeue
    public Patient dequeue() {
        if (patients.size() == 0){
            return null;
        }
        else{
            Integer Maxpriority = 0;
            int index = 0;
            for(int i = 0; i <= patients.size() - 1; i ++) {
                Integer Currentpriority = (Integer) patients.get(i).getPriority();
                if (Currentpriority.compareTo(Maxpriority) > 0){
                    Maxpriority = Currentpriority;
                    index = i;
                }
            }
            Patient rPatient = patients.get(index);
            patients.remove(index);
            return rPatient;
        }
    }

    public <V, P> void addPatient(V value, P priority) {
        Patient patient = new Patient(value, (Integer) priority);
        patients.add(patient);
    }

    public <V> void addPatient(V value) {
        Patient patient = new Patient(value);
        patients.add(patient);
    }

    public List getPatients() {
        return patients;
    }

    public int size() {
        return patients.size();
    }

}
