package com.estate.utils;

import java.util.Arrays;
import java.util.Stack;

public class Solution {

    public static int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.empty() && heights[i] > st.peek()) {
                st.pop();
                ++ans[i];
            }
            if (!st.empty())
                ++ans[i];
            st.push(heights[i]);
        }
        return ans;
    }
    public static void main(String[] args) {
         int[] heights = {10,6,8,5,11,9};
         int[] result = canSeePersonsCount(heights);
         Arrays.stream(result).forEach( i-> {
             System.out.print(i +" ");
         });

    }
}
