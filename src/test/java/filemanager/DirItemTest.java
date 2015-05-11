package test.java.filemanager;

import junit.framework.Assert;
import junit.framework.TestCase;
import main.java.filemanager.DirItem;

public class DirItemTest extends TestCase {

    public void testDetermineType() throws Exception {
        {DirItem item = new DirItem("ahoj.zip",false);  Assert.assertEquals("zip",item.getType(), DirItem.Type.archive);}
        {DirItem item = new DirItem("ahoj.rar",false);  Assert.assertEquals("rar",item.getType(), DirItem.Type.archive);}
        {DirItem item = new DirItem("ahoj.7z",false);   Assert.assertEquals("7z",item.getType(), DirItem.Type.archive);}
        {DirItem item = new DirItem("ahoj.cab",false);   Assert.assertEquals("cab",item.getType(), DirItem.Type.archive);}
        {DirItem item = new DirItem("ahoj.gz",false);   Assert.assertEquals("gz",item.getType(), DirItem.Type.archive);}

        {DirItem item = new DirItem("ahoj.jpg",false);   Assert.assertEquals("jpg",item.getType(), DirItem.Type.image);}
        {DirItem item = new DirItem("ahoj.jpeg",false);   Assert.assertEquals("jpeg",item.getType(), DirItem.Type.image);}
        {DirItem item = new DirItem("ahoj.gif",false);   Assert.assertEquals("gif",item.getType(), DirItem.Type.image);}
        {DirItem item = new DirItem("ahoj.png",false);   Assert.assertEquals("png",item.getType(), DirItem.Type.image);}
        //{DirItem item = new DirItem("ahoj.bmp",false);   Assert.assertEquals("bmp",item.getType(), DirItem.Type.image);}

        {DirItem item = new DirItem("ahoj.txt",false);   Assert.assertEquals(item.getType(), DirItem.Type.text);}
        {DirItem item = new DirItem("ahoj.html",false);   Assert.assertEquals(item.getType(), DirItem.Type.text);}

        {DirItem item = new DirItem("ahoj.xddsdaf",false);   Assert.assertEquals(item.getType(), DirItem.Type.unknown);}

        {DirItem item = new DirItem("ahoj.zip",true);  Assert.assertEquals("zip",item.getType(), DirItem.Type.folder);}
        {DirItem item = new DirItem("ahoj.jpg",true);   Assert.assertEquals("jpg",item.getType(), DirItem.Type.folder);}
        {DirItem item = new DirItem("ahoj.txt",true);   Assert.assertEquals(item.getType(), DirItem.Type.folder);}
        {DirItem item = new DirItem("ahoj.xddsdaf",true);   Assert.assertEquals(item.getType(), DirItem.Type.folder);}
    }

    public void testGetName() throws Exception {
        {DirItem item = new DirItem("ahoj.zip",false);  Assert.assertEquals(item.getName(), "ahoj.zip");}
        {DirItem item = new DirItem("/ahoj/nazdar/ahoj.zip",false);  Assert.assertEquals(item.getName(), "/ahoj/nazdar/ahoj.zip");}
    }

    public void testSetType() throws Exception {
        DirItem item = new DirItem("ahoj.zip",false);
        Assert.assertEquals(item.getType(), DirItem.Type.archive);
        item.setType(DirItem.Type.unknown);
        Assert.assertEquals(item.getType(), DirItem.Type.unknown);
    }
}