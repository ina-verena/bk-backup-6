package backup.system.file.handler;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

/**
 *
 */
public class ConfigData {
    private String uuid;
    private long lastModified;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public void createConfigData(Path path, ConfigData configData){
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(path.toString())));
            xmlEncoder.writeObject(configData);
            xmlEncoder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public ConfigData loadFile(Path path){
        ConfigData configData = null;
        try{
            XMLDecoder xmlDecoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(path.toString())));
            configData = (ConfigData) xmlDecoder.readObject();
            xmlDecoder.close();

        }catch(IOException ex){
            ex.printStackTrace();
        }
        return configData;
    }


    public boolean searchConfigFile(Path path){
        try {
            ConfigData configData = loadFile(path);
            if (configData == null) {
                return false;
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    private void createDir(){}

    public void initConfigData(Path path){
        if(searchConfigFile(path) == false) {
            ConfigData configData = new ConfigData();
            configData.setUuid(UUID.randomUUID().toString());
            createConfigData(path, configData);
        }
    }

    public void startSearchDrive(){}
}
