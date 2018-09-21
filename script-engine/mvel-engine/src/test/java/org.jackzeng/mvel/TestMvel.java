package org.jackzeng.mvel;

import com.google.common.collect.Maps;
import org.mvel2.MVEL;
import org.mvel2.compiler.ExecutableAccessor;

import java.util.Map;

/**
 * @author zengxijin created on 2018/9/21
 */
public class TestMvel {
    public static void main(String[] args) {
        String expression = "def addTwo(num1, num2) { num1 + num2; } val = addTwo(a, b);";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", 2);
        paramMap.put("b", 4);
        Object object = MVEL.eval(expression, paramMap);
        System.out.println(object);


        Map<String, Object> vars = Maps.newHashMap();
        vars.put("x", 100);
        vars.put("y", 100);

        ExecutableAccessor compiled = (ExecutableAccessor) MVEL.compileExpression("x * y");
        int val = (Integer) MVEL.executeExpression(compiled, vars);
        System.out.println(val);

    }
}
