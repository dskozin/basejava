package ru.dskozin.resumeapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("C:/Java/TopJava/basejava/config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private String storageDir;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = properties.getProperty("storage.dir");
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid propertie file " + PROPS.getAbsolutePath());
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public String getDBUser() {
        return dbUser;
    }

    public String getDBUrl() {
        return dbUrl;
    }

    public String getDBPassword() {
        return dbPassword;
    }
}
