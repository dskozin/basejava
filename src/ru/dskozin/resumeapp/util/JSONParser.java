package ru.dskozin.resumeapp.util;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import ru.dskozin.resumeapp.model.Section;

        import java.io.Reader;
        import java.io.Writer;

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
}
