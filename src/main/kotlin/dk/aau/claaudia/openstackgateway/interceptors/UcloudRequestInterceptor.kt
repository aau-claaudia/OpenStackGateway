package dk.aau.claaudia.openstackgateway.interceptors

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

@Component
class UcloudRequestInterceptor : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        val requestCacheWrapperObject: HttpServletRequest = ContentCachingRequestWrapper(request)

        // invoke the following method to ensure that request data is cached? Doesnt seem to work
        val parameterMap = requestCacheWrapperObject.getParameter("baba")

        // Read inputStream from requestCacheWrapperObject and log it
        // The problem is that once this is done the context cannot be read againg.
        // So use this interceptor for debugging only
        val readText = requestCacheWrapperObject.reader.readText()

        logger.info("Incoming url is: $readText")
        logger.info("Incoming url is: " + requestCacheWrapperObject.requestURL)

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        //
    }

    companion object {
        val logger = getLogger()
    }
}