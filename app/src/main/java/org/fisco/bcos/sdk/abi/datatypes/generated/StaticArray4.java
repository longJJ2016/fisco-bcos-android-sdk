package org.fisco.bcos.sdk.abi.datatypes.generated;

import org.fisco.bcos.sdk.abi.datatypes.StaticArray;
import org.fisco.bcos.sdk.abi.datatypes.Type;

import java.util.List;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modifiy!</strong>
 *
 * <p>Please use AbiTypesGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray4<T extends Type> extends StaticArray<T> {
    public StaticArray4(List<T> values) {
        super(4, values);
    }

    @SafeVarargs
    public StaticArray4(T... values) {
        super(4, values);
    }
}