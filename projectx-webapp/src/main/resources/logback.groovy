import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.net.*
import ch.qos.logback.core.*
import ch.qos.logback.core.encoder.*
import ch.qos.logback.core.read.*
import ch.qos.logback.core.rolling.*
import ch.qos.logback.core.status.*
import com.wlw.immortal.log.logback.LogbackAppender

statusListener(OnConsoleStatusListener)

def props = new Properties()
props.load(this.getClass().getClassLoader().getResourceAsStream("properties/application.properties"))

def config = new ConfigSlurper().parse(props)

def env =  System.properties['spring.profiles.active'] ?: 'production'
def appenderList = []
def level = ERROR
def LOG_DIR = '/apps/logs/'+config.appname+'/logs'

if (env == 'production') {
	appenderList.add("ROLLING")
} else if(env == 'integratetest') {
	appenderList.add("ROLLING")
	level = WARN
} else if(env == 'development') {
	appenderList.add("CONSOLE")
	level = INFO
}
if(env=='development') {
	appender("CONSOLE", ConsoleAppender) {
		encoder(PatternLayoutEncoder) { pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n" }
	}
}

if(env=='production' || env=='integratetest') {
	appender("ROLLING", RollingFileAppender) {
		encoder(PatternLayoutEncoder) { Pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n" }
		rollingPolicy(TimeBasedRollingPolicy) {
			fileNamePattern = "${LOG_DIR}/translator-%d{yyyy-MM-dd-HH}.zip"
			triggeringPolicy(timeBasedFileNamingAndTriggeringPolicy) { maxFileSize = '10M' }
		}
	}
}

if(env=='production' || env=='integratetest') {
	appender("IMMORTAL", LogbackAppender) {
	  encoder(PatternLayoutEncoder) {
	    pattern = "%date [%thread] %-5level %logger{80} - %msg%n"
	  }
	}
	
	logger("com.cci.projectx.core.controller", INFO, ["IMMORTAL"], false)
	appender("ImmortalTraceFileAppender", RollingFileAppender) {
	  file = "/apps/logs/trace/logs/trace-log.out"
	  rollingPolicy(FixedWindowRollingPolicy) {
	    fileNamePattern = "/apps/logs/trace/logs/trace-log-%i.out"
	    minIndex = 1
	    maxIndex = 10
	  }
	  append = true
	  triggeringPolicy(SizeBasedTriggeringPolicy) {
	    maxFileSize = "10GB"
	  }
	  encoder(PatternLayoutEncoder) {
	    pattern = "%msg%n"
	  }
	}
	
	logger("immortal-trace-logger", INFO, ["ImmortalTraceFileAppender"], false)
	
	appender("ImmortalLogFileAppender", RollingFileAppender) {
	  file = "/apps/logs/trace/logs/log-log.out"
	  rollingPolicy(FixedWindowRollingPolicy) {
	    fileNamePattern = "/apps/logs/trace/logs/log-log-%i.out"
	    minIndex = 1
	    maxIndex = 10
	  }
	  append = true
	  triggeringPolicy(SizeBasedTriggeringPolicy) {
	    maxFileSize = "10GB"
	  }
	  encoder(PatternLayoutEncoder) {
	    pattern = "%msg%n"
	  }
	}
	
	logger("immortal-log-logger", INFO, ["ImmortalLogFileAppender"], false)
	
	appender("ImmortalMetricFileAppender", RollingFileAppender) {
	  file = "/apps/logs/trace/logs/metric-log.out"
	  rollingPolicy(FixedWindowRollingPolicy) {
	    fileNamePattern = "/apps/logs/trace/logs/metric-log-%i.out"
	    minIndex = 1
	    maxIndex = 10
	  }
	  append = true
	  triggeringPolicy(SizeBasedTriggeringPolicy) {
	    maxFileSize = "10GB"
	  }
	  encoder(PatternLayoutEncoder) {
	    pattern = "%msg%n"
	  }
	}
	
	logger("immortal-metric-logger", INFO, ["ImmortalMetricFileAppender"], false)
}

root(level, appenderList)