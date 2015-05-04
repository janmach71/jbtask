package main.java.filemanager;

import java.io.File;
import javax.servlet.jsp.JspWriter;
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
    public static void getDirJson(JspWriter out, String dir) {
        //keep in list, so we can sort on arbitrary property
        List<DirItem> list = getDir(dir);
        out.println("Ahoj 1" );
        for (DirItem item : list) {
            out.println(item.getName() + "<br>");
        }
        out.println( "Ahoj 2" );
    }
}
