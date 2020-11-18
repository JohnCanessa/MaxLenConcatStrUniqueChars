import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/**
 * LeetCode 1239. Maximum Length of a Concatenated String with Unique Characters
 * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
 */
public class MaxLenConcatStrUniqueChars {


    /**
     * Combinations
     */
    static class Combinations {

        // **** members ****
        public int n;
        public int r;

        /**
         * Constructor(s)
         */
        public Combinations() {
        }

        public Combinations(int n, int r) {
            this.n = n;
            this.r = r;
        }

        /**
         * Order not important.
         */
        public int count(int n, int r) {

            // **** ****
            int nMinusRFac = 1;

            // **** (n - r)! ****
            for (int i = n - r; i > 1; i--) {
                nMinusRFac *= i;
            }

            // **** ****
            int nFac = 1;

            // **** n! ****
            for (int i = n; i > 1; i--) {
                nFac *= i;
            }

            // **** ****
            int rFac = 1;

            // **** r! ****
            for (int i = r; i > 1; i--) {
                rFac *= i;
            }

            // **** ****
            return nFac / (rFac * nMinusRFac);
        }

        // **** ****
        public int count() {

            // **** end conditions ****
            if (n == r)
                return 1;

            if (r == 1)
                return n;

            // **** ****
            int count = 1;

            // **** initialization ****
            for (int i = n; i >= (n - r) + 1; i--) {
                count *= i;
            }

            // **** count of combinations ****
            return count;
        }
    }


    /**
     * Maximum Length of a Concatenated String with Unique Characters
     * 
     * Runtime: 340 ms, faster than 5.00% of Java online submissions
     * Memory Usage: 38.4 MB, less than 91.48% of Java online submissions
     */
    static int maxLength0(List<String> arr) {
        
        // **** sanity check(s) ****
        if (arr == null)
            return 0;

        if (arr.size() == 1)
            return arr.get(0).length();

        // **** create and populate the array ****
        int[] array = new int[arr.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = i;

        // **** initialize the max string length ****
        int[] maxLen = new int[1];

        // **** loop through r ****
        for (int r = array.length; r > 0; r--) {

            // **** temporary array to store all combination one by one ****
            int data[] = new int[r];

            // **** generate all combination using the temporary array data[] ****
            combinationUtil(array, array.length, r, 0, data, 0, arr, maxLen);
        }

        // **** return the max string length ****
        return maxLen[0];
    }

    
    /**
     * Recursive call.
     */
    static void combinationUtil(int arr[], 
                                int n, 
                                int r, 
                                int index, 
                                int data[], 
                                int i, 
                                List<String> al, 
                                int[] maxLen) {

        // **** base case ****
        if (index == r) {
            
            // **** ****
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data.length; j++)
                sb.append(al.get(data[j]));

            // **** check if not long enough (return) ****
            if (sb.length() <= maxLen[0])
                return;

            // **** check if string contains a duplicate character (return) ****
            if (uniqueCharCount(sb.toString()) == 0) {
                return;
            }

            // **** update the max length ****
            maxLen[0] = sb.length();

            // **** ****
            return; 
        }
        if (i >= n) 
            return; 

        // **** current is included, put next at next location ****
        data[index] = arr[i];
        combinationUtil(arr, n, r, index + 1, data, i + 1, al, maxLen); 

        // **** current is excluded, replace it with next
        //      (note that i + 1 is passed, but index is not changed) ****
        combinationUtil(arr, n, r, index, data, i + 1, al, maxLen);
    }


    /**
     * Maximum Length of a Concatenated String with Unique Characters
     * 
     * Runtime: 175 ms, faster than 13.60% of Java online submissions.
     * Memory Usage: 38.7 MB, less than 64.65% of Java online submissions.
     * 
     * Runtime: 184 ms, faster than 8.60% of Java online submissions.
     * Memory Usage: 38.6 MB, less than 82.71% of Java online submissions.
     */
    static int maxLength1(List<String> arr) {

        // **** initialization ****
        int[] maxLen = new int[1];

        // **** recursive call ****
        maxUnique(arr, 0, "", maxLen);

        // **** return result ****
        return maxLen[0];
    }


    /**
     * Recursive call.
     */
    static void maxUnique(List<String> strs, int i, String cs, int[] maxLen) {

        // **** base case ****
        if (i == strs.size() && uniqueCharCount(cs) > maxLen[0]) {
            maxLen[0] = cs.length();
            return;
        }
        if (i == strs.size()) {
            return;
        }

        // **** ****
        maxUnique(strs, i + 1, cs, maxLen);

        // **** ****
        maxUnique(strs, i + 1, cs + strs.get(i), maxLen);
    }


    /**
     * Unique character count.
     * Utility function.
     * Returns count of unique characters.
     * Returns 0 when encountering duplicate character.
     */
    static int uniqueCharCount(String s) {

        // **** initialization ****
        int[] counts = new int[26];

        // **** check if we have a duplicate character ****
        for (char ch : s.toCharArray()) {
            if (counts[ch - 'a']++ > 0) {
                return 0;
            }
        }

        // **** length of string with unique characters ****
        return s.length();
    }


    /**
     * Generate combinations of integers using iterative algorithm.
     */
    static List<int[]> generateIntCombinations(int n, int r) {

        /// **** initialization ****
        List<int[]> combinations    = new ArrayList<>();
        int[] combination           = new int[r];
     
        // **** initialize with lowest lexicographic combination ****
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }
     
        // **** ****
        while (combination[r - 1] < n) {
            combinations.add(combination.clone());
     
             // **** generate next combination in lexicographic order ****
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }
     
        // **** return list of combinations ****
        return combinations;
    }


    /**
     * Return the length of this combination of strings.
     */
    static int uniqueStringLen(List<String> al, int[] c) {

        // **** initialization ****
        int length  = 0;
        int[] chars = new int[26];

        // **** loop through the strings that make the specified combination ****
        for (int i = 0; i < c.length; i++) {

            // **** current string ****
            String s = al.get(c[i]);

            // **** traverse string ****
            for (char ch : s.toCharArray()) {
                if (chars[ch - 'a']++ > 0)
                    return 0;
            }
        }

        // **** traverse all strings ****
        for (int i = 0; i < c.length; i++) {
            length += al.get(c[i]).length();
        }

        // **** return length of all concatenated strings ****
        return length;
    }



    // **** combination -> chars array ****
    static HashMap<String, int[]> combCharArr = new HashMap<String, int[]>();

    // **** combination -> unique string ****
    static HashMap<String, String> combUniqueStr = new HashMap<String, String>();


    /**
     * Return the length of this combination of strings.
     */
    static int uniqueStrLen(String cus, int[] chars) {

        // **** ****
        for (char ch : cus.toCharArray()) {
            if (chars[ch - 'a']++ > 0) {
                return 0;
            }
        }

        // **** ****
        return cus.length();
    }


    /**
     * Return the length of the specified combination strings.
     * With memoization.
     */
    static int uniqueStringLenMemo(List<String> al, int[] c) {

        // **** initialization ****
        int length              = 0;
        String combStr          = "";
        String partialUniqStr   = "";
        int[] partialChars      = new int[26];
        
        // **** generate combination string ****
        if (c.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < c.length - 1; i++) {
                sb.append(c[i]);
            }
            combStr = sb.toString();
        }

        // **** look up partial unique string ****
        if (combUniqueStr.containsKey(combStr)){
            partialUniqStr = combUniqueStr.get(combStr);
        }

        // **** look up partial chars array ****
        if (combCharArr.containsKey(combStr)) {
            partialChars = combCharArr.get(combStr).clone();
        }

        // **** generate current unique string ****
        String cus = al.get(c[c.length - 1]);

        // **** check if cus is unique or not ****
        length = uniqueStrLen(cus, partialChars);

        // **** should we add to NOT unique strings (????) ****
        if (length == 0)
            return 0;

        // **** update combination string ****
        combStr += "" + c[c.length - 1];

        // **** update length ****
        length += partialUniqStr.length();

        // **** update the unique string ****
        combUniqueStr.put(combStr, partialUniqStr + cus);

        // **** update array holding character counts ****
        combCharArr.put(combStr, partialChars);

        // **** return length of all combined strings ****
        return length;
    }


    /**
     * Runtime: 161 ms, faster than 14.85% of Java online submissions.
     * Memory Usage: 44.8 MB, less than 7.72% of Java online submissions.
     * 
     * Runtime: 473 ms, faster than 5.05% of Java online submissions.
     * Memory Usage: 73.1 MB, less than 5.22% of Java online submissions
     */
    static int maxLength2(List<String> arr) {

        // **** global initialization  ****
        combCharArr = new HashMap<String, int[]>();
        combUniqueStr = new HashMap<String, String>();

        // **** local initialization ****
        int maxLen  = 0;
        int n       = arr.size();

        // **** generate list of integer combinations (iterative approach)
        for (int r = 1; r <= n; r++) {

            // **** generate list of combinations [1] ****
            List<int[]> combs = generateIntCombinations(n, r);

            // **** traverse these combinations updating the max length [2] ****
            for (int i = 0; i < combs.size(); i++) {
                maxLen = Math.max(maxLen, uniqueStringLenMemo(arr, combs.get(i)));
            }
        }
        
        // **** return the length of the longest unique string ****
        return maxLen;
    }


    /**
     * Seems like the initial set of strings may contain 
     * dupplicate characters.
     * 
     * Runtime: 103 ms, faster than 16.80% of Java online submissions.
     * Memory Usage: 47.2 MB, less than 5.76% of Java online submissions.
     */
    static int maxLength(List<String> arr) {
        
        // **** number of strings ****
        int n = arr.size();

        // **** one mask per string ****
        int[] masks = new int[n];

        // **** loop once per string ****
        for (int i = 0; i < n; i++) {

            // **** get current string ****
            String str = arr.get(i);

            // **** loop once per character in string ****
            for (int j = 0; j < str.length(); j++) {

                // **** ****
                int curr = 1 << (str.charAt(j) - 'a');

                // **** check for duplicate characters in a string ****
                if ((masks[i] & curr) != 0) {

                    // **** indicate an error ****
                    masks[i] = -1;
                    break;
                }

                // **** ****
                masks[i] |= curr;
            }
        }

        // **** loop generating all possible combinations ****
        List<int[]> combs = new ArrayList<int[]>();
        for (int r = 1; r <= n; r++) {
            combs.addAll(generateIntCombinations(n, r));
        }

        // **** return max length of unique string ****
        return uniqueStrMaxLen(arr, combs, masks);
    }


    /**
     * 
     */
    static int uniqueStrMaxLen(List<String> arr, List<int[]> combs, int[] masks) {

        // **** initialization ****
        int maxLen  = 0;

        // **** loop once per combination ****
        for (int c = 0; c < combs.size(); c++) {

            // **** get current combination ****
            int[] comb = combs.get(c);

            // **** determine if this combination is unique ****
            int mask        = 0;
            boolean unique  = true;
            for (int i = 0; unique && i < comb.length; i++) {

                // **** check if not unique ****
                if ((mask & masks[comb[i]]) != 0) {
                    unique = false;
                    continue;
                }

                // **** update mask ****
                mask = mask | masks[comb[i]];
            }

            // **** duplicate characters in this string :o) ****
            if (mask == -1)
                continue;

            // **** get the length of the unique string & update max length ****
            if (unique) {

                // **** generate the length of the string ****
                int len = 0;
                while (mask != 0) {
                    if ((mask & 1) == 1) {
                        len++;
                    }
                    mask >>= 1;
                }

                // **** update the max length of the unique string ****
                maxLen = (len > maxLen) ? len : maxLen;
            }
        }

        // **** return the max length ****
        return maxLen;
    }


    /**
     * Test scaffolding
     */
    public static void main(String[] args) {
        
        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read strings into array ****
        String[] strs = sc.nextLine().trim().split(",");

        // **** close scanner ****
        sc.close();

        // **** create and populate list ****
        List<String> arr = new ArrayList<String>();
        for (int i = 0; i < strs.length; i++) {
            arr.add(strs[i].substring(1, strs[i].length() - 1));
        }
        
        // ???? display list ????
        for (int i = 0; i < arr.size(); i++)
            System.out.println("main <<< " + i + " s ==>" + arr.get(i) + "<==");

        // **** process and display results ****
        System.out.println("main <<< maxLength0: " + maxLength0(arr));

        // **** process and display results ****
        System.out.println("main <<< maxLength1: " + maxLength1(arr));

        // **** process and display results ****
        System.out.println("main <<< maxLength2: " + maxLength2(arr));

        // **** process and display results ****
        System.out.println("main <<<  maxLength: " + maxLength(arr));
    }
}