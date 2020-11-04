# JdbcPagingItemReader
Retrieve database records using JdbcPagingItemReader in a paging fashion.

#Retrieve database records in paging fashion using JdbcPagingItemReader<T>.
 
In this post we will understand how to read the input data of batch job by using JdbcPagingItemReader. It significantly reduces the result set fetching time. 
Let's have a look with following example to read student records from 'STUDENTS' table using pagination. 

- This is the model class Student.
<ins>Student.Java</ins>

```sh
public class Student {
  private String id;
  private String name;
}
```

- The mapper class will map each row of data in the ResultSet.
<ins>StudentMapper.java</ins>

```sh
public class StudentMapper implements RowMapper<Student> {
    @Override
    public StudentMapper mapRow(final ResultSet rs, final int rowNum) {
        Student student = new Student();
        student.setId(rs.getString(“id”));
        student.setDonationType(rs.getString(“name”));
        return student;
     }
}  
```
