package com.syolab.demos.empmgr.dao;

import com.syolab.demos.empmgr.dao.domain.BaseEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BeforeSaveListener extends AbstractMongoEventListener<BaseEntity> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<BaseEntity> event) {

        Date timestamp = new Date();

        // Add a timestamp to the created date if it does not yet exist
        if (event.getSource().getCreatedAt() == null)
            event.getSource().setCreatedAt(timestamp);

        // Update the timestamp to the current time
        //event.getSource().setLastModified(timestamp);

        //super.onBeforeSave(event);
    }
}
