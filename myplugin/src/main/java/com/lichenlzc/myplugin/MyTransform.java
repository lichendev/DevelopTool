package com.lichenlzc.myplugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import java.io.IOException;
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
        transformInvocation.getInputs().forEach((it)-> {
            it.getDirectoryInputs().forEach((it1)-> {
                System.out.println("CHenLi transform-getDirectoryInputs："+it1.getFile().getAbsolutePath());
            });
            it.getJarInputs().forEach((it1)-> {
                System.out.println("CHenLi transform-getJarInputs："+it1.getFile().getAbsolutePath());
            });
        });
        TransformOutputProvider transformOutputProvider = transformInvocation.getOutputProvider();
    }
}
