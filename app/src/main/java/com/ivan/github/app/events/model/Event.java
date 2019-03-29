package com.ivan.github.app.events.model;

import com.google.gson.annotations.SerializedName;

/**
 * Events a user received
 *
 * @author  Ivan J. Lee on 2019-03-30 00:31.
 * @version v0.1
 * @since   v1.0
 */
public class Event {

    private String id;
    private String type;
    private Actor actor;
    private Repo repo;
    private Payload payload;
    @SerializedName("public")
    private boolean isPublic;
    private String created_at;
    private Org org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}


