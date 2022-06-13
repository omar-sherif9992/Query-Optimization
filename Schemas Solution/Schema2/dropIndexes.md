
## see what indexes are created for that table

select *
from pg_indexes
where tablename = 'sailors';



-- see constraint names

SELECT con.*
       FROM pg_catalog.pg_constraint con
            INNER JOIN pg_catalog.pg_class rel
                       ON rel.oid = con.conrelid
            INNER JOIN pg_catalog.pg_namespace nsp
                       ON nsp.oid = connamespace
       WHERE  rel.relname = 'reserves';
       
       
       
       
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


-- Drop default Index of sailors
DROP INDEX   IF EXISTS  sailors_pkey cascade; 




