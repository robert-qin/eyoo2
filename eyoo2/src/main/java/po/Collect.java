package po;

import java.util.Date;

public class Collect {
    private Integer collectId;

    private Integer eyooId;

    private Integer userId;

    private Date collectTime;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer geteyooId() {
        return eyooId;
    }

    public void seteyooId(Integer eyooId) {
        this.eyooId = eyooId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}