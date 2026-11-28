USE skillup_db;

-- La BD del proyecto ya fue migrada. Este script es solo de verificación;
-- no modifica ni vuelve a crear columnas.
SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('usuario', 'perfil_tutor', 'tutorias')
  AND COLUMN_NAME IN ('foto', 'foto_tipo')
ORDER BY TABLE_NAME, COLUMN_NAME;
