class Student {
    String name;
    double n1;
    double n2;
    double n3;
    double n4;


    public Student(String name, double n1, double n2, double n3, double n4) {
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
    }

    double sum() {
        return n1+n2+n3+n4;
    }

    String getStatus() {
        if (sum() >= 28) {
            return "Passed";
        }
            return "Failed";
    }

    void showData() {
        System.out.println("Name: " + name);
        System.out.println("Total: " + sum());
        System.out.println("Status: " + getStatus());
    }
}
