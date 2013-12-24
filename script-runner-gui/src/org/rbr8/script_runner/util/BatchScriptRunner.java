/*
 * The MIT License
 *
 * Copyright 2013 robert.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.rbr8.script_runner.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author robert
 */
public class BatchScriptRunner {

    private final String executable;
    
    private final String arguments;

    private final NotifierLogOutputStream logOutputStream;

    public BatchScriptRunner(String executable, String arguments) {
        this.executable = executable;
        this.arguments = arguments;

        this.logOutputStream = new NotifierLogOutputStream();
    }

    public void processFile(File file) throws IOException {

        CommandLine cmdLine = new CommandLine(executable);
        cmdLine.addArguments(arguments);

        Map<String, Object> map = new HashMap<>();
        map.put(SubstitutionHelper.FILE, file);
        cmdLine.setSubstitutionMap(map);

        DefaultExecutor executor = new DefaultExecutor();
//        executor.setExitValue(1);

//        NotifierLogOutputStream outputLog = new NotifierLogOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(logOutputStream);
        executor.setStreamHandler(psh);

//        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//        executor.setWatchdog(watchdog);
        int exitValue = executor.execute(cmdLine);
    }

    public String getExecutable() {
        return executable;
    }

    public String getArguments() {
        return arguments;
    }
    
    public NotifierLogOutputStream getLogOutputStream() {
        return logOutputStream;
    }
}
