# Boardel

Classic forum, project for He-Arc


# Quick Start
* `Docker-compose up --build`
* `docker-compose exec db mysql -u root -p`
* enter the password (root)
* `CREATE database BOARDEL`
* `Docker-compose up`
* Sign up
* in db :
`INSERT INTO role (name) value ("ROLE_ADMIN");
INSERT INTO role (name) value ("ROLE_MODO");
insert into user_roles (custom_user_id, roles_id) value (1,2);`

And the server is ready !
