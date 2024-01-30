package backup.system.file.handler;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConfiguration {

    public void writeFile(String path, ConfigData configData){

        try {
            XMLEncoder xmlEncoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(path)));
            xmlEncoder.writeObject(configData);
            xmlEncoder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigData loadFile(String path){
        ConfigData configData = null;
        try{
            XMLDecoder xmlDecoder = new XMLDecoder(
                                        new BufferedInputStream(
                                            new FileInputStream(path)));
            configData = (ConfigData) xmlDecoder.readObject();
            xmlDecoder.close();

        }catch(IOException ex){
            ex.printStackTrace();
        }
        return configData;
    }

    public void hideFile(String filePath){
        Path path = Paths.get(filePath);
        try {
            Files.setAttribute(path, "dos:hidden", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
