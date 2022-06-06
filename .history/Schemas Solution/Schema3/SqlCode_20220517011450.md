## SQL Code 

-- Database: schema3

-- DROP DATABASE IF EXISTS schema3;

CREATE DATABASE schema3
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	
select count(sid)
from sailors;

select count(*)
from boat;

select count(*)
from reserves;


delete from Reserves;

delete from sailors;

delete from Boat;


--- Sailors  
-- see what indexes are created for that table
select *
from pg_indexes
where tablename = 'sailors' or tablename='reserves' or tablename='boat';

-- see constraint names
SELECT con.*
       FROM pg_catalog.pg_constraint con
            INNER JOIN pg_catalog.pg_class rel
                       ON rel.oid = con.conrelid
            INNER JOIN pg_catalog.pg_namespace nsp
                       ON nsp.oid = connamespace
       WHERE  rel.relname = 'sailors'  or rel.relname='reserves' or rel.relname='boat';

-- drop constraint over a table
ALTER TABLE sailors   
DROP CONSTRAINT sailors_pkey;

ALTER TABLE reserves   
DROP CONSTRAINT reserves_pkey;

ALTER TABLE reserves   
DROP CONSTRAINT reserves_sid_fkey;

ALTER TABLE reserves   
DROP CONSTRAINT reserves_bid_fkey;

ALTER TABLE reserves   
DROP CONSTRAINT sailors_pkey;

ALTER TABLE boat   
DROP CONSTRAINT boat_pkey;

-- Drop default Index of boat    
DROP INDEX   IF EXISTS  boat_pkey cascade; 


-- Drop default Index of boats
select *
from pg_indexes
where tablename = 'boats';
DROP INDEX   IF EXISTS  boat_pkey cascade; 

-- Drop default Index of reserves

select *
from pg_indexes
where tablename = 'reserves';


DROP INDEX   IF EXISTS  reserves_pkey cascade; 





-- ==================================================== Query 7 ===================================================================

--  Query 7 COUNTING RESULT SET , Number Of Rows = 582
select count(s.sname)
from sailors s
where
s.sid in( select r.sid
from reserves r
where r.bid = 103 );



-- Query 7 optimized  (COUNTING RESULT SET) , Number Of Rows = 582

select count(s.sname)
from sailors s inner join (select distinct r.sid from reserves r where r.bid =103 ) as r1 on s.sid =r1.sid




--  Query 7 (STATISTICS)

explain analyze select s.sname
from sailors s
where
s.sid in( select r.sid
from reserves r
where r.bid = 103 );


-- Query 7 optimized  (STATISTICS)

explain analyze select s.sname
from sailors s inner join (select distinct r.sid from reserves r where r.bid =103 ) as r1 on s.sid =r1.sid



-- ==================================================== Query 8 ===================================================================


--  Query 8 (COUNTING RESULT SET) , Number Of Rows = 673

select count(s.sid)
from sailors s
where s.sid in ( select r.sid
from reserves r
where r. bid in (select b.bid
from boat b
where b.color = 'red'));

-- Query 8 optimized (COUNTING RESULT SET) , Number Of Rows = 673

select count(s.sid)
from sailors s inner join (select distinct r.sid from boat b, reserves r  where color='red' and r.bid=b.bid   ) as r1 on s.sid=r1.sid





--  Query 8 (STATISTICS)

explain analyze select s.sid
from sailors s
where s.sid in ( select r.sid
from reserves r
where r. bid in (select b.bid
from boat b
where b.color = 'red'));

-- Query 8 optimized (STATISTICS)

explain analyze select s.sid
from sailors s inner join (select distinct r.sid from boat b, reserves r  where color='red' and r.bid=b.bid   ) as r1 on s.sid=r1.sid




-- ==================================================== Query 9 ===================================================================


--  Query 9 (COUNTING RESULT SET) , Number Of Rows = 177
select  count(s.sname)
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

-- Query 9 optimization (COUNTING RESULT SET)  , Number Of Rows = 177

select count(s.sname)
from sailors s, reserves r1,reserves r2, boat b1,boat b2
where
s.sid = r1.sid
and
r1.bid = b1.bid
and
b1.color = 'green'
and
s.sid= r2.sid
and
b2.bid=r2.bid
and 
b2.color ='red'




--  Query 9  (STATISTICS)
explain analyze select  count(s.sname)
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

-- Query 9 optimization (STATISTICS)

explain analyze select s.sname
from sailors s, reserves r1,reserves r2, boat b1,boat b2
where
s.sid = r1.sid
and
r1.bid = b1.bid
and
b1.color = 'green'
and
s.sid= r2.sid
and
b2.bid=r2.bid
and 
b2.color ='red'
