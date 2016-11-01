import com.sun.btrace.*;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class XSS {

    static final Class<?> mapClass  = classForName("java.util.Map");
    static final Class<?> iterClass = classForName("java.lang.Iterable");

    @TLS static Object URI;

    @OnMethod(
            clazz = "org.apache.velocity.runtime.parser.node.ASTReference",
            method = "execute",
            location = @Location(Kind.RETURN)
    )
    public static void onRef(@Self Object self, @Return Object value) {
        if (value != null) {
            final Class<?> c = classOf(value);
            if (!isArray(c) && !isPrimitive(c) && !isAssignableFrom(mapClass, c) && !isAssignableFrom(iterClass, c)) {
                println("Render " + get(field(classOf(self), "nullString"), self) + " -> " + value);

                if (matches("<", str(value))) {
                    println("Got it : " + value + ", from call " +  URI);
                }
            }
        }
    }

    @OnMethod(
            clazz = "javax.servlet.http.HttpServlet",
            method = "service",
            location = @Location(Kind.ENTRY)
    )
    public static void onService(AnyType[] args) {
        final AnyType req = args[0];
        final Object ireq = get(field(classOf(req), "request"), req);
        final Object iireq = get(field(classOf(ireq), "coyoteRequest"), ireq);
        final Object uri = get(field(classOf(iireq), "uriMB"), iireq);
        URI = uri;
    }

}
