INSERT INTO user(firstname, lastname, email, password, role, address)
VALUES ("John", "Doe", "a@a.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "Adresse 1"),
       ("Jay", "White", "b@b.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Adresse 2"),
       ("Bob", "Allen", "c@c.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 3"),
       ("Gary", "Cow", "d@d.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 4");

INSERT INTO user(firstname, lastname, email, password, role, address)
VALUES
    ("Alice", "Dupont", "alice.dupont@a.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "1 rue de la Paix, Paris"),
    ("Marc", "Lemoine", "marc.lemoine@b.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "12 avenue des Champs, Paris"),

    -- 8 Techs
    ("Léo", "Martin", "leo.martin@c.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "25 rue Victor Hugo, Lyon"),
    ("Sophie", "Bernard", "sophie.bernard@d.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "10 rue des Fleurs, Marseille"),
    ("Thomas", "Lemoine", "thomas.lemoine@e.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "5 boulevard de la République, Toulouse"),
    ("Julie", "Robert", "julie.robert@f.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "22 rue de la Liberté, Lille"),
    ("Antoine", "Durand", "antoine.durand@g.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "3 place de la Concorde, Paris"),
    ("Clara", "Moreau", "clara.moreau@h.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "14 rue de l'Opéra, Paris"),
    ("Louis", "Gauthier", "louis.gauthier@i.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "18 rue du Faubourg, Lyon"),
    ("Chloé", "Hervé", "chloe.herve@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "6 avenue de Verdun, Nantes"),
    -- Clients
    ("John", "Doe", "john.doe@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 1"),
    ("Jane", "Smith", "jane.smith@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 2"),
    ("Michael", "Johnson", "michael.johnson@mj.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 3"),
    ("Sarah", "Lee", "sarah.lee@sl.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 4"),
    ("Emily", "Davis", "emily.davis@ed.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 5"),
    ("Daniel", "Brown", "daniel.brown@db.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 6"),
    ("Lisa", "Wilson", "lisa.wilson@lw.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 7"),
    ("James", "Miller", "james.miller@jm.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 8"),
    ("Elizabeth", "Moore", "elizabeth.moore@em.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 9"),
    ("Robert", "Taylor", "robert.taylor@rt.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 10"),
    ("Sophia", "Anderson", "sophia.anderson@sa.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 11"),
    ("Matthew", "Thomas", "matthew.thomas@mt.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 12"),
    ("Olivia", "Jackson", "olivia.jackson@oj.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 13"),
    ("William", "White", "william.white@ww.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 14"),
    ("Mia", "Harris", "mia.harris@mh.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 15"),
    ("Lucas", "Martin", "lucas.martin@lm.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 16"),
    ("Chloe", "Lee", "chloe.lee@cl.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 17"),
    ("Ethan", "Gonzalez", "ethan.gonzalez@eg.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 18"),
    ("Ava", "Perez", "ava.perez@ap.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 19"),
    ("Logan", "Martinez", "logan.martinez@lm.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 20"),
    ("Grace", "Rodriguez", "grace.rodriguez@gr.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 21"),
    ("Sebastian", "Young", "sebastian.young@sy.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 22"),
    ("Zoe", "King", "zoe.king@zk.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 23"),
    ("Jack", "Scott", "jack.scott@js.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 24"),
    ("Lily", "Green", "lily.green@lg.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 25"),
    ("Aiden", "Adams", "aiden.adams@aa.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 26"),
    ("Sophie", "Nelson", "sophie.nelson@sn.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 27"),
    ("Benjamin", "Carter", "benjamin.carter@bc.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 28"),
    ("Hannah", "Morris", "hannah.morris@hm.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 29"),
    ("Samuel", "Roberts", "samuel.roberts@sr.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 30");

ALTER TABLE user MODIFY address VARCHAR(255) DEFAULT 'Inconnue';

-- état du matériel
INSERT INTO etat (id, name)
VALUES (1, 'neuf'),
       (2, 'occasion'),
       (3, 'bon état'),
       (4, 'reconditionné'),
       (5, 'très bon état');

-- Insérer les étiquettes du matériel
INSERT INTO label (id, name, color)
VALUES (1, 'Neuf', '#77FF77'),
       (2, 'Matériel haut de gamme', 'yellow'),
       (3, 'Reconditionné premium', 'grey'),
       (4, 'Location longue durée', 'darkred'),
       (5, 'Soldes', 'red');

-- Insérer du matériel avec des états appropriés
INSERT INTO product (id, name, code, description, price, etat_id, creator_id)
VALUES (1, 'MacBook Pro M2', 'mbp-m2', 'Ordinateur portable Apple 16 pouces', 2499, 1, 1),
       (2, 'Dell XPS 15', 'dellxps15', 'Ultrabook performant pour professionnels', 1899, 1, 1),
       (3, 'ThinkPad X1 Carbon', 'thinkpadx1', 'PC portable léger et puissant', 1599, 2, 1),
       (4, 'iPad Pro 12.9', 'ipadpro12', 'Tablette haut de gamme pour créatifs', 1399, 1, 2),
       (5, 'Surface Laptop Studio', 'surface-studio', 'PC hybride Microsoft', 1699, 2, 2),
       (6, 'HP Spectre x360', 'hpspectre', 'Ultrabook tactile polyvalent', 1499, 3, 2),
       (7, 'Mac Mini M2', 'macmini-m2', 'Mini PC performant Apple', 799, 1, 3),
       (8, 'ASUS ROG Zephyrus', 'asusrog', 'PC gamer puissant', 2299, 2, 3);

-- étiquettes matériels
INSERT INTO product_label (product_id, label_id)
VALUES (1, 1), -- MacBook Pro M2 en promotion
       (2, 2), -- Dell XPS 15 haut de gamme
       (3, 3), -- ThinkPad X1 Carbon reconditionné premium
       (4, 4), -- iPad Pro pour location longue durée
       (5, 5), -- Surface Laptop Studio en soldes
       (6, 3), -- HP Spectre x360 reconditionné premium
       (7, 1), -- Mac Mini en promotion
       (8, 2); -- ASUS ROG Zephyrus haut de gamme