/*
  Name: Milon Lonappan
  PID:  A15231106
 */

import java.time.LocalDate;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Messenger Application Test
 * @author Milon Lonappan
 * @since  04/19/2020
 */
public class MessengerApplicationTest {

    /*
      Messages defined in starter code. Remember to copy and paste these strings to the
      test file if you cannot directly access them. DO NOT change the original declaration
      to public.
     */
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    /*
      Global test variables. Initialize them in @Before method.
     */
    PremiumUser marina;
    MessageExchange room;

    /*
      The date used in Message and its subclasses. You can directly
      call this in your test methods.
     */
    LocalDate date = LocalDate.now();

    @Before
    public void setup() {
        marina = new PremiumUser("Marina", "Instructor");
        room = new ChatRoom();
    }

    /*
      Recap: Assert exception without message
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new PremiumUser("Marina", null);
    }

    /*
      Assert exception with message
     */
    @Test
    public void testPhotoMessageThrowsODE() {
        try {
            PhotoMessage pm = new PhotoMessage(marina, "PA02.zip");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    User user1 = new StandardUser("Sowrya", "CS Student");
    User user2 = new StandardUser("Abhinay", "Bio Student");
    User user3 = new StandardUser("Vinay", "Econ Student");

    User premUser1 = new PremiumUser("Elakya", "Comm Student");
    User premUser2 = new PremiumUser("Sanskriti", "Cog Sci Studnet");
    User premUser3 = new PremiumUser("Saachi", "Law Student");

    @Test
    public void setBioTest() {
        user1.setBio("Amazon intern");
        assertEquals("Amazon intern", user1.displayBio());
    }


    @Test
    public void photoGetContents() throws Exception {
        boolean exceptionThrown = false;
        try {
            Message photo = new PhotoMessage(user1, "hi.jpg");
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        Message photo = new PhotoMessage(premUser1, "hi.jpg");
        assertEquals("<Premium> Elakya [2020-04-20]: Picture at hi.jpg", photo.getContents());

        exceptionThrown = false;
        try {
            Message photo1 = new PhotoMessage(user1, "holi.asd");
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        Message photo2 = new PhotoMessage(premUser2, "lol.png");
        assertEquals("<Premium> Sanskriti [2020-04-20]: Picture at lol.png", photo2.getContents());

        Message photo3 = new PhotoMessage(premUser3, "yell.jpg");
        assertEquals("<Premium> Saachi [2020-04-20]: Picture at yell.jpg", photo3.getContents());

        assertEquals(premUser1, photo.getSender());
        assertEquals(premUser2, photo2.getSender());
    }

    @Test
    public void stickerGetContents() throws Exception {
        boolean exceptionThrown = false;
        try {
            Message sticker = new StickerMessage(user2, "MilonStickerPack/LOLFace");
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        Message sticker = new StickerMessage(premUser3, "MilonStickerPack/LOLFace");
        assertEquals("<Premium> Saachi [2020-04-20]: Sticker [LOLFace] from Pack [MilonStickerPack]",
                sticker.getContents());

        Message sticker1 = new StickerMessage(premUser2, "MilonStickerPack/LOLFace");
        assertEquals("<Premium> Sanskriti [2020-04-20]: Sticker [LOLFace] from Pack [MilonStickerPack]",
                sticker1.getContents());

        Message sticker2 = new StickerMessage(premUser1, "MilonStickerPack/LOLFace");
        assertEquals("<Premium> Elakya [2020-04-20]: Sticker [LOLFace] from Pack [MilonStickerPack]",
                sticker2.getContents());

        assertEquals(premUser1, sticker2.getSender());
        assertEquals(premUser2, sticker1.getSender());
    }

    @Test
    public void textGetContents() throws Exception{
        Message text = new TextMessage(user1, "Hello guys");
        assertEquals("Sowrya [2020-04-20]: Hello guys", text.getContents());
        Message text2 = new TextMessage(user2, "How is everyone");
        assertEquals("Abhinay [2020-04-20]: How is everyone", text2.getContents());
        Message text3 = new TextMessage(premUser1, "I am good");
        assertEquals("<Premium> Elakya [2020-04-20]: I am good", text3.getContents());
        assertEquals(user2, text2.getSender());
        assertEquals(user1, text.getSender());

    }

    ChatRoom students = new ChatRoom();
    PhotoRoom photoStudents = new PhotoRoom();

    @Test
    public void checkUsers() throws Exception{
        students.addUser(user1);
        students.addUser(user3);
        students.addUser(premUser1);
        assertEquals(students.getUsers().size(), 3);

        photoStudents.addUser(premUser1);
        photoStudents.addUser(premUser2);
        photoStudents.addUser(premUser3);
        assertEquals(photoStudents.getUsers().size(), 3);

        Message text3 = new TextMessage(premUser1, "I am good");
        Message text2 = new TextMessage(user2, "How is everyone");
        Message text = new TextMessage(user1, "Hello guys");
        Message sticker2 = new StickerMessage(premUser1, "MilonStickerPack/LOLFace");
        Message photo3 = new PhotoMessage(premUser3, "yell.jpg");

        assertFalse(photoStudents.recordMessage(sticker2));
        assertTrue(photoStudents.recordMessage(photo3));

        assertTrue(students.recordMessage(text));
        assertTrue(students.recordMessage(text2));

        students.removeUser(user1);
        photoStudents.removeUser(premUser3);
        students.removeUser(user3);
        assertEquals(students.getUsers().size(), 1);
        assertEquals(photoStudents.getUsers().size(), 2);
    }
}
