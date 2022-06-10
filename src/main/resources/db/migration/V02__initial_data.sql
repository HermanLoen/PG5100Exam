insert into users
values (nextval('users_user_id_seq'), 'admin@admin.com','$2y$10$vgKYiD.otRYQUn/me0ixP.5h/XK1Cs0JYdWpCNACymNmcYmNux8zO', now(), true );

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into users_authorities
values (1,1);

insert into users_authorities
values (1,2);