package engine.annotation;

@java.lang.annotation.Target(value={java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)

//mark if a transient field is needed to write to client.
public @interface NeedWrite {

}
