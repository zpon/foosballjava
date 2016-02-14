package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.UUID;

/**
 * Created by sjuul on 10/31/15.
 */
public class User {
    @XmlElement(name = "Id")
    private UUID id;
    @XmlElement(name = "Email")
    private String email;
    @XmlElement(name = "Username")
    private String username;
    @XmlElement(name = "GravatarEmail")
    private String gravatarEmail;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGravatarEmail() {
        return gravatarEmail;
    }

    public void setGravatarEmail(String gravatarEmail) {
        this.gravatarEmail = gravatarEmail;
    }
}
