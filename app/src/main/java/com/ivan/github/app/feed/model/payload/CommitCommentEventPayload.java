package com.ivan.github.app.feed.model.payload;

import com.ivan.github.account.model.User;
import com.ivan.github.app.feed.model.Comment;
import com.ivan.github.app.feed.model.Payload;
import com.ivan.github.app.feed.model.Repository;

/**
 * com.ivan.github.app.events.model.payload.CommitCommentEventPayload
 *
 * @author  Ivan J. Lee on 2020-01-01
 * @version v0.1
 * @since v1.0
 **/
public class CommitCommentEventPayload extends Payload {

    private Comment comment;
    private Repository repository;
    private User sender;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
