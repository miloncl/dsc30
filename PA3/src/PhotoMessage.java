/*
  Name: Milon Lonappan
  PID:  A15231106
 */

public class PhotoMessage extends Message {

    // Error message to use in OperationDeniedException
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    // input validation criteria
    private static final String[] ACCEPTABLE_EXTENSIONS =
            new String[] {"jpg", "jpeg", "gif", "png", "tif"};

    // instance variable
    private String extension;

    public PhotoMessage(User sender, String photoSource) throws OperationDeniedException {
        super(sender);
        if(photoSource == null) {
            throw new IllegalArgumentException();
        }
        this.contents = photoSource;
        String [] split = photoSource.split("\\.");
        extension = split[1].toLowerCase();

        if (!(sender instanceof PremiumUser)) {
            throw new OperationDeniedException(DENIED_USER_GROUP);
        }

        boolean found = false;
        for (int x = 0; x < ACCEPTABLE_EXTENSIONS.length; x++) {
            if (extension.equals(ACCEPTABLE_EXTENSIONS[x])){
                found = true;
                break;
            }
        }
        if (!found){
            throw new OperationDeniedException(INVALID_INPUT);
        }
    }

    public String getContents() {
        String userName = super.getSender().displayName();
        String date = super.getDate().toString();
        String returnString = userName + " [" + date + "]: " + "Picture at " + this.contents;
        return returnString;
    }

    public String getExtension() {
        return this.extension;
    }

}
