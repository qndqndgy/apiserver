package com.my.iot.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WinProcessUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WinProcessUtil.class);
	private static List<Process> runningProcs = new ArrayList<Process>();

	public static final void startInfluxDProc() throws IOException, InterruptedException {
		String[] cmdArray = {"influxd.exe", "--config", "influxdb.conf"};
//		executeProcessByCommandArray(cmdArray);
		executeProcessByCommandArrayAsync(cmdArray);
	}
	
	public static final void startTelegrafDProc() throws IOException, InterruptedException {
		String[] cmdArray = {"telegraf.exe", "--config", "telegraf.conf"};
//		executeProcessByCommandArray(cmdArray);
		executeProcessByCommandArrayAsync(cmdArray);
	}
	
	// Api내부에서 Process의 OutputStream 출력 기능 때문에 Thread Block 문제가 존재하여, 사용하지 않는다.
	@Deprecated
	private static void executeProcessByCommandArray(String[] cmdArray) throws IOException,InterruptedException {
		// Apache에서 제공하는 Common Executor를 사용해서, 외부 Process 실행을 간단히 구현했다.
		DefaultExecutor executor = new DefaultExecutor();
	    
	    CommandLine cmdLine = CommandLine.parse(cmdArray[0]);
	    
	    // 개인적으로 선호하지 않는 Stream임.. (Lambda와 Stream은 익숙치 않으니 종종 써 본다.)
	    IntStream.range(1, cmdArray.length)
	    	.mapToObj(idx -> cmdArray[idx]).forEach(cmdLine::addArgument);
	    
	    // Windows shell의 Print Stream을 알아서 출력해준다.
	    executor.execute(cmdLine);
	}
	
	// 비동기로 외부 Process만 실행하고, Graceful Terminate를 위해 Process 목록만 저장한다.
	private static void executeProcessByCommandArrayAsync(String[] cmdArray) throws IOException,InterruptedException {
		Runtime runtime = Runtime.getRuntime();
        runningProcs.add(runtime.exec(cmdArray));
	}
	
	public static final void shutdownAllProcessesGracefully() {
		// 모든 Process를 종료한다.
		logger.error("Shutting down all processes reqested.");
		logger.error(String.format("All (%d) processes will be destroyed. ", runningProcs.size()) );
		
		for(Process proc : runningProcs) proc.destroy();
		
		//실행중인 process 배열 초기화
		runningProcs.clear();
		
		logger.error("Shutting down all processes success.");
	}
	
}
