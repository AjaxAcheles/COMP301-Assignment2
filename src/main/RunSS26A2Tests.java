package main;

import grader.basics.execution.BasicProjectExecution;
import gradingTools.comp301ss24.assignment2.SS24Assignment2Suite;
import trace.grader.basics.GraderBasicsTraceUtility;

public class RunSS26A2Tests {
	private static final int MAX_PRINTED_TRACES = 600;
	private static final int MAX_TRACES = 2000;
	private static final int PROCESS_TIMEOUT_SECONDS = 5;

	public static void main(String[] args) {
		GraderBasicsTraceUtility.setTracerShowInfo(true);
		GraderBasicsTraceUtility.setBufferTracedMessages(true);
		GraderBasicsTraceUtility.setMaxPrintedTraces(MAX_PRINTED_TRACES);
		GraderBasicsTraceUtility.setMaxTraces(MAX_TRACES);
		BasicProjectExecution.setProcessTimeOut(PROCESS_TIMEOUT_SECONDS);
		SS24Assignment2Suite.main(args);
	}
}
