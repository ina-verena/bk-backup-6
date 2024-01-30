package backup.system.drive.handler;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class App {

    /**
     * This method lists all internal and external drives and puts them into an array
     * @return array of File's
     */
    public File[] listAllDrives() {
        File[] drives = File.listRoots();
        return drives;
    }

    /**
     * This method checks the
     * @param aDrive
     * @return
     */
    public long getUsableSpace(File aDrive) {
        long freeSpace = aDrive.getUsableSpace();
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
                System.out.println("\tFree space: " + app.getUsableSpace(aDrive) + "Gb");
                System.out.println();
            }
        }


//        File sourceFile = new File("path/to/source/file");
//        File destFile = new File("path/to/destination/file");
//
//        try (FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
//             FileChannel destChannel = new FileOutputStream(destFile).getChannel()) {
//            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
