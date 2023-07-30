-- Inserting data for flow_element_types
INSERT INTO "flow_element_types" ("type_id", "name", "personal_name")
VALUES ('4a1c506a-4f09-45e0-9860-5581d22ad079', 'Type A', 'Type A Personal'),
       ('907e5d63-5911-44cc-a962-335b070d4d93', 'Type B', 'Type B Personal'),
       ('4e0b0e96-cc1a-4f80-8df2-847007ec8f6e', 'Type 11', 'Personal Name 11'),
       ('1f4c58a2-df86-407c-baa4-af5583c340c6', 'Type 12', 'Personal Name 12'),
       ('f5d8d4f3-8c25-4f96-967f-69d91f08c7d0', 'Type 13', 'Personal Name 13'),
       ('1cf5a46d-53e6-4eab-966b-08d8b3e46e46', 'Type 14', 'Personal Name 14'),
       ('b70d9f44-3dc9-41c1-90b0-c5fb66958aa3', 'Type 15', 'Personal Name 15'),
       ('d2697d0c-c881-43c2-80f6-1ce57b01e7eb', 'Type 16', 'Personal Name 16'),
       ('f7ab0b65-9d0b-4384-905a-cc5ac942e7b3', 'Type 17', 'Personal Name 17'),
       ('32e3c625-9d5b-4e00-95fb-2f9d65171c18', 'Type 18', 'Personal Name 18'),
       ('e6e9709c-5d6c-4d33-b5fc-9243139e6e67', 'Type 19', 'Personal Name 19'),
       ('b72f47d8-9b77-4e2d-b4a2-92c0eb8c1fb6', 'Type 20', 'Personal Name 20');

-- Inserting data for patients
INSERT INTO "patients" ("pat_id", "first_name", "last_name", "gender", "birth_date")
VALUES ('1a0df2e0-0e1f-4c65-86e6-74e9c56a9c5d', 'John', 'Doe', 'Male', '1990-01-15'),
       ('f8e23572-6fc2-4c0f-9815-b442784e42b2', 'Jane', 'Smith', 'Female', '1985-05-20'),
       ('c5a7d958-00c1-4c37-860e-097499eb8aa7', 'Michael', 'Johnson', 'Male', '1988-09-10'),
       ('bf3f5edf-3670-4c6d-9c32-1f6576c20c89', 'Emily', 'Williams', 'Female', '1993-04-25'),
       ('6c2e17db-3af5-40c1-9316-9d8a8825a95f', 'David', 'Brown', 'Male', '1977-07-12');

-- Inserting data for cases
INSERT INTO "cases" ("case_id", "arrived_by", "date", "pat_id")
VALUES ('1ae3a1c1-4a54-4895-97a5-c47d92a3b5b1', 'Ambulance', '2023-07-25', '1a0df2e0-0e1f-4c65-86e6-74e9c56a9c5d'),
       ('b95b46ac-2b98-4b69-97d6-997f160d6fc7', 'Car', '2023-07-26', 'f8e23572-6fc2-4c0f-9815-b442784e42b2'),
       ('3ebb76ce-1a5c-491b-be4d-d98b2c23c1ed', 'Ambulance', '2023-07-27', 'c5a7d958-00c1-4c37-860e-097499eb8aa7'),
       ('eb5c23ca-e12a-4e3f-b43c-4effb580f828', 'Car', '2023-07-28', 'bf3f5edf-3670-4c6d-9c32-1f6576c20c89'),
       ('cc346da2-6da2-46a0-9570-09c8ef6e55af', 'Ambulance', '2023-07-29', '6c2e17db-3af5-40c1-9316-9d8a8825a95f');

-- Inserting data for flow_chart_elements
INSERT INTO "flow_chart_elements" ("flow_id", "name", "type_id")
VALUES ('55b8e15d-bc8d-4cb5-8ed2-44a0b2980e92', 'Flow Element 1', '4a1c506a-4f09-45e0-9860-5581d22ad079'),
       ('a220cdd6-d0dd-47c1-90dd-460e59f7c0a1', 'Flow Element 2', '907e5d63-5911-44cc-a962-335b070d4d93'),
       ('ab329d70-1079-43e6-8ea1-61f92f0f85d1', 'Flow Element 3', '4a1c506a-4f09-45e0-9860-5581d22ad079'),
       ('1e470e48-e66e-4260-96fc-50a9567551e2', 'Flow Element 4', '907e5d63-5911-44cc-a962-335b070d4d93'),
       ('e5d2dd42-ae6d-4153-80f3-32472eaed594', 'Flow Element 5', '4a1c506a-4f09-45e0-9860-5581d22ad079'),
       ('186c4b3e-4d92-4c8e-8e3d-b76c0b32a3b7', 'Flow Element 6', '907e5d63-5911-44cc-a962-335b070d4d93'),
       ('94b2b9c9-d372-44a5-a46e-17afaa6c0f52', 'Flow Element 7', '4e0b0e96-cc1a-4f80-8df2-847007ec8f6e'),
       ('d2d72070-23db-4784-b1bf-6e09f050ac7b', 'Flow Element 8', '1f4c58a2-df86-407c-baa4-af5583c340c6'),
       ('5f8f9227-d731-4e78-bdb5-61886be4dd25', 'Flow Element 9', 'f5d8d4f3-8c25-4f96-967f-69d91f08c7d0'),
       ('f983614e-3cc0-4f47-b5ac-5e7c8d33e7b9', 'Flow Element 10', '1cf5a46d-53e6-4eab-966b-08d8b3e46e46');

-- Inserting data for flow_edges
INSERT INTO "flow_edges" ("edge_id", "name", "end_node_id", "start_node_id")
VALUES ('ef8b8e4b-8af4-43d2-8ca1-0b3000956c8f', 'Edge 1', 'a220cdd6-d0dd-47c1-90dd-460e59f7c0a1',
        '55b8e15d-bc8d-4cb5-8ed2-44a0b2980e92'),
       ('2b6327c3-086b-4c03-9ad7-3cb45ad1bade', 'Edge 2', '55b8e15d-bc8d-4cb5-8ed2-44a0b2980e92',
        'a220cdd6-d0dd-47c1-90dd-460e59f7c0a1'),
       ('aed900e3-d4f2-4cd7-a1d3-6f81e55084a5', 'Edge 3', '1e470e48-e66e-4260-96fc-50a9567551e2',
        '55b8e15d-bc8d-4cb5-8ed2-44a0b2980e92'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e34', 'Edge 4', '94b2b9c9-d372-44a5-a46e-17afaa6c0f52',
        '55b8e15d-bc8d-4cb5-8ed2-44a0b2980e92'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e35', 'Edge 5', '5f8f9227-d731-4e78-bdb5-61886be4dd25',
        'a220cdd6-d0dd-47c1-90dd-460e59f7c0a1'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e36', 'Edge 6', 'f983614e-3cc0-4f47-b5ac-5e7c8d33e7b9',
        '5f8f9227-d731-4e78-bdb5-61886be4dd25'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e37', 'Edge 7', '5f8f9227-d731-4e78-bdb5-61886be4dd25',
        'd2d72070-23db-4784-b1bf-6e09f050ac7b'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e38', 'Edge 8', 'd2d72070-23db-4784-b1bf-6e09f050ac7b',
        '94b2b9c9-d372-44a5-a46e-17afaa6c0f52'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e39', 'Edge 9', 'd2d72070-23db-4784-b1bf-6e09f050ac7b',
        'e5d2dd42-ae6d-4153-80f3-32472eaed594'),
       ('61aa0511-7e38-4325-89a3-7d23887c3e40', 'Edge 10', 'e5d2dd42-ae6d-4153-80f3-32472eaed594',
        '1e470e48-e66e-4260-96fc-50a9567551e2');

-- Inserting data for case_steps
-- For each combination of flow_id and case_id, create 2 to 5 steps
DO
$$
    DECLARE
        flow_case RECORD;
    BEGIN
        FOR flow_case IN
            SELECT DISTINCT flow_id, case_id
            FROM cases,
                 flow_chart_elements
            LOOP
                FOR i IN 1..(2 + floor(random() * 4))
                    LOOP
                        INSERT INTO "case_steps" ("step_id", "data", "case_id", "flow_id")
                        VALUES (uuid_generate_v4(), 'Step ' || i || ' Data', flow_case.case_id, flow_case.flow_id);
                    END LOOP;
            END LOOP;
    END
$$;
