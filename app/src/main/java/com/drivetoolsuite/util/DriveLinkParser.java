package com.drivetoolsuite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pure-Java port of the JavaScript regex logic from the original HTML app.
 *
 * 1) extractDriveFileIds  -> the JavaScript /driveRegex/ that finds Google Drive
 *    file IDs inside arbitrary text. The pattern is character-for-character
 *    equivalent to the original so behaviour (including its edge cases) is
 *    preserved exactly.
 *
 * 2) extractUrls -> the JavaScript /urlRegex/ used by the Bulk Downloader.
 */
public final class DriveLinkParser {

    private DriveLinkParser() {
        // Utility class, no instances.
    }

    /**
     * Original JS: /(?:drive\.google\.com\/(?:[^\/]+\/)*?(?:file\/d\/|d\/|open\?id=)|docs\.google\.com\/(?:[^\/]+\/)*?d\/|drive\.google\.com\/uc\?id=)([a-zA-Z0-9_-]{25,})/g
     */
    private static final Pattern DRIVE_FILE_ID_PATTERN = Pattern.compile(
            "(?:drive\\.google\\.com/(?:[^/]+/)*?(?:file/d/|d/|open\\?id=)"
                    + "|docs\\.google\\.com/(?:[^/]+/)*?d/"
                    + "|drive\\.google\\.com/uc\\?id=)"
                    + "([a-zA-Z0-9_-]{25,})");

    /**
     * Original JS: /(https?:\/\/[^\s]+)/g
     */
    private static final Pattern URL_PATTERN = Pattern.compile("(https?://[^\\s]+)");

    /**
     * Extracts all unique-in-order Google Drive file IDs found in the text.
     * Mirrors the JS loop: while ((match = driveRegex.exec(input)) !== null).
     */
    public static List<String> extractDriveFileIds(String text) {
        List<String> ids = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return ids;
        }
        Matcher matcher = DRIVE_FILE_ID_PATTERN.matcher(text);
        while (matcher.find()) {
            ids.add(matcher.group(1));
        }
        return ids;
    }

    /**
     * Extracts all http(s) URLs found in the text (mirrors text.match(urlRegex)).
     */
    public static List<String> extractUrls(String text) {
        List<String> urls = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return urls;
        }
        Matcher matcher = URL_PATTERN.matcher(text);
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }
}
