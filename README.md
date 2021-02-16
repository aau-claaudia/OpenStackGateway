# OpenStackGateway

Compute provider API for UCloud

# Microstack Openstack Setup

This is outdated. Update or remove

First, install microstack:

`sudo snap install microstack --beta --devmode` \
`sudo microstack init --auto --control`


# User setup

Create user: \
`microstack.openstack user create ucloud --password 1234`

Create project: \
`microstack.openstack project create ucloud`

Create role: \
`microstack.openstack role create ucloud`

To assign a user to a project, you must assign the role to a user-project pair. To do this, you need the user, role, and project IDs.
List users and note the user ID you want to assign to the role: \
`microstack.openstack user list`

List role IDs and note the role ID you want to assign: \
`microstack.openstack role list`

List projects and note the project ID you want to assign to the role:
Assign a role to a user-project pair: \
`microstack.openstack role add --user ucloud --project PROJECT_ID ucloud`


# Model generation.

`./gradlew openApiGenerate`

can be used to generate models
Not everything works however. I needed to use the bulkrequest model from ucloud as well as some modifications to them.
This will need to be updated in the future.
It uses the spec definition from the specs folder.


# Datebase migrations

`./gradlew diffChangeLog`

can be used to generate database migrations. 
They do not merge very good with existing migrations.
Also, for some reason, the migration always contain a change to create the hibernate sequence.
When generating new migrations this will need to be removed. 
The code should be moved to a seperate file in the "changes" folder and included in the changelog-master.
This is a bit dirty, but it works for now.
The alternative is to define migrations manually.