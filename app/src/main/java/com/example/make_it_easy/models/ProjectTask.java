package com.example.make_it_easy.models;


import android.annotation.SuppressLint;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectTask{
   public String name;
   public boolean status;

    public ProjectTask(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    static   public ProjectTask fromMap(Map<String, Object> map) throws ParseException {
        return new ProjectTask(
                Objects.requireNonNull(map.getOrDefault("name", "")).toString(),
                (Boolean) Objects.requireNonNull(map.getOrDefault("status", false))
        );
    }

    public final Map<String, Object> toMap() {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new HashMap<String, Object>() {{
            put("name", name);
            put("status", status);
        }};
    }

}