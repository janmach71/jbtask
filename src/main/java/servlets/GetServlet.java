package main.java.servlets;

/**
 * Created by mac on 12/05/15.
 */

import main.java.filemanager.DirItem;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLConnection;


@WebServlet("/api/v1/GetServlet")
public class GetServlet extends HttpServlet {

    /**
     * get file content
     */
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String path=request.getParameter("p");
        if (path == null || path.isEmpty()) {
            response.getWriter().println("Path is empty.");
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            response.getWriter().println("Path does not exists.");
            return;
        }
        switch (DirItem.determineType(file)){
            case text:
                response.setContentType("text/plain");
                break;
            case archive:
                response.getWriter().println("Path is directory.");
                return;
            case folder:
                response.getWriter().println("Unsupported file type.");
                return;
            default:
                InputStream stream = new FileInputStream(file);
                String mimeType = URLConnection.guessContentTypeFromStream(stream);
                stream.close();
                if (mimeType == null) {
                    mimeType = URLConnection.guessContentTypeFromName(path);
                    if (mimeType == null) {
                        response.getWriter().println("Unrecognized file type.");
                        return;
                    }
                }
                response.setContentType(mimeType);
        }
        InputStream stream = new FileInputStream(file);
        copyStream(stream, response.getOutputStream());
        stream.close();
    }
    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}