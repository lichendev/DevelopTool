package com.lichenlzc.myplugin;

import static org.objectweb.asm.Opcodes.ASM9;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.POP;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestMethodClassAdapter extends ClassVisitor implements Opcodes {

    public TestMethodClassAdapter(ClassVisitor classVisitor) {
        super(ASM9, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return (mv == null) ? null : new TestMethodVisitor(mv);
    }
}

class TestMethodVisitor extends MethodVisitor {

    public TestMethodVisitor(MethodVisitor methodVisitor) {
        super(ASM9, methodVisitor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        System.out.println("== TestMethodVisitor, owner = " + owner + ", name = " + name);
        //方法执行之前打印
        mv.visitLdcInsn(" before method exec");
        mv.visitLdcInsn(" [ASM 测试] method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);

        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        //方法执行之后打印
        mv.visitLdcInsn(" after method exec");
        mv.visitLdcInsn(" method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
    }
}