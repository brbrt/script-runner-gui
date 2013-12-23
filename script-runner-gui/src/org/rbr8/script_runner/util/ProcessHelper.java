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

import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author robert
 */
public class ProcessHelper {
    
    public ProcessHelper() {}
    
    public void startProcess(String fileName) throws IOException {

        
        CommandLine cmdLine = new CommandLine("ls");
//        cmdLine.addArgument("-l");
//        cmdLine.addArgument("/h");
//        cmdLine.addArgument("${file}");
        
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("file", new File(fileName));
//        cmdLine.setSubstitutionMap(map);
        
        
        DefaultExecutor executor = new DefaultExecutor();
//        executor.setExitValue(1);
        
        CollectingLogOutputStream outputLog = new CollectingLogOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(outputLog);
        executor.setStreamHandler(psh);
        
//        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//        executor.setWatchdog(watchdog);
        
        int exitValue = executor.execute(cmdLine);
        
        
        System.out.println(outputLog.getLines());
    }
}