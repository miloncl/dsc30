/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.util.ArrayList;

public abstract class User {

    // Error message to use in OperationDeniedException
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    // instance variables
    protected String username;
    protected String bio;
    protected ArrayList<MessageExchange> rooms;

    public User(String username, String bio) {
        if(username == null || bio == null) {
            throw new IllegalArgumentException();
        }
        this.username = username;
        this.bio = bio;
        rooms = new ArrayList<MessageExchange>();
    }

    public void setBio(String newBio) {
        if (newBio == null) {
            throw new IllegalArgumentException();
        }
        this.bio = newBio;
    }

    public String displayBio() {
        return this.bio;
    }

    public void joinRoom(MessageExchange me) throws OperationDeniedException {
        if(me == null) {
            throw new IllegalArgumentException();
        }
        else if (rooms.contains(me) || !(me.addUser(this))){
            throw new OperationDeniedException(JOIN_ROOM_FAILED);
        }

        rooms.add(me);
        me.addUser(this);
    }

    public void quitRoom(MessageExchange me) {
        if(me == null) {
            throw new IllegalArgumentException();
        }
        rooms.remove(me);
        me.removeUser(this);
    }

    public MessageExchange createChatRoom(ArrayList<User> users) {
        if (users == null) {
            throw new IllegalArgumentException();
        }

        MessageExchange newChatRoom = new ChatRoom();
        for (User i : users){
            try {
                i.joinRoom(newChatRoom);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }
        try {
            this.joinRoom(newChatRoom);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return newChatRoom;
    }

    public void sendMessage(MessageExchange me, int msgType, String contents) {
        if (me == null || contents == null) {
            throw new IllegalArgumentException();
        }
        if (!me.getUsers().contains(this)){
            throw new IllegalArgumentException();
        }
        Message newMessage;
        try {
            if (msgType == Message.PHOTO) {
                newMessage = new PhotoMessage(this, contents);
            } else if (msgType == Message.STICKER){
                newMessage = new StickerMessage(this, contents);
            } else if (msgType == Message.TEXT){
                newMessage = new TextMessage(this, contents);
            } else {
                throw new IllegalArgumentException(INVALID_MSG_TYPE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        me.recordMessage(newMessage);
    }

    public abstract String fetchMessage(MessageExchange me);

    public abstract String displayName();
}
