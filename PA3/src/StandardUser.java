/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.util.ArrayList;
import java.util.List;

public class StandardUser extends User {

    // Message to append when fetching non-text message
    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";
    private final int MESSAGE_COUNT = 10;

    public StandardUser(String username, String bio) {
        super(username, bio);
    }

    public String fetchMessage(MessageExchange me) {
        if (me == null) {
            throw new IllegalArgumentException();
        }
        String message = "";
        int numMessages = me.getLog().size() / MESSAGE_COUNT;
        for( int i = 0; i < numMessages; i++){
            if (me.getLog().get(me.getLog().size() - i - 1) instanceof TextMessage) {
                message += me.getLog().get(me.getLog().size() - i - 1).getContents() + "\n";
            } else {
                message += FETCH_DENIED_MSG + '\n';
            }
        }
        return message;
    }

    public String displayName() {
        return this.username;
    }
}
