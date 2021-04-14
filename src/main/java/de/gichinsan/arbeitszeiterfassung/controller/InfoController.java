package de.gichinsan.arbeitszeiterfassung.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@Component(value = "info")
public class InfoController implements Serializable {

    @Value("${spring.application.name:Arbeitszeiterfassung}")
    private String applicationName;

    @Value("${spring.build.version:0.0.1}")
    private String buildVersion;

    @Value("${spring.build.timestamp:2020-11-11}")
    private String buildTimestamp;


    @RequestMapping(value = "/v1/info", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[Application info]");
        sb.append("\nApplication name : " + applicationName);
        sb.append("\nBuild version    : " + buildVersion);
        sb.append("\nBuild timestamp  : " + buildTimestamp);
        return sb.toString();
    }
}
