package com.lbq.exer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyInput {

	public static String readString() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String string = "";
		
		try {
			string = br.readLine();
		}catch(IOException ex) {
			System.out.println(ex);
		}
		return string;
	}
	
	public static int readInt() {
		return Integer.parseInt(readString());
	}
	
	public static double readDouble() {
		return Double.parseDouble(readString());
	}
	
	public static byte readByte() {
		return Byte.parseByte(readString());
	}
	
	public static short readShort() {
		return Short.parseShort(readString());
	}
	
	public static long readLong() {
		return Long.parseLong(readString());
	}
	
	public static float readFloat() {
		return Float.parseFloat(readString());
	}
}
