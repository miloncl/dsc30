/*
  Name: Milon Lonappan
  PID:  A15231106
 */

public class StickerMessage extends Message {

    // instance variable
    private String packName;

    public StickerMessage(User sender, String stickerSource)
            throws OperationDeniedException {
        super(sender);
        if(stickerSource == null){
            throw new IllegalArgumentException();
        }
        if (!(sender instanceof PremiumUser)){
            throw new OperationDeniedException(DENIED_USER_GROUP);
        }

        this.contents = stickerSource.split("/")[1];
        this.packName = stickerSource.split("/")[0];
    }

    public String getContents() {
        String userName = super.getSender().displayName();
        String date = super.getDate().toString();
        String returnString = userName + " [" + date + "]: " + "Sticker [" + this.contents + "] from Pack ["
                + this.packName + "]";
        return returnString;
    }

    public String getPackName() {
        return this.packName;
    }
}
