/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.util.ArrayList;

public class PremiumUser extends User {

    // instance variable
    private String customTitle;

    public PremiumUser(String username, String bio) {
        super(username, bio);
        this.customTitle = "Premium";
    }

    public String fetchMessage(MessageExchange me) {
        if (me == null) {
            throw new IllegalArgumentException();
        }
        String messages = "";
        for (int i = 0; i < me.getLog().size(); i++){
            messages += me.getLog().get(i).getContents() + "\n";
        }
        return messages;
    }

    public MessageExchange createPhotoRoom(ArrayList<User> users) {
        if (users == null) {
            throw new IllegalArgumentException();
        }

        MessageExchange newPhotoRoom = new PhotoRoom();
        for (User i : users){
            try {
                i.joinRoom(newPhotoRoom);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }
        try {
            this.joinRoom(newPhotoRoom);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return newPhotoRoom;
    }

    public String displayName() {
        return "<" + this.customTitle + "> " + this.username;
    }

    public void setCustomTitle(String newTitle) {
        this.customTitle = newTitle;
    }
}
