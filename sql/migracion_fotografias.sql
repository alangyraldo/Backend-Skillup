-- Ejecutar una sola vez sobre skillup_db si las tablas ya existen.
-- Las fotografías se almacenan como Data URL (Base64) en LONGTEXT.
USE skillup_db;

ALTER TABLE usuario
    ADD COLUMN foto LONGTEXT NULL;

ALTER TABLE perfil_tutor
    MODIFY COLUMN foto LONGTEXT NULL;

ALTER TABLE tutorias
    ADD COLUMN foto LONGTEXT NULL;
