# JdbcPagingItemReader
Retrieve database records using JdbcPagingItemReader in a paging fashion.

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

- The main reader class  
<ins>StudentDataReader.java</ins>

```sh
 Public class StudentDataReader {

        @Autowired
        private DataSource dataSource;
        
        private static final String GET_STUDENT_INFO = "SELECT * from STUDENTS where id = :id and name = :name ";
        
        public JdbcPagingItemReader<Student> getPaginationReader(Student student) {

            final JdbcPagingItemReader<Student> reader = new JdbcPagingItemReader<>();
            final StudentMapper studentMapper = new StudentMapper();
            reader.setDataSource(dataSource);
            reader.setFetchSize(100);
            reader.setPageSize(100);
            reader.setRowMapper(studentMapper);
            reader.setQueryProvider(createQuery());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put(“id”, student.getId());
            parameters.put(“name”, student.getName());

            reader.setParameterValues(parametersList);
            return reader;
        }
        
        private PostgresPagingQueryProvider createQuery() {
            final Map<String, Order> sortKeys = new HashMap<>();
            sortKeys.put(“id”, Order.ASCENDING);
            final PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
            queryProvider.setSelectClause("*");
            queryProvider.setFromClause(getFromClause());
            queryProvider.setSortKeys(sortKeys);
            return queryProvider;
        }

        private String getFromClause() {
            return "( " + GET_STUDENT_INFO + ")" + " AS RESULT_TABLE "
                    ;

        }
    }
```



