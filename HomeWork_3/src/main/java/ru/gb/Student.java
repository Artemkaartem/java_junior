package ru.gb;
public class Student {

    private int id;

    private String firstName;

    private String SecondName;

    private int age;

    public Student() {

    }

    public Student(int id, String firstName, String SecondName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.SecondName = SecondName;
        this.age = age;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "")
    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String SecondName) {
        this.SecondName = SecondName;
    }

    @Column(name = "")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }
}