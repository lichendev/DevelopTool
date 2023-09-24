package com.lichenlzc.myplugin;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        BaseExtension baseExtension = (BaseExtension) project.getExtensions().getByName("android");
        baseExtension.registerTransform(new MyTransform());
        System.out.println("ChenLi: MyClass apply, registerTransform");
    }
}

