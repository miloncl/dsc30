/**
 * Name: Milon Lonappan
 * PID: A15231106
 */

/**
 * This is a Startup class with multiple methods to get a hang of using java
 *
 * @author Milon Lonappan
 * @since 03/30/2020
 */
public class Startup {

    /**
     * This method takes an int array and returns a char array that has ‘E’ at any index i to represent an even number
     * at index i of array arr, and ‘O’ to represent an odd number at the corresponding index of array arr.
     * @param arr an integer array of random numbers
     * @return a character array of E's and O's in correspondence to arr
     */
    public static char[] arrEvenOdd(int[] arr) {
        char[] charArray = new char [arr.length];
        for (int x = 0; x < arr.length; x++) {
            if (arr[x] % 2 == 0) {
                charArray[x] = 'E';
            } else {
                charArray[x] = 'O';
            }
        }
        return charArray;
    }

    /**
     * This method checks if subStr is a substring of mainStr.
     * @param mainStr is a string
     * @param subStr is the string to be checked in the main string
     * @return true if subStr is a substring of mainStr, else false
     */
    public static boolean isSubstring(String mainStr, String subStr) {
        if (subStr == "") {
            return true;
        }
        int subLength = subStr.length();
        if (mainStr.length() < subLength) {
            return false;
        } else if (mainStr.substring(0, subLength).equals(subStr)) {
            return true;
        } else {
            return isSubstring(mainStr.substring(1), subStr);
        }
    }

    /**
     * This method checks if all digits in num are in the same row or column of the numpad.
     * @param num integer to be checked
     * @return true if all numbers in num are in the same row or column, else false
     */
    public static boolean numpadSRC(int num) {
        String[] rows = {"0", "123", "456", "789"};
        String[] columns = {"0147", "0258", "0369"};
        int row = 0;
        int col = 0;
        String newNum = Integer.toString(num);


        if(newNum.contains("0"))
            row++;
        if(newNum.contains("1") || newNum.contains("2") || newNum.contains("3"))
            row++;
        if(newNum.contains("4") || newNum.contains("5") || newNum.contains("6"))
            row++;
        if(newNum.contains("7") || newNum.contains("8") || newNum.contains("9"))
            row++;

        if(newNum.contains("1") || newNum.contains("4") || newNum.contains("7"))
            col++;
        if(newNum.contains("2") || newNum.contains("5") || newNum.contains("8"))
            col++;
        if(newNum.contains("3") || newNum.contains("6") || newNum.contains("9"))
            col++;

        if(row == 1 || col == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method converts arr to a set and returns the converted set.
     * @param arr array of positive integers in randomized order
     * @return converted set with no duplicates
     */
    public static int[] createSet(int[] arr) {
        if (arr.length == 1) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            for(int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        int[] newArr = new int[arr.length];
        int index = 0;

        for (int x = 1; x < arr.length; x++) {
            if (arr[x - 1] != arr[x]) {
                newArr[index++] = arr[x - 1];
            }
        }
        return newArr;
    }

    /**
     * This method checks if set1 is a subset of set2, and returns true if it is (false otherwise).
     * @param set1 the set to be checked if it is a subset of set2
     * @param set2 the set that is being checked in
     * @return true if set1 is a subset of set2, else false
     */
    public static boolean subsetChecker(int[] set1, int[] set2) {
        if(set1.length == 0) {
            return true;
        }
        int x = 0;
        int y = 0;
        for (x = 0; x < set1.length; x++) {
            for (y = 0; y < set2.length; y++) {
                if (set1[x] == set2[y]) {
                    break;
                }
            }
            if (y == set2.length){
                return false;
            }
        }

        return true;
    }

    /**
     * This method uses a recursive binary search to find the position of a target integer between the index left
     * and right (both inclusive) of the input array arr.
     * @param arr the array being searched through
     * @param left left index of the search
     * @param right right index of the search
     * @param target target value being searched
     * @return Return the index of the target if found, return -1 otherwise.
     */
    public static int recursiveBinarySearch (int[] arr, int left, int right, int target) {
        int divider = 2;
        if (right >= left) {
            int middle = left + (right - left) / divider;

            if (arr[middle] == target) {
                return middle;
            }

            if (arr[middle] > target) {
                return recursiveBinarySearch(arr, left, middle - 1, target);
            }

            return recursiveBinarySearch(arr, middle + 1, right, target);
        }

        return -1;
    }

    /**
     * This method encrypts the input string by applying the Atbash cipher on letters (both uppercase and lowercase)
     * and reversing the encrypted string,
     * @param s string to be encrypted and reversed
     * @return the encrypted string
     */
    public static String encryptString(String s) {
        int upperULimit = 91;
        int upperLLimit = 64;
        int lowerULimit = 96;
        int lowerLLimit = 123;
        int length = s.length();
        char[] string = new char[length];
        for(int i = 0; i < s.length(); i++) {
            string[i] = s.charAt(i);
        }

        for(int x = 0; x < string.length; x++){
            if (Character.isLetter(string[x])) {
                if(Character.isUpperCase(string[x])) {
                    int ascii = (int) string[x];
                    int character = upperULimit - (ascii - upperLLimit);
                    string[x] = (char)character;
                }
                if(Character.isLowerCase(string[x])) {
                    int ascii = (int) string[x];
                    int character = lowerULimit - (ascii - lowerLLimit);
                    string[x] = (char) character;
                }
            }
        }
        String returnString = "";
        for(int y = string.length - 1; y >= 0; y--) {
            returnString += string[y];
        }

        return returnString;
    }

    /**
     * This method draws a ⨝ pattern using ‘*’ characters.
     * @param width the number of rows the string should contain
     * @return a string of the pattern
     */
    public static String drawPattern(int width) {
        int divisor = 2;
        int multiplier = 2;
        String returnString = "";
        if(width == 0) {
            return returnString;
        }
        if(width % divisor == 1) {
            width++;
        }
        for (int i= 1; i<= width / divisor ; i++)
        {
            for (int k=1; k<=i;k++) {
                returnString += "*";
            }

            for (int j= (i * multiplier); j < width ;j++) {
                returnString += " ";
            }

            for (int k=1; k<=i;k++) {
                returnString += "*";
            }

            returnString += "\n";
        }
        for (int i= (width / divisor); i>=1; i--) {

            for(int k=1; k<i ;k++) {
                returnString += "*";
            }

            for(int j=i; j<= width / divisor; j++) {
                returnString += "  ";
            }

            for(int k=1; k<i ;k++) {
                returnString += "*";
            }

            returnString += "\n";

        }
        return returnString;
    }

    /**
     * Contains answers to all the questions
     * @return int array with all answers to the Big O questions
     */
    public static int[] runtimeAnswers() {
        return new int[] {
                1, 1, 3, 2, 1, 2, 3, 1, 2, 1
        };
    }

}

