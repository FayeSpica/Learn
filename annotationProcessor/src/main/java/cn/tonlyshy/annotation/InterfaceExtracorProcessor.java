package cn.tonlyshy.annotation;

import cn.tonlyshy.annotation.ExtractInterface;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.mirror.apt.AnnotationProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
                    String interfaceName = annot.value();
                    messager.printMessage(Diagnostic.Kind.NOTE, "ExtractInterface = " + interfaceName);
                    messager.printMessage(Diagnostic.Kind.NOTE, "modifiers=" + element.getModifiers() + " name=" + element.getSimpleName());
                    for (Element e : element.getEnclosedElements()) {
                        messager.printMessage(Diagnostic.Kind.NOTE, "modifiers=" + e.getModifiers() + " name=" + e.getSimpleName());
                    }

                    try {
                        List<MethodSpec> methodSpecList = new ArrayList<>();
                        for (Element e : element.getEnclosedElements()) {
                            methodSpecList.add(MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                    .build());
                        }
                        TypeSpec interfaceJavaFile = TypeSpec.interfaceBuilder(interfaceName)
                                .addModifiers(Modifier.PUBLIC)
                                .addMethods(methodSpecList)
                                .build();

                        JavaFile javaFile = JavaFile.builder(ExtractInterface.class.getPackage().getName(), interfaceJavaFile)
                                .build();

                        messager.printMessage(Diagnostic.Kind.NOTE, "packageName = " + ExtractInterface.class.getPackage().toString());
                        javaFile.writeTo(new File("/Users/liaowm5/Desktop/Learn/Learn/annotationsTest/lib/"));
                        messager.printMessage(Diagnostic.Kind.NOTE, javaFile.toString());
                    } catch (Exception e) {
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
