package dk.aau.claaudia.openstackgateway.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * A class that partially matches a OpenStack Stack Template
 * It can be used to parse the parameters of a template
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class JsonTemplate (
    val parameters: Map<String, Map<String, Any>>
)
