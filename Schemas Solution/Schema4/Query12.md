## Query 12

* Find the titles of all movies directed by the director whose first and last name are Woddy Allen.


## Note !
 * all flags are set to default



### Original Query

```
select mov_title
from movie
where mov_id in (
select mov_id
from movie_direction
where dir_id=
(select dir_id
from director
where dir_fname='Woddy'
and
dir_lname='Allen'));
```


#### Result Set

* 387 Rows


#### Report

1) given query without an index :
<img src="screenshots\Query 12\12 normal no index.png" alt="no-index-Stats" height="400px">

##### Explanation :

   * Metrics :
  
      | Execution Time : 11.155 ms | Total Expected Cost : 3435.52 |
      |----------------------------|-------------------------------|
   


2) given query with B+ trees indices only :


<img src="screenshots\Query 12\12 normal btree.png" alt="b-tree" height="400px">


##### Explanation :

   * Metrics :
  
      | Execution Time : 0.543 ms | Total Expected Cost : 24.94   |
      |---------------------------|-------------------------------|

* Indices created as shown on above screenshot.

* The Search is done with O(Log n) for (dir_lname='Allen') for example or any search using columns index created on  (Total Expected Cost Was 3435.52 and became 24.94).
  
3) given query with hash indices only :
<img src="screenshots\Query 12\12 normal hash.png" alt="hash" height="400px">

##### Explanation :

   * Metrics :
  
      | Execution Time : 0.798 ms | Total Expected Cost : 24.08   |
      |---------------------------|-------------------------------|

  * Indices created as shown on above screenshot.
  
  * The Search is done with O(1) when we search for values of any column I created index on it so the performance slightly improved with respect to performance of the query with B+ Tree index and the increase in performance is small due to the small number of rows(Total Expected Cost Was 3435.52 without index and 24.94 with B+ tree and became 24.08).

4) given query with BRIN indices only :
<img src="screenshots\Query 12\12 normal brin.png" alt="BRIN" height="400px">

##### Explanation :

  * Metrics :

      | Execution Time : 179.504 ms | Total Expected Cost : 10000002275.21  |
      |-----------------------------|---------------------------------------|

  * Indices created as shown on above screenshot.

  * Here the BRIN was not used in the original Query Plan settings because using brin in our case here is not efficient as all my query searches is not searching in small range  within the range of the column "not low selectivity query" so I set flag seqscan=off .
  
  * The Execution Time and Expected Cost became the Worst of all as a result of turning seqscan flag off.
  
5) given query with mixed indices (any mix of your choice) :

<img src="screenshots\Query 12\12 normal mix.png" alt="mix" height="400px">

##### Explanation :

  * Metrics :

      | Execution Time 0.687   ms | Total Expected Cost : 24.36      |
      |---------------------------|----------------------------------|        
 
  * Indices created as shown on above screenshot.

  * Now the Performance is better than with B+ index but performance with Hash only is better as any search with values of column dir_id of table movie_direction will be in O(Log n) and with values of column mov_id of table movie or dir_fname,dir_lname of table director will be in O(1).




### Optimized Query

```
CREATE MATERIALIZED VIEW query_12
AS
 select dir_id from director
 where dir_fname='Woddy' and dir_lname='Allen' 
WITH  DATA;



 explain analyze select mov_title
from movie
where mov_id in (select m1.mov_id from movie_direction m1 
				 where exists (select * from query_12
							   m2 where m1.dir_id=m2.dir_id ))


```

#### Result Set

* 387 Rows

#### Report

1) Optimized Query without an index :

<img src="screenshots\Query 12\12 opt no index.png" height="400px">

##### Explanation :

   * Metrics :
  
      | Execution Time : 10.908 ms | Total Expected Cost : 3290.31 |
      |----------------------------|-------------------------------|

 * Reason :

Since selecting dir_id of director whose first name is Woddy and last name is Allen is needed as sub query so I created materialized view storing those values with name of Query 12 and used it inside my query.In addition, used exists instead of = because more than one director can have same first and last name.Finally, The performance is improved with respect to performance of original query after using materialized view Query 12 (Total Expected Cost Was 3435.52 and became 3290.31). 

2) Optimized Query with B+ trees indices only :
<img src="screenshots\Query 12\12 opt btree.png" height="400px">

##### Explanation :

   * Metrics :
  
      | Execution Time : 0.539 ms | Total Expected Cost : 11.13   |
      |---------------------------|-------------------------------|

* Indices created as shown on above screenshot.

* The Search is done with O(Log n) using columns index created on, so the performance is improved with respect to performance of the query without index (Total Expected Cost Was 3290.31 and became 11.13).

3) Optimized Query with hash indices only :
<img src="screenshots\Query 12\12 opt hash.png">

##### Explanation :

   * Metrics :
  
      | Execution Time : 0.612 ms | Total Expected Cost : 10.72   |
      |---------------------------|-------------------------------|

  * Indices created as shown on above screenshot.
  
  * The Search is done in O(1) when we search for values of any column I created hash index on it so the performance slightly improved with respect to performance of the query with B+ Tree index and the increase in performance is small due to the small number of rows(Total Expected Cost Was 3290.31 without index and  11.13 with B+ tree and became 10.72).
   
  
 

4) Optimized Query with BRIN indices only :

<img src="screenshots\Query 12\12 opt brin.png" height="400px"> 

##### Explanation :

  * Metrics :

      | Execution Time : 179.753 ms | Total Expected Cost : 100000072197.24|
      |-----------------------------|--------------------------------------|

  * Indices created as shown on above screenshot.

  * Here the BRIN was not used in the original Query Plan settings because using brin in our case here is not efficient as all my query searches is not searching in small range within the range of the column "not low selectivity query" so I set flag seqscan=off.
  
  * The Execution Time and Expected Cost became the Worst of all as a result of turning seqscan flag off.

5) Optimized Query with mixed indices (any mix of your choice) :

<img src="screenshots\Query 12\12 opt mix.png" height="400px"> 

##### Explanation :

  * Metrics :

      | Execution Time : 0.709 ms| Total Expected Cost :  11.00     |
      |--------------------------|----------------------------------|        


  * Indices created as shown on above screenshot.

  * Now the Performance is slightly better than with B+ index but performance with Hash only is slightly better as any search with values of column dir_id of table movie_direction will be in     O(Log n) and with values of column mov_id of table movie will be in O(1).

