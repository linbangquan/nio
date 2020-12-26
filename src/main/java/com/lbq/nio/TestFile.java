package com.lbq.nio;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TestFile {

	@Test
	public void testRenameTo() throws IOException {
		String filePath1 = "D:/file1.txt";
		String filePath2 = "D:/file2.txt";
		File file1 = new File(filePath1);
		if(!file1.exists()) {
			System.out.println("不存在");
			if(!file1.getParentFile().exists()) {
				file1.getParentFile().mkdirs();
			}
			file1.createNewFile();
		}
		File file2 = new File(filePath2);
//		if(!file2.exists()) {
//			System.out.println("不存在");
//			if(!file2.getParentFile().exists()) {
//				file2.getParentFile().mkdirs();
//			}
//			file2.createNewFile();
//		}
		boolean flag = file1.renameTo(file2);
		System.out.println(flag);
//		file1.renameTo(new File(filePath2));
	}
}
