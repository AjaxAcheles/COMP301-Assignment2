package main;

import grader.basics.execution.BasicProjectExecution;
import gradingTools.comp301ss24.assignment2.SS24Assignment2Suite;
import trace.grader.basics.GraderBasicsTraceUtility;

public class RunSS26A2Tests {
	private static final int MAX_PRINTED_TRACES = 600;
	private static final int MAX_TRACES = 2000;
	private static final int PROCESS_TIMEOUT_SECONDS = 5;

	public static void main(String[] args) {
		// if you set this to false, grader steps will not be traced
		GraderBasicsTraceUtility.setTracerShowInfo(true);
		// if you set this to false, all grader steps will be traced,
		// not just the ones that failed
		GraderBasicsTraceUtility.setBufferTracedMessages(true);
		// Change this number if a test trace gets longer than MAX_PRINTED_TRACES and is clipped
		GraderBasicsTraceUtility.setMaxPrintedTraces(MAX_PRINTED_TRACES);
		// Change this number if all traces together are longer than MAX_TRACES
		GraderBasicsTraceUtility.setMaxTraces(MAX_TRACES);
		// Change this number if your process times out prematurely
		BasicProjectExecution.setProcessTimeOut(PROCESS_TIMEOUT_SECONDS);
		// You need to always call such a method
		SS24Assignment2Suite.main(args);
	}
}
