package com.SkillUp.Backend.util;

import java.util.Base64;

/** Convierte Data URL del frontend a bytes binarios para persistir en MEDIUMBLOB. */
public final class FotoUtils {
    private FotoUtils() {}
    public record Foto(byte[] bytes, String contentType) {}

    public static Foto desdeDataUrl(String dataUrl) {
        if (dataUrl == null || dataUrl.isBlank()) return null;
        if (!dataUrl.startsWith("data:")) throw new IllegalArgumentException("La fotografía debe enviarse como Data URL Base64");
        int separador = dataUrl.indexOf(',');
        if (separador < 0) throw new IllegalArgumentException("Formato de fotografía inválido");
        String metadata = dataUrl.substring(5, separador);
        if (!metadata.toLowerCase().contains(";base64")) throw new IllegalArgumentException("La fotografía debe estar codificada en Base64");
        String contentType = metadata.substring(0, metadata.indexOf(';')).toLowerCase();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/webp") && !contentType.equals("image/gif"))
            throw new IllegalArgumentException("Formato permitido: JPG, PNG, WebP o GIF");
        byte[] bytes;
        try { bytes = Base64.getDecoder().decode(dataUrl.substring(separador + 1)); }
        catch (IllegalArgumentException ex) { throw new IllegalArgumentException("La fotografía Base64 no es válida", ex); }
        if (bytes.length > 5 * 1024 * 1024) throw new IllegalArgumentException("La fotografía no puede superar 5 MB");
        return new Foto(bytes, contentType);
    }

    public static String aDataUrl(byte[] bytes, String contentType) {
        if (bytes == null || bytes.length == 0) return null;
        String tipo = (contentType == null || contentType.isBlank()) ? "image/jpeg" : contentType;
        return "data:" + tipo + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }
}
