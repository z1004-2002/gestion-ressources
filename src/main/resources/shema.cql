CREATE KEYSPACE IF NOT EXISTS yowyobdb
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE yowyobdb;
-- Création de la table ressources
CREATE TABLE ressources (
    id TEXT PRIMARY KEY,                -- Identifiant unique de la ressource
    name TEXT,                          -- Nom de la ressource
    description TEXT,                   -- Description de la ressource
    domainKey TEXT,                     -- Domaine clé associé à la ressource
    quantity INT,                       -- Quantité disponible
    buyingPrice DECIMAL,                -- Prix d'achat
    sellingPrice DECIMAL,               -- Prix de vente
    idOwner TEXT,                       -- Identifiant du propriétaire
    numberUsage INT,                    -- Nombre d'utilisations
    transferable BOOLEAN,               -- Indique si la ressource est transférable
    maxReservation INT,                 -- Nombre maximum de réservations
    state TEXT                          -- État de la ressource (stocké comme texte)
);

-- Création de la table images
CREATE TABLE images (
    id TEXT PRIMARY KEY,                -- Identifiant unique de l'image
    name TEXT,                          -- Nom de l'image
    path TEXT,                          -- Chemin où l'image est stockée
    size BIGINT,                        -- Taille de l'image (en octets)
    fileType TEXT,                      -- Type de fichier (par ex : "image/png")
    idRessource TEXT                    -- Identifiant de la ressource associée
);
