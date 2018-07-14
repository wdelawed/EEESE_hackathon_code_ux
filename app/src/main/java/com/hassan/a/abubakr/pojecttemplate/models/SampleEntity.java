package com.hassan.a.abubakr.pojecttemplate.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

@Entity
public class SampleEntity {
    @NonNull
    @PrimaryKey
    int id;

    int name;

    String creationDate;

    public SampleEntity(@NonNull int id, int name, String creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Dao
    public interface SampleEntityDao{
        @Insert
        void insertOne(SampleEntity entity);
        @Insert
        void insertList(List<SampleEntity> entities);
        @Query("SELECT * FROM SampleEntity WHERE id = :entity_id")
        SampleEntity fetchEntities (int entity_id);
        @Query("SELECT * FROM SampleEntity")
        SampleEntity fetchAllEntities ();
        @Update
        void updateEntity (SampleEntity movies);
        @Delete
        void deleteEntity (SampleEntity movies);
    }
}
