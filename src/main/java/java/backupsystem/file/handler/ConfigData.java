package java.backupsystem.file.handler;

import java.util.Date;
import java.util.UUID;

public record ConfigData(UUID uuid, Date lastModified) {
}
