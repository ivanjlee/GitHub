package com.ivan.github.app.homepage.model.entity.payload;

import com.ivan.github.account.model.User;
import com.ivan.github.app.homepage.model.entity.Payload;
import com.ivan.github.app.homepage.model.entity.Repository;

/**
 * com.ivan.github.app.events.model.payload.WatchEventPayload
 *
 * @author Ivan J. Lee on 2020-01-01 20:08
 * @version v0.1
 * @since v1.0
 **/
public class WatchEventPayload extends Payload {

    private Repository repository;
    private User sender;
}
