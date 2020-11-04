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
public class StudentMapper implements RowMapper<Student>  {

    @Override
    public StudentMapper mapRow(final ResultSet rs, final int rowNum) {
       Student student = new Student (); 
       student.setId(rs.getString(“id”));
       student.setDonationType(rs.getString(“name”));
	return student;
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
            return "( " + GET_STUDENT_INFO + ")" + " AS RESULT_TABLE ";
        }
    }
```

<b><i>PagingQueryProvider</i><b> It executes the SQL built by the PagingQueryProvider to retrieve requested data. 
The query is executed using paged requests of a size specified in setPageSize(int). The number of rows to retrieve at a time. pageSize is the number of rows to fetch per page.
setFetchSize(int): Gives the JDBC driver a hint as to the number of rows that should be fetched from the database when more rows are needed for the ResultSet object. If the fetch size specified is zero, the JDBC driver ignores the value. It takes the number of rows to fetch.
Additional pages are requested when needed as read () method is called, returning an object corresponding to current position.
setSortKeys(Map<String, Order> sortKeys) : sortkey to use to sort and limit page content. It takes a map of sort columns as the key and boolean for ascending/descending
On restart it uses the last sort key value to locate the first page to read (so it doesn't matter if the successfully processed items have been removed or modified). It is important to have a unique key constraint on the sort key to guarantee that no data is lost between executions.
setSelectClause(String selectClause) : SELECT clause part of SQL query string
setFromClause(String fromClause): FROM clause part of SQL query string
In this example our Query will look like SELECT * (SELECT * from STUDENTS where id = :id and name = :name)  AS RESULT_TABLE




