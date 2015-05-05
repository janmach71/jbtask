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
        types.put("image/",Type.image);
        types.put("text/",Type.text);
        types.put("application/zip",Type.archive);
        types.put("application/rar",Type.archive);
    }
    protected Type determineType(File file) {
        if (file.isDirectory()) {
            return Type.folder;
        }
        return determineType(name);
    }
    protected Type determineType(String name) {
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
        return Type.unknown;
    }
    String getName() {
        return name;
    }
    Type getType() {
        return type;
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
