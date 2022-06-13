## Query 6

- For each department that has more than five employees, retrieve the department number and the
number of its employees who are making more than $40,000.


### Original Query

```
select dnumber, count(*)
from department, employee
where dnumber=dno
and
salary > 40000
and
dno = (
select dno
from employee
group by dno
having count (*) > 5)
group by dnumber;
```

#### Result Set

- 150 Rows



<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 6/S1.png" alt="no-index" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 13.136 ms | Total Expected Cost : 2489.99 |
  | -------------------------- | ----------------------------- |

<div style="page-break-after: always; break-after: page;"></div>


2. given query with B+ trees indices only :

<img src="./screenshots/Query 6/S2.png" alt="b-tree" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 9.090 ms | Total Expected Cost : 951.74 |
  | ------------------------- | ----------------------------- |

- index created on column dno of table employee
- performance has been increased as the it's an exact value query (dnumber=dno) so the index improve the search to be in 
O(log(n)) complexity instead of linear search O(n)


  <br />
  <br />
  <br />

<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :

<img src="./screenshots/Query 6/S3.png" alt="hash" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 16.191 ms | Total Expected Cost : 2294.81 |
  | ------------------------- | ----------------------------- |

- index create on column ssn of table employee
- the performance is increased but by small amount due to collisions in the hash table which makes the search almost linearly

<br/>
<br/>

<div style="page-break-after: always; break-after: page;"></div>

4. given query with BRIN indices only :

<img src="./screenshots/Query 6/S4.png" alt="brin" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 289.449 ms | Total Expected Cost : 20000044814.34 |
  | --------------------------- | -------------------------------- |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>



5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 6/S5.png" alt="mix" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 11.909 ms | Total Expected Cost : 951.74 |
  | ------------------------- | ---------------------------- |

- we made a Hash based index on column salary of table employee
- we made a B+ tree index on column dno of table employee
- Hash based  index is not used
- perfomance is became the same as the case of using B+ tree only to get the exact value  (dnumber=dno)

<div style="page-break-after: always; break-after: page;"></div>


### Optimized Query

```
-- 
create materialized view dep as
select dno
from employee
group by dno
having count (*) > 5;

create materialized view emp as
select * from employee where salary>40000;

select d.dno, count(*)
from dep as d, emp as e
where d.dno=e.dno
group by d.dno;

```

#### Result Set

- 150 Rows


<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 6 optimized/S1.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 5.318 ms | Total Expected Cost : 1384.05 |
  | ------------------------- | ----------------------------- |

- we made two materialized views emp, dep to save the tables of inner queries to get them later on
- the performance is increased due to using the views
<div style="page-break-after: always; break-after: page;"></div>


2. given query with B+ trees indices only :

<img src="./screenshots/Query 6 optimized/S2.png" alt="b-tree" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 2.097 ms | Total Expected Cost :449.33 |
  | ------------------------- | --------------------------- |

- index created on column dno of view emp
- the performance is improved as it's an exact value query (e.dno=d.dno) so the it improved the search to take O(log(n)) time instead of 
O(n)
<div style="page-break-after: always; break-after: page;"></div>


3. given query with hash indices only :

<img src="./screenshots/Query 6 optimized/S3.png" alt="hash" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 9.268 ms | Total Expected Cost : 2295.27 |
  | -------------------------- | ---------------------------- |

- index create on column dno of view emp
- the performance is increased but by small amount due to collisions in the hash table which makes the search almost linearly

<div style="page-break-after: always; break-after: page;"></div>


4. given query with BRIN indices only :

<img src="./screenshots/Query 6 optimized/S4.png" alt="brin" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 248.628 ms | Total Expected Cost : 10000294702.04 |
  | ------------------------ | ------------------------------------ |

- Here the BRIN was not used in the original Query Plan settings (Hashjoin and HashAgg are off) so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>


5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 6 optimized/S5.png" alt="mix" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1.209 ms | Total Expected Cost : 999.20 |
  | ------------------------- | --------------------------- |

- we made a Hash based index on column dno of view dep
- we made a B+ tree index on column dno of view emp
- Hash based  index is not used
- perfomance is became the same as the case of using B+ tree only to get the exact value  (d.dno=e.dno)
