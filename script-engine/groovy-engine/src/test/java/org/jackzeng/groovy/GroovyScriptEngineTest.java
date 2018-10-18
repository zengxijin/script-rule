package org.jackzeng.groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author xijin.zeng created on 2018/10/18
 * GroovyScriptEngine can only load the script but not the class. However, it can handles class dependency and hot reloading.
 * GroovyScriptEngine只能执行groovy脚本，但是无法单纯加载groovy类，而且GroovyScriptEngine能够解析脚本的依赖，支持热加载，但是它不能字符脚本的内容，只能传脚本的文件名
 * GroovyClassLoader can load the class directly but cannot handle it dependency.
 * 相对应比较的是Groovy只能加载类，不能执行Groovy脚本，也不能解析依赖
 */
public class GroovyScriptEngineTest {

    public static void main(String[] args) throws IOException {
        try {
            loadStringAndRun();
//            loadScriptAndRun();
//            loadClassAndRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final static String SCRIPT_PATH = "groovy-engine/src/test/resources";

    /**
     * 直接调用脚本，bind机制，可以相互传一部分变量
     * @throws IOException
     * @throws ResourceException
     * @throws ScriptException
     */
    private static void loadScriptAndRun() throws IOException, ResourceException, ScriptException {
        Binding binding = new Binding();
        binding.setVariable("key", "key1");

        GroovyScriptEngine engine = new GroovyScriptEngine(SCRIPT_PATH);

        String result = (String) engine.run("script_engine_test.groovy", binding);
        System.out.println(result);
    }

    /**
     * 使用GroovyScriptEngine加载脚本的，会默认生成一个Script的类，然后将脚本的方法绑定到run的方法上去
     */
    @SuppressWarnings("unchecked")
    static void loadClassAndRun() throws Exception {
        // Declaring a class to conform to a java interface class would get rid
        // of a lot of the reflection here
        Class scriptClass = new GroovyScriptEngine(SCRIPT_PATH)
                .loadScriptByName("script_engine_hello.groovy");
        Object scriptInstance = scriptClass.newInstance();
        scriptClass.getDeclaredMethod("hello_world", new Class[] {}).invoke(
                scriptInstance, new Object[] {});
    }

    static void loadStringAndRun() throws IOException, ResourceException, ScriptException {
        StringBuilder scriptContent = new StringBuilder();
        scriptContent.append("def a=3 ");
        scriptContent.append("\n");
        scriptContent.append("def b=3 ");
        scriptContent.append("\n");
        scriptContent.append("def c=a+b");

        Path path = Files.createTempFile("temp_script", ".groovy");
        //Writing data here
        byte[] buf = scriptContent.toString().getBytes();
        Files.write(path, buf);

        GroovyScriptEngine engine = new GroovyScriptEngine(path.toFile().getPath());

        Integer result = (Integer) engine.run(path.toFile().getName(), new Binding());
        System.out.println(result);

        path.toFile().deleteOnExit();
    }
}
