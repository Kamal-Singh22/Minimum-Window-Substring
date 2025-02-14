import java.util.*;

public class MinimumWindowSubstring {

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Frequency map for characters in t
        Map<Character, Integer> targetCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCount.put(c, targetCount.getOrDefault(c, 0) + 1);
        }
        
        int required = targetCount.size();
        int formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<>();
        int left = 0, right = 0;
        int[] ans = {-1, 0, 0}; // [window length, left, right]
        
        while (right < s.length()) {
            // Add one character from the right to the window
            char c = s.charAt(right);
            windowCounts.put(c, windowCounts.getOrDefault(c, 0) + 1);
            
            // Check if the current character's frequency matches the required frequency in t
            if (targetCount.containsKey(c) && windowCounts.get(c).intValue() == targetCount.get(c).intValue()) {
                formed++;
            }
            
            // Try and contract the window till the point it ceases to be 'desirable'
            while (left <= right && formed == required) {
                c = s.charAt(left);
                
                // Save the smallest window until now.
                if (ans[0] == -1 || right - left + 1 < ans[0]) {
                    ans[0] = right - left + 1;
                    ans[1] = left;
                    ans[2] = right;
                }
                
                // The character at the position pointed by `left` is no longer a part of the window
                windowCounts.put(c, windowCounts.get(c) - 1);
                if (targetCount.containsKey(c) && windowCounts.get(c) < targetCount.get(c)) {
                    formed--;
                }
                
                left++;
            }
            
            // Expand the window once we are done contracting
            right++;
        }
        
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
    
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String result = minWindow(s, t);
        System.out.println("Minimum window substring: " + result); // Expected Output: "BANC"
    }
}
