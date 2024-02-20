package backup.system;

import backup.system.drive.handler.Drive;
import backup.system.file.handler.ConfigData;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        Drive drive = new Drive();
        UUID uuid = UUID.randomUUID();
        configData.setUuid(uuid.toString());
        configData.setLastModified(2123132141);
//        configData.createConfigData();

//        configData = fileConfiguration.loadFile();
//        System.out.println(configData.getUuid());
//        System.out.println(configData.getLastModified());

    }
}
