package org.example;

import java.util.HashMap;
import java.util.Map;

public class Subject {
    private final String subjectName;
    private final Teacher teacher;
    public Map<Student, Integer> studentGrades;

    public Map<Student, Integer> getStudentGrades() {
        return studentGrades;
    }

    public Subject(String subjectName, Teacher teacher) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.studentGrades = new HashMap<>();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String addStudent(Student student, int grade){
        if (!student.mapOfSubjects.containsKey(this.subjectName)){
            studentGrades.put(student, grade);
            return this.subjectName;
        } else throw new RuntimeException("Student have this subject already");
    }
}
