function initializeCoreMod() {
    var desc = '(Lnet/minecraft/world/IEnviromentBlockReader;Lnet/minecraft/util/math/BlockPos;)I';
    return {
        coreModName: {
            target: {
                type: 'METHOD',
                class: 'net.minecraftforge.fluids.FluidAttributes$Water',
                methodName: 'getColor',
                methodDesc: desc
            },
            transformer: function (method) {
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
                var instructions = method.instructions;
                for (var i = 0; i < instructions.size(); i++) {
                    var instruction = instructions.get(i);
                    if(instruction.getOpcode() === Opcodes.INVOKESTATIC) {
                        instructions.insertBefore(instruction, new MethodInsnNode(Opcodes.INVOKESTATIC, 'com/github/mnesikos/samhain/client/renderer/color/OtherworldColors', 'getLiquidColor', desc, false));
                        instructions.remove(instruction);
                    }
                }
                return method;
            }
        }
    }
}
