package pt.com.santos.util.program.mkvtoolnix;

import pt.com.santos.util.program.ProgramDirectory;
import java.io.FileNotFoundException;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;

public class Dcadec extends ProgramDirectory {
    protected static Dcadec instance;

    private Dcadec() {
        super("dcadec", new String[]{"dcadec"});
    }

    public static Dcadec getInstance() {
        if (instance == null) instance = new Dcadec();
        return instance;
    }

    public ExecutableFile getProgram() throws NotExecutableException,
            FileNotFoundException, UnsupportedOperatingSystemException {
        return getProgram("dcadec");
    }
}
