package backup.system.file.handler;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Dieses Objekt stellt den Inhalt der Config Datei dar.
 * Die wichtigen Informationen diesen Objekt's sind die Attribute uuid und lastModified.
 * Diese initialisieren das Laufwerk und geben mit dem lastModified Änderungen des Dateisystems an.
 * Diese Änderungen werden für den vergleich bei dem Backup benötigt
 */
public class ConfigData {
    /**
     * Eine einzigartige Id, um das Laufwerk eindeutig erkennbar zu machen
     */
    private String uuid;
    /**
     * Ein long Wert in der ein Datum gespeichert ist, um anzugeben,
     * wann ein Backup zuletzt gemacht wurden
     */
    private long lastModified;

    /**
     * Gibt die uuid zurück
     * @return String uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Legt die uuid fest
     * @param uuid zum festlegen
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gibt den lastModified Wert zurück
     * @return long lastModified
     */
    public long getLastModified() {
        return lastModified;
    }

    /**
     * Lege den lastModified Wert fest
     * @param lastModified Wert zum festlegen
     */
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Diese Methode erzeugt eine Config Datei mit dem übergebenen Pfad
     * In diesem Config File sind alle Inhalte (Attribute sowie Funktionen) der Klasse @ConfigData enthalten
     * @param path In der das Config File gespeichert wird
     * @param configData Informationen/Daten welche abgespeichert werden
     */
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


    /**
     * Diese Methode läd eine Config Datei und konvertiert diese in ein ConfigData Objekt
     * @param path der Config Datei
     * @return ConfigData Objekt
     */
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

    /**
     * Diese Methode kontrolliert, ob eine Config Datei existiert
     * @param path der Config Datei
     * @return boolean
     */
    public boolean doesConfigFileExist(Path path){
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

    /**
     * Diese Methode initialisiert eine Config Datei und speichert diese unter dem Pfad ab,
     * falls noch keine Config Datei existiert
     * @param path der Config Datei
     */
    public void initConfigData(Path path){
        if(doesConfigFileExist(path) == false) {
            ConfigData configData = new ConfigData();
            configData.setUuid(UUID.randomUUID().toString());
            createConfigData(path, configData);
        }
    }

}
