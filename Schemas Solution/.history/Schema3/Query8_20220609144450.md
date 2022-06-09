## Query 8

* Find the names of sailors 'who have reserved a red boat.

## Note !
  * Flags Hashjoin and HashAgg here where disabled for future after many trials and errors , I've discovered the best way to show the difference interms of the cost and to beat the Postgres Query Optimizer Algorithm to be able to show indices effect and cost differenes .
  
 * The Execution time was changing by 10 Ms in each Execution which is considered high and I can't take it as a measurable Metric because it was Linux (Ubuntu) Operating System performance and I took permission from Prof. Wael as do not take it as my main objective I take the Overall Cost and Compare it .


### Original Query
```
select s.sname
from sailors s
where s.sid in ( select r.sid
from reserves r
where r. bid in (select b.bid
from boat b
where b.color = 'red'));

```

#### Result Set
* 673 Rows

<img src="./screenshots/Query8/result-set-number-of-rows.png" alt="result-set-number-of-rows">


#### Report

1) given query without an index,

<img src="./screenshots/Query8/no-index.png" alt="no-index">


2) given query with B+ trees indices only,




3) given query with hash indices only,
*

4) given query with BRIN indices only,
*

5) given query with mixed indices (any mix of your choice).
*


### Optimized Query

```


```

#### Report

1) given query without an index,

*

2) given query with B+ trees indices only,
*
3) given query with hash indices only,
*

4) given query with BRIN indices only,
*

5) given query with mixed indices (any mix of your choice).
*

