package com.saf.fileupload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\moham\\Downloads";

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }


}
