/*
 * H2 script: Load the database with reference data and unit test data.
 */

insert into item (created_on, done, todo, user_id, item_id) values ('2018-09-16', false, 'Incomplete Todo Item', 1, 1);
insert into item (created_on, done, todo, user_id, item_id) values ('2018-09-16', true, 'Complete Todo Item', 1, 2);