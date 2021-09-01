package com.cos.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.logging.log4j.util.Chars;

public class Sales {
	
	public boolean containsDuplicate(int[] nums) {
        HashMap<Integer, Integer> numMap = new HashMap<>();
		for(int i=0; i<nums.length; i++) {
        	if(numMap.containsKey(nums[i])) return true;
        	numMap.put(nums[i], nums[i]);
        }
		return false;	
    }
	public int singleNumber(int[] nums) {
		if(nums.length == 1) return nums[0];
		HashMap<Integer, Integer> numMap = new HashMap<>();
		for(int i=0; i<nums.length; i++) {
			if(numMap.containsKey(nums[i])) numMap.remove(nums[i]);
		    else numMap.put(nums[i], i);	
		}
		int answer = 0;
		for(Integer key: numMap.keySet()) answer = nums[numMap.get(key)];
		return answer;
	}
	
	public int[] plusOne(int[] digits) {
		if(digits.length == 1) {
        	if(digits[0] == 9) {
        		return new int [] {1, 0};
        	} else {
        		return new int [] {digits[0]+1};
        	}
        }
        for(int i=digits.length-1; i>=0; i--) {
        	if(digits[i] == 9) {
        		digits[i] = 0;
        		if(i==0) {
        			int [] newDigits = new int[digits.length+1];
        			newDigits[0] = 1;
        			for(int j=1; j<newDigits.length; j++) {
        				newDigits[j] = digits[j-1];
        			}
        			return newDigits;
        		}
        	} else {
        		digits[i] += 1;
        		return digits;
        	}
        }
        return digits;
	}
	
	public void moveZeroes(int[] nums) {
        if(nums.length==1) System.out.println(Arrays.toString(nums));
        
        for(int i=0; i<nums.length; i++) {
        	if(nums[i] != 0) continue;
        	int cnt = 0;
        	for(int j=i+1; j<nums.length; j++) {
        		if(nums[j] == 0) continue;
        		else {
        			int temp = nums[i];
        			nums[i] = nums[j];
        			nums[j] = temp;
        			cnt = 1;
        			break;
        		}
        	}
        	if(cnt == 0) break;
        }
        System.out.println(Arrays.toString(nums));
    }
	
	public int[] twoSum(int[] nums, int target) {
        
        HashMap<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
        	int minus = target - nums[i];
            if (numMap.containsKey(minus)) {
                return new int[] { numMap.get(minus), i };
            }
            numMap.put(nums[i], i);
        }
        return null;
    }
	/*
	public boolean isValidSudoku(char[][] board) {
		
		ArrayList<HashMap<String, Integer>> lists = new ArrayList<>();
        for(int i=0; i<board.length; i++) {
        	lists.add(new HashMap<String, Integer>()); // hashMap 객체 생성
        	for(int j=0; j<board.length; j++) {
        		String key = String.valueOf(board[i][j]); // 현재 배열의 값
        		if(key.equals(".")) continue;  // '.' 은 건너뛰기
        		for(int k=0; k<lists.size(); k++) {   // ArrayList의 0번째 hashMap 객체부터 끝까지 현재 배열의 값을 키 값으로 가지고 있는지 확인
        			if(lists.get(k).containsKey(key)) if(lists.get(k).get(key) == j) return false;
        		}
        		if(lists.get(i).containsKey(key)) return false;  // 현재 ArrayList의 hashMap 객체에 이미 같은 값이 있는지 확인
    			lists.get(i).put(String.valueOf(board[i][j]), Integer.valueOf(j));  // 모두 통과했다면 값을 집어넣기
        	}
        }
        
        for(int i=0; i<lists.size(); i++) {
            for(String key: lists.get(i).keySet()) {
            	System.out.println("key: "+key+", value: "+lists.get(i).get(key));
            }
            System.out.println("==================================================");
        }
		return true;
		
		ArrayList<HashMap<Character, Character>> row = new ArrayList<>();
		ArrayList<HashMap<Character, Character>> col = new ArrayList<>();
		ArrayList<HashMap<Character, Character>> box = new ArrayList<>();
		
		for(int i=0; i<boardNum; i++) {
			set[i] = new HashSet<Character>();
			row.add(new HashMap<Character, Character>(boardNum));
			col.add(new HashMap<Character, Character>(boardNum));
			box.add(new HashMap<Character, Character>(boardNum));
		}
		for(int i=0; i<boardNum; i++) {
			for(int j=0; j<boardNum; j++) {
				String rowChar = String.valueOf(board[i][j]);
				String colChar = String.valueOf(board[j][i]);
				if(!row.get(i).containsKey(board[i][j]) && !rowChar.equals(".")) {
					row.get(i).put(board[i][j], board[i][j]);
					System.out.println("row.get("+j+").get(board["+i+"]["+j+"]:"+row.get(j).get(board[i][j]));
				} else {
					if(!rowChar.equals(".")) return false;
				}
				if(!col.get(i).containsKey(board[j][i]) && !colChar.equals(".")) {
					col.get(i).put(board[j][i], board[j][i]);
				} else {
					if(!colChar.equals(".")) return false;
				}
				
				int boxNum = i / 3 * 3 + j / 3;
				if(!box.get(boxNum).containsKey(board[i][j]) && !rowChar.equals(".")) {
					box.get(boxNum).put(board[i][j], board[i][j]);
				} else {
					if(!rowChar.equals(".")) return false;
				}
			}
		}
    }*/
	
	public boolean isValidSudoku(char[][] board) {
		int boardNum = 9;
		
		HashSet<Character> [] rowSet = new HashSet[9];
		HashSet<Character> [] colSet = new HashSet[9];
		HashSet<Character> [] boxSet = new HashSet[9];
		
		for(int i=0; i<boardNum; i++) {
			rowSet[i] = new HashSet<Character>();
			colSet[i] = new HashSet<Character>();
			boxSet[i] = new HashSet<Character>();
		}
		
		for(int row = 0; row < boardNum; row++) {
			for(int col = 0; col <boardNum; col++) {
				char value = board[row][col];
				
				if(value == '.') continue;
				
				if(rowSet[row].contains(value)) return false;
				rowSet[row].add(value);
				
				if(colSet[col].contains(value)) return false;
				colSet[col].add(value);
				
				int boxNum = row / 3 * 3 + col / 3;
				
				if(boxSet[boxNum].contains(value)) return false;
				boxSet[boxNum].add(value);
			}
		}
		
		return true;
    }
	
	public void rotate(int[][] matrix) {
        if(matrix.length <= 1) return;
        int length = matrix.length;
        
        int [][] cp_matrix = new int[length][length];
        for(int i=0; i < length; i++) {
        	System.arraycopy(matrix[i], 0, cp_matrix[i], 0, length);
        }
        for(int i=0; i < matrix.length; i++) {
        	length--;
        	for(int j=0; j < matrix.length; j++) {
        		matrix[j][i] = cp_matrix[length][j];
        	}
        }
    }
	int cnt_num = 0;
	public int climbStairs(int n) {
		if(n <= 3) return cnt_num = n;
		for(int i=1; i < n; i++) {
			n -= 1;
			cnt_num += climbStairs(n-1);
		}
		return cnt_num;
	}

	public int maxSubArray(int[] nums) {
		
		int sum = 0;
		int return_val = -1000000;
		
		for(int i=0; i < nums.length; i++) {
			int curVal = nums[i];
			sum = sum < 0 ? curVal : sum + curVal;
			return_val = Math.max(sum, return_val);
		}
		return return_val;
    }

	public static void main(String[] args) {
		Sales s = new Sales();
		int [] nums = {-2,1,-3,4,-1,2,1,-5,4};
		int [] nu = {-2, -1};
		// 1 - 3: -2,   -2 + 4: 2,  2-1: 1,   1+2: 3,   3+1: 4,   3-5: -2,   -2+4: 2 
		System.out.println(s.maxSubArray(nu));
		// false c: 1836311903
	}
	
	public int maxProfit(int[] prices) {
        int profit = 0;
        int minNum = 0;
        int idx = 0;
        for(int i=1; i < prices.length; i++) {
        	int curProfit = prices[i]-prices[i-1];
        	if(curProfit > 0 ) {
        		if(curProfit > profit) {
        			
        		}
        		idx = i-1;
        		profit = curProfit;
        		minNum = prices[i-1];
        	}
            profit += Math.max(0, prices[i]-prices[i-1]);
            System.out.println("profit: "+profit);
        }
        return profit;
    }

}
