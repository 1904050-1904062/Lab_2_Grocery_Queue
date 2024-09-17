public class Main {
    public static void main(String[] args) {
        int simulationMinutes = 2; // Simulate for 2 minutes

        // 3 cashiers, max queue length 2 (excluding customer being served)
        GroceryQueues groceryQueues = new GroceryQueues(3, 2);  

        QueueSimulator simulator = new QueueSimulator(simulationMinutes, groceryQueues);
        simulator.simulate();
        simulator.printResults();
    }
}
