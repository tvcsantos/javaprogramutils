package pt.com.santos.util.program.mkvtoolnix;

import pt.com.santos.util.program.ProgramDirectory;
import java.io.FileNotFoundException;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;

public class Eac3to extends ProgramDirectory {
    protected static Eac3to instance;
    
    private Eac3to() {
        super("eac3to", new String[]{"eac3to"});
    }    
    
    public static Eac3to getInstance() {
        if (instance == null) instance = new Eac3to();
        return instance;
    }

    public ExecutableFile getProgram() throws NotExecutableException,
            FileNotFoundException, UnsupportedOperatingSystemException {
        return getProgram("eac3to");
    }
}
