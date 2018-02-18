package iceblood.spacexdata;

import java.util.Date;

/**
 * Created by Titan'ik on 13.02.2018.
 */

public class RocketData {
    private String rocketName;
    private long launchDataUnix;
    private String urlMissionPatch;
    private String details;
    private String videoLink;

    public String getRocketName() {
        return rocketName;
    }

    public void setRocketName(String rocketName) {
        this.rocketName = rocketName;
    }

    public String getLaunchData(){
        return new java.text.SimpleDateFormat("dd.M.Y").format(new Date(getLaunchDataUnix()*1000));
    }

    public long getLaunchDataUnix() {
        return launchDataUnix;
    }

    public void setLaunchDataUnix(int launchDataUnix) {
        this.launchDataUnix = launchDataUnix;
    }

    public String getUrlMissionPatch() {
        return urlMissionPatch;
    }

    public void setUrlMissionPatch(String urlMissionPatch) {
        this.urlMissionPatch = urlMissionPatch;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public RocketData(String rocketName, long launchDataUnix, String urlMissionPatch, String details, String videoLink) {
        this.rocketName = rocketName;
        this.launchDataUnix = launchDataUnix;
        this.urlMissionPatch = urlMissionPatch;
        this.details = details;
        this.videoLink = videoLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
