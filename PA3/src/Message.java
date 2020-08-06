/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.time.LocalDate;

public abstract class Message {

    // Use these variable names as the msgType argument of sendMessage()
    // DO NOT MODIFY!
    public static final int TEXT    = 1000;
    public static final int PHOTO   = 1001;
    public static final int STICKER = 1002;

    // Error message to use in OperationDeniedException
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";

    // instance variables
    private LocalDate date;
    private User sender;
    protected String contents;

    public Message(User sender) {
        this.date = LocalDate.now();
        if (sender == null){
            throw new IllegalArgumentException();
        } else {
            this.sender = sender;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public User getSender() {
        return this.sender;
    }

    public abstract String getContents();

}