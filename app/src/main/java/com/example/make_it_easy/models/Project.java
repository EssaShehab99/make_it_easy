package com.example.make_it_easy.models;

import java.util.Date;
import java.util.List;

public class Project {

    public     String id;

 public Project(String id, String name, int progress, Date deadline, String attachedFile, List<String> steps) {
  this.id = id;
  this.name = name;
  this.progress = progress;
  this.deadline = deadline;
  this.attachedFile = attachedFile;
  this.steps = steps;
 }

 public String name;
     public int progress;
     public Date deadline;
     public String attachedFile;
     public List<String> steps;

    public Project() {

    }
}
