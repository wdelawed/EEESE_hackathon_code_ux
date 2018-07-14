package com.hassan.a.abubakr.pojecttemplate;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hassan.a.abubakr.pojecttemplate.models.SampleEntity;

/**
 * this was cached just in case in the future we might need to work on some internal database units
 */
@Database(entities = {SampleEntity.class},version = 1,exportSchema = false)
public abstract class DB extends RoomDatabase{
    public abstract SampleEntity.SampleEntityDao entityDao();
}
