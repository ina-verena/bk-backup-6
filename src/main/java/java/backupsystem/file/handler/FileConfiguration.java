package java.backupsystem.file.handler;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class FileConfiguration {

    public boolean doesFileExists() {
        File f = new File("config.txt");
        if (f.exists()) {
            return true;
        }else{
            return false;
        }
    }

    public void writeFile(File fileName){
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName)));
            xmlEncoder.writeObject(fileName);
            xmlEncoder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(File file){
        try{
            XMLDecoder xmlDecoder = new XMLDecoder(
                                        new BufferedInputStream(
                                            new FileInputStream(file)));
            xmlDecoder.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
