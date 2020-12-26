package com.lbq;

public class StringBuilderTest {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("qwe");
		sb.insert(0, "/");
		sb.insert(0, "zxc");
		sb.insert(0, "/");
		sb.insert(0, "asd");
		System.out.print(sb);
	}

}
