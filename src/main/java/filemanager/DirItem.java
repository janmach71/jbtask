package main.java.filemanager;

import java.io.File;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 03/05/15.
 */
public class DirItem {
    enum Type {
        image,
        text,
        archive,
        folder,
        unknown
    }
    String name;
    Type type;
    private static Map<String,Type> types = new HashMap<String, Type>();
    static {
        types.put("image",Type.image);
        types.put("text",Type.text);
        types.put("application/zip",Type.archive);
        types.put("application/x-rar-compressed",Type.archive);
        types.put("application/x-7z-compressed",Type.archive);
        types.put("application/x-ace-compressed",Type.archive);
        types.put("application/vnd.ms-cab-compressed",Type.archive);
        types.put("arj",Type.archive);
        types.put("rar",Type.archive);
        types.put("7z",Type.archive);
        types.put("gz",Type.archive);
        types.put("cab",Type.archive);
    }
    public static Type determineType(File file) {
        if (file.isDirectory()) {
            return Type.folder;
        }
        String extension = "";

        return determineType(file.getAbsolutePath());
    }
    public static Type determineType(String name) {
        String mimeType = URLConnection.guessContentTypeFromName(name);
        if (mimeType != null) {
            int i = mimeType.indexOf('/');
            if (i > 0) {
                Type t = types.get(mimeType.substring(0, i));
                if (t != null) {
                    return t;
                }
            }
            Type t = types.get(mimeType);
            if (t != null) {
                return t;
            }
        }
        int i = name.lastIndexOf('.');
        if (i >= 0) {
            Type t = types.get(name.substring(i+1));
            if (t != null) {
                return t;
            }
        }
        return Type.unknown;
    }
    String getName() {
        return name;
    }
    Type getType() {
        return type;
    }
    void setType(Type type) {
        this.type = type;
    }
    DirItem(File file) {
        this.name = file.getAbsolutePath();
        this.type=determineType(file);
    }
    DirItem(String name,boolean dir) {
        this.name = name;
        if (dir) {
            this.type = Type.folder;
        } else {
            this.type = determineType(name);
        }
    }
}
