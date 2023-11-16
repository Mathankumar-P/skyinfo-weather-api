package com.skyinfo.weatherforecast.ip2location;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Ip2LocationTests {
    private String dbPath = "E:\\REST-Api-Masterclass\\skyinfo-weather-api\\weather-api-service\\src\\main\\resources\\ip2localdb\\IP2LOCATION-LITE-DB3.BIN";

    @Test
    public void testInvalidIP() throws IOException {
        IP2Location ipLocator = new IP2Location();
        ipLocator.Open(dbPath);
        String ipAddress = "abc";
        IPResult ipResult = ipLocator.IPQuery(ipAddress);
        assertThat(ipResult.getStatus().equals("INVALID_IP_ADDRESS"));
        System.out.println(ipResult.getStatus() + "\n"+ipResult);
    }

    @Test
    public void testInvalidIP1() throws IOException {
        IP2Location ipLocator = new IP2Location();
        ipLocator.Open(dbPath);
        String ipAddress = "108.30.178.78";
        IPResult ipResult = ipLocator.IPQuery(ipAddress);
        assertThat(ipResult.getStatus().equals("OK"));
        System.out.println(ipResult.getStatus() + "\n"+ipResult);
    }

    @Test
    public void testInvalidIP2() throws IOException {
        IP2Location ipLocator = new IP2Location();
        ipLocator.Open(dbPath);
        String ipAddress = "103.48.198.141";
        IPResult ipResult = ipLocator.IPQuery(ipAddress);
        assertThat(ipResult.getStatus().equals("OK"));
        System.out.println(ipResult.getStatus() + "\n"+ipResult);
    }
}
