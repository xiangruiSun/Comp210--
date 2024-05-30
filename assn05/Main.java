package assn05;

public class Main {

    public static void main(String[] args) {
        testP1();
        testP2();
        testP3();
        testP4();
    }

    // test Part 1
    public static void testP1(){
        /*
        Part 1 - Write some tests to convince yourself that your code for Part 1 is working
         */
        SimpleEmergencyRoom exampleRoom = new SimpleEmergencyRoom();
        exampleRoom.addPatient("Michael",5);
        exampleRoom.addPatient("John",4);
        exampleRoom.addPatient("Mary",15);
        Patient rPatient = exampleRoom.dequeue();

        System.out.println(rPatient.getValue()); //should print out "Mary"
    }

    // test Part 2
    public static void testP2(){
       /*
        Part 2 - Write some tests to convince yourself that your code for Part 2 is working
         */
        MaxBinHeapER<String,Integer> exampleHeap = new MaxBinHeapER<>();
        exampleHeap.enqueue("Mary");
        exampleHeap.enqueue("Michael",5);
        System.out.println(exampleHeap.getMax());
        System.out.println(exampleHeap.dequeue()); //These two outputs should be the same.
    }

    /*
    Part 3
     */
    public static void testP3(){
        MaxBinHeapER transfer = new MaxBinHeapER(makePatients());
        Prioritized[] arr = transfer.getAsArray();
        for(int i = 0; i < transfer.size(); i++) {
            System.out.println("Value: " + arr[i].getValue()
                    + ", Priority: " + arr[i].getPriority());
        }
    }

    /*
    Part 4
     */
    public static void testP4() {
               /*
        Part 4 - Write some tests to convince yourself that your code for Part 4 is working
         */
        // Print out the results
        double[] results = compareRuntimes();
        System.out.println(results);
        System.out.println("Performance Comparison:");
        System.out.println("SimpleEmergencyRoom:");
        System.out.println("  Total Time: " + results[0] + " s");
        System.out.println("  Average Time per Dequeue: " + results[1] + " ns");
        System.out.println("MaxBinHeapER:");
        System.out.println("  Total Time: " + results[2] + " s");
        System.out.println("  Average Time per Dequeue: " + results[3] + " ns");
    }

    public static void fillER(MaxBinHeapER complexER) {
        for(int i = 0; i < 100000; i++) {
            complexER.enqueue(i);
        }
    }
    public static void fillER(SimpleEmergencyRoom simpleER) {
        for(int i = 0; i < 100000; i++) {
            simpleER.addPatient(i);
        }
    }

    public static Patient[] makePatients() {
        Patient[] patients = new Patient[10];
        for(int i = 0; i < 10; i++) {
            patients[i] = new Patient(i);
        }
        return patients;
    }

    public static double[] compareRuntimes() {
        // Array which you will populate as part of Part 4
        double[] results = new double[4];

        SimpleEmergencyRoom simplePQ = new SimpleEmergencyRoom();
        fillER(simplePQ);

        // Code for (Task 4.1) Here
        double tTimeSimpleER = 0.0;
        double result1 = System.nanoTime();
        while (simplePQ.size() != 0) {
            simplePQ.dequeue();
        }
        double result2 = System.nanoTime();
        tTimeSimpleER = result2 - result1;
        double aveTimeSimpleER = tTimeSimpleER / 100000.0;
        results[0] = tTimeSimpleER;
        results[1] = aveTimeSimpleER;


        MaxBinHeapER binHeap = new MaxBinHeapER();
        fillER(binHeap);

        // Code for (Task 4.2) Here
        double tTimeHeapER = 0.0;
        double result11 = System.nanoTime();
        while (binHeap.size() != 0) {
            binHeap.dequeue();
        }
        double result21 = System.nanoTime();
        tTimeHeapER = result21 - result11;
        double aveTimeHeapER = tTimeHeapER / 100000.0;
        results[2] = tTimeHeapER;
        results[3] = aveTimeHeapER;

        return results;
    }

}
