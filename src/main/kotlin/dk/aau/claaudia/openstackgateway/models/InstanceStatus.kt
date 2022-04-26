package dk.aau.claaudia.openstackgateway.models

/**
 * Openstack stack status enum
 * Based on https://github.com/openstack/nova/blob/master/nova/compute/vm_states.py
 */
enum class InstanceStatus() {
    ACTIVE,
    BUILDING,
    PAUSED,
    SUSPENDED,
    STOPPED,
    RESCUED,
    RESIZED,
    SOFT_DELETED,
    DELETED,
    ERROR,
    SHELVED,
    SHELVED_OFFLOADED
}
