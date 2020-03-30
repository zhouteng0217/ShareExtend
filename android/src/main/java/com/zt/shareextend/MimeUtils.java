package com.zt.shareextend;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouteng on 2020-03-29
 */
public class MimeUtils {

    private static final String DEFAULT_MINE_TYPE = "application/octet-stream";

    private static final Map<String, String> extensionToMimeTypeMap = new HashMap<>();

    static {
        add("epub", "application/epub+zip");
        add("ogx", "application/ogg");
        add("odp", "application/vnd.oasis.opendocument.presentation");
        add("otp", "application/vnd.oasis.opendocument.presentation-template");
        add("yt", "application/vnd.youtube.yt");
        add("hwp", "application/x-hwp");
        add("3gpp", "video/3gpp");
        add("3gp", "video/3gpp");
        add("3gpp2", "video/3gpp2");
        add("3g2", "video/3gpp2");
        add("oga", "audio/ogg");
        add("ogg", "audio/ogg");
        add("spx", "audio/ogg");
        add("dng", "image/x-adobe-dng");
        add("cr2", "image/x-canon-cr2");
        add("raf", "image/x-fuji-raf");
        add("nef", "image/x-nikon-nef");
        add("nrw", "image/x-nikon-nrw");
        add("orf", "image/x-olympus-orf");
        add("rw2", "image/x-panasonic-rw2");
        add("pef", "image/x-pentax-pef");
        add("srw", "image/x-samsung-srw");
        add("arw", "image/x-sony-arw");
        add("ogv", "video/ogg");
        add("tgz", "application/x-gtar-compressed");
        add("taz", "application/x-gtar-compressed");
        add("csv", "text/csv");
        add("gz", "application/gzip");
        add("cab", "application/vnd.ms-cab-compressed");
        add("7z", "application/x-7z-compressed");
        add("bz", "application/x-bzip");
        add("bz2", "application/x-bzip2");
        add("z", "application/x-compress");
        add("jar", "application/x-java-archive");
        add("lzma", "application/x-lzma");
        add("xz", "application/x-xz");
        add("m3u", "audio/x-mpegurl");
        add("m3u8", "audio/x-mpegurl");
        add("p7b", "application/x-pkcs7-certificates");
        add("spc", "application/x-pkcs7-certificates");
        add("p7c", "application/pkcs7-mime");
        add("p7s", "application/pkcs7-signature");
        add("es", "application/ecmascript");
        add("js", "application/javascript");
        add("json", "application/json");
        add("ts", "application/typescript");
        add("perl", "text/x-perl");
        add("pl", "text/x-perl");
        add("pm", "text/x-perl");
        add("py", "text/x-python");
        add("py3", "text/x-python");
        add("py3x", "text/x-python");
        add("pyx", "text/x-python");
        add("wsgi", "text/x-python");
        add("rb", "text/ruby");
        add("sh", "application/x-sh");
        add("yaml", "text/x-yaml");
        add("yml", "text/x-yaml");
        add("asm", "text/x-asm");
        add("s", "text/x-asm");
        add("cs", "text/x-csharp");
        add("azw", "application/vnd.amazon.ebook");
        add("ibooks", "application/x-ibooks+zip");
        add("mobi", "application/x-mobipocket-ebook");
        add("woff", "font/woff");
        add("woff2", "font/woff2");
        add("msg", "application/vnd.ms-outlook");
        add("eml", "message/rfc822");
        add("eot", "application/vnd.ms-fontobject");
        add("ttf", "font/ttf");
        add("otf", "font/otf");
        add("ttc", "font/collection");
        add("markdown", "text/markdown");
        add("md", "text/markdown");
        add("mkd", "text/markdown");
        add("conf", "text/plain");
        add("ini", "text/plain");
        add("list", "text/plain");
        add("log", "text/plain");
        add("prop", "text/plain");
        add("properties", "text/plain");
        add("rc", "text/plain");
        add("flv", "video/x-flv");
    }

    private static void add(String extension, String mimeType) {
        if (!extensionToMimeTypeMap.containsKey(extension)) {
            extensionToMimeTypeMap.put(extension, mimeType);
        }
    }

    public static String getMineType(File file) {
        final int lastDot = file.getName().lastIndexOf('.');
        if (lastDot < 0) {
            return DEFAULT_MINE_TYPE;
        }

        final String extension = file.getName().substring(lastDot + 1).toLowerCase();
        String mineType = extensionToMimeTypeMap.get(extension);
        if (!TextUtils.isEmpty(mineType)) {
            return mineType;
        }

        mineType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (!TextUtils.isEmpty(mineType)) {
            return mineType;
        }
        return DEFAULT_MINE_TYPE;
    }
}
