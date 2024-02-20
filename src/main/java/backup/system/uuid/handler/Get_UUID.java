package backup.system.uuid.handler;

import backup.system.file.handler.ConfigData;

import java.util.UUID;

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

    /**
     * Füllt das Array mit gefundenen Laufwerken
     */
    public void assign(){

    }
}
