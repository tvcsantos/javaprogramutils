package pt.com.santos.util.program.mkvtoolnix;

import pt.com.santos.util.program.ProgramDirectory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import org.apache.commons.exec.ExecuteException;
import pt.com.santos.util.Properties;
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;

public class MKVToolnix extends ProgramDirectory {
    protected ExecutableFile mkvmerge;
    protected ExecutableFile mkvinfo;
    protected ExecutableFile mkvextract;
    
    protected static MKVToolnix instance;
    
    public static MKVToolnix getInstance() {
        if (instance == null) 
            instance = new MKVToolnix();
        return instance;
    }
    
    private MKVToolnix() {
        super("mkvtoolnix", new String[]{ "mkvinfo", 
            "mkvmerge", "mkvextract" });
    }
    
    public ExecutableFile getMKVInfo() throws NotExecutableException, 
            FileNotFoundException, UnsupportedOperatingSystemException {
        if (mkvinfo == null)
            mkvinfo = getProgram("mkvinfo");
        return mkvinfo;
    }
    
    public ExecutableFile getMKVMerge() throws NotExecutableException, 
            FileNotFoundException, UnsupportedOperatingSystemException {
        if (mkvmerge == null)
            mkvmerge = getProgram("mkvmerge");
        return mkvmerge;
    }
    
    public ExecutableFile getMKVExtract() throws NotExecutableException, 
            FileNotFoundException, UnsupportedOperatingSystemException {
        if (mkvextract == null)
            mkvextract = getProgram("mkvextract");
        return mkvextract;
    }
    
    private static int getLevel(String line) {
        int index = line.indexOf("+");
        if (index < 0) {
            return -1;
        }
        String ls = line.substring(0, index + 1);
        if (!ls.contains("|")) {
            return 0;
        } else {
            String blanks = ls.substring(1, ls.length() - 1);
            return blanks.length() + 1;
        }
    }

    public Properties getMKVInfoProperties(File source)
            throws IOException, NotExecutableException, 
            FileNotFoundException, ExecuteException,
            UnsupportedOperatingSystemException {
        if (source == null) {
            throw new NullPointerException("file must be non null");
        }
        if (!source.exists()) {
            throw new FileNotFoundException("file doesn't exist");
        }
        if (!source.isFile()) {
            throw new IllegalArgumentException("file must be a file");
        }
        ExecutableFile execFile = getMKVInfo();

        ExecutableFile.ExecutionResult res = execFile.execute(
                new String[]{"\"" + source.getAbsolutePath() + "\""});

        if (res.getExitCode() != 0) {
            return null;
        }
        BufferedReader bufferedreader =
                new BufferedReader(new StringReader(res.getOutput()));

        String line;
        Properties root = new Properties("File", source.getName());
        Properties current = root;
        int lastLevel = -1;
        while ((line = bufferedreader.readLine()) != null) {
            line = line.trim();
            if (line.length() <= 0) {
                continue;
            }
            int level = getLevel(line);
            String rep = line.replace("+", "").replace("|", "");
            int i = rep.indexOf(":");
            if (i < 0) {
                i = rep.indexOf(",");
            }
            String key = null;
            String value = null;
            if (i < 0) {
                key = rep.trim();
            } else {
                key = rep.substring(0, i).trim();
                value = rep.substring(i + 1).trim();
            }
            Properties newNode = new Properties(key, value);
            if (level > lastLevel) {
                current.add(newNode);
                current = newNode;
            } else if (level < lastLevel) {
                int down = lastLevel - level + 1;
                Properties node = current;
                while (down-- > 0) {
                    node = node.getParent();
                }
                node.add(newNode);
                current = newNode;
            } else {
                Properties parent = current.getParent();
                parent.add(newNode);
                current = newNode;
            }
            lastLevel = level;
        }
        return root;
    }
}
