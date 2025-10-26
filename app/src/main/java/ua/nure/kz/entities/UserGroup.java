package ua.nure.kz.entities;

public class UserGroup {
    private long userId;
    private long groupId;

    public UserGroup(long userId, long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public long getGroupId() {
        return groupId;
    }
}
