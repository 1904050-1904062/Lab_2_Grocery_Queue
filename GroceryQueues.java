import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroceryQueues {
    private List<GroceryQueue> queues;
    private int maxQueueLength;

    public GroceryQueues(int numberOfQueues, int maxQueueLength) {
        this.queues = new ArrayList<>();
        for (int i = 0; i < numberOfQueues; i++) {
            queues.add(new GroceryQueue(maxQueueLength));

            // Create cashier threads for each queue
            new Thread(new Cashier(i + 1, queues.get(i))).start();
        }
        this.maxQueueLength = maxQueueLength;
    }

    public boolean addCustomer(Customer customer) {
        GroceryQueue bestQueue = null;
        int minQueueSize = Integer.MAX_VALUE;
        for (GroceryQueue queue : queues) {
            int size = queue.getQueueSize();
            if (size < minQueueSize) {
                minQueueSize = size;
                bestQueue = queue;
            }
        }

        if (bestQueue != null && minQueueSize < maxQueueLength) {
            return bestQueue.addCustomer(customer);
        } else {
            return false;
        }
    }

    // Cashier threads process customers from their respective queues
    private class Cashier implements Runnable {
        private int cashierId;
        private GroceryQueue queue;

        public Cashier(int cashierId, GroceryQueue queue) {
            this.cashierId = cashierId;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) { // Continuous service loop
                Customer customer = queue.serveCustomer();
                if (customer != null) {
                    try {
                        Thread.sleep(customer.getServiceTime() * 1000L); // Simulate service time
                        customer.setServed(true);
                        System.out.println("Cashier " + cashierId + " served a customer.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
