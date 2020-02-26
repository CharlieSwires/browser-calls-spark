create table person (
  id SERIAL UNIQUE not null PRIMARY KEY,
  name varchar(100) not null,
  phone_number varchar(15) not null
);