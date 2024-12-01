CREATE KEYSPACE IF NOT EXISTS yowyobdb
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE yowyobdb;

CREATE TABLE IF NOT EXISTS reservations (
    id TEXT PRIMARY KEY,
    idReserver TEXT,
    idRessource TEXT,
    quantity INT
);

CREATE TABLE IF NOT EXISTS images (
    idRessource TEXT,
    id TEXT,
    name TEXT,
    path TEXT,
    size BIGINT,
    fileType TEXT,
    PRIMARY KEY (idRessource, id)
);
CREATE TABLE IF NOT EXISTS ressources (
    id TEXT PRIMARY KEY,               -- Clé primaire
    name TEXT,                         -- Nom de la ressource
    description TEXT,                  -- Description de la ressource
    quantity INT,                      -- Quantité disponible
    buying_price DECIMAL,              -- Prix d'achat
    selling_price DECIMAL,             -- Prix de vente
    id_owner TEXT,                     -- Identifiant du propriétaire
    number_usage INT,                  -- Nombre d'utilisations
    transferable BOOLEAN,              -- Ressource transférable ou non
    max_reservation INT,               -- Réservation maximale
    state TEXT                         -- État de la ressource (Enum)
);
