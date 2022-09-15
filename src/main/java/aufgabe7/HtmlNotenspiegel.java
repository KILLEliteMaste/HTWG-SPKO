// HtmlNotenspiegel.java
package aufgabe7;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class HtmlNotenspiegel {
    private HtmlNotenspiegel() {
    }

    public static void main(String[] args) throws Exception {
        String path = "path/to/file.stg";
        ST templ = new STGroupFile(path).getInstanceOf("renderclasses");

        List<ClassWrapper> classes = List.of(
                new ClassWrapper(Class.forName("java.lang.String")),
                new ClassWrapper(Class.forName("java.util.Iterator")),
                new ClassWrapper(Class.forName("java.time.Month")));

        templ.add("classes", classes);
        String result = templ.render();
        System.out.println(result);
        Files.writeString(Paths.get("path/to/ausgabe.html"), result);
    }
}

final class ClassWrapper {
    public final boolean isInterface;
    public final String name;
    public final List<MethodWrapper> methods = new ArrayList<>();

    public final List<ClassWrapper> interfaces = new ArrayList<>();

    public ClassWrapper(Class<?> clazz) {
        isInterface = clazz.isInterface();
        if (isInterface) {
            for (final Method declaredMethod : clazz.getDeclaredMethods()) {
                methods.add(new MethodWrapper(declaredMethod));
            }
        } else {
            for (final Class<?> anInterface : clazz.getInterfaces()) {
                interfaces.add(new ClassWrapper(anInterface));
            }
        }
        name = clazz.getName();
    }
}

final class MethodWrapper {

    public final String returnType;
    public final String name;
    public final String parameterName;

    public MethodWrapper(final Method declaredMethod) {
        returnType = declaredMethod.getReturnType().getTypeName();
        name = declaredMethod.getName();
        Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
        StringBuilder stringBuilder = new StringBuilder();
        for (final Class<?> parameterType : parameterTypes) {
            stringBuilder.append(parameterType.getTypeName()).append(" ");
        }
        this.parameterName = stringBuilder.toString();
    }
}