package dk.aau.claaudia.openstackgateway.security


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import dk.aau.claaudia.openstackgateway.config.AppProperties
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizationFilter(authManager: AuthenticationManager?, private val appProperties: AppProperties) :
    BasicAuthenticationFilter(authManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(appProperties.security.incoming.headerString)
        if (header == null || !header.startsWith(appProperties.security.incoming.tokenPrefix)) {
            chain.doFilter(req, res)
            return
        }
        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): PreAuthenticatedAuthenticationToken {
        val token = request.getHeader(appProperties.security.incoming.headerString)
            .replace(appProperties.security.incoming.tokenPrefix, "")

        val publicKeyPEM = appProperties.security.incoming.publicKey
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(System.lineSeparator().toRegex(), "")
            .replace("-----END PUBLIC KEY-----", "")

        val encoded = Base64.getDecoder().decode(publicKeyPEM)

        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(encoded)
        val publicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey

        val algorithm = Algorithm.RSA256(publicKey, null)

        val run = JWT.require(algorithm).run {
            withIssuer("cloud.sdu.dk")
            withClaim("sub", "_UCloud")
            withClaim("role", "SERVICE")
            build()
        }

        val verify: DecodedJWT = run.verify(token)

        return PreAuthenticatedAuthenticationToken(verify.subject, null, listOf())
    }
}