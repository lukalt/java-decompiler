package me.lukas81298.jdecompile.bytecode.flow.struct;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.flow.InstructionEntry;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractIfSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lukas
 * @since 09.03.2019
 */
@Getter
public class WhileStructure extends Structure {

    private final List<InstructionEntry> conditions;
    private final List<Structure> body;

    public WhileStructure( int level, List<InstructionEntry> conditions, List<Structure> body ) {
        super( level );
        this.conditions = conditions;
        this.body = body;
    }

    @Override
    public void process( Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        List<String> andConditions = new ArrayList<>();
        for ( InstructionEntry entry : this.conditions ) {
            for ( Instruction instruction : entry.getInstructions() ) {
                instruction.getInstructionSpec().process( this.level, instruction, stack, writer, context );
            }
            Instruction condition = entry.getCondition();
            if ( !( condition.getInstructionSpec() instanceof AbstractIfSpec ) ) {
                throw new DecompileException( condition + " is not an if statement" );
            }
            AbstractIfSpec ifCond = (AbstractIfSpec) condition.getInstructionSpec();
            andConditions.add( String.format( ifCond.getCondition( condition ), ifCond.getParams( stack ) ) );
        }


        writer.writeln( this.level, "while (" + String.join( " && ", andConditions ) + ") {" );
        for ( Structure structure : this.body ) {
            structure.process( stack, writer, context );
        }
        writer.writeln( this.level, "}" );

    }

    @Override
    public String toString() {
        return this.conditions + ":" + this.body;
    }

}
