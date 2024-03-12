package backup.system.uuid.handler;

import backup.system.file.handler.ConfigData;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Get_UUID {

    /**
     * Diese Methode vergleicht die UUID in der Local gespeicherten Config Datei
     * mit der UUID in der Config Datei auf dem externen Laufwerk
     * @param UUIDLocal UUID Lokal
     * @param UUIDDrive UUID externes Laufwerk
     * @return boolean
     */
    public boolean compareUUID(String UUIDLocal, String UUIDDrive){
        if(UUIDLocal.equals(UUIDDrive)){
            return true;
        }
        return false;
    }

    /**
     * Wandelt die UUID in dem File des übergebenen Paths in einen Strong
     * @param path
     * @return
     */
    public String getUUIDAsString(Path path){
        ConfigData configData = new ConfigData();
        return configData.loadFile(path).getUuid();
    }


}
