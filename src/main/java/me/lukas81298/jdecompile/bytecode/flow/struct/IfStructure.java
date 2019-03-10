package me.lukas81298.jdecompile.bytecode.flow.struct;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractIfSpec;

import java.util.List;
import java.util.Stack;

/**
 * @author lukas
 * @since 09.03.2019
 */
@Getter
public class IfStructure extends Structure {

    private final Instruction condition;
    private final List<Structure> body;

    public IfStructure( int level, Instruction condition, List<Structure> body ) {
        super( level );
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void process( Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        if ( !( this.condition.getInstructionSpec() instanceof AbstractIfSpec ) ) {
            throw new DecompileException( this.condition + " is not an if statement" );
        }
        AbstractIfSpec ifCond = (AbstractIfSpec) this.condition.getInstructionSpec();
        System.out.println( this.condition );
        System.out.println( stack );
        writer.writeln( this.level, "if (" + String.format( ifCond.getCondition( this.condition ), ifCond.getParams( stack ) ) + ") {" );
        for ( Structure structure : this.body ) {
            structure.process( stack, writer, context );
        }
        writer.writeln( this.level, "}" );
    }

    @Override
    public String toString() {
        return this.condition.toString() + ": " + this.body;
    }
}
