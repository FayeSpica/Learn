package cn.tonlyshy.annotation;

import cn.tonlyshy.annotation.ExtractInterface;
import com.google.auto.service.AutoService;
import com.sun.mirror.apt.AnnotationProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liaowm5 on 17/7/31.
 */
@AutoService(Processor.class)
public class InterfaceExtracorProcessor extends AbstractProcessor {

    private Set<String> supportedAnnotationTypes = new HashSet<String>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        supportedAnnotationTypes.add(ExtractInterface.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                ExtractInterface annot = element.getAnnotation(ExtractInterface.class);

                if (annot != null) {
                    String value = annot.value();
                    messager.printMessage(Diagnostic.Kind.NOTE, "ExtractInterface = " + value);
                    messager.printMessage(Diagnostic.Kind.NOTE, "modifiers=" + element.getModifiers() + " name=" + element.getSimpleName());
                    for (Element e : element.getEnclosedElements()) {
                        messager.printMessage(Diagnostic.Kind.NOTE, "modifiers=" + e.getModifiers() + " name=" + e.getSimpleName());
                    }

                    try {
                        PrintWriter printWriter=
                    } catch (IOException e) {
                        messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
