## Query 4

- Retrieve the name of each employee who has a dependent with the same first name and is the
same sex as the employee.





### Original Query

```
select e.fname, e.lname
from employee as e
where e.ssn in (
select essn
from dependent as d
where e.fname = d.dependent_name
and
e.sex = d.sex );
```

#### Result Set

- 1000 Rows

<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 4/s1.png" alt="no-index-Stats" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1331.772 ms | Total Expected Cost : 208483 |
  | -------------------------- | ----------------------------- |


<div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :


<img src="./screenshots/Query 4/S2.png" alt="b-tree" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 58.240 ms | Total Expected Cost : 69043 |
  | ------------------------- | ---------------------------- |

- index created on column dependent_name of table dependent
- performance has been increased as the it's an exact value query (e.fname=d.dependent_name) so the index improve the search to be in O(log(n)) complexity instead of linear search O(n)


<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :


<img src="./screenshots/Query 4/S3.png" alt="hash" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 9.697 ms | Total Expected Cost : 64643 |
  | ------------------------- | ----------------------------- |

- index created on column dependent_name of table dependent

- performance has been increased as the it's an exact value query (e.fname=d.dependent_name) so the index improve the search to be in O(1) complexity instead of linear search O(n).

- Hash index is the best between all other indecies as it Hash the key in Hash tables and get it in O(1) time.

<div style="page-break-after: always; break-after: page;"></div>

4. given query with BRIN indices only :

<img src="./screenshots/Query 4/S4.png" alt="brin" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1640.412 ms | Total Expected Cost : 10000400999 |
  | --------------------------- | -------------------------------- |

- Here the BRIN was not used in the original Query Plan settings  so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :


<img src="./screenshots/Query 4/S5.png" alt="mix" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 9.147 ms | Total Expected Cost : 64643 |
  | ------------------------- | ---------------------------- |

- we made a Hash based index on column dependent_name of table dependent
- we made a B+ tree index on column ssn of table employee
- B+ tree index is not used
- perfomance is became the same as the case of using Hash index only to get the exact value  (e.fname=d.dependent_name)

<div style="page-break-after: always; break-after: page;"></div>

### Optimized Query

```
explain analyze
select e.fname, e.lname
from employee as e inner join dependent as d on e.ssn=d.essn
where d.sex=e.sex and d.dependent_name=e.fname;

```

#### Result Set

- 1000 Rows

<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 4 optimized/s1.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 4.756 ms | Total Expected Cost : 1631.10 |
  | ------------------------- | ----------------------------- |

- we reconstructed the query to get the same result by only doing an inner join betwwen table employee and table dependent to get all employees that have dependents with the same name and sex
- performance in increased due to using join instead of the inner query

  <div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :


<img src="./screenshots/Query 4 optimized/S2.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 3.923 ms | Total Expected Cost : 1612  .04 |
  | ------------------------- | --------------------------- |

- index created on column essn of table dependent
- performance has been increased as the it's an exact value query (e.ssn=d.essn) so the index improve the search to be in O(log(n)) complexity instead of linear search O(n)

<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :


<img src="./screenshots/Query 4 optimized/S3.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 8.199 ms | Total Expected Cost : 1011 |
  | -------------------------- | ---------------------------- |

- index created on column essn of table dependent

- performance has been increased as the it's an exact value query (e.ssn=d.essn) so the index improve the search to be in O(1) complexity instead of linear search O(n).

- Hash index is the best between all other indecies as it Hash the key in Hash tables and get it in O(1) time.

<div style="page-break-after: always; break-after: page;"></div>

4. given query with BRIN indices only :

<img src="./screenshots/Query 4 optimized/S4.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 240.997 ms | Total Expected Cost : 13072345099 |
  | ------------------------ | ------------------------------------ |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 4 optimized/S5.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 8.057 ms | Total Expected Cost : 1011 |
  | ------------------------- | --------------------------- |

- we made a Hash based index on column essn of table dependent
- we made a B+ tree index on column dependent_name of table dependent
- B+ tree index is not used
- perfomance is became the same as the case of using Hash index only to get the exact value  (e.ssn=d.essn)
