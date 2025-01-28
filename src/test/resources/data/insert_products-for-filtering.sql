-- Insert Stores
insert into store (id, name)
values ('023e4795-5f59-4650-bd86-d7990803759c', 'Sister Ray'),
       ('fe22ec46-383b-46a0-a7f0-470bc5977f57', 'Rough Trade');

-- Insert Product Groups
insert into product_group (id, title, release_date)
values ('e38abc59-47ef-4610-96f1-fbdc0b2fd9b0', 'Music', '2021-08-07'),
       ('bfb4c101-ac4d-4172-8c85-14228f85acd1', 'Apparel', '2022-01-01'),
       ('ac7297d9-a74d-486e-9bca-bd714f33dc23', 'Merch', '2023-12-31');

-- Insert Products
insert into product (id, title, distribution, format, price_gbp, price_usd, price_eur, release_date, store_id, product_group_id)
values  ('15ac6b6c-e6e8-423c-b5a8-bf7d44f43e77', 'Chelsea Wolfe CD', 'PHYSICAL', 'CD', 19.99, 17.99, 18.99, '2024-03-15', '023e4795-5f59-4650-bd86-d7990803759c', 'e38abc59-47ef-4610-96f1-fbdc0b2fd9b0'),
        ('34ae6825-59d3-4e14-b7f5-5e648464011c', 'Goat Girl MP3', 'DIGITAL', 'MP3', 14.99, 18.99, 16.99, '2023-08-01', 'fe22ec46-383b-46a0-a7f0-470bc5977f57', 'e38abc59-47ef-4610-96f1-fbdc0b2fd9b0'),
        ('c540296b-df4d-49fe-b856-8355764b27d8', 'Fraulein Cassette', 'PHYSICAL', 'CASSETTE', 9.99, 8.99, 7.99, '2023-06-20', 'fe22ec46-383b-46a0-a7f0-470bc5977f57', 'ac7297d9-a74d-486e-9bca-bd714f33dc23'),
        ('70a4f3cc-8083-42cc-9a11-31a252d1fef1', 'Deep Tan Vinyl', 'PHYSICAL', 'VINYL', 24.99, 22.99, 23.99, '2023-11-10', 'fe22ec46-383b-46a0-a7f0-470bc5977f57', 'e38abc59-47ef-4610-96f1-fbdc0b2fd9b0'),
        ('3ee2cb68-cfa4-4d30-ad84-b6e4351e7a76', 'Ghum CD', 'PHYSICAL', 'CD', 24.99, 22.99, 23.99, '2024-07-12', 'fe22ec46-383b-46a0-a7f0-470bc5977f57', 'e38abc59-47ef-4610-96f1-fbdc0b2fd9b0');
