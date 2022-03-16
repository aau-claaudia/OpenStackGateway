package dk.aau.claaudia.openstackgateway.models

/**
 * Openstack stack status enum
 */
enum class StackStatus() {
    CREATE_COMPLETE,
    CREATE_IN_PROGRESS,
    CREATE_FAILED,
    UPDATE_COMPLETE,
    UPDATE_IN_PROGRESS,
    UPDATE_FAILED,
    RESUME_FAILED,
    DELETE_FAILED,
    DELETE_IN_PROGRESS,
    DELETE_COMPLETE
}
