package backup.system.services;

import backup.system.model.ConfigData;

import java.nio.file.Path;

public class GetUUIDService {

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
     * Wandelt die UUID in dem File des übergebenen Paths in einen String
     * @param path des Files
     * @return
     */
    public String getUUIDAsString(Path path){
        ConfigData configData = new ConfigData();
        return configData.loadFile(path).getUuid();
    }


}
