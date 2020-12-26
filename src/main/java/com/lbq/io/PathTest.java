package com.lbq.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * 1.jdk7.0时，引入了Path、Paths、Files三个类。
 * 2.此三个类声明在：java.nio.file包下。
 * 3.Path可以看作是java.io.File类的升级版本。也可以表示文件或文件目录，与平台无关
 * 
 * 4.如何实例化Path:使用Paths.
 * static Path get(String first, String ... more):用于将多个字符串串连成路径
 * static Path get(URI uri): 返回指定uri对应的Path路径
 * @author 14378
 *
 */
public class PathTest {

	//如何使用Paths实例化Path
	@Test
	public void test1() {
		Path path1 = Paths.get("d:\\nio\\hello.txt");//new File(String filepath)
		
		Path path2 = Paths.get("d:\\", "nio\\hello.txt");//new File(String parent, String filename)
		
		System.out.println(path1);
		System.out.println(path2);
		
		Path path3 = Paths.get("d:\\", "nio");
		System.out.println(path3);
	}
	
	@Test
	public void test2() {
		Path path1 = Paths.get("d:\\", "DevInstall\\test\\nio\\dbcp.txt");
		Path path2 = Paths.get("hello.txt");
		
		//String toString():返回调用Path对象的字符串表示形式
		System.out.println(path1);
		
		//boolean startsWith(String path):判断是否以path路径开始
		System.out.println(path1.startsWith("d:\\nio"));
		
		//boolean endsWith(String path):判断是否以path路径结束
		System.out.println(path1.endsWith("hello.txt"));
		
		//boolean isAbsolute():判断是否是绝对路径
		System.out.println(path1.isAbsolute() + "~");
		System.out.println(path2.isAbsolute() + "~");
		
		//Path getParent():返回Path对象包含整个路径，不包含Path对象指定的文件路径
		System.out.println(path1.getParent());
		System.out.println(path2.getParent());
		
		//Path getRoot():返回调用Path对象的根路径
		System.out.println(path1.getRoot());
		System.out.println(path2.getRoot());
		
		//Path getFileName():返回与调用Path对象关联的文件名
		System.out.println(path1.getFileName() + "~");
		System.out.println(path2.getFileName() + "~");
		
		//int getNameCount():返回Path根目录后面元素的数量
		// Path getName(int idx):返回指定索引位置idx的路径名称
		for(int i = 0; i < path1.getNameCount(); i++) {
			System.out.println(path1.getName(i) + "*****");
		}
		
		//Path toAbsolutePath():作为绝对路径返回调用Path对象
		System.out.println(path1.toAbsolutePath());
		System.out.println(path2.toAbsolutePath());
		
		//Path resolve(Path p):合并两个路径，返回合并后的路径对应的Path对象
		Path path3 = Paths.get("d:\\", "nio");
		Path path4 = Paths.get("nio\\hi.txt");
		
		path3 = path3.resolve(path4);
		System.out.println(path3);
		
		//File toFile():将Path转化为File类的对象
		File file = path1.toFile();//Path --->File的转换
		Path newPath = file.toPath();//File --->Path的转换
	}
}
