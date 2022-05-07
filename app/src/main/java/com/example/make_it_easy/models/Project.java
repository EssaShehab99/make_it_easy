package com.example.make_it_easy.models;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Project {

    public String id;
    public String name;
    public float progress;
    public Date deadline;
    public Date date;
    public String attachedFile;
    public List<ProjectTask> steps;

    public Project(String id, String name, float progress, Date deadline,Date date, String attachedFile, List<ProjectTask> steps) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.deadline = deadline;
        this.date = date;
        this.attachedFile = attachedFile;
        this.steps = steps;
    }

    public Project() {

    }

  static   public Project fromMap(Map<String, Object> map, String id) throws ParseException {
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new Project(
                id,
                Objects.requireNonNull(map.getOrDefault("name", "")).toString(),
                Float.parseFloat(Objects.requireNonNull(map.getOrDefault("progress", "")).toString()),
                formatter.parse(Objects.requireNonNull(map.getOrDefault("deadline", "1")).toString()),
                formatter.parse(Objects.requireNonNull(map.getOrDefault("date", "1")).toString()),
                Objects.requireNonNull(map.getOrDefault("attachedFile", "")).toString(),
                getData((List<Map<String, Object>>)Objects.requireNonNull(map.get("steps")))

                );
    }
    static  List<ProjectTask> getData(List<Map<String, Object>> listJson) throws ParseException {
        List<ProjectTask> projectTaskList=new ArrayList<>();
        for (Map<String, Object> item:listJson){
            projectTaskList.add(ProjectTask.fromMap(item));
        }

        return projectTaskList;
    }
    public final Map<String, Object> toMap() {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new HashMap<String, Object>() {{
            put("name", name);
            put("progress", progress);
            put("deadline", formatter.format(deadline));
            put("date", formatter.format(date));
            put("attachedFile", attachedFile);
            put("steps", steps.stream().map(ProjectTask::toMap).collect(Collectors.toList()));
        }};
    }
}