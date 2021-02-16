package dk.aau.claaudia.openstackgateway.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles

inline fun getLogger(): Logger {
    return LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
}