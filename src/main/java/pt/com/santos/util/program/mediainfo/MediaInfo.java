package pt.com.santos.util.program.mediainfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;
import pt.com.santos.util.Properties; 
import pt.com.santos.util.exception.NotExecutableException;
import pt.com.santos.util.exception.UnsupportedOperatingSystemException;
import pt.com.santos.util.io.ExecutableFile;
import pt.com.santos.util.io.ExecutableFile.ExecutionResult;
import pt.com.santos.util.program.ProgramDirectory;

public class MediaInfo extends ProgramDirectory {
    protected static MediaInfo instance;

    private MediaInfo() {
        super("mediainfo", new String[]{"mediainfo"});
    }

    public static MediaInfo getInstance() {
        if (instance == null) instance = new MediaInfo();
        return instance;
    }

    public ExecutableFile getProgram() throws NotExecutableException,
            FileNotFoundException, UnsupportedOperatingSystemException {
        return getProgram("mediainfo");
    }
    
    public Properties load(File file) throws
            FileNotFoundException, IOException, 
            NotExecutableException, UnsupportedOperatingSystemException {
        if (!file.exists())
            throw new FileNotFoundException("file doesn't exist");
        if (!file.isFile())
            throw new IllegalArgumentException("file must be a file");

        ExecutionResult result = 
                getProgram().execute(new String[]{file.toString()});
        
        StringReader sr = new StringReader(result.getOutput());
        BufferedReader bufferedreader = new BufferedReader(sr);

        // read the ls output
        Properties root = new Properties(file.toString(), null);
        Properties curr = root;

        String line;
        while ((line = bufferedreader.readLine()) != null) {
            line = line.trim();
            if (line.length() <= 0) continue;
            int index = line.indexOf(":");
            boolean isParent = index == -1;
            if (isParent) {
                int index2 = line.indexOf("#");
                if (index2 >= 0)
                    line = line.substring(0, index2).trim();
                Properties node = new Properties(line, null);
                root.add(node);
                curr = node;
            } else {
                String left = line.substring(0, index).trim();
                String right = line.substring(index + 1).trim();
                int index2 = right.indexOf(" / ");
                if (index2 >= 0) {
                    StringTokenizer st = new StringTokenizer(right,"/");
                    Properties p = new Properties(left, null);
                    curr.add(p);
                    curr = p;
                    while(st.hasMoreTokens()) {
                        String token = st.nextToken().trim();
                        String[] arr = token.split("=");
                        if (arr.length < 2) continue;
                        Properties node = new Properties(arr[0],arr[1]);
                        curr.add(node);
                    }
                    curr = curr.getParent();
                } else {
                    Properties node = new Properties(left, right);
                    curr.add(node);
                }
            }
        }
        return root;
    }
}
