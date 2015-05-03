package main.webapp.filemanager;

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
        zip,
        folder,
        unknown
    }
    //String mimeType;
    String name;
    Type type;
    private static Map<String,Type> types = new HashMap<String, Type>();
    static {
        types.put("image/",Type.image);
        types.put("text/",Type.text);
        types.put("application/zip",Type.zip);
    }
    protected Type determineType(String name) {
        if (new File(name).isDirectory()) {
            return Type.folder;
        }
        String mimeType = URLConnection.guessContentTypeFromName(name);
        int i = mimeType.indexOf('/');
        if (i > 0) {
            Type t = types.get(mimeType.substring(0, i));
            if (t!=null) {
                return t;
            }
        }
        Type t = types.get(mimeType);
        if (t!=null) {
            return t;
        }
        return Type.unknown;
    }
    String getName() {
        return name;
    }
    DirItem(String name) {
        this.name = name;
        this.type=determineType(name);
    }
}
