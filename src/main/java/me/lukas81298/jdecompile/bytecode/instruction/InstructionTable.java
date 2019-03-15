package me.lukas81298.jdecompile.bytecode.instruction;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import me.lukas81298.jdecompile.bytecode.instruction.spec.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class InstructionTable {

    @Getter
    private final static InstructionTable instance = new InstructionTable();

    private final TIntObjectMap<InstructionSpec> instructions = new TIntObjectHashMap<>();

    public InstructionTable() {
        this.register( "nop", 0x00, 0, NopSpec.class );
        this.register( "aconst_null", 0x01, 0, ConstSpec.class, "null", OperandType.REFERENCE );
        this.register( "iconst_m1", 0x02, 0, ConstSpec.class, ( -1 ), OperandType.INT );
        // 0x03 to 0x08
        for ( int i = 0; i <= 5; i++ ) {
            this.register( "iconst_" + i, 0x03 + i, 0, ConstSpec.class, i, OperandType.INT );
        }
        for ( long l = 0L; l <= 1; l++ ) {
            this.register( "lconst_" + l, (int) ( 0x09 + l ), 0, ConstSpec.class, l, OperandType.LONG );
        }
        for ( float f = 0L; f <= 2; f++ ) {
            this.register( "fconst_" + (int) f, (int) ( 0x0b + f ), 0, ConstSpec.class, f, OperandType.FLOAT );
        }
        for ( float f = 0L; f <= 1; f++ ) {
            this.register( "dconst_" + (int) f, (int) ( 0x0e + f ), 0, ConstSpec.class, f, OperandType.DOUBLE );
        }

        this.register( "bipush", 0x10, 1, BiPushSpec.class );
        this.register( "sipush", 0x11, 2, SiPushSpec.class );
        this.register( "ldc", 0x12, 1, LdcSpec.class );
        this.register( "ldc_w", 0x13, 2, LdcWideSpec.class );
        this.register( "ldc2_w", 0x14, 2, LdcWideSpec.class );

        this.register( "iload", 0x15, 1, LoadSpec.class );
        this.register( "lload", 0x16, 1, LoadSpec.class );
        this.register( "fload", 0x17, 1, LoadSpec.class );
        this.register( "dload", 0x18, 1, LoadSpec.class );
        this.register( "aload", 0x19, 1, LoadSpec.class );

        this.register( "iload_0", 0x1a, 0, LoadSpec.LoadSpecShort.class, 0 );
        this.register( "iload_1", 0x1b, 0, LoadSpec.LoadSpecShort.class, 1 );
        this.register( "iload_2", 0x1c, 0, LoadSpec.LoadSpecShort.class, 2 );
        this.register( "iload_3", 0x1d, 0, LoadSpec.LoadSpecShort.class, 3 );

        this.register( "lload_0", 0x1e, 0, LoadSpec.LoadSpecShort.class, 0 );
        this.register( "lload_1", 0x1f, 0, LoadSpec.LoadSpecShort.class, 1 );
        this.register( "lload_2", 0x20, 0, LoadSpec.LoadSpecShort.class, 2 );
        this.register( "lload_3", 0x21, 0, LoadSpec.LoadSpecShort.class, 3 );

        this.register( "fload_0", 0x22, 0, LoadSpec.LoadSpecShort.class, 0 );
        this.register( "fload_1", 0x23, 0, LoadSpec.LoadSpecShort.class, 1 );
        this.register( "fload_2", 0x24, 0, LoadSpec.LoadSpecShort.class, 2 );
        this.register( "fload_3", 0x25, 0, LoadSpec.LoadSpecShort.class, 3 );

        this.register( "dload_0", 0x26, 0, LoadSpec.LoadSpecShort.class, 0 );
        this.register( "dload_1", 0x27, 0, LoadSpec.LoadSpecShort.class, 1 );
        this.register( "dload_2", 0x28, 0, LoadSpec.LoadSpecShort.class, 2 );
        this.register( "dload_3", 0x29, 0, LoadSpec.LoadSpecShort.class, 3 );

        this.register( "aload_0", 0x2a, 0, LoadSpec.LoadSpecShort.class, 0 );
        this.register( "aload_1", 0x2b, 0, LoadSpec.LoadSpecShort.class, 1 );
        this.register( "aload_2", 0x2c, 0, LoadSpec.LoadSpecShort.class, 2 );
        this.register( "aload_3", 0x2d, 0, LoadSpec.LoadSpecShort.class, 3 );

        this.register( "iaload", 0x2e, 0, ArrLoadSpec.class, OperandType.INT );
        this.register( "laload", 0x2f, 0, ArrLoadSpec.class, OperandType.LONG );
        this.register( "faload", 0x30, 0, ArrLoadSpec.class, OperandType.FLOAT );
        this.register( "daload", 0x31, 0, ArrLoadSpec.class, OperandType.DOUBLE );
        this.register( "aaload", 0x32, 0, ArrLoadSpec.class, OperandType.REFERENCE );
        this.register( "baload", 0x33, 0, ArrLoadSpec.class, OperandType.BYTE );
        this.register( "caload", 0x34, 0, ArrLoadSpec.class, OperandType.CHAR );
        this.register( "saload", 0x35, 0, ArrLoadSpec.class, OperandType.SHORT );

        this.register( "istore", 0x36, 1, StoreSpec.class );
        this.register( "lstore", 0x37, 1, StoreSpec.class );
        this.register( "fstore", 0x38, 1, StoreSpec.class );
        this.register( "dstore", 0x39, 1, StoreSpec.class );
        this.register( "astore", 0x3a, 1, StoreSpec.class );

        this.register( "istore_0", 0x3b, 0, StoreSpec.StoreSpecShort.class, 0 );
        this.register( "istore_1", 0x3c, 0, StoreSpec.StoreSpecShort.class, 1 );
        this.register( "istore_2", 0x3d, 0, StoreSpec.StoreSpecShort.class, 2 );
        this.register( "istore_3", 0x3e, 0, StoreSpec.StoreSpecShort.class, 3 );

        this.register( "lstore_0", 0x3f, 0, StoreSpec.StoreSpecShort.class, 0 );
        this.register( "lstore_1", 0x40, 0, StoreSpec.StoreSpecShort.class, 1 );
        this.register( "lstore_2", 0x41, 0, StoreSpec.StoreSpecShort.class, 2 );
        this.register( "lstore_3", 0x42, 0, StoreSpec.StoreSpecShort.class, 3 );

        this.register( "fstore_0", 0x43, 0, StoreSpec.StoreSpecShort.class, 0 );
        this.register( "fstore_1", 0x44, 0, StoreSpec.StoreSpecShort.class, 1 );
        this.register( "fstore_2", 0x45, 0, StoreSpec.StoreSpecShort.class, 2 );
        this.register( "fstore_3", 0x46, 0, StoreSpec.StoreSpecShort.class, 3 );

        this.register( "dstore_0", 0x47, 0, StoreSpec.StoreSpecShort.class, 0 );
        this.register( "dstore_1", 0x48, 0, StoreSpec.StoreSpecShort.class, 1 );
        this.register( "dstore_2", 0x49, 0, StoreSpec.StoreSpecShort.class, 2 );
        this.register( "dstore_3", 0x4a, 0, StoreSpec.StoreSpecShort.class, 3 );

        this.register( "astore_0", 0x4b, 0, StoreSpec.StoreSpecShort.class, 0 );
        this.register( "astore_1", 0x4c, 0, StoreSpec.StoreSpecShort.class, 1 );
        this.register( "astore_2", 0x4d, 0, StoreSpec.StoreSpecShort.class, 2 );
        this.register( "astore_3", 0x4e, 0, StoreSpec.StoreSpecShort.class, 3 );

        this.register( "iastore", 0x4f, 0, ArrStoreSpec.class );
        this.register( "lastore", 0x50, 0, ArrStoreSpec.class );
        this.register( "fastore", 0x51, 0, ArrStoreSpec.class );
        this.register( "dastore", 0x52, 0, ArrStoreSpec.class );
        this.register( "aastore", 0x53, 0, ArrStoreSpec.class );
        this.register( "bastore", 0x54, 0, ArrStoreSpec.class );
        this.register( "castore", 0x55, 0, ArrStoreSpec.class );
        this.register( "sastore", 0x56, 0, ArrStoreSpec.class );

        this.register( "pop", 0x57, 0, PopSpec.class );
        this.register( "pop2", 0x58, 0, Pop2Spec.class );
        this.register( "dup", 0x59, 0, DupSpec.class );
        this.register( "dup_x1", 0x5a, 0, Dupx1Spec.class );
        // dup_x2
        this.register( "dup2", 0x5c, 0, Dup2Spec.class );
        // dup2_x1
        // dup2_x2
        this.register( "swap", 0x5f, 0, SwapSpec.class );
        this.register( "iadd", 0x60, 0, BinaryOpSpec.class, "+" );
        this.register( "ladd", 0x61, 0, BinaryOpSpec.class, "+" );
        this.register( "fadd", 0x62, 0, BinaryOpSpec.class, "+" );
        this.register( "dadd", 0x63, 0, BinaryOpSpec.class, "+" );

        this.register( "isub", 0x64, 0, BinaryOpSpec.class, "-" );
        this.register( "lsub", 0x65, 0, BinaryOpSpec.class, "-" );
        this.register( "fsub", 0x66, 0, BinaryOpSpec.class, "-" );
        this.register( "dsub", 0x67, 0, BinaryOpSpec.class, "-" );

        this.register( "imul", 0x68, 0, BinaryOpSpec.class, "*" );
        this.register( "lmul", 0x69, 0, BinaryOpSpec.class, "*" );
        this.register( "fmul", 0x6a, 0, BinaryOpSpec.class, "*" );
        this.register( "dmul", 0x6b, 0, BinaryOpSpec.class, "*" );

        this.register( "idiv", 0x6c, 0, BinaryOpSpec.class, "/" );
        this.register( "ldiv", 0x6d, 0, BinaryOpSpec.class, "/" );
        this.register( "fdiv", 0x6e, 0, BinaryOpSpec.class, "/" );
        this.register( "ddiv", 0x6f, 0, BinaryOpSpec.class, "/" );

        this.register( "irem", 0x70, 0, BinaryOpSpec.class, "%" );
        this.register( "lrem", 0x71, 0, BinaryOpSpec.class, "%" );
        this.register( "frem", 0x72, 0, BinaryOpSpec.class, "%" );
        this.register( "drem", 0x73, 0, BinaryOpSpec.class, "%" );

        this.register( "ineg", 0x74, 0, NegSpec.class );
        this.register( "lneg", 0x75, 0, NegSpec.class );
        this.register( "fneg", 0x76, 0, NegSpec.class );
        this.register( "dneg", 0x77, 0, NegSpec.class );

        this.register( "ishl", 0x78, 0, BinaryOpSpec.class, "<<" );
        this.register( "lshl", 0x79, 0, BinaryOpSpec.class, "<<" );
        this.register( "fshl", 0x7a, 0, BinaryOpSpec.class, "<<" );
        this.register( "dshl", 0x7b, 0, BinaryOpSpec.class, "<<" );

        this.register( "ishr", 0x7c, 0, BinaryOpSpec.class, ">>" );
        this.register( "lshr", 0x7d, 0, BinaryOpSpec.class, ">>" );
        this.register( "fshr", 0x7e, 0, BinaryOpSpec.class, ">>" );
        this.register( "dshr", 0x7f, 0, BinaryOpSpec.class, ">" );

        this.register( "ior", 0x80, 0, BinaryOpSpec.class, "|" );
        this.register( "lor", 0x81, 0, BinaryOpSpec.class, "|" );
        this.register( "ixor", 0x82, 0, BinaryOpSpec.class, "^" );
        this.register( "lxor", 0x83, 0, BinaryOpSpec.class, "^" );
        this.register( "iinc", 0x84, 2, IincSpec.class );
        this.register( "i2l", 0x85, 0, PrimitiveCastSpec.class, OperandType.LONG );
        this.register( "i2f", 0x86, 0, PrimitiveCastSpec.class, OperandType.FLOAT );
        this.register( "i2d", 0x87, 0, PrimitiveCastSpec.class, OperandType.DOUBLE );

        this.register( "l2i", 0x88, 0, PrimitiveCastSpec.class, OperandType.DOUBLE );
        this.register( "l2f", 0x89, 0, PrimitiveCastSpec.class, OperandType.DOUBLE );
        this.register( "l2d", 0x8a, 0, PrimitiveCastSpec.class, OperandType.DOUBLE );

        this.register( "f2i", 0x8b, 0, PrimitiveCastSpec.class, OperandType.INT );
        this.register( "f2l", 0x8c, 0, PrimitiveCastSpec.class, OperandType.LONG );
        this.register( "f2d", 0x8d, 0, PrimitiveCastSpec.class, OperandType.DOUBLE );
        this.register( "d2i", 0x8e, 0, PrimitiveCastSpec.class, OperandType.INT );
        this.register( "d2l", 0x8f, 0, PrimitiveCastSpec.class, OperandType.LONG );
        this.register( "d2f", 0x90, 0, PrimitiveCastSpec.class, OperandType.FLOAT );

        this.register( "i2b", 0x91, 0, PrimitiveCastSpec.class, OperandType.BYTE );
        this.register( "i2c", 0x92, 0, PrimitiveCastSpec.class, OperandType.CHAR );
        this.register( "i2s", 0x93, 0, PrimitiveCastSpec.class, OperandType.LONG );

        this.register( "ifeq", 0x99, 2, SimpleIfSpec.class, "!= 0"  );
        this.register( "ifne", 0x9a, 2, SimpleIfSpec.class, "== 0"  );
        this.register( "iflt", 0x9b, 2, SimpleIfSpec.class, ">= 0"  );
        this.register( "ifge", 0x9c, 2, SimpleIfSpec.class, "< 0"  );
        this.register( "ifgt", 0x9d, 2, SimpleIfSpec.class, "<= 0"  );
        this.register( "ifle", 0x9e, 2, SimpleIfSpec.class, "> 0"  );

        this.register( "if_icmpeq", 0x9f, 2, BinIfSpec.class, "!=" );
        this.register( "if_icmpne", 0xa0, 2, BinIfSpec.class, "==" );
        this.register( "if_icmplt", 0xa1, 2, BinIfSpec.class, ">=" );
        this.register( "if_icmpge", 0xa2, 2, BinIfSpec.class, "<" );
        this.register( "if_icmpgt", 0xa3, 2, BinIfSpec.class, "<=" );
        this.register( "if_icmple", 0xa4, 2, BinIfSpec.class, ">" );
        this.register( "if_acmpeq", 0xa5, 2, BinIfSpec.class, "!=" );
        this.register( "if_acmpne", 0xa6, 2, BinIfSpec.class, "==" );
        this.register( "goto", 0xa7, 2, GotoSpec.class );
        this.register( "ireturn", 0xac, 0, AReturnSpec.class );
        this.register( "lreturn", 0xad, 0, AReturnSpec.class );
        this.register( "freturn", 0xae, 0, AReturnSpec.class );
        this.register( "dreturn", 0xaf, 0, AReturnSpec.class );
        this.register( "areturn", 0xb0, 0, AReturnSpec.class );
        this.register( "return", 0xb1, 0, ReturnSpec.class );
        this.register( "getstatic", 0xb2, 2, GetStaticSpec.class );
        this.register( "putstatic", 0xb3, 2, PutStaticSpec.class );
        this.register( "getfield", 0xb4, 2, GetFieldSpec.class );
        this.register( "putfield", 0xb5, 2, PutFieldSpec.class );
        this.register( "invokevirtual", 0xb6, 2, InvokeVirtualSpec.class );
        this.register( "invokespecial", 0xb7, 2, InvokeSpecialSpec.class );
        this.register( "invokestatic", 0xb8, 2, InvokeStaticSpec.class );
        this.register( "invokeinterface", 0xb9, 3, InvokeInterfaceSpec.class );
        this.register( "new", 0xbb, 2, NewSpec.class );
        this.register( "anewarray", 0xbd, 2, ANewArraySpec.class );
        this.register( "arraylength", 0xbe, 0, ArrayLengthSpec.class );
        this.register( "athrow", 0xbf, 0, AthrowSpec.class );

        this.register( "checkcast", 0xc0, 2, CheckCastSpec.class );
        this.register( "instanceof", 0xc1, 2, InstanceOfSpec.class );
        this.register( "wide", 0xc4, 0, WideSpec.class );
        this.register( "ifnull", 0xc6, 2, SimpleIfSpec.class, "== null" );
        this.register( "ifnotnull", 0xc7, 2, SimpleIfSpec.class, "!= null" );
        this.register( "goto_w", 0xc8, 4, GotoWideSpec.class );


    }

    public void register( String mnemonic, int id, int len, Class<? extends InstructionSpec> clazz, Object... args ) {
        if( this.instructions.containsKey( id ) ) {
            throw new IllegalArgumentException( "Already registered: " + Integer.toHexString( id ) + ": " + clazz.getName() );
        }
        Class<?>[] types = new Class[3 + args.length];
        Object[] values = new Object[3 + args.length];
        types[0] = String.class;
        types[1] = int.class;
        types[2] = int.class;
        values[0] = mnemonic;
        values[1] = id;
        values[2] = len;
        for ( int i = 0; i < args.length; i++ ) {
            types[3 + i] = args[i].getClass();
            values[3 + i] = args[i];
        }
        try {
            this.instructions.put( id, clazz.getDeclaredConstructor( types ).newInstance( values ) );
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
            e.printStackTrace();
        }

    }

    public InstructionSpec getSpec( int type ) {
        return this.instructions.get( type );
    }
}
