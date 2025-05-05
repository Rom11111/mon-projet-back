INSERT INTO user(firstname, lastname, email, password, role, address)
VALUES ("John", "Doe", "a@a.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "Adresse 1"),
       ("Jay", "White", "b@b.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Adresse 2"),
       ("Bob", "Allen", "c@c.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 3"),
       ("Gary", "Cow", "d@d.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 4");

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