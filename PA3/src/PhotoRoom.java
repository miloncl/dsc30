/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.util.ArrayList;

public class PhotoRoom implements MessageExchange {

    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;

    public PhotoRoom() {
        users = new ArrayList<>();
        log = new ArrayList<>();
    }

    public ArrayList<Message> getLog() {
        return this.log;
    }

    public boolean addUser(User u) {
        if (!u.displayName().contains("Premium")) {
            return false;
        } else {
            this.users.add(u);
            return true;
        }
    }

    public void removeUser(User u) {
        this.users.remove(u);
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public boolean recordMessage(Message m) {
        if (m instanceof PhotoMessage) {
            this.log.add(m);
            return true;
        } else {
            return false;
        }
    }

}
