package cn.mineki.CardReaders;

import com.sun.jna.Library;
import com.sun.jna.Native;


public interface ReaderAPI extends Library {
	ReaderAPI instanceDll  = (ReaderAPI)Native.loadLibrary("Id_Synjones.dll",ReaderAPI.class);
	int initDriver(Object callback);
	int ReadInfo(byte[] args, byte[] resmsg, byte[] errmsg, int readTimeout, int writeTimeout);
	int deinit();
}

