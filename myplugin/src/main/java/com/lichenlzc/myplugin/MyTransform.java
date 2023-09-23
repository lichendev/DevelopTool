package com.lichenlzc.myplugin;

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

public class MyTransform extends Transform {

    @Override
    public String getName() {
        return "lichenlzc_MyTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        System.out.println("ChenLi: MyTransform");
        TransformOutputProvider transformOutputProvider = transformInvocation.getOutputProvider();
        transformInvocation.getInputs().forEach((transformInput)-> {
            transformInput.getDirectoryInputs().forEach((directoryInput)-> {
                System.out.println("CHenLi transform-getDirectoryInputs："+directoryInput.getFile().getAbsolutePath());
                File directoryOut = transformOutputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
                System.out.println("CHenLi transform-directoryOut："+directoryOut.getAbsolutePath());
                try {
                    transformDirectory(directoryInput.getFile(), directoryOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            transformInput.getJarInputs().forEach((jarInput)-> {
                System.out.println("CHenLi transform-getJarInputs："+jarInput.getFile().getAbsolutePath());
                File directoryOut = transformOutputProvider.getContentLocation(jarInput.getName(),
                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                try {
                    if(jarInput.getFile().getUsableSpace()>0) {
                        if(!directoryOut.getParentFile().exists()){
                            directoryOut.getParentFile().mkdirs();
                        }
                        copyFile(jarInput.getFile(), directoryOut);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private static void copyFile(File input, File dest) throws IOException {
        if(dest.exists()){
            dest.delete();
        }
        Files.copy(input.toPath(), dest.toPath());
        System.out.println("=== ChenLi-copyFile ===, input=" + input.getAbsolutePath() + ", dest=" + dest.getAbsolutePath());
    }

    private static void transformDirectory(File input, File dest) throws IOException {
        String srcDirPath = input.getAbsolutePath();
        String destDirPath = dest.getAbsolutePath();
        for (File file : input.listFiles()) {
            String destFilePath = file.getAbsolutePath().replace(srcDirPath, destDirPath);
            File destFile = new File(destFilePath);
            if (file.isDirectory()) {
                transformDirectory(file, destFile);
            } else if (file.isFile() && file.getName().endsWith(".class")) {
                transformSingleFile(file, destFile);
            }else {
                if(!destFile.getParentFile().exists()){
                    destFile.getParentFile().mkdirs();
                }
                copyFile(file, destFile);
            }
        }
    }

    private static void touch(File file) throws IOException {
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(file.exists()){
            file.delete();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static void transformSingleFile(File input, File dest) throws IOException {
        System.out.println("=== transformSingleFile ===, input=" + input.getAbsolutePath() + ", dest=" + dest.getAbsolutePath());
        touch(dest);
        weave(input.getAbsolutePath(), dest.getAbsolutePath());
    }

    private static void weave(String inputPath, String outputPath) {
        try {
            FileInputStream is = new FileInputStream(inputPath);
            ClassReader cr = new ClassReader(is);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            TestMethodClassAdapter adapter = new TestMethodClassAdapter(cw);
            cr.accept(adapter, 0);
            FileOutputStream fos = new FileOutputStream(outputPath);
            fos.write(cw.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
