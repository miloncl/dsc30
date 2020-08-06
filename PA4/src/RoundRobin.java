public class RoundRobin {

    // constants
    private static final int DEFAULT_QUANTUM =  3;
    private static final String TASK_HANDLED = "All tasks are already handled.";

    // instance variables
    private DoublyLinkedList<Task> waitlist, finished;
    private int quantum, burstTime, waitTime;

    public RoundRobin(Task[] toHandle) {
        this(DEFAULT_QUANTUM, toHandle);
    }

    public RoundRobin(int quantum, Task[] toHandle) {

        if (quantum < 1) {
            throw new IllegalArgumentException();
        } else if (toHandle.equals(null) || toHandle.length == 0) {
            throw new IllegalArgumentException();
        }

        waitlist = new DoublyLinkedList<Task>();
        finished = new DoublyLinkedList<Task>();
        this.quantum = quantum;
        burstTime = 0;
        waitTime = 0;

        for (Task i: toHandle) {
            waitlist.add(i);
        }
    }

    public String handleAllTasks() {

        if (waitlist.isEmpty()) {
            return TASK_HANDLED;
        }

        while (waitlist.size() > 0) {
            Task newTask = waitlist.get(0);
            for (int j = 0; j < quantum; j++) {
                newTask.handleTask();
                burstTime += 1;
                waitTime += waitlist.size()-1;

                if(newTask.isFinished()) {
                    finished.add(newTask);
                    waitlist.remove(0);
                    break;
                }
            }
            if(newTask.isFinished() == false) {
                waitlist.remove(0);
                waitlist.add(newTask);
            }
        }

        String returnString = "All tasks are handled within " + burstTime + " units of " +
                "burst time and " + waitTime + " units of wait time. The tasks are finished " +
                "in this order:\n" + finished.get(0).toString();

        for(int x = 1; x < finished.size(); x++) { //adds string representations in order
            returnString += " -> " + finished.get(x).toString();
        }

        return returnString;

    }

    /**
     * Main method for testing.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Task[] test1 = {new Task("A",  3), new Task("B", 4), new Task("C", 4),
                new Task("D", 12), new Task("E", 6), new Task("F", 3)};
        RoundRobin rr1 = new RoundRobin(test1);     // Quantum: 3, ToHandle: test1
        System.out.println(rr1.handleAllTasks());   // Burst: 32, Wait: 86, Order: AFBCED
        System.out.println();
        System.out.println(rr1.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test2 = {new Task("A", 9), new Task("B", 8), new Task("C", 6),
                new Task("D", 4), new Task("E", 4), new Task("F", 3)};
        RoundRobin rr2 = new RoundRobin(4, test2);  // Quantum: 4, ToHandle: test2
        System.out.println(rr2.handleAllTasks());   // Burst: 34, Wait: 123, Order: DEFBCA
        System.out.println();
        System.out.println(rr2.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test3 = {new Task("A", 7), new Task("B", 5), new Task("C", 3), new Task("D", 1),
                new Task("E", 2), new Task("F", 4), new Task("G", 6), new Task("H", 8)};
        RoundRobin rr3 = new RoundRobin(test3);     // Quantum: 3, ToHandle: test3
        System.out.println(rr3.handleAllTasks());   // Burst: 36, Wait: 148, Order: CDEBFGAH
        System.out.println();
        System.out.println(rr3.handleAllTasks());   // TASK_HANDLED
        System.out.println();
    }
}