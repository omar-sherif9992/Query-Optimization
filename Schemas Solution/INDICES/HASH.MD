1) given query with hash indices only,

-- CREATE HASH INDICES

CREATE INDEX b_sailorsSID ON sailors USING HASH(sid );
CREATE INDEX b_reservesSID ON reserves  USING HASH(sid );
CREATE INDEX b_reservesBID ON reserves  USING HASH(bid );
CREATE INDEX b_boat ON boat USING HASH(bid,color );


-- DROP INDICES

DROP INDEX   IF EXISTS  b_sailorsSID cascade; 
DROP INDEX   IF EXISTS  b_reservesSID cascade; 
DROP INDEX   IF EXISTS  b_reservesBID cascade; 
DROP INDEX   IF EXISTS  b_boat cascade; 


-- SHOW INDICES

select *
from pg_indexes
where tablename = 'sailors' or tablename='reserves' or tablename='boat';
