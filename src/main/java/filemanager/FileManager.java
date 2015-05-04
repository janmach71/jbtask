package main.java.filemanager;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 03/05/15.
 */
public class FileManager {
    public static List<DirItem> getDir(String dir) {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        List<DirItem> list = new ArrayList<DirItem>(listOfFiles.length);

        for (File file : listOfFiles) {
            DirItem item = new DirItem(file);
            list.add(item);
        }
        return list;
    }
    public static void getDirJson(Writer out, String dir) throws IOException{
        //keep in list, so we can sort on arbitrary property
        List<DirItem> list = getDir(dir);
        String col="";
        out.write("\"dir\" : {[\n");
        for (DirItem item : list) {
            out.write(col);
            out.write("\"i\" : {\n");
            out.write("\"n\" : \"");
            out.write(item.getName());
            out.write("\",\n");
            out.write("\"t\" : \"");
            out.write(item.getType().toString());
            out.write("\"}");
            col=",";
        }
        out.write( "]}" );
    }
}
