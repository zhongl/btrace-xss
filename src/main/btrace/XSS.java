import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class XSS {

    static final Class<?> mapClass  = classForName("java.util.Map");
    static final Class<?> iterClass = classForName("java.lang.Iterable");

    @OnMethod(
            clazz = "org.apache.velocity.runtime.parser.node.ASTReference",
            method = "execute",
            location = @Location(Kind.RETURN)
    )
    public static void onRef(@Self Object self, @Return Object value) {
        final Class<?> c = classOf(value);
        if (!isArray(c) && !isPrimitive(c) && !isAssignableFrom(mapClass, c) && !isAssignableFrom(iterClass, c)) {
            println("Render " + get(field(classOf(self), "nullString"), self) + " -> " + value);
        }
    }

}
