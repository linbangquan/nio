package com.lbq.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

/**
 * 处理流之一：缓冲流的使用
 * 
 * 1.缓冲流：
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 * 
 * 2.作用：提高流的读取、写入的速度
 * 	 提高读写速度的原因：内部提供了一个缓冲区
 * 
 * 3.处理流，就是“套接”在已有的流的基础上。
 * @author 14378
 *
 */
public class BufferedTest {

	@Test
	public void BufferedStreamTest() {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			//1.造文件
			File srcFile = new File("爱情与友情.jpg");
			File destFile = new File("爱情与友情3.jpg");
			//2.造流
			//2.1造节点流
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			//2.2造缓冲流
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			//3.复制的细节：读取、写入
			byte[] buffer = new byte[10];
			int len;
			while((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
				//bos.flush();//刷新缓冲区
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			//4.资源关闭
			//要求：先关外层的流，再关闭内层的流
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void copyFileWithBuffered(String srcPath, String destPath) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			//1.造文件
			File srcFile = new File(srcPath);
			File destFile = new File(destPath);
			//2.造流
			//2.1造节点流
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			//2.2造缓冲流
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			//3.复制的细节：读取、写入
			byte[] buffer = new byte[1024];
			int len;
			while((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
				//bos.flush();//刷新缓冲区
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			//4.资源关闭
			//要求：先关外层的流，再关闭内层的流
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void testCopyFileWithBuffered() {
		long start= System.currentTimeMillis();
		
		String srcPath = "";
		String destPath = "";
		
		copyFileWithBuffered(srcPath, destPath);
		
		long end = System.currentTimeMillis();
		
		System.out.println("复制操作花费的时间为：" + (end - start));
	}
	
	/**
	 * 使用BufferedReader和BufferedWriter实现文本的复制
	 */
	@Test
	public void testBufferedReaderBufferedWriter() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			//创建文件和相应的流
			br = new BufferedReader(new FileReader(new File("dbcp.txt")));
			bw = new BufferedWriter(new FileWriter(new File("dbcp1.txt")));
			//读写操作
			//方式一：使用char[]数组
//			char[] cbuf = new char[1024];
//			int len;
//			while((len = br.read(cbuf)) != -1) {
//				bw.write(cbuf, 0, len);
//				//bw.flush();
//			}
			//方式二：使用String
			String data;
			while((data = br.readLine()) != null) {
				//方式一：
//				bw.write(data + "\n");//data中不包含换行符
				//方式二：
				bw.write(data);
				bw.newLine();//提供换行的操作
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			//关闭资源
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
