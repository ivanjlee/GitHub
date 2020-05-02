package com.ivan.github.app.events.model.payload;

import com.github.log.Logan;
import com.github.utils.L;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ivan.github.GitHub;
import com.ivan.github.account.model.User;
import com.ivan.github.app.events.model.Event;
import com.ivan.github.app.events.model.Org;
import com.ivan.github.app.events.model.Payload;
import com.ivan.github.app.events.model.Repository;

import java.io.IOException;
import java.util.Date;

/**
 * com.ivan.github.app.events.model.payload.EventTypeAdapter
 *
 * @author  Ivan on 2019-12-29
 * @version v0.1
 * @since  v1.0
 **/
public class EventTypeAdapter extends TypeAdapter<Event> {

    private static final String TAG = "EventTypeAdapter";

    @Override
    public void write(JsonWriter out, Event value) throws IOException {
        out.beginObject();
        out.value(value.getId());
        out.value(value.getType());
        writeField(out, value.getActor());
        writeField(out, value.getPayload());
        out.value(value.isPublic());
        writeField(out, value.getCreatedAt());
        writeField(out, value.getOrg());
    }

    @Override
    public Event read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            in.nextNull();
            return null;
        }
        in.beginObject();
        Event event = new Event();
        while (in.hasNext()) {
            String name = in.nextName();
            if ("payload".equals(name)) {
                event.setPayload(readByType(in, event.getType()));
            } else if ("id".equals(name)){
                event.setId(in.nextString());
            } else if ("type".equals(name)) {
                event.setType(in.nextString());
            } else if ("actor".equals(name)) {
                event.setActor(read(in, User.class));
            } else if ("repo".equals(name)) {
                event.setRepo(read(in, Repository.class));
            } else if ("public".equals(name)) {
                event.setPublic(in.nextBoolean());
            } else if ("created_at".equals(name)) {
                event.setCreatedAt(read(in, Date.class));
            } else if ("org".equals(name)) {
                event.setOrg(read(in, Org.class));
            }
        }
        in.endObject();
        return event;
    }

    private <T> T read(JsonReader reader, Class<T> tClass) {
        try {
            return GitHub.appComponent().gson().getAdapter(tClass).read(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> void writeField(JsonWriter writer, T t) {
        try {
            GitHub.appComponent().gson().getAdapter((Class<T>)t.getClass()).write(writer, t);
        } catch (IOException e) {
            Logan.e(TAG, "", e);
        }
    }

    private Payload readByType(JsonReader reader, String type) {
        if (type == null || type.isEmpty()) {
            return null;
        } else {
            Class<? extends Payload> clazz = (Class<? extends Payload>) PayloadFactory.getClass(type);
            if (clazz == null) {
                try {
                    reader.skipValue();
                } catch (IOException e) {
                    L.e(e);
                }
                return null;
            } else {
                return read(reader, clazz);
            }
        }
    }
}
