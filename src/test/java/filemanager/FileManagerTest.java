package test.java.filemanager;

import junit.framework.Assert;
import junit.framework.TestCase;
import main.java.filemanager.DirItem;
import main.java.filemanager.FileManager;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManagerTest extends TestCase {

    public void testIsInDirectory() throws Exception {
        Assert.assertFalse("a0",FileManager.isInDirectory("",""));
        Assert.assertTrue("a1", FileManager.isInDirectory("aaa", ""));
        Assert.assertFalse("a1a", FileManager.isInDirectory("", "aaa"));
        Assert.assertFalse("a2", FileManager.isInDirectory("aaa", "aaa"));
        Assert.assertFalse("a3",FileManager.isInDirectory("aaa", "aaa/aaa"));
        Assert.assertTrue("a4", FileManager.isInDirectory("aaa/aaa", "aaa"));
        Assert.assertFalse("a5", FileManager.isInDirectory("aaa/aaa", "aaa/aaa"));
        Assert.assertTrue("a6", FileManager.isInDirectory("aaa/aaa/aaa", "aaa/aaa"));
        Assert.assertFalse("b0", FileManager.isInDirectory("/", "/"));
        Assert.assertTrue("b1", FileManager.isInDirectory("aaa/", "/"));
        Assert.assertFalse("b1a", FileManager.isInDirectory("/", "aaa/"));
        Assert.assertFalse("b2", FileManager.isInDirectory("aaa/", "aaa/"));
        Assert.assertFalse("b3", FileManager.isInDirectory("aaa/", "aaa/aaa/"));
        Assert.assertTrue("b4", FileManager.isInDirectory("aaa/aaa/", "aaa/"));
        Assert.assertFalse("b5", FileManager.isInDirectory("aaa/aaa/", "aaa/aaa/"));
        Assert.assertTrue("b6", FileManager.isInDirectory("aaa/aaa/aaa/", "aaa/aaa/"));
        Assert.assertFalse("c0", FileManager.isInDirectory("/", ""));
        Assert.assertTrue("c1", FileManager.isInDirectory("aaa/", ""));
        Assert.assertFalse("c1a", FileManager.isInDirectory("/", "aaa"));
        Assert.assertFalse("c2", FileManager.isInDirectory("aaa/", "aaa"));
        Assert.assertFalse("c3", FileManager.isInDirectory("aaa/", "aaa/aaa"));
        Assert.assertTrue("c4", FileManager.isInDirectory("aaa/aaa/", "aaa"));
        Assert.assertFalse("c5", FileManager.isInDirectory("aaa/aaa/", "aaa/aaa"));
        Assert.assertTrue("c6", FileManager.isInDirectory("aaa/aaa/aaa/", "aaa/aaa"));
        Assert.assertFalse("d0", FileManager.isInDirectory("", "/"));
        Assert.assertTrue("d1", FileManager.isInDirectory("aaa", "/"));
        Assert.assertFalse("d1a", FileManager.isInDirectory("", "aaa/"));
        Assert.assertFalse("d2", FileManager.isInDirectory("aaa", "aaa/"));
        Assert.assertFalse("d3", FileManager.isInDirectory("aaa", "aaa/aaa/"));
        Assert.assertTrue("d4", FileManager.isInDirectory("aaa/aaa", "aaa/"));
        Assert.assertFalse("d5", FileManager.isInDirectory("aaa/aaa", "aaa/aaa/"));
        Assert.assertTrue("d6", FileManager.isInDirectory("aaa/aaa/aaa", "aaa/aaa/"));
        Assert.assertTrue("e1", FileManager.isInDirectory("/aaa/", "/"));
        Assert.assertFalse("e2", FileManager.isInDirectory("/aaa/", "aaa/"));
        Assert.assertFalse("e3", FileManager.isInDirectory("/aaa/", "aaa/aaa/"));
        Assert.assertTrue("e4", FileManager.isInDirectory("/aaa/aaa/", "aaa/"));
        Assert.assertFalse("e5", FileManager.isInDirectory("/aaa/aaa/", "aaa/aaa/"));
        Assert.assertTrue("e6", FileManager.isInDirectory("/aaa/aaa/aaa/", "aaa/aaa/"));
        Assert.assertFalse("f2", FileManager.isInDirectory("/aaa/", "/aaa/"));
        Assert.assertFalse("f3",FileManager.isInDirectory("/aaa/", "/aaa/aaa/"));
        Assert.assertTrue("f4", FileManager.isInDirectory("/aaa/aaa/", "/aaa/"));
        Assert.assertFalse("f5", FileManager.isInDirectory("/aaa/aaa/", "/aaa/aaa/"));
        Assert.assertTrue("f6", FileManager.isInDirectory("/aaa/aaa/aaa/", "/aaa/aaa/"));
        Assert.assertFalse("g2", FileManager.isInDirectory("aaa/", "/aaa/"));
        Assert.assertFalse("g3",FileManager.isInDirectory("aaa/", "/aaa/aaa/"));
        Assert.assertTrue("g4", FileManager.isInDirectory("aaa/aaa/", "/aaa/"));
        Assert.assertFalse("g5", FileManager.isInDirectory("aaa/aaa/", "/aaa/aaa/"));
        Assert.assertTrue("g6", FileManager.isInDirectory("aaa/aaa/aaa/", "/aaa/aaa/"));
    }

    public void testGetJson() throws Exception {
        List<DirItem> list = new ArrayList<DirItem>();
        list.add(new DirItem("ahoj.zip",true));
        list.add(new DirItem("ahoj.zip",false));
        list.add(new DirItem("ahoj.jpg", false));
        list.add(new DirItem("ahoj.txt",false));
        list.add(new DirItem("ahoj.xddsdaf",false));
        StringWriter writer = new StringWriter();
        FileManager.getJson(writer,list);
        String json = writer.toString();
        Assert.assertTrue("JSON", json.equals("{\"dir\" : [\n" +
                "{\"i\" : {\"n\" : \"ahoj.zip\",\"t\" : \"folder\"}},\n" +
                "{\"i\" : {\"n\" : \"ahoj.zip\",\"t\" : \"archive\"}},\n" +
                "{\"i\" : {\"n\" : \"ahoj.jpg\",\"t\" : \"image\"}},\n" +
                "{\"i\" : {\"n\" : \"ahoj.txt\",\"t\" : \"text\"}},\n" +
                "{\"i\" : {\"n\" : \"ahoj.xddsdaf\",\"t\" : \"unknown\"}}\n" +
                "]}"));
    }
}