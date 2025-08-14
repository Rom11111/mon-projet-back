-- ADMIN / CLIENT / TECH
INSERT INTO user(firstname, lastname, company, email, password, role, company_address, phone, user_status)
VALUES
    -- 1 Admin
    ('John', 'Doe', 'Locatech', 'a@a.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', 'Adresse 1', '0612345678', 'ACTIVE'),
    ('Jay', 'White', 'Locatech', 'b@b.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', 'Adresse 2', '0623456789', 'ACTIVE'),
    ('Bob', 'Allen', 'Locatech','c@c.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', 'Adresse 3', '0634567890', 'ACTIVE'),
    -- 2 Admin
    ('Alice', 'Dupont', 'Locatech', 'alice.dupont@a.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', '1 rue de la Paix, Paris', '0656789012', 'ACTIVE'),
    ('Marc', 'Lemoine', 'Locatech','marc.lemoine@b.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', '12 avenue des Champs, Paris', '0667890123', 'ACTIVE'),
    -- 4 Techs
    ('Léo', 'Martin', 'Locatech','leo.martin@c.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '25 rue Victor Hugo, Lyon', '0678901234', 'ACTIVE'),
    ('Sophie', 'Bernard', 'Locatech', 'sophie.bernard@d.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '10 rue des Fleurs, Marseille', '0689012345', 'ACTIVE'),
    ('Thomas', 'Lemoine', 'Locatech', 'thomas.lemoine@e.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '5 boulevard de la République, Toulouse', '0690123456', 'ACTIVE'),
    ('Julie', 'Robert', 'Locatech','julie.robert@f.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '22 rue de la Liberté, Lille', '0601234567', 'ACTIVE'),
    -- 6 Clients
    ('Jane', 'Smith', 'Green Solutions', 'jane.smith@j.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '45 Rue de l’Écologie, Lyon', '0667980123', 'ACTIVE'),
    ('Michael', 'Johnson', 'Finance Experts', 'michael.johnson@mj.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '78 Boulevard du Commerce, Marseille', '0679801234', 'ACTIVE'),
    ('Sarah', 'Lee', 'Creative Minds', 'sarah.lee@sl.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '22 Rue des Artistes, Toulouse', '0689012356', 'ACTIVE'),
    ('Emily', 'Davis', 'Health First', 'emily.davis@ed.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '5 Allée de la Santé, Bordeaux', '0690123567', 'ACTIVE'),
    ('Daniel', 'Brown', 'Logistics Pro', 'daniel.brown@db.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '33 Route de la Logistique, Lille', '0601235678', 'ACTIVE'),
    ('Lisa', 'Wilson', 'EduTech', 'lisa.wilson@lw.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '14 Rue de l’Éducation, Nantes', '0612356789', 'ACTIVE');


-- état du matériel
INSERT INTO etat (id, name)
VALUES (1, 'disponible'),
       (2, 'loué'),
       (3, 'en retard'),
       (4, 'en réparation');

-- catégories des équipements
INSERT INTO category (name)
VALUES ('Ordinateurs portables'),
       ('Tablettes'),
       ('PC fixes'),
       ('Écrans'),
       ('Accessoires'),
       ('Imprimantes'),
       ('Stations de travail'),
       ('Réseau et connectivité'),
       ('Stockage');

INSERT INTO product (name, code, description, price, etat_id, creator_id, category_id, image_name, available, stock)
-- 1. Ordinateurs portables (category_id = 1)
VALUES ('MacBook Pro M2', 'mbp-m2', 'Ordinateur portable Apple 16 pouces', 37, 1, 1, 1, null, true, 91),
       ('Dell XPS 15', 'dellxps15', 'Ultrabook performant pour professionnels', 28, 1, 1, 1, null, true, 74),
       ('ThinkPad X1 Carbon', 'thinkpadx1', 'PC portable léger et puissant', 24, 2, 1, 1, null, true, 86),
       ('Surface Laptop Studio', 'surface-studio', 'PC hybride Microsoft', 25, 2, 2, 1, null, true, 58),
       ('HP Spectre x360', 'hpspectre', 'Ultrabook tactile polyvalent', 22, 3, 2, 1, null, true, 64),
       ('Lenovo Yoga Slim 7', 'lenovoyoga7', 'Ultrabook compact et puissant', 19, 1, 1, 1, null, true, 79),
       ('HP Envy 13', 'hpenvy13', 'Portable polyvalent avec bon rapport qualité-prix', 16, 1, 1, 1, null, true, 83),
       ('ASUS ZenBook 14 OLED', 'asuszen14', 'Ultrabook avec écran OLED', 15, 1, 2, 1, null, true, 69),
       ('Acer Swift 5', 'acerswift5', 'Ultrabook léger et autonome', 13, 1, 2, 1, null, true, 62),
       ('Microsoft Surface Laptop 5', 'surfacelap5', 'PC portable Microsoft haut de gamme', 21, 2, 2, 1, null, true, 72),
       ('Razer Blade 15', 'razerblade15', 'Portable gamer puissant', 33, 2, 3, 1, null, true, 97),
       ('LG Gram 17', 'lggram17', 'Portable ultraléger avec grand écran', 25, 1, 3, 1, null, true, 85),

-- 2. Tablettes (category_id = 2)
       ('iPad Pro 12.9', 'ipadpro12', 'Tablette haut de gamme pour créatifs', 21, 1, 2, 2, null, true, 77),
       ('iPad Air 5', 'ipadair5', 'Tablette polyvalente Apple', 12, 1, 2, 2, null, true, 63),
       ('Samsung Galaxy Tab S9', 'galaxytabs9', 'Tablette Android haut de gamme', 14, 2, 1, 2, null, true, 81),
       ('Microsoft Surface Pro 9', 'surfacepro9', 'Tablette hybride Windows', 18, 1, 1, 2, null, true, 70),
       ('Lenovo Tab P12 Pro', 'lenovotabp12', 'Tablette Android professionnelle', 10, 2, 3, 2, null, true, 54),
       ('Huawei MatePad Pro', 'matepadpro', 'Tablette fluide avec stylet', 10, 1, 2, 2, null, true, 89),
       ('iPad Mini 6', 'ipadmini6', 'Tablette compacte Apple', 10, 1, 1, 2, null, true, 68),
       ('Samsung Galaxy Tab A8', 'tab-a8', 'Tablette Android pour usage léger', 4, 1, 3, 2, null, true, 59),
       ('Wacom MobileStudio Pro', 'wacompro', 'Tablette graphique autonome', 28, 2, 3, 2, null, true, 93),
       ('Lenovo Yoga Tab 13', 'yogatab13', 'Tablette avec kickstand intégré', 10, 1, 2, 2, null, true, 74),

-- 3. PC fixes (category_id = 3)
       ('Mac Mini M2', 'macmini-m2', 'Mini PC performant Apple', 12, 1, 3, 3, null, true, 68),
       ('iMac 24 pouces M1', 'imac24', 'Tout-en-un Apple pour bureautique et graphisme', 27, 1, 2, 3, null, true, 58),
       ('HP Pavilion Gaming Desktop', 'hppavgaming', 'PC de bureau gaming d’entrée de gamme', 16, 1, 2, 3, null, true, 84),
       ('Dell Inspiron 3891', 'dell3891', 'Tour bureautique performante', 11, 1, 3, 3, null, true, 51),
       ('ASUS ExpertCenter D7', 'asusexpcenter', 'PC fixe professionnel', 13, 2, 3, 3, null, true, 61),
       ('Intel NUC 12 Pro', 'intelnuc12', 'Mini PC compact', 10, 1, 1, 3, null, true, 79),
       ('Acer Veriton Essential', 'acerveriton', 'PC bureautique pour PME', 9, 1, 1, 3, null, true, 65),
       ('Lenovo IdeaCentre 5', 'ideacentre5', 'PC de bureau tout-en-un', 13, 2, 2, 3, null, true, 91),
       ('Mac Studio M2 Max', 'macstudio', 'Station Apple ultra-performante', 33, 1, 3, 3, null, true, 88),
       ('Beelink SER5 Mini PC', 'beelinkser5', 'Mini PC Ryzen compact', 7, 2, 3, 3, null, true, 69),

-- 4. Écrans (category_id = 4)
       ('Dell UltraSharp U2723QE', 'dell-u2723qe', 'Écran 27 pouces 4K USB-C', 10, 1, 2, 4, null, true, 92),
       ('LG UltraFine 5K', 'lguf5k', 'Écran Retina pour Mac', 18, 1, 3, 4, null, true, 81),
       ('ASUS ProArt PA278CV', 'asusproart', 'Écran pour créateurs 2K', 7, 1, 3, 4, null, true, 74),
       ('Samsung Smart Monitor M8', 'samsungm8', 'Écran 32" avec Smart TV intégrée', 10, 1, 2, 4, null, true, 87),
       ('BenQ PD2705Q', 'benqpd2705', 'Écran professionnel QHD 27"', 7, 1, 2, 4, null, true, 66),
       ('Philips 346B1C', 'philips346', 'Écran ultra large incurvé', 11, 1, 2, 4, null, true, 76),
       ('HP E27 G4', 'hpe27g4', 'Écran ergonomique Full HD', 5, 1, 1, 4, null, true, 59),
       ('Lenovo ThinkVision P27h', 'thinkvisionp27', 'Écran pro 2560x1440', 7, 2, 3, 4, null, true, 71),
       ('AOC Q32P2', 'aocq32', 'Écran QHD 32 pouces', 6, 2, 3, 4, null, true, 63),
       ('MSI Optix MAG274QRF-QD', 'msiqd274', 'Écran gaming 165Hz QHD', 7, 2, 1, 4, null, true, 88),

-- 5. Accessoires (category_id = 5)
       ('Clavier Logitech MX Keys', 'logimxkeys', 'Clavier sans fil rétroéclairé', 2, 1, 1, 5, null, true, 97),
       ('Souris MX Master 3S', 'mxmaster3s', 'Souris ergonomique sans fil', 1, 1, 2, 5, null, true, 81),
       ('Dock Thunderbolt 4 CalDigit', 'caldigittb4', 'Station d’accueil Thunderbolt', 5, 2, 1, 5, null, true, 64),
       ('Support PC Rain Design', 'raindesign', 'Support en aluminium pour ordinateur', 1, 1, 2, 5, null, true, 73),
       ('Webcam Logitech Brio 4K', 'logibrio', 'Webcam UHD avec HDR', 3, 1, 3, 5, null, true, 58),
       ('Hub USB-C Anker 8-en-1', 'ankerhub', 'Adaptateur multiports compact', 1, 1, 3, 5, null, true, 61),
       ('Casque Jabra Evolve2 65', 'jabraevolve2', 'Casque pro avec micro antibruit', 4, 2, 2, 5, null, true, 69),
       ('Tapis de souris SteelSeries QcK', 'qcksteel', 'Tapis de souris gamer antidérapant', 1, 1, 2, 5, null, true, 86),
       ('Adaptateur HDMI vers USB-C', 'hdmiusb', 'Adaptateur vidéo universel', 1, 1, 1, 5, null, true, 75),
       ('Lecteur de carte SD SanDisk', 'sandiskreader', 'Lecteur haute vitesse USB-C', 1, 1, 1, 5, null, true, 70),

-- 6. Imprimantes (category_id = 6)
       ('HP OfficeJet Pro 9025e', 'hp9025e', 'Imprimante jet d’encre tout-en-un', 4, 1, 2, 6, null, true, 82),
       ('Canon PIXMA G6050', 'canong6050', 'Imprimante jet d’encre à réservoirs', 4, 1, 1, 6, null, true, 78),
       ('Brother HL-L2350DW', 'brother2350', 'Imprimante laser monochrome', 2, 1, 2, 6, null, true, 55),
       ('Epson EcoTank ET-2850', 'epsonet2850', 'Imprimante éco avec réservoirs rechargeables', 4, 2, 3, 6, null, true, 93),
       ('HP Color LaserJet Pro M255dw', 'hpm255dw', 'Imprimante laser couleur rapide', 5, 1, 3, 6, null, true, 73),
       ('Canon i-SENSYS MF445dw', 'canonmf445', 'Multifonction laser noir et blanc', 5, 1, 1, 6, null, true, 69),
       ('Brother MFC-L3770CDW', 'brother3770', 'Imprimante laser couleur multifonction', 6, 2, 1, 6, null, true, 77),
       ('Epson WorkForce WF-2830DWF', 'epsonwf2830', 'Imprimante compacte multifonction', 1, 1, 2, 6, null, true, 60),
       ('Samsung Xpress SL-M2026W', 'samsungxpress', 'Imprimante laser Wi-Fi', 1, 1, 2, 6, null, true, 52),
       ('Xerox Phaser 6510', 'xerox6510', 'Imprimante laser couleur professionnelle', 4, 1, 3, 6, null, true, 80),

-- 7. Stations de travail (category_id = 7)
       ('Dell Precision 5570', 'precision5570', 'Station mobile haute performance', 37, 1, 2, 7, null, true, 94),
       ('HP ZBook Fury 16 G9', 'zbookfury16', 'Station mobile pour calcul intensif', 40, 1, 3, 7, null, true, 87),
       ('Lenovo ThinkStation P360', 'thinkstationp360', 'Tour puissante pour professionnels', 27, 1, 2, 7, null, true, 79),
       ('Apple Mac Studio M2 Ultra', 'macstudio-ultra', 'Station compacte Apple ultra-puissante', 55, 1, 1, 7, null, true, 84),
       ('Dell OptiPlex 7000', 'optiplex7000', 'Station de travail bureautique', 21, 1, 2, 7, null, true, 71),
       ('HP Z2 Mini G9', 'hpz2mini', 'Mini station compacte pour CAO', 24, 1, 3, 7, null, true, 67),
       ('Lenovo ThinkPad P1 Gen 6', 'thinkpadp1', 'Portable professionnel haut de gamme', 39, 2, 3, 7, null, true, 92),
       ('BOXX APEXX A3', 'boxxa3', 'Station de travail 3D & animation', 66, 1, 2, 7, null, true, 97),
       ('ASUS ProArt Station PD5', 'proartpd5', 'PC créatif puissant pour design', 33, 2, 1, 7, null, true, 65),
       ('HP ZBook Firefly 14', 'zbookfirefly', 'PC mobile pour professionnels en déplacement', 27, 2, 3, 7, null, true, 74),

-- 8. Équipements réseau (category_id = 8)
       ('Routeur TP-Link Archer AX6000', 'tplinkax6000', 'Routeur Wi-Fi 6 haut débit', 4, 1, 1, 8, null, true, 89),
       ('Netgear Nighthawk RAX200', 'rax200', 'Routeur Wi-Fi 6 tri-bande', 5, 1, 2, 8, null, true, 78),
       ('Google Nest WiFi Pro', 'nestwifipro', 'Routeur maillé intelligent', 3, 1, 3, 8, null, true, 72),
       ('Ubiquiti UniFi Dream Machine', 'unifiudm', 'Routeur pro avec sécurité intégrée', 6, 2, 2, 8, null, true, 83),
       ('Switch Netgear GS308', 'gs308', 'Switch 8 ports Gigabit', 1, 1, 3, 8, null, true, 54),
       ('TP-Link Deco X50', 'decotplink', 'Pack Wi-Fi Mesh pour grandes surfaces', 4, 1, 3, 8, null, true, 69),
       ('ASUS RT-AX88U', 'asusax88', 'Routeur gaming Wi-Fi 6', 4, 1, 1, 8, null, true, 60),
       ('Synology RT6600ax', 'synort6600', 'Routeur avec sécurité avancée', 5, 1, 2, 8, null, true, 82),
       ('D-Link DIR-X5460', 'dirx5460', 'Routeur 6 antennes Wi-Fi 6', 3, 2, 2, 8, null, true, 70),
       ('Switch Cisco CBS220-24T', 'ciscocbs220', 'Switch pro 24 ports manageable', 5, 2, 3, 8, null, true, 76),

-- 9. Stockage externe (category_id = 9)
       ('SSD Samsung T7 1To', 'samsungt7', 'SSD externe rapide USB-C', 2, 1, 1, 9, null, true, 85),
       ('WD My Passport 2To', 'wdpassport2', 'Disque dur portable compact', 2, 1, 2, 9, null, true, 79),
       ('SanDisk Extreme Pro SSD 1To', 'sandiskextpro', 'SSD robuste et rapide', 3, 1, 3, 9, null, true, 66),
       ('Seagate Expansion Desktop 4To', 'seagate4to', 'Disque dur externe pour backup', 2, 2, 3, 9, null, true, 58),
       ('Crucial X9 Pro 2To', 'crucialx9', 'SSD portable à haute vitesse', 3, 1, 2, 9, null, true, 62),
       ('Synology DS220+', 'synods220', 'NAS 2 baies performant', 6, 1, 2, 9, null, true, 73),
       ('WD Black P50 Game Drive 1To', 'wdblackp50', 'SSD pour jeux haute vitesse', 3, 1, 3, 9, null, true, 69),
       ('Toshiba Canvio Basics 1To', 'toshibacanvio', 'Disque dur USB 3.0 pas cher', 1, 1, 1, 9, null, true, 77),
       ('Samsung 980 Pro 1To (NVMe)', 'samsung980pro', 'SSD interne ultra rapide', 3, 1, 3, 9, null, true, 91),
       ('LaCie Rugged USB-C 2To', 'lacierugged', 'Disque dur résistant pour terrain', 3, 2, 2, 9, null, true, 80);



-- On crée une location test (rental) pour le client 2 sur le produit 1
INSERT INTO rental (product_id, client_id, start_date, end_date, status, created_at, quantity)
VALUES (1, 2, '2025-08-01', '2025-08-05', 'APPROVED', NOW(), 1);

-- Puis on crée un report lié à ce rental (ici l'id = 1 car c'est la première location créée)
INSERT INTO reports (rental_id, reported_by_id, description, status, created_at)
VALUES (1, 2, 'Le produit ne fonctionne plus', 'OPEN', NOW());
