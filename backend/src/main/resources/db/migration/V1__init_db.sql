create table form
(
    id         bigserial primary key not null,
    man        boolean default false not null,
    woman      boolean default false not null,
    friendship boolean default false not null,
    love       boolean default false not null,
    sex        boolean default false not null,
    flirt      boolean default false not null
);

create table "user"
(
    id          bigserial primary key                     not null,
    username    varchar(255)                              not null unique,
    first_name  varchar(255)                default ''    ,
    last_name   varchar(255)                default ''    ,
    password    text                        default ''    not null,
    email       varchar(255)                              not null,
    gender      varchar(255)                              ,
    birthday    timestamp without time zone               ,
    description text                        default ''    ,
    is_active   boolean                     default true  not null,
    form_id     bigint,
    create_ts   timestamp without time zone default now() not null,
    update_ts   timestamp without time zone,
    delete_ts   timestamp without time zone,
    foreign key (form_id) references form (id)
);