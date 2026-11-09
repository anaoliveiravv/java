import java.lang.Math;
class Student {
    String name;
    int age;
    double g1;
    double g2;
    String course;

    public Student(String name, int age, double g1, double g2, String course) {
        this.name = name;
        this.age = age;
        this.g1 = g1;
        this.g2 = g2;
        this.course = course;
    }

    String getName() {
        return name;
    }

    double calculateAverage() {
        return (g1+g2)/2;
    }

    long roundAverage() {
        return Math.round(calculateAverage());
    }

    String seeSituation() {
        return calculateAverage() >= 7? "Passed" : "Failed";
    }
}
