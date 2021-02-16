package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class ComputeOpenInteractiveSessionResponse(val sessions: List<OpenSession>)

const val TYPE_PROPERTY = "type"

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = TYPE_PROPERTY
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = OpenSession.Shell::class,
        name = "shell"
    ),
    JsonSubTypes.Type(
        value = OpenSession.Web::class,
        name = "web"
    ),
    JsonSubTypes.Type(
        value = OpenSession.Vnc::class,
        name = "vnc"
    ),
)
sealed class OpenSession {
    abstract val jobId: String
    abstract val rank: Int

    data class Shell(
        override val jobId: String,
        override val rank: Int,
        val sessionIdentifier: String,
    ) : OpenSession()

    data class Web(
        override val jobId: String,
        override val rank: Int,
        val redirectClientTo: String,
    ) : OpenSession()

    data class Vnc(
        override val jobId: String,
        override val rank: Int,
        val url: String,
        val password: String? = null
    ) : OpenSession()
}