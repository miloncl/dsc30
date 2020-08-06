/*
 * Name: Milon Lonappan
 * PID: A15231106
 */

/**
 * 
 * @author Milon Lonappan
 * @since
 */
public class HashTable implements IHashTable {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DEFAULT_CAPACITY = 20;
    private static final double MAX_LOAD_FACTOR = 0.5;
    public static final int DEFAULT_SIZE = 10;
    private static final int two = 2;
    private double loadFactor = 0;
    private int release = 0;
    private static String statsLog = "";
    private int expand = 0;
    private static final int alphabets = 26;



    /* Instance variables */
    private String[] table1, table2; // sub-tables
    private int nElems; // size

    public HashTable() {
        table1 = new String[DEFAULT_SIZE];
        table2 = new String[DEFAULT_SIZE];
        nElems = 0;
    }

    public HashTable(int capacity) {
        if (capacity < MIN_INIT_CAPACITY) {
            throw new IllegalArgumentException();
        }

        int subCapacity;
        if (capacity % two == 1) {
            subCapacity = capacity/two -1;
        } else {
            subCapacity = capacity/two;
        }

        table1 = new String[subCapacity];
        table2 = new String[subCapacity];
        nElems = 0;
    }

    @Override
    public boolean insert(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if(lookup(value)) {
            return false;
        } else {
            this.loadFactor = (double)this.nElems / (double)this.capacity();
            if (loadFactor > MAX_LOAD_FACTOR) {
                this.rehash();
            }

            this.nElems++;
            String returnString = insertHelper(value);
            while (returnString != null) {
                this.rehash();
                returnString = insertHelper(returnString);
            }

            return true;
        }
    }

    private String insertHelper(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        int iCounter = 0;
        String replacedTable = null;
        String insert = value;
        while (iCounter < this.size() + 1) {
            if (iCounter % two == 0) {
                int hashValue = hashOne(insert);
                replacedTable = table1[hashValue];
                table1[hashValue] = insert;
            } else {
                int hashValue = hashTwo(insert);
                replacedTable = table2[hashValue];
                table2[hashValue] = insert;
            }

            insert = replacedTable;
            if (insert == null) {
                return null;
            } else {
                this.release++;
            }
            iCounter++;
        }
        return insert;
    }

    @Override
    public boolean delete(String value) {

        if (value == null) {
            throw new NullPointerException();
        }

        if(!lookup(value)) {
            return false;
        } else {
            int table1Index = hashOne(value);
            int table2Index = hashTwo(value);

            if (table1[table1Index].equals(value)){
                table1[table1Index] = null;
                nElems--;
                return true;
            } else if (table2[table2Index].equals(value)) {
                table2[table2Index] = null;
                nElems--;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean lookup(String value) {

        if (value == null) {
            throw new NullPointerException();
        }

        int table1Index = hashOne(value);
        int table2Index = hashTwo(value);

        if (this.table1[table1Index] != null) {
            if (this.table1[table1Index].equals(value)) {
                return true;
            }
        }
        if (this.table2[table2Index] != null) {
            if (this.table1[table2Index].equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return nElems;
    }

    @Override
    public int capacity() {
        return this.table1.length + this.table2.length;
    }

    /**
     * Get the string representation of the hash table.
     *
     * Format Example:
     * | index | table 1 | table 2 |
     * | 0 | Marina | [NULL] |
     * | 1 | [NULL] | DSC30 |
     * | 2 | [NULL] | [NULL] |
     * | 3 | [NULL] | [NULL] |
     * | 4 | [NULL] | [NULL] |
     *
     * @return string representation
     */
    @Override
    public String toString() {

        String table1;
        String table2;
        String returnString = "| index | table 1 | table2 |" + "\n";
        for (int x = 0; x < this.table1.length; x++) {
            if (this.table1[x] == null) {
                table1 = "[NULL]";
            } else {
                table1 = this.table1[x];
            }

            if (this.table2[x] == null) {
                table2 = "[NULL]";
            } else {
                table2 = this.table2[x];
            }

            returnString += "| " + String.valueOf(x)+ " | " + table1 + " | " + table2 + " |" + "\n";
        }

        return returnString;
    }

    /**
     * Get the rehash stats log.
     *
     * Format Example: 
     * Before rehash # 1: load factor 0.80, 3 evictions.
     * Before rehash # 2: load factor 0.75, 5 evictions.
     *
     * @return rehash stats log
     */
    public String getStatsLog() {
       return statsLog;
    }

    private void rehash() {

        this.loadFactor = (double)this.nElems/(double)this.capacity();
        this.expand++;

        statsLog += "Before rehash # " + String.valueOf(this.expand)+
                ": load factor " + String.format("%1.2f", this.loadFactor) + ", "
                +this.release + " evictions." + "\n";

        this.release = 0;
        this.loadFactor = 0;

        String[] newTable1 = new String[this.table1.length * two];
        String[] newTable2 = new String[this.table2.length * two];

        String[] temp = this.table1;
        String[] temp1 = this.table2;
        this.table1 = newTable1;
        this.table2 = newTable2;

        for (String s : temp) {
            if (s != null) {
                this.insertHelper(s);
            }
        }

        for (String s : temp1) {
            if (s != null) {
                this.insertHelper(s);
            }
        }
    }

    private int hashOne(String value) {
        int hashOne = 0;
        for(int x = 0; x < value.length(); x++) {
            int letter = value.charAt(x);
            hashOne = (hashOne * alphabets +letter) % this.table1.length;
        }

        return hashOne;
    }

    private int hashTwo(String value) {
        int hashTwo = 0;
        for(int x = 0; x < value.length(); x++){
            int left = hashTwo << 5;
            int right = hashTwo >>> (alphabets + 1);
            hashTwo = (left | right) ^ value.charAt(x);
        }
        return Math.abs(hashTwo % this.table2.length);
    }
}
