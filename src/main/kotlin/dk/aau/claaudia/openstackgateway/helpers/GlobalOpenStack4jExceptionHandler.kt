package dk.aau.claaudia.openstackgateway.helpers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import org.openstack4j.api.exceptions.*
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalOpenStack4jExceptionHandler {
    @ExceptionHandler
    fun handleConnectionException(ex: ApiNotFoundException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: AuthenticationException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ClientResponseException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ConnectionException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ConnectorNotFoundException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ContainerNotEmptyException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: OS4JException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: RegionEndpointNotFoundException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ResponseException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    @ExceptionHandler
    fun handleConnectionException(ex: ServerResponseException) {
        logger.error("OpenStack4jExceptionHandler $ex ${ex.stackTraceToString()}")
    }

    companion object {
        val logger = getLogger()
    }
}
