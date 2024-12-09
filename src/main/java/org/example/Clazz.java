package org.example;

import java.util.ArrayList;
import java.util.List;

public class Clazz {
    private final String clazzName;
    private final Teacher teacher;
    public List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public Clazz(String clazzName, Teacher teacher) {
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.clazzName = clazzName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public String addStudent(Student student){
        if (student.getClassName().isEmpty()){
            students.add(student);
            return this.getClazzName();
        } else throw new RuntimeException("Student have class already");
    }

    public void studentsByAverageGrade(){
        System.out.println("Students of " + this.clazzName + " and their average grades:");
        students.stream().forEach(s -> {
            double averageGrade = s.getMapOfSubjects().values().stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
            System.out.println(s.getStudentName() + " - " + String.format("%.2f", averageGrade));
        });
        System.out.println("");
    }
}
