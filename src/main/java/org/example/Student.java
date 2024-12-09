package org.example;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String studentName;
    private String className;
    public Map<String, Integer> mapOfSubjects;

    public String getStudentName() {
        return studentName;
    }

    public Map<String, Integer> getMapOfSubjects() {
        return mapOfSubjects;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    Student(String studentName) {
        this.studentName = studentName;
        this.className = "";
        this.mapOfSubjects = new HashMap<>();
    }

    public void addSubject(String subject, int grade){
        this.mapOfSubjects.put(subject, grade);
    }
}
