package pt.com.santos.util.program.mkvtoolnix;

import pt.com.santos.util.program.ProgramDirectory;
import java.io.FileNotFoundException;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;

public class Aften extends ProgramDirectory {
    protected static Aften instance;

    private Aften() {
        super("aften", new String[]{"aften"});
    }

    public static Aften getInstance() {
        if (instance == null) instance = new Aften();
        return instance;
    }

    public ExecutableFile getProgram() throws NotExecutableException,
            FileNotFoundException, UnsupportedOperatingSystemException {
        return getProgram("aften");
    }
}
