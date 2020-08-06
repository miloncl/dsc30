/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.util.ArrayList;

public class ChatRoom implements MessageExchange {

    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;

    public ChatRoom() {
        users = new ArrayList<>();
        log = new ArrayList<>();
    }

    public ArrayList<Message> getLog() {
        return this.log;
    }

    public boolean addUser(User u) {
        this.users.add(u);
        return true;
    }

    public void removeUser(User u) {
        this.users.remove(u);
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public boolean recordMessage(Message m) {
        this.log.add(m);
        return true;
    }


}