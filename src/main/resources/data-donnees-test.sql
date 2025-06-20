INSERT INTO user(firstname, lastname, company, email, password, role, company_address, phone)
VALUES
    ('John', 'Doe', 'Locatech', 'a@a.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', 'Adresse 1', '0612345678'),
    ('Jay', 'White', 'Locatech', 'b@b.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', 'Adresse 2', '0623456789'),
    ('Bob', 'Allen', 'Locatech','c@c.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', 'Adresse 3', '0634567890'),
-- 2 Admin
    ('Alice', 'Dupont', 'Locatech', 'alice.dupont@a.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', '1 rue de la Paix, Paris', '0656789012'),
    ('Marc', 'Lemoine', 'Locatech','marc.lemoine@b.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'ADMIN', '12 avenue des Champs, Paris', '0667890123'),
-- 4 Techs
    ('Léo', 'Martin', 'Locatech','leo.martin@c.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '25 rue Victor Hugo, Lyon', '0678901234'),
    ('Sophie', 'Bernard', 'Locatech', 'sophie.bernard@d.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '10 rue des Fleurs, Marseille', '0689012345'),
    ('Thomas', 'Lemoine', 'Locatech', 'thomas.lemoine@e.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '5 boulevard de la République, Toulouse', '0690123456'),
    ('Julie', 'Robert', 'Locatech','julie.robert@f.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'TECH', '22 rue de la Liberté, Lille', '0601234567'),
-- 6 Clients
    ('Jane', 'Smith', 'Green Solutions', 'jane.smith@j.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '45 Rue de l’Écologie, Lyon', '0667980123'),
    ('Michael', 'Johnson', 'Finance Experts', 'michael.johnson@mj.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '78 Boulevard du Commerce, Marseille', '0679801234'),
    ('Sarah', 'Lee', 'Creative Minds', 'sarah.lee@sl.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '22 Rue des Artistes, Toulouse', '0689012356'),
    ('Emily', 'Davis', 'Health First', 'emily.davis@ed.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '5 Allée de la Santé, Bordeaux', '0690123567'),
    ('Daniel', 'Brown', 'Logistics Pro', 'daniel.brown@db.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '33 Route de la Logistique, Lille', '0601235678'),
    ('Lisa', 'Wilson', 'EduTech', 'lisa.wilson@lw.com', '$2a$10$rW2iq9f1qYIo7975JE0hIOQtSqBZ3TGpyLlRPDES4RbyijtpXDb4C', 'CLIENT', '14 Rue de l’Éducation, Nantes', '0612356789');


-- état du matériel
INSERT INTO etat (id, name)
VALUES (1, 'disponible'),
       (2, 'loué'),
       (3, 'en retard'),
       (4, 'en réparation');

-- catégories des équipements
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

INSERT INTO product (name, code, description, price, etat_id, creator_id, category_id, image_name)
-- 1. Ordinateurs portables (category_id = 1)
VALUES ('MacBook Pro M2', 'mbp-m2', 'Ordinateur portable Apple 16 pouces', 2499, 1, 1, 1, null),
       ('Dell XPS 15', 'dellxps15', 'Ultrabook performant pour professionnels', 1899, 1, 1, 1, null),
       ('ThinkPad X1 Carbon', 'thinkpadx1', 'PC portable léger et puissant', 1599, 2, 1, 1, null),
       ('Surface Laptop Studio', 'surface-studio', 'PC hybride Microsoft', 1699, 2, 2, 1, null),
       ('HP Spectre x360', 'hpspectre', 'Ultrabook tactile polyvalent', 1499, 3, 2, 1, null),
       ('Lenovo Yoga Slim 7', 'lenovoyoga7', 'Ultrabook compact et puissant', 1299, 1, 1, 1, null),
       ('HP Envy 13', 'hpenvy13', 'Portable polyvalent avec bon rapport qualité-prix', 1099, 1, 1, 1, null),
       ('ASUS ZenBook 14 OLED', 'asuszen14', 'Ultrabook avec écran OLED', 999, 1, 2, 1, null),
       ('Acer Swift 5', 'acerswift5', 'Ultrabook léger et autonome', 899, 1, 2, 1, null),
       ('Microsoft Surface Laptop 5', 'surfacelap5', 'PC portable Microsoft haut de gamme', 1399, 2, 2, 1, null),
       ('Razer Blade 15', 'razerblade15', 'Portable gamer puissant', 2199, 2, 3, 1, null),
       ('LG Gram 17', 'lggram17', 'Portable ultraléger avec grand écran', 1699, 1, 3, 1, null),

-- 2. Tablettes (category_id = 2)

       ('iPad Pro 12.9', 'ipadpro12', 'Tablette haut de gamme pour créatifs', 1399, 1, 2, 2, null),
       ('iPad Air 5', 'ipadair5', 'Tablette polyvalente Apple', 799, 1, 2, 2, null),
       ('Samsung Galaxy Tab S9', 'galaxytabs9', 'Tablette Android haut de gamme', 949, 2, 1, 2, null),
       ('Microsoft Surface Pro 9', 'surfacepro9', 'Tablette hybride Windows', 1199, 1, 1, 2, null),
       ('Lenovo Tab P12 Pro', 'lenovotabp12', 'Tablette Android professionnelle', 699, 2, 3, 2, null),
       ('Huawei MatePad Pro', 'matepadpro', 'Tablette fluide avec stylet', 699, 1, 2, 2, null),
       ('iPad Mini 6', 'ipadmini6', 'Tablette compacte Apple', 659, 1, 1, 2, null),
       ('Samsung Galaxy Tab A8', 'tab-a8', 'Tablette Android pour usage léger', 249, 1, 3, 2, null),
       ('Wacom MobileStudio Pro', 'wacompro', 'Tablette graphique autonome', 1899, 2, 3, 2, null),
       ('Lenovo Yoga Tab 13', 'yogatab13', 'Tablette avec kickstand intégré', 649, 1, 2, 2, null),

-- 3. PC fixes (category_id = 3)
       ('Mac Mini M2', 'macmini-m2', 'Mini PC performant Apple', 799, 1, 3, 3, null),
       ('iMac 24 pouces M1', 'imac24', 'Tout-en-un Apple pour bureautique et graphisme', 1799, 1, 2, 3, null),
       ('HP Pavilion Gaming Desktop', 'hppavgaming', 'PC de bureau gaming d’entrée de gamme', 1099, 1, 2, 3, null),
       ('Dell Inspiron 3891', 'dell3891', 'Tour bureautique performante', 749, 1, 3, 3, null),
       ('ASUS ExpertCenter D7', 'asusexpcenter', 'PC fixe professionnel', 899, 2, 3, 3, null),
       ('Intel NUC 12 Pro', 'intelnuc12', 'Mini PC compact', 699, 1, 1, 3, null),
       ('Acer Veriton Essential', 'acerveriton', 'PC bureautique pour PME', 599, 1, 1, 3, null),
       ('Lenovo IdeaCentre 5', 'ideacentre5', 'PC de bureau tout-en-un', 849, 2, 2, 3, null),
       ('Mac Studio M2 Max', 'macstudio', 'Station Apple ultra-performante', 2199, 1, 3, 3, null),
       ('Beelink SER5 Mini PC', 'beelinkser5', 'Mini PC Ryzen compact', 499, 2, 3, 3, null),

-- 4. Écrans (category_id = 4)
       ('Dell UltraSharp U2723QE', 'dell-u2723qe', 'Écran 27 pouces 4K USB-C', 699, 1, 2, 4, null),
       ('LG UltraFine 5K', 'lguf5k', 'Écran Retina pour Mac', 1199, 1, 3, 4, null),
       ('ASUS ProArt PA278CV', 'asusproart', 'Écran pour créateurs 2K', 499, 1, 3, 4, null),
       ('Samsung Smart Monitor M8', 'samsungm8', 'Écran 32" avec Smart TV intégrée', 649, 1, 2, 4, null),
       ('BenQ PD2705Q', 'benqpd2705', 'Écran professionnel QHD 27"', 479, 1, 2, 4, null),
       ('Philips 346B1C', 'philips346', 'Écran ultra large incurvé', 749, 1, 2, 4, null),
       ('HP E27 G4', 'hpe27g4', 'Écran ergonomique Full HD', 349, 1, 1, 4, null),
       ('Lenovo ThinkVision P27h', 'thinkvisionp27', 'Écran pro 2560x1440', 439, 2, 3, 4, null),
       ('AOC Q32P2', 'aocq32', 'Écran QHD 32 pouces', 429, 2, 3, 4, null),
       ('MSI Optix MAG274QRF-QD', 'msiqd274', 'Écran gaming 165Hz QHD', 479, 2, 1, 4, null),

-- 5. Accessoires (category_id = 5)
       ('Clavier Logitech MX Keys', 'logimxkeys', 'Clavier sans fil rétroéclairé', 109, 1, 1, 5, null),
       ('Souris MX Master 3S', 'mxmaster3s', 'Souris ergonomique sans fil', 99, 1, 2, 5, null),
       ('Dock Thunderbolt 4 CalDigit', 'caldigittb4', 'Station d’accueil Thunderbolt', 329, 2, 1, 5, null),
       ('Support PC Rain Design', 'raindesign', 'Support en aluminium pour ordinateur', 69, 1, 2, 5, null),
       ('Webcam Logitech Brio 4K', 'logibrio', 'Webcam UHD avec HDR', 199, 1, 3, 5, null),
       ('Hub USB-C Anker 8-en-1', 'ankerhub', 'Adaptateur multiports compact', 79, 1, 3, 5, null),
       ('Casque Jabra Evolve2 65', 'jabraevolve2', 'Casque pro avec micro antibruit', 249, 2, 2, 5, null),
       ('Tapis de souris SteelSeries QcK', 'qcksteel', 'Tapis de souris gamer antidérapant', 25, 1, 2, 5, null),
       ('Adaptateur HDMI vers USB-C', 'hdmiusb', 'Adaptateur vidéo universel', 39, 1, 1, 5, null),
       ('Lecteur de carte SD SanDisk', 'sandiskreader', 'Lecteur haute vitesse USB-C', 29, 1, 1, 5, null),

-- 6. Imprimantes (category_id = 6)
       ('HP OfficeJet Pro 9025e', 'hp9025e', 'Imprimante jet d’encre tout-en-un', 249, 1, 2, 6, null),
       ('Canon PIXMA G6050', 'canong6050', 'Imprimante jet d’encre à réservoirs', 279, 1, 1, 6, null),
       ('Brother HL-L2350DW', 'brother2350', 'Imprimante laser monochrome', 139, 1, 2, 6, null),
       ('Epson EcoTank ET-2850', 'epsonet2850', 'Imprimante éco avec réservoirs rechargeables', 239, 2, 3, 6, null),
       ('HP Color LaserJet Pro M255dw', 'hpm255dw', 'Imprimante laser couleur rapide', 319, 1, 3, 6, null),
       ('Canon i-SENSYS MF445dw', 'canonmf445', 'Multifonction laser noir et blanc', 329, 1, 1, 6, null),
       ('Brother MFC-L3770CDW', 'brother3770', 'Imprimante laser couleur multifonction', 399, 2, 1, 6, null),
       ('Epson WorkForce WF-2830DWF', 'epsonwf2830', 'Imprimante compacte multifonction', 99, 1, 2, 6, null),
       ('Samsung Xpress SL-M2026W', 'samsungxpress', 'Imprimante laser Wi-Fi', 89, 1, 2, 6, null),
       ('Xerox Phaser 6510', 'xerox6510', 'Imprimante laser couleur professionnelle', 299, 1, 3, 6, null),

-- 7. Stations de travail (category_id = 7)
       ('Dell Precision 5570', 'precision5570', 'Station mobile haute performance', 2499, 1, 2, 7, null),
       ('HP ZBook Fury 16 G9', 'zbookfury16', 'Station mobile pour calcul intensif', 2699, 1, 3, 7, null),
       ('Lenovo ThinkStation P360', 'thinkstationp360', 'Tour puissante pour professionnels', 1799, 1, 2, 7, null),
       ('Apple Mac Studio M2 Ultra', 'macstudio-ultra', 'Station compacte Apple ultra-puissante', 3699, 1, 1, 7, null),
       ('Dell OptiPlex 7000', 'optiplex7000', 'Station de travail bureautique', 1399, 1, 2, 7, null),
       ('HP Z2 Mini G9', 'hpz2mini', 'Mini station compacte pour CAO', 1599, 1, 3, 7, null),
       ('Lenovo ThinkPad P1 Gen 6', 'thinkpadp1', 'Portable professionnel haut de gamme', 2599, 2, 3, 7, null),
       ('BOXX APEXX A3', 'boxxa3', 'Station de travail 3D & animation', 4399, 1, 2, 7, null),
       ('ASUS ProArt Station PD5', 'proartpd5', 'PC créatif puissant pour design', 2199, 2, 1, 7, null),
       ('HP ZBook Firefly 14', 'zbookfirefly', 'PC mobile pour professionnels en déplacement', 1799, 2, 3, 7, null),

-- 8. Équipements réseau (category_id = 8)
       ('Routeur TP-Link Archer AX6000', 'tplinkax6000', 'Routeur Wi-Fi 6 haut débit', 299, 1, 1, 8, null),
       ('Netgear Nighthawk RAX200', 'rax200', 'Routeur Wi-Fi 6 tri-bande', 349, 1, 2, 8, null),
       ('Google Nest WiFi Pro', 'nestwifipro', 'Routeur maillé intelligent', 229, 1, 3, 8, null),
       ('Ubiquiti UniFi Dream Machine', 'unifiudm', 'Routeur pro avec sécurité intégrée', 399, 2, 2, 8, null),
       ('Switch Netgear GS308', 'gs308', 'Switch 8 ports Gigabit', 39, 1, 3, 8, null),
       ('TP-Link Deco X50', 'decotplink', 'Pack Wi-Fi Mesh pour grandes surfaces', 249, 1, 3, 8, null),
       ('ASUS RT-AX88U', 'asusax88', 'Routeur gaming Wi-Fi 6', 279, 1, 1, 8, null),
       ('Synology RT6600ax', 'synort6600', 'Routeur avec sécurité avancée', 349, 1, 2, 8, null),
       ('D-Link DIR-X5460', 'dirx5460', 'Routeur 6 antennes Wi-Fi 6', 199, 2, 2, 8, null),
       ('Switch Cisco CBS220-24T', 'ciscocbs220', 'Switch pro 24 ports manageable', 319, 2, 3, 8, null),

-- 9. Stockage externe (category_id = 9)
       ('SSD Samsung T7 1To', 'samsungt7', 'SSD externe rapide USB-C', 149, 1, 1, 9, null),
       ('WD My Passport 2To', 'wdpassport2', 'Disque dur portable compact', 109, 1, 2, 9, null),
       ('SanDisk Extreme Pro SSD 1To', 'sandiskextpro', 'SSD robuste et rapide', 189, 1, 3, 9, null),
       ('Seagate Expansion Desktop 4To', 'seagate4to', 'Disque dur externe pour backup', 129, 2, 3, 9, null),
       ('Crucial X9 Pro 2To', 'crucialx9', 'SSD portable à haute vitesse', 179, 1, 2, 9, null),
       ('Synology DS220+', 'synods220', 'NAS 2 baies performant', 399, 1, 2, 9, null),
       ('WD Black P50 Game Drive 1To', 'wdblackp50', 'SSD pour jeux haute vitesse', 199, 1, 3, 9, null),
       ('Toshiba Canvio Basics 1To', 'toshibacanvio', 'Disque dur USB 3.0 pas cher', 59, 1, 1, 9, null),
       ('Samsung 980 Pro 1To (NVMe)', 'samsung980pro', 'SSD interne ultra rapide', 169, 1, 3, 9, null),
       ('LaCie Rugged USB-C 2To', 'lacierugged', 'Disque dur résistant pour terrain', 179, 2, 2, 9, null);




