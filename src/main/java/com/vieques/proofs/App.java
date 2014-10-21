package com.vieques.proofs;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

public class App {

    private enum EmailType {
        ContestKickoff,
        ContestEnd
    }

    private enum Language {
        English("en"),
        German("de");

        Language(String langCode) {
            this.langCode = langCode;
        }

        private String langCode;
        public String getLangCode() { return langCode; }
    }

    // these represent mapping tables
    private final HashMap<EmailType, String> emailTypeTemplateMapping;
    private final HashMap<Language, String> languageVersionMapping;

    private final static String SENDGRID_USERNAME = "USERNAME";
    private final static String SENDGRID_PASSWORD  = "PASSWORD";
    private final static String SENDGRID_ACCESS_TOKEN = "ACCESS_TOKEN";

    public static void main(String[] args) {
        new App();
    }

    public App() {
        // this would be in db mapping tables...
        emailTypeTemplateMapping = new HashMap<EmailType, String>() {{ put(EmailType.ContestKickoff, "SOME_TEMPLATE_ID"); put(EmailType.ContestEnd, "ANOTHER_TEMPLATE_ID"); }};
        languageVersionMapping = new HashMap<Language, String>() {{ put(Language.German, "SOME_VERSION_ID"); put(Language.English, "ANOTHER_VERSION_ID");}};

        // begin POC...
        SendGrid sendGrid = new SendGrid(SENDGRID_USERNAME, SENDGRID_PASSWORD);

        System.out.println("Getting Contest Kickoff email in English: ");
        String kickOffEmail = getTemplateIdForType(EmailType.ContestKickoff);
        String englishVersion = getTemplateVersionForLanguage(Language.English);
        TemplateVersion englishTemplate = getTemplateVersionFromSendGrid(kickOffEmail, englishVersion);

        System.out.println(englishTemplate.toString());

        String germanVersion = getTemplateVersionForLanguage(Language.German);
        TemplateVersion germanTemplate = getTemplateVersionFromSendGrid(kickOffEmail, germanVersion);
        System.out.println(germanTemplate.toString());

        System.out.println("Sending English Email...");
        SendGrid.Email email = new SendGrid.Email().addTo("you@somewhere.com").setFrom("proof-of-concepts@virginpulse.com").setHtml(englishTemplate.getHtml_content()).setSubject(englishTemplate.getSubject());
        try {
            SendGrid.Response response = sendGrid.send(email);
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            e.printStackTrace();
        }

        System.out.println("Sending German Email...");
        email = new SendGrid.Email().addTo("youh@somewhere.com").setFrom("proof-of-concepts@virginpulse.com").setHtml(germanTemplate.getHtml_content()).setSubject(germanTemplate.getSubject());
        try {
            SendGrid.Response response = sendGrid.send(email);
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            e.printStackTrace();
        }
    }

    private TemplateVersion getTemplateVersionFromSendGrid(String templateId, String versionId) {
        TemplateVersion sendGridTemplateVersion = null;
        try {
            Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
            WebTarget resource = client.target("https://api.sendgrid.com/v3/").path("templates/" + templateId + "/versions/" + versionId);
            sendGridTemplateVersion = resource.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Basic " + SENDGRID_ACCESS_TOKEN).get(TemplateVersion.class);
        } catch(Exception e) {
            System.out.println("There was an error reading template from SendGrid");
            e.printStackTrace();
        }

        return sendGridTemplateVersion;
    }

    // this represents a DB call...
    private String getTemplateIdForType(EmailType emailType) {
        return emailTypeTemplateMapping.get(emailType);
    }

    // this represents a DB call...
    private String getTemplateVersionForLanguage(Language language) {
        return languageVersionMapping.get(language);
    }

}
