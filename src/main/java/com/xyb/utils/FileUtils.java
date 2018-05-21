package com.xyb.utils;

import java.io.*;

public class FileUtils {
    public static boolean writeBytesToFile(String path, byte[] bytes) throws IOException {
        if(path==null || bytes==null || bytes.length<0)
        {
            return false;
        }
        FileOutputStream stream = new FileOutputStream(path);
        try {
            stream.write(bytes);
            return true;
        } finally {
            stream.close();
        }
    }
}
