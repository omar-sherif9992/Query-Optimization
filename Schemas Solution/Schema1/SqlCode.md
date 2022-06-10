
-------Initial Configuration---------

set enable_hashjoin = off;
set enable_hashagg = off;
set enable_bitmapscan = on;
set enable_seqscan=on;

set enable_async_append= off;
set enable_gathermerge = off;
set enable_incremental_sort = off;
set enable_indexscan = on;
set enable_indexonlyscan = on;
set enable_material = off;
set enable_memoize = off;
set enable_mergejoin = on;

set enable_parallel_append = off;
set enable_parallel_hash = off;
set enable_partition_pruning = off;
set enable_partitionwise_join = off;
set enable_partitionwise_aggregate = off;
set enable_sort = on;
set enable_tidscan = off;




-- pk constraints
ALTER TABLE student DROP CONSTRAINT student_pkey cascade;
ALTER TABLE section DROP CONSTRAINT section_pkey cascade;
ALTER TABLE takes DROP CONSTRAINT takes_pkey cascade;


TRUNCATE classroom;
truncate course;
truncate student;
truncate department;
truncate instructor;
truncate pre_requiste;
truncate section;
truncate section_time;
truncate takes;
truncate time_slot;
truncate student;




-- Check Indexes
select *
from pg_indexes
where tablename='student' or tablename='section' or tablename='takes'





-------------------Original Query



-- Hash Indices
CREATE INDEX idx_dept ON student 
USING hash (department);
CREATE INDEX idx_id ON student 
USING hash (id);
CREATE INDEX idx_sec ON section 
USING hash (section_id);
CREATE INDEX idx_takes ON takes 
USING hash (section_id);
CREATE INDEX idx_semester ON section
USING hash (semester);
CREATE INDEX idx_year ON section
USING hash (year);










--- Btree Indices
CREATE INDEX idx_dept ON student 
USING btree (department);
CREATE INDEX idx_id ON student 
USING btree (id);
CREATE INDEX idx_sec ON section 
USING btree (section_id);
CREATE INDEX idx_takes ON takes 
USING btree (section_id);
CREATE INDEX idx_semester ON section
USING btree (semester);
CREATE INDEX idx_year ON section
USING btree (year);













drop index if exists idx_dept;
drop index if exists idx_id;
drop index if exists idx_sec;
drop index if exists idx_takes;
drop index if exists idx_semester;
drop index if exists idx_year;






--- BRIN Indices
CREATE INDEX idx_dept ON student 
USING brin (department);
CREATE INDEX idx_semester ON section
USING brin (semester);



--- Mixed Indices
CREATE INDEX idx_dept ON student 
USING btree (department);
CREATE INDEX idx_id ON student 
USING btree (id);
CREATE INDEX idx_sec ON section 
USING hash (section_id);
CREATE INDEX idx_takes ON takes 
USING hash (section_id);
CREATE INDEX idx_semester ON section
USING brin (semester);
CREATE INDEX idx_year ON section
USING hash (year);



-- Original query
explain analyse select *
from (select *
from student
where
department = 'CSEN') as CS1_student
full outer join
(select *
from takes t inner join section s
on t.section_id = s.section_id
where semester=1
and
year = 2019) as sem1_student
on CS1_student.id = sem1_student.student_id;





-- Alternative query

CREATE MATERIALIZED VIEW stud
 AS
 select * from student
 where
 department = 'CSEN'
 WITH DATA;




 create MATERIALIZED VIEW sec_Take
 AS
 select t.student_id,t.section_id,t.grade,s.semester,s.year,s.instructor_id,s.course_id,s.classroom_building,
 s.classroom_room_no
 from takes t inner join section s
 on t.section_id = s.section_id
 where s.semester=1 and s.year = 2019
 WITH DATA;




 CREATE MATERIALIZED VIEW sec_Take2
 AS
 select t.student_id,t.section_id,t.grade,s.semester,s.year,s.instructor_id,s.course_id,s.classroom_building,
 s.classroom_room_no
 from takes t right outer join section s
 on t.section_id = s.section_id
 where semester=2 and year=2019
 WITH DATA;







 explain analyse
 Select * From stud left outer join sec_Take ON stud.id=sec_Take.student_id
 UNION 
 ( 
 Select *
 from stud right outer join
 (select t.student_id,t.section_id,t.grade,s.semester,s.year,s.instructor_id,s.course_id,s.classroom_building,
 s.classroom_room_no
 from takes t right outer join section s
 on t.section_id = s.section_id
 where semester=2 and year=2019) as sec2
 ON stud.id=sec2.student_id
 )
 
 

 
 
--- Mixed Indices 
CREATE INDEX idx_takes ON takes 
USING hash (section_id);
CREATE INDEX idx_semester ON section
USING btree (semester);
 

 
 
-- Hash indices 
 CREATE INDEX idx_dept ON stud 
USING hash(department);
CREATE INDEX idx_id ON takes 
USING hash (student_id);

CREATE INDEX idx_year ON section
USING hash (year);
CREATE INDEX idx_sec ON section 
USING hash (section_id);
CREATE INDEX idx_semester ON section
USING hash (semester);


--Btree
CREATE INDEX idx_dept ON stud 
USING btree (department);
CREATE INDEX idx_id ON student 
USING btree (id);
CREATE INDEX idx_sec ON sec_Take 
USING btree (section_id);
CREATE INDEX idx_takes ON sec_Take 
USING btree (section_id);

CREATE INDEX idx_year ON sec_Take
USING btree (year);



--BRIN indices
CREATE INDEX idx_dept ON stud 
USING brin (department);
CREATE INDEX idx_semester ON sec_Take 
USING brin (semester);



drop index if exists idx_dept;
drop index if exists idx_id;
drop index if exists idx_sec;
drop index if exists idx_takes;
drop index if exists idx_semester;
drop index if exists idx_year;






