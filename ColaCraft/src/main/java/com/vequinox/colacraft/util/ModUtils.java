package com.vequinox.colacraft.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.google.common.base.CaseFormat;

import net.minecraft.util.text.translation.I18n;

public class ModUtils {
    public static String toUnlocalizedName(String name) {
        return StringUtils.uncapitalize(WordUtils.capitalize(name, '_'))
        		.replace("_", "")
        		.replace(Reference.MOD_ID + ".", "_")
        		.replace("item.", "")
        		.replace("tile.", "")
        		.replace(":", ".");
    }

    public static String toRegistryName(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name).replace(".", "_");
    }

    public static String toCamelCase(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, name).replace("_", "");
    }

    public static String format(String key) {
        return I18n.translateToLocal(key);
    }

    public static String format(String key, Object... objects) {
        return I18n.translateToLocalFormatted(key, objects);
    }

    public static boolean hasKey(String key) {
        return I18n.canTranslate(key);
    }
}
