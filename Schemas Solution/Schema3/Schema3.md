# Schema 3
* Sailors( sid:integer, sname: string, rating: integer, age: real)
* Boats( bid:integer, bname: string, color: string)
* Reserves (sid: integer, bid: integer, day: date)



#### Table Statistics
```
analyze sailors;
analyze boat;
analyze reserves;
select * from pg_stats where tablename = 'sailors' or tablename='reserves' or tablename='boat';
```

### Modifications to Insertion Code:
* The insertion code provided to you inserts dummy values. You are required to change it to have
19000 sailors, 3000 boats, and 35000 reserves. Before inserting the data, check queries 7-9
below you are going to analyze, the data that you insert must have values used in those queries
Note: in order to see different decisions made internally by the database engine in the query plan
and index selection, you must return a non-trivial number of rows in the result set (in the
hundreds): an empty result set is not acceptable.


### Query 7:
```
Find the names of sailors who have reserved boat 103.
select s.sname
from sailors s
where
s.sid in( select r.sid
from reserves r
where r.bid = 103 );
```

### Query 8:
```
Find the names of sailors 'who ha'ue reserved a red boat.
select s.sname
from sailors s
where s.sid in ( select r.sid
from reserves r
where r. bid in (select b.bid
from boat b
where b.color = 'red'));
```
### Query 9:
```
Query 9:
Find the names of sailors who have reserved both a red and a green boat.
select s.sname
from sailors s, reserves r, boat b
where
s.sid = r.sid
and
r.bid = b.bid
and
b.color = 'red'
and
s.sid in ( select s2.sid
from sailors s2, boat b2, reserves r2
where s2.sid = r2.sid
and
r2.bid = b2.bid
and
b2.color = 'green');
```



-------------------Query 7 Optimized + materialized view ----------------------------------

-- Query 7 view table of reserves with bid = 103

create MATERIALIZED VIEW query_7
as
select r.sid
from reserves r
where  r.bid =103  ;


select s.sname
from sailors s
where exists (select R.sid
              from query_7 R
              where s.sid =R.sid);
              
---------------------Query 8 Optimized + materialized view -----------------
  
  -- Query 8 view table of reserves sids that have red boats

create MATERIALIZED VIEW query_8
as
select r1.sid
 from (select  r.sid
       from reserves r
       where exists (select * from boat b where r.bid=b.bid  and color='red'  ) ) as r1 ;

explain analyze select s.sname
from sailors s where exists (select * from query_8 r1  where s.sid=r1.sid);

  
  
---------------------Query 9 Optimized  -----------------  
select s.sname
from sailors s
where exists
        (
         select rTotal.sid
            from (select r1.sid
             from
                (select r.sid
                from reserves r
                 where  exists
                    (select bid
                     from boat b
                     where color = 'green' and r.bid =b.bid )
                )as r1
             inner join
                (select r.sid
                from reserves r
                 where  exists
                    (select bid
                     from boat b
                     where color = 'red' and r.bid =b.bid )
                ) as r2
             on r2.sid = r1.sid  ) as rTotal
          where rTotal.sid=s.sid
)       
             
