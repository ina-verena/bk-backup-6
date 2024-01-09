
import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class App {

    public File[] listAllDrives() {
        File[] drives = File.listRoots();
        return drives;
    }


    public long getFreeSpace(File aDrive) {
        long freeSpace = aDrive.getFreeSpace();
        return freeSpace / 1000000000;
    }

    public long getTotalSpace(File aDrive) {
        long totalSpace = aDrive.getTotalSpace();
        return totalSpace / 1000000000;
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        FileSystemView fsv = FileSystemView.getFileSystemView();

        File[] drives = app.listAllDrives();

        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) { 
                System.out.println("Drive Letter: " + aDrive);
                System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
                System.out.println("\tTotal space: " + app.getTotalSpace(aDrive) + "Gb");
                System.out.println("\tFree space: " + app.getFreeSpace(aDrive) + "Gb");
                System.out.println();
            }
        }
    }
}
