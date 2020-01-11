package pt.com.santos.util.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import pt.com.santos.util.system.SystemUtilities;
import pt.com.santos.util.collection.ArraysExtended;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;

public class ProgramDirectory {
    protected String dirName;
    protected String propPathName;
    protected File path;
    protected String[] programs;
    
    public ProgramDirectory(String dirName) {
        this(dirName, dirName.replaceAll(" ", "")
                .toLowerCase().concat(".path"), null);
    }

    public ProgramDirectory(String dirName, String propPathName) {
        this(dirName, propPathName, null);
    }

    public ProgramDirectory(String dirName, String propPathName,
            String[] programs) {
        this.dirName = dirName;
        this.propPathName = propPathName;
        this.programs = programs;
    }

    public ProgramDirectory(String dirName, String[] programs) {
        this(dirName, dirName.replaceAll(" ", "")
                .toLowerCase().concat(".path"), programs);
    }
   
    public File getDirectory()
            throws FileNotFoundException, UnsupportedOperatingSystemException {
        if (path == null) {
            /** location overrided by some application **/
            String s = System.getProperty(propPathName);
            if (s == null) {
                /** location not overrided try system location **/
                if (SystemUtilities.osIsWindows()) s = getWindows();
                else if (SystemUtilities.osIsLinux()) s = getLinux();
                else if (SystemUtilities.osIsMac()) s = getMac();
                else s = getOtherOS();
            }

            if (s != null) {
                path = new File(s);
                if (!path.exists() || !path.isDirectory()) {
                    path = null;
                    throw new FileNotFoundException();
                }
            } else throw new FileNotFoundException();
        }
        return path;
    }

    protected String getWindows() {
        String s = null;
        String pp = System.getenv("ProgramFiles(x86)");
        if (pp != null) /** 64 bits **/
        {
            s = System.getenv("ProgramW6432")
                    + SystemUtilities.FILE_SEPARATOR + dirName;
            /** check 64bit dir **/
            if (!new File(s).exists()) {
                s = null;
            }
            if (s == null) /** if not found at 64bit dir try 32bit **/
            {
                s = pp + SystemUtilities.FILE_SEPARATOR + dirName;
            }
        } else {
            /** must be 32bits os **/
            s = System.getProperty("ProgramFiles")
                    + SystemUtilities.FILE_SEPARATOR + dirName;
        }
        return s;
    }

    protected String getLinux() throws UnsupportedOperatingSystemException {
        if (programs == null || programs.length <= 0)
            throw new UnsupportedOperatingSystemException();
        String s = null;
        try {
            String where = SystemUtilities.whereIs(programs[0]);
            if (where != null) {
                File f = new File(where);
                if (f.exists()) {
                    s = f.getParentFile().getAbsolutePath();
                }
            }
        } catch (IOException ex) {
        }
        return s;
    }

    protected String getMac() throws UnsupportedOperatingSystemException {
        return getLinux();
    }

    protected String getOtherOS() throws UnsupportedOperatingSystemException {
        throw new UnsupportedOperatingSystemException();
    }
    
    public ExecutableFile getProgram(String name)
        throws NotExecutableException, FileNotFoundException,
            UnsupportedOperatingSystemException {
        if (!ArraysExtended.contains(programs, name))
            throw new FileNotFoundException(name + 
                    " not found in this program directory");
        File dir = getDirectory();
        ExecutableFile res = null;
        if (SystemUtilities.osIsWindows())
            res = new ExecutableFile(dir, name + ".exe");
        else if (SystemUtilities.osIsLinux() || SystemUtilities.osIsMac())
            res = new ExecutableFile(dir, name);
        else throw new UnsupportedOperatingSystemException();
        if (res == null || !res.exists() || !res.isFile())
            throw new FileNotFoundException();
        return res;
    }
}
