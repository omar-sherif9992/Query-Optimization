# Schema 3
* Sailors( sid:integer, sname: string, rating: integer, age: real)
* Boats( bid:integer, bname: string, color: string)
* Reserves (sid: integer, bid: integer, day: date)

### Modifications to Insertion Code:
* The insertion code provided to you inserts dummy values. You are required to change it to have
19000 sailors, 3000 boats, and 35000 reserves. Before inserting the data, check queries 7-9
below you are going to analyze, the data that you insert must have values used in those queries
Note: in order to see different decisions made internally by the database engine in the query plan
and index selection, you must return a non-trivial number of rows in the result set (in the
hundreds): an empty result set is not acceptable.


### Query 7:
Find the names of sailors who have reserved boat 103.
select s.sname
from sailors s
where
s.sid in( select r.sid
from reserves r
where r.bid = 103 );


### Query 8:
Find the names of sailors 'who ha'ue reserved a red boat.
select s.sname
from sailors s
where s.sid in ( select r.sid
from reserves r
where r. bid in (select b.bid
from boat b
where b.color = 'red'));

### Query 9:
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
