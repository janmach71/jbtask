package main.java.filemanager;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mac on 06/05/15.
 */
public class Main {
    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter(System.out);
            //basic zip test
            FileManager.getDirJson(writer,"/Users/mac/IdeaProjects/jbtask.zip");
            FileManager.getDirJson(writer,"/Users/mac/IdeaProjects/jbtask.zip/jbtask");
            FileManager.getDirJson(writer,"/Users/mac/IdeaProjects/jbtask.zip/__MACOSX");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
