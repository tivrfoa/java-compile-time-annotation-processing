import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementKindVisitor6;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;


@SupportedAnnotationTypes("Get")
public class GetProcessor extends AbstractProcessor {
	@Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init( processingEnv );

		System.out.println("GetProcessor#init");
    }

	@Override
    public SourceVersion getSupportedSourceVersion() {
		System.out.println("GetProcessor#getSupportedSourceVersion");
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment) {
		System.out.println("GetProcessor#process");

		if ( !roundEnvironment.processingOver() ) {
			System.out.println("processingOver false");
			for (TypeElement annotation : annotations) {
				Set<? extends Element> annotatedElements
				  = roundEnvironment.getElementsAnnotatedWith(annotation);

				System.out.println(annotation);
				System.out.println(annotatedElements);
				String className = null;
				for (var e : annotatedElements) {
					System.out.println("Element: " + e);
					System.out.println("Element TypeMirror: " + e.asType());
					System.out.println("Element Kind: " + e.getKind());
					System.out.println("Element modifiers: " + e.getModifiers());
					className = ((TypeElement) e.getEnclosingElement())
						.getQualifiedName().toString();
					String elementName = e.getSimpleName().toString();
					System.out.println(elementName);
				}
				// …


				try {
					writeBuilderFile(className, annotatedElements);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("processingOver true");
		}

		return false; // TODO ?
	}

    private void writeBuilderFile(final String className, Set<? extends Element> elements)
			throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Get";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.print(" extends " + className);
            out.println(" {");
            out.println();

			for (var e : elements) {
				String elementName = e.getSimpleName().toString();
				out.print("\tpublic ");
				out.print(e.asType().toString());
				out.print(" get" + elementName.substring(0, 1).toUpperCase() +
						elementName.substring(1));
				out.println("() {");
				out.print("\t\treturn " + elementName);
				out.println(";");
				out.println("\t}");
				out.println();
			}


            out.println("}");

        }
    }
}
