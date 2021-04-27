package cn.mineki.CardReaders;

import java.io.UnsupportedEncodingException;

public class ReaderTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		int ret=ReaderAPI.instanceDll.initDriver(null);
		System.out.println("initDriver ret="+ret);
		byte[]resmsg=new byte[4*1024*1024];
		byte[]errmsg=new byte[256];
		ret=ReaderAPI.instanceDll.ReadInfo(null, resmsg, errmsg, 0, 0);
		System.out.println("ReadInfo ret="+ret);
		System.out.println("ReadInfo resmsg="+new String(resmsg, "GBK").trim());
		System.out.println("ReadInfo errmsg="+new String(errmsg,"GBK").trim());
		ReaderAPI.instanceDll.deinit();
	}
}
