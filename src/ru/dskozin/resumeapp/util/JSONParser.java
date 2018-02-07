package ru.dskozin.resumeapp.util;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import ru.dskozin.resumeapp.model.Section;

        import java.io.Reader;
        import java.io.Writer;
        import java.util.Objects;

public class JSONParser {

    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JSONSectionAdapter())
            .create();

    public static <T> T read(Reader r, Class<T> clazz) {
        return GSON.fromJson(r, clazz);
    }

    public static <T> void write(Writer w, T object) {
        GSON.toJson(object, w);
    }

    public static <T> T read(String content, Class<T> tClass){
        return GSON.fromJson(content, tClass);
    }

    public static <T> String write(T object, Class<T> tClass){
        return GSON.toJson(object, tClass);
    }
}
