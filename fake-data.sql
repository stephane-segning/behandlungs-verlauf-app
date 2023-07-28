-- Generating 20 rows for the "patients" table
INSERT INTO "patients" ("pat_id", "address", "birth_date", "birth_place", "first_name", "gender", "id_number",
                        "last_name", "tel_number", "zip")
SELECT gen_random_uuid()                                                                           AS "pat_id",
       concat('Address_', gen_random_uuid())                                                       AS "address",
       date '1980-01-01' + (random() * (date '2003-01-01' - date '1980-01-01')) * interval '1 day' AS "birth_date",
       concat('Birth_Place_', gen_random_uuid())                                                   AS "birth_place",
       concat('First_Name_', gen_random_uuid())                                                    AS "first_name",
       CASE WHEN random() < 0.5 THEN 'Male' ELSE 'Female' END                                      AS "gender",
       gen_random_uuid()                                                                           AS "id_number",
       concat('Last_Name_', gen_random_uuid())                                                     AS "last_name",
       concat('Tel_', gen_random_uuid())                                                           AS "tel_number",
       concat('ZIP_', gen_random_uuid())                                                           AS "zip"
FROM
    generate_series(1, 20);

-- Generating 20 rows for the "flow_element_types" table
INSERT INTO "flow_element_types" ("type_id", "name", "personal_name")
SELECT gen_random_uuid()                      AS "type_id",
       concat('Type_', gen_random_uuid())     AS "name",
       concat('Personal_', gen_random_uuid()) AS "personal_name"
FROM
    generate_series(1, 20);

-- Generating 20 rows for the "flow_chart_elements" table
INSERT INTO "flow_chart_elements" ("flow_id", "name", "type_id")
SELECT gen_random_uuid()                                                      AS "flow_id",
       concat('Flow Element_', gen_random_uuid())                             AS "name",
       (SELECT "type_id" FROM "flow_element_types" ORDER BY random() LIMIT 1) AS "type_id"
FROM
    generate_series(1, 20);

-- Generating 100 rows for the "flow_edges" table
INSERT INTO "flow_edges" ("edge_id", "name", "end_node_id", "start_node_id")
SELECT gen_random_uuid()                                                       AS "edge_id",
       concat('Edge_', gen_random_uuid())                                      AS "name",
       (SELECT "flow_id" FROM "flow_chart_elements" ORDER BY random() LIMIT 1) AS "end_node_id",
       (SELECT "flow_id" FROM "flow_chart_elements" ORDER BY random() LIMIT 1) AS "start_node_id"
FROM
    generate_series(1, 100);

-- Generating 20 rows for the "cases" table
INSERT INTO "cases" ("case_id", "arrived_by", "date", "pat_id")
SELECT gen_random_uuid()                                                                           AS "case_id",
       CASE floor(random() * 3)::int
           WHEN 0 THEN 'Ambulance'
           WHEN 1 THEN 'Car'
           WHEN 2 THEN 'Walk-in'
           END                                                                                     AS "arrived_by",
       date '2021-01-01' + (random() * (date '2023-07-27' - date '2021-01-01')) * interval '1 day' AS "date",
       "pat_id"
FROM (SELECT "pat_id" FROM "patients" ORDER BY random() LIMIT 20) AS p;

-- Generating 100 rows for the "case_steps" table
INSERT INTO "case_steps" ("step_id", "data", "case_id", "flow_id")
SELECT gen_random_uuid()                                                       AS "step_id",
       concat('Step_', gen_random_uuid(), ' Data')                             AS "data",
       "case_id",
       (SELECT "flow_id" FROM "flow_chart_elements" ORDER BY random() LIMIT 1) AS "flow_id"
FROM (SELECT "case_id" FROM "cases" ORDER BY random() LIMIT 100) AS c;
