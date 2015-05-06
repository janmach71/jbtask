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

        if (listOfFiles ==null) {
            return new ArrayList<DirItem>();
        }

        List<DirItem> list = new ArrayList<DirItem>(listOfFiles.length);

        for (File file : listOfFiles) {
            DirItem item = new DirItem(file);
            list.add(item);
        }
        return list;
    }
    protected static boolean isInDirectory(String name,String dir) {
        String[] na = name.split("[\\\\/]",-1) ;
        String[] da = dir.split("[\\\\/]",-1) ;

        if ( (dir == null || dir.isEmpty()))  {
            if (na.length == 0) {
                return true;
            }
            if ( na.length ==2 && na[1].isEmpty()) {
                return true;
            }
            return false;
        }
        if (na.length == 0) {
            return false;
        }
        if (na[na.length-1].isEmpty()) {
            String n = "";
            for (int i=0; i<na.length - 2; i++) {
                n+=na[i]+"/";
            }
            if (na.length == da.length + 1 && n.equals(dir)) {
                return true;
            }
        } else {
            String n = "";
            for (int i=0; i<na.length - 1; i++) {
                n+=na[i]+"/";
            }
            if (na.length == da.length && n.equals(dir)) {
                return true;
            }
        }
        return false;
    }
    public static List<DirItem> getDirInZip(String zip,String dir) throws Exception{
        List<DirItem> list = new ArrayList<DirItem>();
        File zipfile = new File(zip);
        ZipInputStream zipStream=new ZipInputStream(new FileInputStream(zipfile));
        if (zipStream == null) {
            return list;
        }
        ZipEntry entry=zipStream.getNextEntry();
        String basename=zip + "/";
        while (entry != null) {
            String name = entry.getName();
            if ( !name.isEmpty() && isInDirectory (name, dir)) {
                if ( name.charAt(name.length()-1)=='/') {
                    name = name.substring(0,name.length()-1);
                }
                DirItem item = new DirItem(basename + name, entry.isDirectory());
                if (item.getType() == DirItem.Type.archive) {
                    item.setType(DirItem.Type.unknown);
                }
                list.add(item);
            }
            zipStream.closeEntry();
            entry=zipStream.getNextEntry();
        }
        return list;
    }
    public static void getDirJson(Writer out, String dir) throws Exception{
        String[] array = dir.split("[\\\\/]",-1) ;
        String name="";
        for (int i=0 ;i<array.length;i++) {
            if (array[i].length() == 0) {
                continue;
            }
            name += "/" + array[i];
            File file = new File(name);
            switch (DirItem.determineType(file)) {
                case archive: {
                    String mimeType = URLConnection.guessContentTypeFromName(name);
                    if (mimeType != null && mimeType.equals("application/zip")) {
                        String zipdir = "";
                        for (int j = i+1; j < array.length; j++) {
                            zipdir += array[j]+"/";
                        }
                        getJson(out, getDirInZip(name, zipdir));
                        return;
                    } else {
                        throw new Exception("Unsupported archive type.");
                    }
                }
                default:
                    break;
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
