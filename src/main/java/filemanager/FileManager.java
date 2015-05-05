package main.java.filemanager;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by mac on 03/05/15.
 */
public class FileManager {
    public static List<DirItem> getDir(String dir) throws Exception{
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        List<DirItem> list = new ArrayList<DirItem>(listOfFiles.length);

        for (File file : listOfFiles) {
            DirItem item = new DirItem(file);
            list.add(item);
        }
        return list;
    }
    protected static boolean isInDirectory(String name,String dir) {
        String[] array = name.split("[\\\\/]",-1) ;
        if ( (dir == null || dir.isEmpty()) && array.length == 1)  {
            return true;
        }
        String n = "";
        for (int i=0 ;i<array.length - 1;i++) {
            n+=array[i];
        }
        if (n.equals(dir)) {
            return true;
        }
        return false;
    }
    public static List<DirItem> getDirInZip(String zip,String dir) throws Exception{
        File zipfile = new File(zip);
        ZipInputStream zipStream=new ZipInputStream(new FileInputStream(zipfile));
        ZipEntry entry=zipStream.getNextEntry();
        List<DirItem> list = new ArrayList<DirItem>();
        String basename=zip + "/";
        while (entry != null) {
            String name = entry.getName();
            if ( isInDirectory (name, dir)) {
                DirItem item = new DirItem(basename + name, entry.isDirectory());
                list.add(item);
            }
            zipStream.closeEntry();
            entry=zipStream.getNextEntry();
        }
        return list;
    }
    public static void getJsonDir(Writer out, String dir) throws Exception{
        String[] array = dir.split("[\\\\/]",-1) ;
        String name="";
        for (int i=0 ;i<array.length;i++) {
            name+=array[i];
            File file = new File(name);
            if (file.isFile()) {
                String mimeType = URLConnection.guessContentTypeFromName(name);
                if (mimeType.equals("application/zip")) {
                    String zipdir = "";
                    for (int j=i ; j< array.length; j++) {
                        zipdir += array[j];
                    }
                    getJson(out, getDirInZip(name, zipdir));
                    return;
                } else if  (mimeType.equals("application/zip")) {
                    throw new Exception("RAR is not supported.");
                }
            }
        }
        getJson(out,getDir(dir));
    }
    public static void getJson(Writer out, List<DirItem> list) throws Exception{
        //keep in list, so we can sort on arbitrary property
        String col="";
        out.write("{\"dir\" : [\n");
        for (DirItem item : list) {
            out.write(col);
            out.write("{\"i\" : {");
            out.write("\"n\" : \"");
            out.write(item.getName());
            out.write("\",");
            out.write("\"t\" : \"");
            out.write(item.getType().toString());
            out.write("\"}}");
            col=",\n";
        }
        out.write( "\n]}" );
    }
}
