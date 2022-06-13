## Query 7

- Select the names of employees whose salary is greater than the salary of all the employees in
department 5.

## Note !
## All flags settings are included in the scenarios screenshots.




### Original Query

```
select lname, fname
from employee
where salary > all (
select salary
from employee
where dno=5 );
```

#### Result Set

- 1093 Rows
  
<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 3/s1.png" alt="no-index-Stats" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1732.734 ms | Total Expected Cost : 3706603 |
  | -------------------------- | ----------------------------- |

<div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :

<img src="./screenshots/Query 3/S2.png" alt="b-tree" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 53.279 ms | Total Expected Cost : 2901873.09 |
  | ------------------------- | ---------------------------- |

- index created on column dno of table employee
- performance has been increased as the it's an exact value query (dno=5) so the index improve the search to be in O(log(n)) complexity instead of linear search O(n)


<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :

<img src="./screenshots/Query 3/S3.png" alt="hash" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 57.217 ms | Total Expected Cost : 2897583 |
  | ------------------------- | ----------------------------- |

- index created on column dno of table employee

- performance has been increased as the it's an exact value query (dno=5) so the index improve the search to be in O(1) complexity instead of linear search O(n).

- Hash index is the best between all other indecies as it Hash the key in Hash tables and get it in O(1) time.



<div style="page-break-after: always; break-after: page;"></div>

4. given query with BRIN indices only :


<img src="./screenshots/Query 3/S4.png" alt="brin" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 2360.705 ms | Total Expected Cost : 10003900567 |
  | --------------------------- | -------------------------------- |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 3/S5.png" alt="mix" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 57.083 ms | Total Expected Cost : 2897583 |
  | ------------------------- | ---------------------------- |

- we made a Hash based index on column dno of table employee
- we made a B+ tree index on column salary of table employee
- B+ tree index is not used
- the perofmance increased compared to the same query with no indecies but it's the same perfomance as the case of using B+ tree only, as table employee will be sequential scaned to get the salary of each employee then compare it to the salary of all employees of department 5 by using the Hash index on dno to chechk the exaxt value of (dno=5) 
- so the only index used is the Hash index on column dno of table emloyee

<div style="page-break-after: always; break-after: page;"></div>

### Optimized Query

```
explain analyze
select lname, fname
from employee
where salary > (select max(salary)
				        from employee
			          where dno=5);


```

#### Result Set

- 1093 Rows

<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 3 optimized/s1.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 3.279 ms | Total Expected Cost : 926.28 |
  | ------------------------- | ----------------------------- |

- we substituted all with maximum value of salaries of department 5 so the perfomance is improved



<div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :


<img src="./screenshots/Query 3 optimized/S22.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 2.570 ms | Total Expected Cost : 825.40 |
  | ------------------------- | --------------------------- |

- we made a B+ tree index on column salary of table employee
- the perfomance is improved as we don't need to scan all the table to get the maximum salary, we only need to get the right most sub tree which will have the maximum salary
- B+ tree is the index with the highest performance as it's the optimal index to get minimum and maximum values


<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :


<img src="./screenshots/Query 3 optimized/S3.png" height="400px">

##### Explanation :




- Metrics :

  | Execution Time : 2.359 ms | Total Expected Cost : 825.15 |
  | -------------------------- | ---------------------------- |

- we made a Hash based index on column dno of table employee
- performance has been increased as the it's an exact value query (dno=5) so the index improve the search to be in O(1) complexity instead of linear search O(n).


<div style="page-break-after: always; break-after: page;"></div>


4. given query with BRIN indices only :


<img src="./screenshots/Query 3 optimized/S4.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 3.781 ms | Total Expected Cost : 10000000938.40 |
  | ------------------------ | ------------------------------------ |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 3 optimized/S5.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1.577 ms | Total Expected Cost : 825.40 |
  | ------------------------- | --------------------------- |

-  we made a B+ tree index on column salary of table employee

- we made a Hash based index on column dno of table employee

- the Hash index is not used

- perfomance is became the same as the case of using B+ tree only to get the maximum salary 