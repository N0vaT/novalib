DELETE FROM nl_role;

INSERT INTO nl_role(role_id, role_name)
VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

--INSERT INTO nl_users_role(user_id, role_id)
--VALUES (1, 1);