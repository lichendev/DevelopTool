package com.lichenlzc.myplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyClass implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getLogger().debug("ChenLi: MyClass apply");
        System.out.println("ChenLi: MyClass apply");
    }
}
