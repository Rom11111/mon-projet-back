INSERT INTO user(firstname, lastname, email, password, role, address, phone)
VALUES
    ("John", "Doe", "a@a.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "Adresse 1", "0612345678"),
    ("Jay", "White", "b@b.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Adresse 2", "0623456789"),
    ("Bob", "Allen", "c@c.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 3", "0634567890"),
    ("Gary", "Cow", "d@d.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "Adresse 4", "0645678901"),

-- 2 Admin
    ("Alice", "Dupont", "alice.dupont@a.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "1 rue de la Paix, Paris", "0656789012"),
    ("Marc", "Lemoine", "marc.lemoine@b.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "ADMIN", "12 avenue des Champs, Paris", "0667890123"),

-- 8 Techs
    ("Léo", "Martin", "leo.martin@c.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "25 rue Victor Hugo, Lyon", "0678901234"),
    ("Sophie", "Bernard", "sophie.bernard@d.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "10 rue des Fleurs, Marseille", "0689012345"),
    ("Thomas", "Lemoine", "thomas.lemoine@e.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "5 boulevard de la République, Toulouse", "0690123456"),
    ("Julie", "Robert", "julie.robert@f.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "22 rue de la Liberté, Lille", "0601234567"),
    ("Antoine", "Durand", "antoine.durand@g.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "3 place de la Concorde, Paris", "0612345689"),
    ("Clara", "Moreau", "clara.moreau@h.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "14 rue de l'Opéra, Paris", "0623456798"),
    ("Louis", "Gauthier", "louis.gauthier@i.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "18 rue du Faubourg, Lyon", "0634567980"),
    ("Chloé", "Hervé", "chloe.herve@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "TECH", "6 avenue de Verdun, Nantes", "0645679801"),

-- Clients
    ("John", "Doe", "john.doe@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 1", "0656798012"),
    ("Jane", "Smith", "jane.smith@j.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 2", "0667980123"),
    ("Michael", "Johnson", "michael.johnson@mj.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 3", "0679801234"),
    ("Sarah", "Lee", "sarah.lee@sl.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 4", "0689012356"),
    ("Emily", "Davis", "emily.davis@ed.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 5", "0690123567"),
    ("Daniel", "Brown", "daniel.brown@db.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 6", "0601235678"),
    ("Lisa", "Wilson", "lisa.wilson@lw.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 7", "0612356789"),
    ("James", "Miller", "james.miller@jm.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 8", "0623567890"),
    ("Elizabeth", "Moore", "elizabeth.moore@em.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 9", "0635678901"),
    ("Robert", "Taylor", "robert.taylor@rt.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 10", "0646789012"),
    ("Sophia", "Anderson", "sophia.anderson@sa.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 11", "0657890123"),
    ("Matthew", "Thomas", "matthew.thomas@mt.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 12", "0668901234"),
    ("Olivia", "Jackson", "olivia.jackson@oj.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 13", "0679012345"),
    ("William", "White", "william.white@ww.com", "$2a$10$Fpc/zCciK4CwnYR6.YFSAuXNjrYDk7brys.s.V/KcoUuLQ6ElY8q2", "CLIENT", "Client Address 14", "0680123456");

ALTER TABLE user MODIFY address VARCHAR(255) DEFAULT 'Inconnue';

-- état du matériel
INSERT INTO etat (id, name)
VALUES (1, 'neuf'),
       (2, 'occasion'),
       (3, 'bon état'),
       (4, 'reconditionné'),
       (5, 'très bon état'),
       (6, 'très ');

-- Insérer les étiquettes du matériel
INSERT INTO label (id, name, color)
VALUES (1, 'Neuf', '#77FF77'),
       (2, 'Matériel haut de gamme', 'yellow'),
       (3, 'Reconditionné premium', 'grey'),
       (4, 'Location longue durée', 'darkred'),
       (5, 'Soldes', 'red');


INSERT INTO category (id, name)
VALUES (1, 'Ordinateurs portables'),
       (2, 'Tablettes'),
       (3, 'PC fixes'),
       (4, 'Écrans'),
       (5, 'Accessoires'),
       (6, 'Imprimantes'),
       (7, 'Stations de travail'),
       (8, 'Réseau et connectivité'),
       (9, 'Stockage');

INSERT INTO product (name, code, description, price, etat_id, creator_id, category_id)
-- 1. Ordinateurs portables (category_id = 1)
VALUES ('MacBook Pro M2', 'mbp-m2', 'Ordinateur portable Apple 16 pouces', 2499, 1, 1, 1),
       ('Dell XPS 15', 'dellxps15', 'Ultrabook performant pour professionnels', 1899, 1, 1, 1),
       ('ThinkPad X1 Carbon', 'thinkpadx1', 'PC portable léger et puissant', 1599, 2, 1, 1),
       ('Surface Laptop Studio', 'surface-studio', 'PC hybride Microsoft', 1699, 2, 2, 1),
       ('HP Spectre x360', 'hpspectre', 'Ultrabook tactile polyvalent', 1499, 3, 2, 1),
       ('Lenovo Yoga Slim 7', 'lenovoyoga7', 'Ultrabook compact et puissant', 1299, 1, 1, 1),
       ('HP Envy 13', 'hpenvy13', 'Portable polyvalent avec bon rapport qualité-prix', 1099, 1, 1, 1),
       ('ASUS ZenBook 14 OLED', 'asuszen14', 'Ultrabook avec écran OLED', 999, 1, 2, 1),
       ('Acer Swift 5', 'acerswift5', 'Ultrabook léger et autonome', 899, 1, 2, 1),
       ('Microsoft Surface Laptop 5', 'surfacelap5', 'PC portable Microsoft haut de gamme', 1399, 2, 2, 1),
       ('Razer Blade 15', 'razerblade15', 'Portable gamer puissant', 2199, 2, 3, 1),
       ('LG Gram 17', 'lggram17', 'Portable ultraléger avec grand écran', 1699, 1, 3, 1),

-- 2. Tablettes (category_id = 2)

       ('iPad Pro 12.9', 'ipadpro12', 'Tablette haut de gamme pour créatifs', 1399, 1, 2, 2),
       ('iPad Air 5', 'ipadair5', 'Tablette polyvalente Apple', 799, 1, 2, 2),
       ('Samsung Galaxy Tab S9', 'galaxytabs9', 'Tablette Android haut de gamme', 949, 2, 1, 2),
       ('Microsoft Surface Pro 9', 'surfacepro9', 'Tablette hybride Windows', 1199, 1, 1, 2),
       ('Lenovo Tab P12 Pro', 'lenovotabp12', 'Tablette Android professionnelle', 699, 2, 3, 2),
       ('Huawei MatePad Pro', 'matepadpro', 'Tablette fluide avec stylet', 699, 1, 2, 2),
       ('iPad Mini 6', 'ipadmini6', 'Tablette compacte Apple', 659, 1, 1, 2),
       ('Samsung Galaxy Tab A8', 'tab-a8', 'Tablette Android pour usage léger', 249, 1, 3, 2),
       ('Wacom MobileStudio Pro', 'wacompro', 'Tablette graphique autonome', 1899, 2, 3, 2),
       ('Lenovo Yoga Tab 13', 'yogatab13', 'Tablette avec kickstand intégré', 649, 1, 2, 2),

-- 3. PC fixes (category_id = 3)
       ('Mac Mini M2', 'macmini-m2', 'Mini PC performant Apple', 799, 1, 3, 3),
       ('iMac 24 pouces M1', 'imac24', 'Tout-en-un Apple pour bureautique et graphisme', 1799, 1, 2, 3),
       ('HP Pavilion Gaming Desktop', 'hppavgaming', 'PC de bureau gaming d’entrée de gamme', 1099, 1, 2, 3),
       ('Dell Inspiron 3891', 'dell3891', 'Tour bureautique performante', 749, 1, 3, 3),
       ('ASUS ExpertCenter D7', 'asusexpcenter', 'PC fixe professionnel', 899, 2, 3, 3),
       ('Intel NUC 12 Pro', 'intelnuc12', 'Mini PC compact', 699, 1, 1, 3),
       ('Acer Veriton Essential', 'acerveriton', 'PC bureautique pour PME', 599, 1, 1, 3),
       ('Lenovo IdeaCentre 5', 'ideacentre5', 'PC de bureau tout-en-un', 849, 2, 2, 3),
       ('Mac Studio M2 Max', 'macstudio', 'Station Apple ultra-performante', 2199, 1, 3, 3),
       ('Beelink SER5 Mini PC', 'beelinkser5', 'Mini PC Ryzen compact', 499, 2, 3, 3),

-- 4. Écrans (category_id = 4)
       ('Dell UltraSharp U2723QE', 'dell-u2723qe', 'Écran 27 pouces 4K USB-C', 699, 1, 2, 4),
       ('LG UltraFine 5K', 'lguf5k', 'Écran Retina pour Mac', 1199, 1, 3, 4),
       ('ASUS ProArt PA278CV', 'asusproart', 'Écran pour créateurs 2K', 499, 1, 3, 4),
       ('Samsung Smart Monitor M8', 'samsungm8', 'Écran 32" avec Smart TV intégrée', 649, 1, 2, 4),
       ('BenQ PD2705Q', 'benqpd2705', 'Écran professionnel QHD 27"', 479, 1, 2, 4),
       ('Philips 346B1C', 'philips346', 'Écran ultra large incurvé', 749, 1, 2, 4),
       ('HP E27 G4', 'hpe27g4', 'Écran ergonomique Full HD', 349, 1, 1, 4),
       ('Lenovo ThinkVision P27h', 'thinkvisionp27', 'Écran pro 2560x1440', 439, 2, 3, 4),
       ('AOC Q32P2', 'aocq32', 'Écran QHD 32 pouces', 429, 2, 3, 4),
       ('MSI Optix MAG274QRF-QD', 'msiqd274', 'Écran gaming 165Hz QHD', 479, 2, 1, 4),

-- 5. Accessoires (category_id = 5)
       ('Clavier Logitech MX Keys', 'logimxkeys', 'Clavier sans fil rétroéclairé', 109, 1, 1, 5),
       ('Souris MX Master 3S', 'mxmaster3s', 'Souris ergonomique sans fil', 99, 1, 2, 5),
       ('Dock Thunderbolt 4 CalDigit', 'caldigittb4', 'Station d’accueil Thunderbolt', 329, 2, 1, 5),
       ('Support PC Rain Design', 'raindesign', 'Support en aluminium pour ordinateur', 69, 1, 2, 5),
       ('Webcam Logitech Brio 4K', 'logibrio', 'Webcam UHD avec HDR', 199, 1, 3, 5),
       ('Hub USB-C Anker 8-en-1', 'ankerhub', 'Adaptateur multiports compact', 79, 1, 3, 5),
       ('Casque Jabra Evolve2 65', 'jabraevolve2', 'Casque pro avec micro antibruit', 249, 2, 2, 5),
       ('Tapis de souris SteelSeries QcK', 'qcksteel', 'Tapis de souris gamer antidérapant', 25, 1, 2, 5),
       ('Adaptateur HDMI vers USB-C', 'hdmiusb', 'Adaptateur vidéo universel', 39, 1, 1, 5),
       ('Lecteur de carte SD SanDisk', 'sandiskreader', 'Lecteur haute vitesse USB-C', 29, 1, 1, 5),

-- 6. Imprimantes (category_id = 6)
       ('HP OfficeJet Pro 9025e', 'hp9025e', 'Imprimante jet d’encre tout-en-un', 249, 1, 2, 6),
       ('Canon PIXMA G6050', 'canong6050', 'Imprimante jet d’encre à réservoirs', 279, 1, 1, 6),
       ('Brother HL-L2350DW', 'brother2350', 'Imprimante laser monochrome', 139, 1, 2, 6),
       ('Epson EcoTank ET-2850', 'epsonet2850', 'Imprimante éco avec réservoirs rechargeables', 239, 2, 3, 6),
       ('HP Color LaserJet Pro M255dw', 'hpm255dw', 'Imprimante laser couleur rapide', 319, 1, 3, 6),
       ('Canon i-SENSYS MF445dw', 'canonmf445', 'Multifonction laser noir et blanc', 329, 1, 1, 6),
       ('Brother MFC-L3770CDW', 'brother3770', 'Imprimante laser couleur multifonction', 399, 2, 1, 6),
       ('Epson WorkForce WF-2830DWF', 'epsonwf2830', 'Imprimante compacte multifonction', 99, 1, 2, 6),
       ('Samsung Xpress SL-M2026W', 'samsungxpress', 'Imprimante laser Wi-Fi', 89, 1, 2, 6),
       ('Xerox Phaser 6510', 'xerox6510', 'Imprimante laser couleur professionnelle', 299, 1, 3, 6),

-- 7. Stations de travail (category_id = 7)
       ('Dell Precision 5570', 'precision5570', 'Station mobile haute performance', 2499, 1, 2, 7),
       ('HP ZBook Fury 16 G9', 'zbookfury16', 'Station mobile pour calcul intensif', 2699, 1, 3, 7),
       ('Lenovo ThinkStation P360', 'thinkstationp360', 'Tour puissante pour professionnels', 1799, 1, 2, 7),
       ('Apple Mac Studio M2 Ultra', 'macstudio-ultra', 'Station compacte Apple ultra-puissante', 3699, 1, 1, 7),
       ('Dell OptiPlex 7000', 'optiplex7000', 'Station de travail bureautique', 1399, 1, 2, 7),
       ('HP Z2 Mini G9', 'hpz2mini', 'Mini station compacte pour CAO', 1599, 1, 3, 7),
       ('Lenovo ThinkPad P1 Gen 6', 'thinkpadp1', 'Portable professionnel haut de gamme', 2599, 2, 3, 7),
       ('BOXX APEXX A3', 'boxxa3', 'Station de travail 3D & animation', 4399, 1, 2, 7),
       ('ASUS ProArt Station PD5', 'proartpd5', 'PC créatif puissant pour design', 2199, 2, 1, 7),
       ('HP ZBook Firefly 14', 'zbookfirefly', 'PC mobile pour professionnels en déplacement', 1799, 2, 3, 7),

-- 8. Équipements réseau (category_id = 8)
       ('Routeur TP-Link Archer AX6000', 'tplinkax6000', 'Routeur Wi-Fi 6 haut débit', 299, 1, 1, 8),
       ('Netgear Nighthawk RAX200', 'rax200', 'Routeur Wi-Fi 6 tri-bande', 349, 1, 2, 8),
       ('Google Nest WiFi Pro', 'nestwifipro', 'Routeur maillé intelligent', 229, 1, 3, 8),
       ('Ubiquiti UniFi Dream Machine', 'unifiudm', 'Routeur pro avec sécurité intégrée', 399, 2, 2, 8),
       ('Switch Netgear GS308', 'gs308', 'Switch 8 ports Gigabit', 39, 1, 3, 8),
       ('TP-Link Deco X50', 'decotplink', 'Pack Wi-Fi Mesh pour grandes surfaces', 249, 1, 3, 8),
       ('ASUS RT-AX88U', 'asusax88', 'Routeur gaming Wi-Fi 6', 279, 1, 1, 8),
       ('Synology RT6600ax', 'synort6600', 'Routeur avec sécurité avancée', 349, 1, 2, 8),
       ('D-Link DIR-X5460', 'dirx5460', 'Routeur 6 antennes Wi-Fi 6', 199, 2, 2, 8),
       ('Switch Cisco CBS220-24T', 'ciscocbs220', 'Switch pro 24 ports manageable', 319, 2, 3, 8),

-- 9. Stockage externe (category_id = 9)
       ('SSD Samsung T7 1To', 'samsungt7', 'SSD externe rapide USB-C', 149, 1, 1, 9),
       ('WD My Passport 2To', 'wdpassport2', 'Disque dur portable compact', 109, 1, 2, 9),
       ('SanDisk Extreme Pro SSD 1To', 'sandiskextpro', 'SSD robuste et rapide', 189, 1, 3, 9),
       ('Seagate Expansion Desktop 4To', 'seagate4to', 'Disque dur externe pour backup', 129, 2, 3, 9),
       ('Crucial X9 Pro 2To', 'crucialx9', 'SSD portable à haute vitesse', 179, 1, 2, 9),
       ('Synology DS220+', 'synods220', 'NAS 2 baies performant', 399, 1, 2, 9),
       ('WD Black P50 Game Drive 1To', 'wdblackp50', 'SSD pour jeux haute vitesse', 199, 1, 3, 9),
       ('Toshiba Canvio Basics 1To', 'toshibacanvio', 'Disque dur USB 3.0 pas cher', 59, 1, 1, 9),
       ('Samsung 980 Pro 1To (NVMe)', 'samsung980pro', 'SSD interne ultra rapide', 169, 1, 3, 9),
       ('LaCie Rugged USB-C 2To', 'lacierugged', 'Disque dur résistant pour terrain', 179, 2, 2, 9);





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