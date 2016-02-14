package dk.zpon.foosball.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by sjuul on 11/15/15.
 */
public class Achievement {

    public Achievement() {

    }

    public Achievement(String headline, String userName, String count, String type) {
        this.headline = headline;
        this.userName = userName;
        this.count = count;
        this.type = type;
    }

    @XmlElement(name = "Headline")
    private String headline;
    @XmlElement(name = "UserName")
    private String userName;
    @XmlElement(name = "Count")
    private String count;
    @XmlElement(name = "Type")
    private String type;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
