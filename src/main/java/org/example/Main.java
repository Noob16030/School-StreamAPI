package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Povytvaram vsetky entyty
        Teacher teacherAnton = new Teacher("Anton The Teacher");
        Teacher teacherPavel = new Teacher("Pavel Big Teacher");

        Clazz clazz1A = new Clazz("1.A", teacherAnton);
        Clazz clazz2B = new Clazz("2.B", teacherPavel);

        Subject subjectRunning = new Subject("Running", teacherAnton);
        Subject subjectMath = new Subject("Math", teacherAnton);
        Subject subjectPhysics = new Subject("Physics", teacherAnton);
        Subject subjectHistory = new Subject("History", teacherPavel);
        Subject subjectProgramming = new Subject("Programming", teacherPavel);

        Student studentJan = new Student("Jan");
        Student studentMaria = new Student("Mariah Carey");
        Student studentStefan = new Student("Stefan");
        Student studentMiroslav = new Student("Miroslav");
        Student studentKamil = new Student("Kamil");
        Student studentPeter = new Student("Peter");

        // Pridam studentov do tried, vdaka vzajomnim vztahom
        addStudentToClass(studentJan, clazz1A);
        addStudentToClass(studentMaria, clazz1A);
        addStudentToClass(studentStefan, clazz1A);

        addStudentToClass(studentMiroslav, clazz2B);
        addStudentToClass(studentKamil, clazz2B);
        addStudentToClass(studentPeter, clazz2B);

        // Pridam predmety studentom, vdaka vzajomnim vztahom
        addSubjectToStudent(studentJan, subjectRunning, 1);
        addSubjectToStudent(studentJan, subjectMath, 2);
        addSubjectToStudent(studentJan, subjectPhysics, 2);

        addSubjectToStudent(studentMaria, subjectMath, 2);
        addSubjectToStudent(studentMaria, subjectPhysics, 2);
        addSubjectToStudent(studentMaria, subjectHistory, 1);

        addSubjectToStudent(studentStefan, subjectPhysics, 3);
        addSubjectToStudent(studentStefan, subjectHistory, 4);
        addSubjectToStudent(studentStefan, subjectProgramming, 2);

        addSubjectToStudent(studentMiroslav, subjectHistory, 3);
        addSubjectToStudent(studentMiroslav, subjectProgramming, 4);
        addSubjectToStudent(studentMiroslav, subjectRunning, 3);

        addSubjectToStudent(studentKamil, subjectHistory, 5);
        addSubjectToStudent(studentKamil, subjectProgramming, 2);
        addSubjectToStudent(studentKamil, subjectRunning, 2);

        addSubjectToStudent(studentPeter, subjectProgramming, 3);
        addSubjectToStudent(studentPeter, subjectRunning, 3);
        addSubjectToStudent(studentPeter, subjectMath, 2);

        // Skontrolujem, ci kazdy student ma aspon 3 predmety
        controlCountOfSubjects(clazz1A);
        controlCountOfSubjects(clazz2B);

        // vypisem znamky vsetkych studentov v triedach
        clazz1A.studentsByAverageGrade();
        clazz2B.studentsByAverageGrade();

        // Vypisem vsetkych studentov a ich priemerne znamky, zoradene
        List<Student> allStudents = List.of(studentJan, studentKamil, studentMaria, studentMiroslav, studentStefan, studentPeter);
        allStudentsByAverageGrade(allStudents);

        // Vypisem vsetky predmety a ich priemerne znamky, zoradene
        List<Subject> allSubjects = List.of(subjectHistory, subjectRunning, subjectPhysics, subjectMath, subjectProgramming);
        allSubjectsByAverageGrade(allSubjects);

        // Vypisem vsetkych triedy podla priemernej znamky ziakov v triede, zoradene
        List<Clazz> school = List.of(clazz1A, clazz2B);
        allClassesByAverageGrade(school);


    }

    public static void allClassesByAverageGrade(List<Clazz> school){
        System.out.println("Sorted all classes by average grades given to students:");
        school.stream().map(classroom -> new AbstractMap.SimpleEntry<>(
                        classroom.getClazzName(),
                        classroom.getStudents().stream()
                                .flatMapToDouble(student -> student.getMapOfSubjects().values().stream().mapToInt(Integer::intValue).average().stream())
                                .average().orElse(0.0)
                ))
                .sorted(Map.Entry.<String, Double>comparingByValue()) // Zoradenie podľa priemeru (klesajúco)
                .forEach(entry -> System.out.printf("%s - %.2f%n", entry.getKey(), entry.getValue()));
        System.out.println("");
    }

    public static void allSubjectsByAverageGrade(List<Subject> subjects){
        System.out.println("Sorted all subjects by average grades given to students:");
        subjects.stream().map(subject -> new AbstractMap.SimpleEntry<>(
                        subject.getSubjectName(),
                        subject.getMapOfStudents().values().stream().mapToInt(Integer::intValue).average().orElse(0.0)
                ))
                .sorted(Map.Entry.<String, Double>comparingByValue())
                .forEach(entry -> System.out.printf("%s - %.2f%n", entry.getKey(), entry.getValue()));
        System.out.println("");
    }

    public static void allStudentsByAverageGrade(List<Student> students){
        System.out.println("Sorted all students by their average grade:");
        students.stream().map(student -> new AbstractMap.SimpleEntry<>(
                        student.getStudentName(),
                        student.getMapOfSubjects().values().stream().mapToInt(Integer::intValue).average().orElse(0.0)
                ))
                .sorted(Map.Entry.<String, Double>comparingByValue())
                .forEach(entry -> System.out.printf("%s - %.2f%n", entry.getKey(), entry.getValue()));
        System.out.println("");
    }

    public static void addStudentToClass(Student student, Clazz clazz){
        student.setClassName(clazz.addStudent(student));
    }

    public static void addSubjectToStudent(Student student, Subject subject, int grade){
       student.addSubject(subject.addStudent(student, grade), grade);
    }

    public static void controlCountOfSubjects(Clazz clazz){
        if (clazz.getStudents().stream().map(Student::getMapOfSubjects).allMatch(a -> a.size() >= 3)){
            System.out.println("OK, every student in " + clazz.getClazzName() + " have at least 3 subjects.");
        } else throw new RuntimeException("Some students in " + clazz.getClazzName() + " do not have enough subjects.");
        System.out.println("");
    }
}
//---------------------------------------------------------------------------------------
class Teacher{
    private final String teacherName;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }

}
//---------------------------------------------------------------------------------------
class Student{
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
//---------------------------------------------------------------------------------------
class Clazz{
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
//---------------------------------------------------------------------------------------
class Subject{
    private final String subjectName;
    private final Teacher teacher;
    public Map<Student, Integer> mapOfStudents;

    public Map<Student, Integer> getMapOfStudents() {
        return mapOfStudents;
    }

    public Subject(String subjectName, Teacher teacher) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.mapOfStudents = new HashMap<>();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String addStudent(Student student, int grade){
        if (!student.mapOfSubjects.containsKey(this.subjectName)){
            mapOfStudents.put(student, grade);
            return this.subjectName;
        } else throw new RuntimeException("Student have this subject already");
    }
}
//---------------------------------------------------------------------------------------

/*Remember to write readable, clean code with object-oriented principles.
The goal of this challenge is to practice Java OOP concepts while maintaining simplicity.
Beware of over engineering. The hardest thing is to find balance.
School description: Create different entities in school. You can create all instances in Main class.
Your job is to create entities with given unbreakable relationships and then print some statistical data.
In this challenge you have to design school system which have certain rules. You will practice composition and aggregation.
Your program will have very little functionality. How you design your classes matters.

School consists of these entities:
Classes - group of students (You can’t name class ‘Class’ so name it ‘Clazz’)
Teachers
Students
Subjects
Grades

Relationships between entities (Rules):
Every class has one primary teacher (it’s just a role) and multiple students
Teacher can teach multiple subjects
Student can take multiple subjects
Student must be member of exactly one class
Every subject has exactly one teacher
Student must have a grade in each subject. (Grades are between 1 and 5)

Each rule must be kept. You can use some form of validation, for example throw a RuntimeException with a message if some rule is broken.
Think carefully how you apply your rules.
For example, you can have property ‘teacher’ in ‘Subject’ which represent teacher of this subject, or you can have property ‘subjects’ in ‘Teacher’ class.
Or maybe you can have both? Which design is better?

More constrains:
Have at least 2 classes
Each class has at least 3 students
Each student takes at least 3 subjects You can use deduction to find out how many teachers you need.

Statistical data:
Print sorted students by their average grades
Print sorted subjects by average of grades given to students
Print sorted classes with the best students (by average of each student’s average grade)

Bonus A: Create every instance using command line. Ask user to add students to classes, give grades, etc. Bonus B: Load and instantiate all objects from file.
You can choose any bonus, all of them or neither.
Program output example (made up data):
Sorted students by their average grades:
Alice - 1.5
Bob - 2
Mark - 3.5
lubos - 2.0
Jano - 1.2
Sanstos - 2.3

Sorted subjects by average of grades given to students:
Math - 3
Physics - 2.5
History - 1.5
Programming - 1.0
Running - 3.3

Sorted classes with the best students:
2.B - 2
4.A - 2.5
*/