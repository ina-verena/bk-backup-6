package backup.system.uuid.handler;

import backup.system.file.handler.ConfigData;

import java.nio.file.Path;

public class Get_UUID {

    /**
     *
     * @param UUID
     * @return
     */
    public boolean compareUUID(String UUID){
        ConfigData configData = new ConfigData();
        if(configData.getUuid().equals(UUID)){
            return true;
        }
        return false;
    }

    public String getUUIDAsString(Path path){
        ConfigData configData = new ConfigData();
        return configData.loadFile(path).getUuid();
    }


}
