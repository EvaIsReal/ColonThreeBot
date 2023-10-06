package me.eva.database;

public class Userdata {

    private final String id;
    private final String joinedAt;

    private Userdata(String id, String joinedAt) {
        this.id = id;
        this.joinedAt = joinedAt;
    }

    public static Userdata of(String id, String joinedAt) {
        return new Userdata(id, joinedAt);
    }

    public static Userdata of(String id, Long joinedAt) {
        return new Userdata(id, String.valueOf(joinedAt));
    }

    public String getId() {
        return id;
    }

    public String getJoinedAt() {
        return joinedAt;
    }
}
