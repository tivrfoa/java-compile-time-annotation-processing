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
		} else {
			System.out.println("processingOver true");
		}

		return false; // TODO ?
	}
}
