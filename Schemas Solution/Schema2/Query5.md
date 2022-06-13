## Query 5

- Retrieve the names of employees who have dependents.


### Original Query

```
select fname, lname
from employee
where exists ( select *
from dependent
where ssn=essn );
```

#### Result Set

- 1000 Rows

<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 5/S1.png" alt="no-index-Stats" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 4.372 ms | Total Expected Cost : 1631.09 |
  | -------------------------- | ----------------------------- |

<div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :


<img src="./screenshots/Query 5/S2.png" alt="b-tree" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 0.576 ms | Total Expected Cost : 131.74 |
  | ------------------------- | ---------------------------- |

- index created on column ssn of table smployee
- performance has been increased as the it's an exact value query (ssn=essn) so the index improve the search to be in 
O(log(n)) complexity instead of linear search O(n)


<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :


<img src="./screenshots/Query 5/S3.png" alt="hash" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1.503 ms | Total Expected Cost : 1419.33 |
  | ------------------------- | ----------------------------- |

- index create on column ssn of table employee
- the performance is increased but by small amount due to collisions in the hash table which makes the search almost linearly


<div style="page-break-after: always; break-after: page;"></div>


4. given query with BRIN indices only :

<img src="./screenshots/Query 5/S4.png" alt="brin" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 204.886 ms | Total Expected Cost : 13072264949 |
  | --------------------------- | -------------------------------- |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 5/S5.png" alt="mix" hright="400px">

##### Explanation :

- Metrics :

  | Execution Time : 0.551 ms | Total Expected Cost : 131.74 |
  | ------------------------- | ---------------------------- |

- we made a Hash based index on column essn of table dependent
- we made a B+ tree index on column ssn of table employee
- Hash based  index is not used
- perfomance is became the same as the case of using B+ tree only to get the exact value  (ssn=essn)


<div style="page-break-after: always; break-after: page;"></div>

### Alternative Query

```
explain analyze
select fname, lname
from employee
where ssn in ( select essn
from dependent);

```

#### Result Set

- 1000 Rows


<div style="page-break-after: always; break-after: page;"></div>

#### Report

1. given query without an index :

<img src="./screenshots/Query 5 optimized/S1.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 4.211 ms | Total Expected Cost : 1631.09 |
  | ------------------------- | ----------------------------- |

- the query does not have any optimization so that we get an alternative Query that produces the same cost by substituting exists with IN
- the performance remains the same 

<div style="page-break-after: always; break-after: page;"></div>

2. given query with B+ trees indices only :

<img src="./screenshots/Query 5 optimized/S2.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1.313 ms | Total Expected Cost : 131.74 |
  | ------------------------- | --------------------------- |

- index created on column ssn of table employee
- performance has been increased as the it's an exact value query (ssn=essn) so the index improve the search to be in 
O(log(n)) complexity instead of linear search O(n)


<div style="page-break-after: always; break-after: page;"></div>

3. given query with hash indices only :

<img src="./screenshots/Query 5 optimized/S3.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 1.203 ms | Total Expected Cost : 1419.33 |
  | -------------------------- | ---------------------------- |

- index create on column ssn of table employee
- the performance is increased but by small amount due to collisions in the hash table which makes the search almost linearly


<div style="page-break-after: always; break-after: page;"></div>

4. given query with BRIN indices only :

<img src="./screenshots/Query 5 optimized/S4.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 729.640 ms | Total Expected Cost : 10012070780.58 |
  | ------------------------ | ------------------------------------ |

- Here the BRIN was not used in the original Query Plan settings so I've made seqscan=off too.

- The Execution Time and Expected Cost became the Worst of all .

- This happened because the Query Optimizer didnt used it from the first place due to BRIN Usage here was not suitable so we have used it to simulate seqscan behaviour only we traveresed it all and followed all its pointers so it is worst index to use in this case.

<div style="page-break-after: always; break-after: page;"></div>

5. given query with mixed indices (any mix of your choice) :

<img src="./screenshots/Query 5 optimized/S5.png" height="400px">

##### Explanation :

- Metrics :

  | Execution Time : 0.576 ms | Total Expected Cost : 131.74 |
  | ------------------------- | --------------------------- |

- we made a Hash based index on column essn of table dependent
- we made a B+ tree index on column ssn of table employee
- Hash based  index is not used
- perfomance is became the same as the case of using B+ tree only to get the exact value  (ssn=essn)
