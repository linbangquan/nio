package com.lbq.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

/*
 * 一、通道（channel）：用于源节点与目标节点的连接。在Java NIO 中负责缓冲区中数据的传输。Channel本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * java.nio.channels.Channel接口：
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * 
 * 三、获取通道
 * 1.java 针对支持通道的类提供了getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 
 * 网络IO:
 * Socket
 * ServerSocket
 * DatagramSocket
 * 
 * 2.在JDK 1.7 中的NIO.2针对各个通道提供了静态方法open()
 * 3.在JDK 1.7 中的NIO.2的Files工具类的newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散（Scatter）与聚集（Gather）
 * 分散读取（Scattering Reads）：将通道中的数据分散到多缓冲区中
 * 聚集写入（Gathering Writers）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组 ->字符串
 */
public class TestChannel {

	//利用通道完成文件的复制（非直接缓冲区）
	@Test
	public void test() {//6706
		long start = System.currentTimeMillis();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		//1.获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		
		try {
			fis = new FileInputStream("D:/supan/新建文件夹/nio.zip");
			fos = new FileOutputStream("D:/supan/新建文件夹/nio2.zip");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//2.分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//3.将通道中的数据存入缓冲区中
			while(inChannel.read(buf) != -1) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(outChannel != null) {
				try {
					outChannel.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(inChannel != null) {
				try {
					inChannel.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null) {
				try {
					fis.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

	//使用直接缓冲区完成文件的复制（内存映射文件）
	@Test
	public void test2() throws IOException {//1590
		long start = System.currentTimeMillis();
		
		FileChannel inChannel = FileChannel.open(Paths.get("D:/supan/新建文件夹/nio.zip"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("D:/supan/新建文件夹/nio2.zip"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
		
		System.out.println("inChannel.size():"+inChannel.size());
		//内存映射文件
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		System.out.println("inMappedBuf.limit():"+inMappedBuf.limit());
		//直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);
		
		inChannel.close();
		outChannel.close();
		
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}
	
	//通道之间的数据传输（直接缓冲区）
	@Test
	public void test3() throws IOException {//2040-1742
		long start = System.currentTimeMillis();
		FileChannel inChannel = FileChannel.open(Paths.get("D:/supan/新建文件夹/abc.txt"), StandardOpenOption.READ);
		System.out.println("inChannel.size()" + inChannel.size());
		FileChannel outChannel = FileChannel.open(Paths.get("D:/supan/新建文件夹/abc2.txt"), StandardOpenOption.APPEND);
		System.out.println("outChannel.size()" + outChannel.size());
		FileChannel inChannel2 = FileChannel.open(Paths.get("D:/supan/新建文件夹/abc2.txt"), StandardOpenOption.READ);
		System.out.println("inChannel2.size()" + inChannel2.size());
		FileChannel outChannel2 = FileChannel.open(Paths.get("D:/supan/新建文件夹/abc3.txt"), StandardOpenOption.APPEND);
		System.out.println("outChannel2.size()" + outChannel2.size());
//		inChannel.transferTo( 0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, outChannel.size(), inChannel.size());
//		outChannel.transferFrom(inChannel, outChannel.size(), inChannel.size());
//		outChannel.transferTo(0, outChannel.size(), outChannel2);
		outChannel2.transferFrom(inChannel2, outChannel2.size(), inChannel2.size());
		
		
		System.out.println("inChannel2.size()" + inChannel2.size());
		System.out.println("outChannel.size()" + outChannel.size());
		System.out.println("outChannel2.size()" + outChannel2.size());
		
//		inChannel.close();
//		outChannel.close();
//		outChannel2.close();
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}
	
	@Test
	public void test4() throws IOException {
		RandomAccessFile raf1 = new RandomAccessFile("D:/supan/新建文件夹/1.txt", "rw");
		
		//1.获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2.分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		//3.分散读取
		ByteBuffer[] bufs = {buf1, buf2};
		channel1.read(bufs);
		
		for(ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("----------------------------------------------");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
		
		//4.聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("D:/supan/新建文件夹/2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
	}
	
	@Test
	public void test5() {
		System.out.println("default: "+Charset.defaultCharset());
		Map<String, Charset> map = Charset.availableCharsets();
		Set<Entry<String, Charset>> set = map.entrySet();
		
		for(Entry<String, Charset> entry : set) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}
	
	@Test
	public void test6() throws CharacterCodingException {
		Charset cs1 = Charset.forName("GBK");
		
		//获取编码器
		CharsetEncoder ce = cs1.newEncoder();
		//获取解码器
		CharsetDecoder cd = cs1.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("尚硅谷威武！");
		cBuf.flip();
		
		//编码
		ByteBuffer bBuf = ce.encode(cBuf);
		
		for(int i = 0; i < bBuf.limit(); i++) {
			System.out.println(bBuf.get());
		}
		
		//解码
		bBuf.flip();
		CharBuffer cBuf2 = cd.decode(bBuf);
		System.out.println(cBuf2.toString());
		
		System.out.println("---------------------------------------------------");
		
		Charset cs2 = Charset.forName("UTF-8");
		bBuf.flip();
		CharBuffer cBuf3 = cs2.decode(bBuf);
		System.out.println(cBuf3.toString());
	}
}
