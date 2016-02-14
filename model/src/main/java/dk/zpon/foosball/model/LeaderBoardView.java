package dk.zpon.foosball.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjuul on 10/28/15.
 */
public class LeaderBoardView {
    private UUID id;
    private Date timestamp;
    private List<LeaderBoardViewEntry> entries;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<LeaderBoardViewEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LeaderBoardViewEntry> entries) {
        this.entries = entries;
    }
}
