package com.example.compiler;

import com.example.mybutterknife.MyAnnotation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class MyProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    //这里开始处理我们的注解解析了，以及生成Java文件
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, ">>> analysisAnnotated is start... <<<");
        // 遍历所有被注解了@ZyaoAnnotation的元素
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(MyAnnotation.class)) {
            // 检查被注解为@Factory的元素是否是一个类
//            if (annotatedElement.getKind() != ElementKind.CLASS) {
//                error(annotatedElement, "Only classes can be annotated with @%s",
//                        MyAnnotation.class.getSimpleName());
//                return true; // 退出处理
//            }

            TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            String typeName = typeElement.getSimpleName().toString();
            ClassName className = ClassName.get(packageName, typeName);
            ClassName generatedClassName = ClassName
                    .get(packageName, NameStore.getGeneratedClassName(typeName));

                /*
                创建要生成的类，如下所示
                @Keep
                public class MainActivity$Binding {}*/
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                    .addModifiers(Modifier.PUBLIC);

                /*添加构造函数
                *   public MainActivity$Binding(MainActivity activity) {
                    bindViews(activity);
                    bindOnClicks(activity);
                  }
                */
            classBuilder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(className, "activity")
//                        .addStatement("$N($N)",
//                                NameStore.Method.BIND_VIEWS,
//                                NameStore.Variable.ANDROID_ACTIVITY)
//                        .addStatement("$N($N)",
//                                NameStore.Method.BIND_ON_CLICKS,
//                                NameStore.Variable.ANDROID_ACTIVITY)
                    .build());

            /*创建方法bindViews(MainActivity activity)
             * private void bindViews(MainActivity activity) {}
             */
            MethodSpec.Builder bindViewsMethodBuilder = MethodSpec
                    .methodBuilder("getMessage")
                    .addModifiers(Modifier.PRIVATE)
                    .returns(String.class)
                    .addStatement("return $N", "\"zouhecan\"".toString());
            classBuilder.addMethod(bindViewsMethodBuilder.build());

            //将类写入文件中
            try {
                JavaFile.builder(packageName,
                        classBuilder.build())
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }

        messager.printMessage(Diagnostic.Kind.NOTE, ">>> analysisAnnotated is finish... <<<");

        return true;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Collections.singletonList(MyAnnotation.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
