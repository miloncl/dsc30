/*
    Name: Milon Lonappan
    PID:  A15231106
 */

/**
 * 
 * @author Milon Lonappan
 * @since
 */
public class ImageEditor {

    /* static constants, feel free to add more if you need */
    private static final int MAX_PIXEL_VALUE      = 255;
    private static final int STACKS_INIT_CAPACITY = 30;

    /* instance variables, feel free to add more if you need */
    private int[][] image;
    private IntStack undo;
    private IntStack redo;

    ImageEditor(int[][] image) {
        if (image.length == 0 || image[0].length == 0) {
            throw new IllegalArgumentException();
        }
        int row = image[0].length;
        for(int x = 0; x < image.length; x++) {
            if(image[x].length != row) {
                throw new IllegalArgumentException();
            }
        }
        this.image = image;
        undo = new IntStack(STACKS_INIT_CAPACITY);
        redo = new IntStack(STACKS_INIT_CAPACITY);
    }

    int[][] getImage() {
        return this.image;
    }

    public void scale(int i, int j, double scaleFactor) {
        if(0 > i || i > image.length || 0 > j || j > image[0].length) {
            throw new IndexOutOfBoundsException();
        } else if (0 > scaleFactor) {
            throw new IllegalArgumentException();
        }
        int[] pushVals = {i, j, image[i][j]};
        undo.multiPush(pushVals);
        redo.clear();
        if ((int)(image[i][j] * scaleFactor) > MAX_PIXEL_VALUE) {
            image[i][j] = MAX_PIXEL_VALUE;
        } else {
            image[i][j] = (int)(image[i][j] * scaleFactor);
        }
    }

    public void assign(int i, int j, int color) {
        if(0 > i || i > image.length || 0 > j || j > image[0].length) {
            throw new IndexOutOfBoundsException();
        } else if (0 > color || MAX_PIXEL_VALUE < color) {
            throw new IllegalArgumentException();
        }
        int[] pushVals = {i, j, image[i][j]};
        undo.multiPush(pushVals);
        redo.clear();
        image[i][j] = color;
    }

    public void delete(int i, int j) {
        int[] pushVals = {i, j, image[i][j]};
        undo.multiPush(pushVals);
        redo.clear();
        image[i][j] = 0;
    }

    public boolean undo() {
        if(undo.isEmpty()) {
            return false;
        } else {
            int color = undo.pop();
            int j = undo.pop();
            int i = undo.pop();
            int[] pushVals = {i, j, image[i][j]};
            redo.multiPush(pushVals);
            image[i][j] = color;
            return true;
        }
    }

    public boolean redo() {
        if(redo.isEmpty()) {
            return false;
        } else {
            int color = redo.pop();
            int j = redo.pop();
            int i = redo.pop();
            int[] pushVals = {i, j, image[i][j]};
            undo.multiPush(pushVals);
            image[i][j] = color;
            return true;
        }
    }
}
