package pt.com.santos.util.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.program.mediainfo.MediaInfo;
import pt.com.santos.util.program.mkvtoolnix.MKVToolnix;

/*public class ProgramDirectoryTest extends TestCase {
    
    public ProgramDirectoryTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("mkvtoolnix.path", 
                "D:\\Users\\tvcsa\\OneDrive\\Documents\\NetBeansProjects\\WDTVConverter\\mkvtoolnix");
        System.setProperty("mediainfo.path", 
                "C:\\Users\\tvcsa\\Downloads\\mediainfo");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMKVToolnix()
            throws FileNotFoundException, IOException, 
            UnsupportedOperatingSystemException, InterruptedException,
            NotExecutableException {
        File f2 = new File(
                "W:\\Downloads\\Shows\\Family Guy (1999)\\Season 14\\"
                + "Family.Guy.S14E11.720p.HDTV.x264-KILLERS.mkv");
        pt.com.santos.util.Properties p =
                MKVToolnix.getInstance().getMKVInfoProperties(f2);
        List<pt.com.santos.util.Properties> chaps = p.get("Chapters");
        System.out.println(p.toFormatedString());
    }

    public void testMediaInfo()
            throws FileNotFoundException, IOException, 
            NotExecutableException, UnsupportedOperatingSystemException {
        File file = new File(
                "W:\\Downloads\\Shows\\Family Guy (1999)\\Season 14\\"
                + "Family.Guy.S14E11.720p.HDTV.x264-KILLERS.mkv");
        pt.com.santos.util.Properties root =
                MediaInfo.getInstance().load(file);

        System.out.println(root.get("Audio").size());
        System.out.println(root.toFormatedString());
    }
}*/
