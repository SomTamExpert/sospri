DELETE FROM channel;
INSERT INTO channel (id, topic, description) VALUES
(1, 'Webframeworks', 'Everything about HTML, CSS, JavaScript'),
(2, 'Technotrends', 'The latest news about the newest technologies'),
(3, 'Fun', 'The Channel with the most hilarious engineering jokes'),
(4, 'Secret', 'Admin ONLY, content restricted');

DELETE FROM message;
INSERT INTO message (id, content, author, origin, channel_id) VALUES
  (1, 'Spring Security is a powerful and customizable security framework for Java applications. It integrates seamlessly with the Spring framework and offers a wide range of features, including support for various authentication mechanisms, such as LDAP, OAuth, and OpenID, and integration with other security-related technologies like JWT and SAML. With Spring Security, developers can easily configure security policies and rules based on their specific requirements, using pre-built filters, annotations, and authentication providers.', 'Albert Einstein', '2020-03-10 10:30:40', 1),
  (2, 'The techno trend of cloud computing has revolutionized the way businesses store, process, and manage data. Cloud computing allows organizations to access computing resources, such as servers, storage, and applications, over the internet, rather than relying on on-premises infrastructure. This provides many benefits, including flexibility, scalability, and cost savings, as businesses only pay for the resources they use.', 'Albert Einstein', '2020-03-10 10:31:22', 2),
  (3, 'Why do programmers prefer dark mode? Because light attracts bugs!', 'Mac Afee', '2020-03-10 10:38:11', 3),
  (4, 'The conflict in Ukraine began in 2014 when protests in the capital city of Kiev led to the ousting of the pro-Russian President Viktor Yanukovych. This triggered unrest in the Russian-speaking region of Crimea, which was annexed by Russia in March 2014. Following this, pro-Russian separatists in the eastern regions of Donetsk and Luhansk declared independence and launched a rebellion against the Ukrainian government.', 'Tony Stark', '2020-03-10 10:42:57', 4),
  (5, 'Artificial intelligence (AI) is revolutionizing the way businesses operate by enabling them to automate tasks, analyze data, and make more informed decisions. AI algorithms can learn from large amounts of data and provide insights and predictions that were previously impossible or difficult to obtain. This trend has been driven by advancements in machine learning, natural language processing, and computer vision, which have made AI more accessible, powerful, and affordable. As businesses continue to adopt AI, we can expect to see increased efficiency, productivity, and innovation in various industries. However, concerns around privacy, security, and bias need to be addressed to ensure that AI is used ethically and responsibly.', 'Albert Einstein', '2021-07-11 06:33:48', 2),
  (6, 'Why do programmers hate nature? Because they''re afraid of open fields!', 'Albert Einstein', '2022-01-01 11:12:11', 3),
  (7, 'What do you get when you cross a software engineer and a vampire? A program that sucks the life out of your computer!', 'Wilhelm Tell', '2019-07-10 17:48:56', 3),
  (8, 'Why do programmers prefer dogs to cats? Because dogs have a main() function!', 'Mac Afee', '2020-03-10 10:38:11', 3);


/* encrypted password for id 1..4 is 1234* */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority, challenge) VALUES
  (1, 'Albert', 'Einstein', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'albert.einstein', 'admin', 'Herr Oberle'),
  (2, 'Mac',  'Afee', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'mac.afee', 'member', 'Herr Rutschmann'),
  (3, 'Tony',  'Stark', '$2a$10$Y5WfhqTlN91E0j88FXVOduxHiwCnSSfgGCrcivzPhzhM889f02.u6', 'toni.stark', 'supervisor', 'Herr Gygax'),
  (4, 'Wilhelm',  'Tell', '$2a$10$4VrJkrAmhq/sPVnBgZ6Gm.S7ctGTHmo.f9QS3jQ2.c8cf1uH4CSfK', 'wilhelm.tell', 'member', 'Herr Lanza'),
  (5, 'Marco',  'Karpf', '$2a$10$hMZ93PbK3CZLJCD22eVBTug95KyYpFNxgdAwvInujUsgtZrl6Qnbe', 'marco.karpf', 'moderator', 'Herr Feuchter');
