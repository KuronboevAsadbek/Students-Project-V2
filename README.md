# Student Service

### Automation of The process of studying students

This system includes:

* Reformed a Student details
* Chance to perform actions on University
* Chance to perform actions on Field of University
* Get resume for each Student on .pdf file
* Get List of All Students on .xlsx file

```java

@SpringBootApplication
public class StudentsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsProjectApplication.class, args);
    }

}
```
| No |                                                                             Services                                                                              | Status |
|:--:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------:|
| 1  |    [University Service](https://github.com/KuronboevAsadbek/Students-Project-V2/blob/main/src/main/java/uz/studentsproject/controller/StudentController.java)     |   ✅    |
| 2  | [Field Of Study Service](https://github.com/KuronboevAsadbek/Students-Project-V2/blob/main/src/main/java/uz/studentsproject/controller/UniversityController.java) |   ✅    |
| 3  |   [Student Service](https://github.com/KuronboevAsadbek/Students-Project-V2/blob/main/src/main/java/uz/studentsproject/controller/FieldOfStudyController.java)    |   ✅    |
| 4  | [File Storage Service](https://github.com/KuronboevAsadbek/Students-Project-V2/blob/main/src/main/java/uz/studentsproject/controller/FileStorageController.java)  |   ✅    |
| 5  |      [Export Services](https://github.com/KuronboevAsadbek/Students-Project-V2/blob/main/src/main/java/uz/studentsproject/controller/ExportController.java)       |   ✅    |