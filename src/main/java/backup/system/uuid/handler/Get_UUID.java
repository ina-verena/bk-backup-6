package backup.system.uuid.handler;

import backup.system.file.handler.ConfigData;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Get_UUID {

    public boolean compareUUID(String UUIDLocal, String UUIDDrive){
        if(UUIDLocal.equals(UUIDDrive)){
            return true;
        }
        return false;
    }

    public String getUUIDAsString(Path path){
        ConfigData configData = new ConfigData();
        return configData.loadFile(path).getUuid();
    }


}
