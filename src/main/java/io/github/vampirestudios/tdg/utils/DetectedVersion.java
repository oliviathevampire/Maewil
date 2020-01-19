package io.github.vampirestudios.tdg.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class DetectedVersion implements GameVersion {
    private static final Logger LOGGER;
    private final String id;
    private final String name;
    private final boolean stable;
    private final int worldVersion;
    private final int protocolVersion;
    private final int packVersion;
    private final Date buildTime;
    private final String releaseTarget;
    
    public DetectedVersion() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.name = "0.1.0-alpha-1";
        this.stable = false;
        this.worldVersion = 2229;
        this.protocolVersion = 577;
        this.packVersion = 1;
        this.buildTime = new Date();
        this.releaseTarget = "0.1.0";
    }
    
    protected DetectedVersion(JsonObject jsonObject) {
        this.id = GsonHelper.getAsString(jsonObject, "id");
        this.name = GsonHelper.getAsString(jsonObject, "name");
        this.releaseTarget = GsonHelper.getAsString(jsonObject, "release_target");
        this.stable = GsonHelper.getAsBoolean(jsonObject, "stable");
        this.worldVersion = GsonHelper.getAsInt(jsonObject, "world_version");
        this.protocolVersion = GsonHelper.getAsInt(jsonObject, "protocol_version");
        this.packVersion = GsonHelper.getAsInt(jsonObject, "pack_version");
        this.buildTime = Date.from(ZonedDateTime.parse(GsonHelper.getAsString(jsonObject, "build_time")).toInstant());
    }
    
    public static GameVersion tryDetectVersion() {
        try (InputStream inputStream1 = DetectedVersion.class.getResourceAsStream("/version.json")) {
            if (inputStream1 == null) {
                DetectedVersion.LOGGER.warn("Missing version information!");
                return new DetectedVersion();
            }
            try (InputStreamReader inputStreamReader3 = new InputStreamReader(inputStream1)) {
                return new DetectedVersion(GsonHelper.parse(inputStreamReader3));
            }
        }
        catch (IOException | JsonParseException ex2) {
            Exception ex = new Exception();
            throw new IllegalStateException("Game version information is corrupt", ex);
        }
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getReleaseTarget() {
        return this.releaseTarget;
    }
    
    public int getWorldVersion() {
        return this.worldVersion;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public int getPackVersion() {
        return this.packVersion;
    }
    
    public Date getBuildTime() {
        return this.buildTime;
    }
    
    public boolean isStable() {
        return this.stable;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
