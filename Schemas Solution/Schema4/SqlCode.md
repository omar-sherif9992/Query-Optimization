-------------------Query 10 Original-------------------
explain analyze
select *
from actor
where act_id in(
select act_id
from movie_cast
where mov_id in(
select mov_id
from movie
where mov_title ='Annie Hall'));
-------------------Query 10 Optimized + materialized view -------------------
create MATERIALIZED VIEW query_10
as 
select mov_id from movie m2 where mov_title ='Annie Hall' WITH data ;


explain analyze select *
from actor
where act_id in( select act_id 
				from movie_cast m1
				where exists (select mov_id from query_10 m2 
							  where m1.mov_id=m2.mov_id  ) );
-------------------Query 11 Original-------------------
explain analyze
select dir_fname, dir_lname
from director
where dir_id in(
select dir_id
from movie_direction
where mov_id in(
select mov_id
from movie_cast
where role =any( select role
from movie_cast
where mov_id in(
select mov_id
from movie
where
mov_title='Eyes Wide Shut'))));

CREATE INDEX b_dir_id ON director USING hash(dir_id );
CREATE INDEX b_role ON movie_cast USING btree(role );
CREATE INDEX b_movID ON movie_cast USING btree(mov_id );
-------------------Query 11 Optimized + materialized view -------------------

CREATE MATERIALIZED VIEW query_11
AS
 select role
from movie_cast m1 
where exists (select mov_id from movie m2 
where mov_title='Eyes Wide Shut' and m1.mov_id=m2.mov_id)



explain analyze
select dir_fname, dir_lname
from director
where dir_id in(
select dir_id
from movie_direction
where mov_id in(
select mov_id
from movie_cast
where role =any( select role from query_11)));
-------------------Query 12 Original-------------------
explain analyze 
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
-------------------Query 12 Optimized + materialized view -------------------
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



CREATE INDEX b_movID ON movie USING hash(mov_id );
CREATE INDEX b_dir_id ON movie_direction USING btree(dir_id );
CREATE INDEX b_dir_fname ON director USING hash(dir_fname );
CREATE INDEX b_dir_lname ON director USING hash(dir_lname );











select *
from pg_indexes



DROP INDEX   IF EXISTS  b_movID cascade; 
DROP INDEX   IF EXISTS  b_dir_id cascade;
DROP INDEX   IF EXISTS  b_dir_fname cascade;
DROP INDEX   IF EXISTS  b_dir_lname cascade;
set enable_seqscan = on;
set enable_indexscan=on;
set enable_bitmapscan = on;

TRUNCATE actor CASCADE;
TRUNCATE director CASCADE;
TRUNCATE genres CASCADE;
TRUNCATE movie CASCADE;
TRUNCATE movie_cast CASCADE;
TRUNCATE movie_direction CASCADE;
TRUNCATE movie_genres CASCADE;
TRUNCATE rating CASCADE;
TRUNCATE reviewer CASCADE;