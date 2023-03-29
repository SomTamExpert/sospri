DELETE FROM channel;
INSERT INTO channel (id, topic, description) VALUES
(1, 'Webframeworks', 'Everything about HTML, CSS, JavaScript'),
(2, 'Technotrends', 'The latest news about the newest technologies'),
(3, 'Fun', 'The Channel with the most hilarious engineering jokes'),
(4, 'Secret', 'Admin ONLY, content restricted');

DELETE FROM message;
INSERT INTO message (id, content, author, origin, channel_id) VALUES
  (1, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:30:40', 1),
  (2, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:31:22', 2),
  (3, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Mac Afee', '2020-03-10 10:38:11', 3),
  (4, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Tony Stark', '2020-03-10 10:42:57', 4);

/* encrypted password for id 1..4 is 1234* */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority, challenge) VALUES
  (1, 'Albert', 'Einstein', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'albert.einstein', 'admin', 'Herr Oberle'),
  (2, 'Mac',  'Afee', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'mac.afee', 'member', 'Herr Rutschmann'),
  (3, 'Tony',  'Stark', '$2a$10$Y5WfhqTlN91E0j88FXVOduxHiwCnSSfgGCrcivzPhzhM889f02.u6', 'toni.stark', 'supervisor', 'Herr Gygax'),
  (4, 'Wilhelm',  'Tell', '$2a$10$4VrJkrAmhq/sPVnBgZ6Gm.S7ctGTHmo.f9QS3jQ2.c8cf1uH4CSfK', 'wilhelm.tell', 'member', 'Herr Lanza'),
  (5, 'Marco',  'Karpf', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'marco.karpf', 'moderator', 'Herr Feuchter');
