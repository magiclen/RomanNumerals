/*
 *
 * Copyright 2015 magiclen.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magiclen.romannumerals;

import java.util.Scanner;

/**
 * 阿拉伯數字/羅馬數字的轉換程式，以小寫英文字母代表羅馬數字的1000倍。
 *
 * @author Magic Len
 */
public class RomanNumerals {

    // -----類別常數-----
    /**
     * 阿拉伯數字對應的羅馬數字。
     */
    private final static String rnums[] = {"m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "Mx", "v", "Mv", "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; //儲存所有羅馬數字
    /**
     * 羅馬數字對應的阿拉伯數字。
     */
    private final static int anums[] = {1000000, 900000, 500000, 400000, 100000, 90000, 50000, 40000, 10000, 9000, 5000, 4000, 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1}; //儲存羅馬數字表示的值

    // -----類別方法-----
    /**
     * 程式進入點，參數可以傳入要計算的阿拉伯數字或是羅馬數字。
     *
     * @param args 傳入要計算的阿拉伯數字或是羅馬數字
     */
    public static void main(final String[] args) { //程式進入點
	final Scanner sc;
	if (args != null && args.length > 0) { //如果使用者有輸入參數
	    //從參數讀取阿拉伯數字或是羅馬數字
	    final StringBuilder sb = new StringBuilder();
	    for (final String arg : args) {
		sb.append(arg).append("\n");
	    }
	    sc = new Scanner(sb.toString());
	} else {
	    //從標準輸入串流讀取阿拉伯數字或是羅馬數字
	    sc = new Scanner(System.in);
	}

	String input; //宣告input字串，用來儲存使用者輸入的值
	while (sc.hasNext()) { //若還有資料尚未讀取，則執行
	    input = sc.nextLine().trim(); //讀取一行字串
	    if (input.length() < 1) {
		continue; //如果沒有輸入任何字串，就重新讀取
	    }
	    if (input.charAt(0) == '#') { //輸入井字號離開
		break;
	    }
	    if (isDigit(input)) { //如果是整數
		int n = Integer.parseInt(input); //將字串轉為整數
		if (n > 0 && n < 4000000) { //如果n大於0且小於4000000
		    System.out.println(input + " = " + toRnums(n));
		} else { //如果超過範圍
		    System.out.println("輸入的阿拉伯數字有誤！必須大於零且小於四百萬！");
		}
	    } else { //如果不是整數
		int n = toAnums(input); //將羅馬字串轉為阿拉伯數字(數值)
		if (toRnums(n).equals(input)) { //判斷輸入的羅馬字串格式是否正確(轉成阿拉伯數字(數值)後再轉回羅馬字串，並與原本的字串做比較)
		    System.out.println(input + " = " + n);
		} else {
		    System.out.println("輸入的羅馬數字有誤！");
		}
	    }
	}
    }

    /**
     * 判斷傳入的字串是否為整數。
     *
     * @param s 傳入要判斷是否為整數的字串
     * @return 傳回傳入的字串是否為整數
     */
    public static boolean isDigit(final String s) {
	try {
	    Integer.parseInt(s); //將字串轉為整數
	} catch (Exception e) { //如果轉換失敗，會丟出例外
	    return false; //不是整數
	}
	return true; //是整數
    }

    /**
     * 將阿拉伯數字(數值)轉成以字串表示的羅馬數字。
     *
     * @param num 傳入要轉換的阿拉伯數字(數值)
     * @return 傳回阿拉伯數字(數值)轉換成的羅馬數字字串
     */
    public static String toRnums(int num) { //將整數轉成以字串表示的羅馬數字
	if (num == 0) { //因為羅馬數字裡並沒有零，所以輸出ZERO！
	    return "ZERO";
	} else if (num < 0 || num > 3999999) { //溢位判斷
	    return "OVERFLOW";
	}

	final StringBuilder output = new StringBuilder(); //儲存羅馬數字字串
	for (int i = 0; num > 0 && i < anums.length; i++) //尋找對應的羅馬數字
	{
	    while (num >= anums[i]) { //將羅馬數字加到output物件內
		num -= anums[i];
		output.append(rnums[i]);
	    }
	}
	return output.toString(); //傳回羅馬數字字串
    }

    /**
     * 將羅馬數字轉成阿拉伯數字(數值)。
     *
     * @param s 傳入要轉換的羅馬數字字串
     * @return 傳回羅馬數字轉換成的阿拉伯數字(數值)
     */
    public static int toAnums(final String s) { //將羅馬數字轉成阿拉伯數字(數值)
	int num = 0;
	final StringBuilder buffer = new StringBuilder(s); //將字串s存入字串緩衝區內
	for (int i = 0; buffer.length() > 0 && i < anums.length; i++) //尋找對應的羅馬數字
	{
	    while (buffer.indexOf(rnums[i]) == 0) { //如果有找到相同的羅馬數字字串，且位置在字串最左邊
		num += anums[i]; //將羅馬數字轉為阿拉伯數字(數值)並存到num變數內
		buffer.delete(0, rnums[i].length()); //刪除已讀取到的字串
	    }
	}
	return num; //傳回轉換後的阿拉伯數字(數值)
    }
}
