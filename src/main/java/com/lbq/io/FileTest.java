package com.lbq.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Test;

/*
 * File类的使用
 * 
 * 1.File类的一个对象，代表一个文件或一个文件目录（俗称：文件夹）
 * 2.File类声明在java.io包下
 * 3.File类中涉及到关于文件或文件目录的创建、删除、重命名、修改时间、文件大小等方法，
 * 	 并未涉及到写入或读取文件内容的操作。如果需要读取或写入文件内容，必须使用IO流来完成。
 * 4.后续File类的对象常会作为参数传递到流的构造器中，指明读取或写入的“终点”。
 */
public class FileTest {
	/**
	 * 1.如何创建File类的实例
	 * 		File(String filePath)
	 * 		File(String parentPath, String childPath)
	 * 		File(File parentFile, String childPath)
	 * 2.相对路径：相较于某个路径下，指明的路径。
	 * 3.绝对路径：包含盘符在内的文件或文件目录的路径
	 * 
	 * 4.路径分隔符：
	 * 		windows:\\
	 * 		unix:/
	 */
	@Test
	public void test1() {
		//构造器1
		File file1 = new File("hello.txt");//相对于当前的项目D:\DevInstall\test\nio\
		File file2 = new File("d:\\DevInstall\\test\\nio\\hello2.txt");
		System.out.println(file1);
		System.out.println(file2);
		
		//构造器2
		File file3 = new File("D:\\DevInstall\\test\\nio", "hello3");
		System.out.println(file3);
		
		//构造器3
		File file4 = new File(file3, "hello4.txt");
		System.out.println(file4);
		
	}
	
	/**
	 * public String getAbsoluePath():获取绝对路径
	 * public String getPath():获取路径
	 * public String getName():获取名称
	 * public String getParent():获取上层文件目录路径。若无，返回null
	 * public long length():获取文件长度（即：字节数）。不能获取目录的长度。
	 * public long lastModified():获取最后一次的修改时间，毫秒值
	 * 
	 * 如下的两个方法适用于文件目录：
	 * public String[] list():获取指定目录下的所有文件或者文件目录的名称数组
	 * public File[] listFiles():获取指定目录下的所有文件或者文件目录的File数组
	 */
	@Test
	public void test2() {
		File file1 = new File("hello.txt");
		File file2 = new File("d:\\io\\hi.txt");
		
		System.out.println(file1.getAbsolutePath());
		System.out.println(file1.getPath());
		System.out.println(file1.getName());
		System.out.println(file1.getParent());
		System.out.println(file1.length());
		System.out.println(file1.lastModified());
		
		System.out.println("------------------------------------");
		
		System.out.println(file2.getAbsolutePath());
		System.out.println(file2.getPath());
		System.out.println(file2.getName());
		System.out.println(file2.getParent());
		System.out.println(file2.length());
		System.out.println(file2.lastModified());
	}
	
	@Test
	public void test3() {
		File file = new File("D:\\DevInstall\\test\\nio");
		String[] list = file.list();
		for(String s : list) {
			System.out.println(s);
		}
		System.out.println("------------------------------------");
		File[] files = file.listFiles();
		for(File f : files) {
			System.out.println(f);
		}
	}
	
	/**
	 * public boolean renameTo(File dest):把文件重命名未指定的文件路径
	 * 比如：file1.renameTo(file2)为例：
	 * 	要想保证返回true,需要file1在硬盘中是存在的，且file2不能再硬盘中存在。
	 */
	@Test
	public void test4() {
		File file1 = new File("hello.txt");
		System.out.println(file1.getAbsolutePath());
		File file2 = new File("hello2.txt");
		
		boolean renameTo = file1.renameTo(file2);
		System.out.println(file1.getAbsolutePath());
		System.out.println(renameTo);
	}
	
	/**
	 * public boolean isDirectory():判断是否是文件目录
	 * public boolean isFile():判断是否是文件
	 * public boolean exists():判断是否存在
	 * public boolean canRead():判断是否可读
	 * public boolean canWrite():判断是否可写
	 * public boolean isHidden():判断是否隐藏
	 */
	@Test
	public void test5() {
		File file1 = new File("hello.txt");
		file1 = new File("hello2.txt");
		
		System.out.println(file1.isDirectory());
		System.out.println(file1.isFile());
		System.out.println(file1.exists());
		System.out.println(file1.canRead());
		System.out.println(file1.canWrite());
		System.out.println(file1.isHidden());
		
		System.out.println();
		
		File file2 = new File("d:\\DevInstall\\test\\nio");
		System.out.println(file2.isDirectory());
		System.out.println(file2.isFile());
		System.out.println(file2.exists());
		System.out.println(file2.canRead());
		System.out.println(file2.canRead());
		System.out.println(file2.canWrite());
		System.out.println(file2.isHidden());
	}
	
	/**
	 * 创建硬盘中对应的文件或文件目录
	 * public boolean createNewFile():创建文件。若文件存在，则不创建，返回false.
	 * public boolean mkdir():创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
	 * public boolean mkidrs():创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，一并创建。
	 * 删除磁盘中的文件或文件目录
	 * public boolean delete():删除文件或者文件夹
	 * 删除注意事项：java中的删除不走回收站
	 * @throws IOException 
	 */
	@Test
	public void test6() throws IOException {
		File file1 = new File("hello1.txt");
		if(!file1.exists()) {
			//文件的创建
			file1.createNewFile();
			System.out.println("创建成功");
		}else {
			file1.delete();
			System.out.println("删除成功");
		}
	}
	
	@Test
	public void test7() {
		//文件目录的创建
		File file1 = new File("d:\\io\\io1\\io3");
		boolean mkdir = file1.mkdir();
		if(mkdir) {
			System.out.println("创建成功1");
		}
		
		File file2 = new File("d:\\io\\io1\\io4");
		boolean mkdir1= file2.mkdirs();
		if(mkdir1) {
			System.out.println("创建成功2");
		}
		//要想删除成功，io4文件目录下不能有子目录或文件
		System.out.println(file2.delete());
		file2 = new File("d:\\io\\io1");
		System.out.println(file2.delete());
	}
	
	/**
	 * 练习1
	 * @throws IOException
	 */
	@Test
	public void test8() throws IOException {
		File file = new File("hello2.txt");
		//创建一个与file同目录下的另一个文件，文件名为：haha.txt
		File destFile = new File(file.getParent(), "haha.txt");
		boolean newFile = destFile.createNewFile();
		if(newFile) {
			System.out.println("创建成功！");
		}
	}
	
	/**
	 * 课后练习2：判断指定目录下是否有后缀名为jpg的文件，如果有，就输出该文件名称
	 */
	@Test
	public void test9() {
		File srcFile = new File("d:\\code");
		
		String[] fileNames = srcFile.list();
		for(String fileName : fileNames) {
			if(fileName.endsWith(".jpg")) {
				System.out.println(fileName);
			}
		}
	}
	
	@Test
	public void test10() {
		File srcFile = new File("d:\\code");
		
		File[] listFiles = srcFile.listFiles();
		for(File file : listFiles) {
			if(file.getName().endsWith(".jpg")) {
				System.out.println(file.getAbsolutePath());
			}
		}
	}
	
	/**
	 * File类提供了两个文件过滤器方法
	 * public String[] list(FilenameFilter filter)
	 * public File[] listFiles(FileFilter filter)
	 */
	@Test
	public void test11() {
		File srcFile = new File("d:\\code");
		
		File[] subFiles = srcFile.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
			
		});
		
		for(File file : subFiles) {
			System.out.println(file.getAbsolutePath());
		}
	}
	
	@Test
	public void test12() {
		File srcFile = new File("d:\\code");
		
		String[] fileNames = srcFile.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
			
		});
		
		for(String fileName : fileNames) {
			System.out.println(fileName);
		}
	}
	
	/**
	 * 练习3：遍历指定目录所有文件名称，包括子文件目录中的文件
	 * 拓展1：并计算指定目录占用空间的大小
	 * 拓展2：删除指定文件目录及其下的所有文件
	 */
	@Test
	public void test13() {
		//递归：文件目录
		//打印出指定目录所有文件名称，包括子文件目录中的文件
		
		//1.创建目录对象
		File dir = new File("D:\\io\\io1\\io2");
		//2.打印目录的子文件
		printSubFile(dir);
	}

	private void printSubFile(File dir) {
		// 打印目录的子文件
		File[] subfiles = dir.listFiles();
		
		for(File f : subfiles) {
			if(f.isDirectory()) {
				printSubFile(f);
			}else {
				System.out.println(f.getAbsolutePath());
			}
		}
	}
	
	/**
	 * 方式二：循环实现
	 * 列出file目录的下级内容，仅列出一级的话
	 * 使用File类的String[] list()比较简单
	 * @param file
	 */
	private void listSubFiles(File file) {
		if(file.isDirectory()) {
			String[] all = file.list();
			for(String s : all) {
				System.out.println(s);
			}
		}else {
			System.out.println(file + "是文件!");
		}
	}
	
	/**
	 * 列出file目录的下级， 如果它的下级还是目录，接着列出下级的下级，依次类推
	 * 建议使用File类的File[] listFiles()
	 * @param file
	 */
	private void listAllSubFiles(File file) {
		if(file.isFile()) {
			System.out.println(file);
		}else {
			File[] all = file.listFiles();
			for(File f : all) {
				listAllSubFiles(f);
			}
		}
	}
	/**
	 * 拓展1：求指定目录所在空间的大小
	 * 求任意一个目录的总大小
	 * @param file
	 * @return
	 */
	public long getDirectorySize(File file) {
		//file是文件，那么直接返回file.length()
		//file是目录，把它的下级的所有大小加起来就是它的总大小
		long size = 0;
		if(file.isFile()) {
			size += file.length();
		}else {
			File[] all = file.listFiles();
			for(File f : all) {
				size += getDirectorySize(f);
			}
		}
		return size;
	}
	
	/**
	 * 拓展2：删除指定的目录
	 * @param file
	 */
	public void deleteDirectory(File file) {
		//如果file是文件，直接delete
		//如果file是目录，先把它的下一级干掉，然后删除自己
		if(file.isDirectory()) {
			File[] all = file.listFiles();
			for(File f : all) {
				deleteDirectory(f);
			}
		}
		file.delete();//删除自己
	}
}
