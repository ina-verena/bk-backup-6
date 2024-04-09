package backup.system.model;

import java.io.File;
import java.util.Date;

public class Data {
    private long length;
    private File source;

    private File target;
    private long lastModified;

    public Data(long length, File source, File target,long lastModified) {
        this.length = length;
        this.source = source;
        this.target = target;
        this.lastModified = lastModified;
    }


    public long getLength() {
        return length;
    }

    public File getSource() {
        return source;
    }

    public File getTarget() {
        return target;
    }

    public long getLastModified() {
        return lastModified;
    }
}
