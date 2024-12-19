# Gatekeeper Configuration

Install the gatekeeper operator using the web UI. The attempt to install using the ACM policy failed for some reason which needs to be investigated. Policy files in this directory will need updating.

After installing the operator create a gatekeeper instance and update the configuration to include :

    apiVersion: operator.gatekeeper.sh/v1alpha1
    kind: Gatekeeper
    metadata:
    name: gatekeeper
    resourceVersion: '3570113'
    spec:
    audit:
        constraintViolationLimit: 600
        emitAuditEvents: Enabled
        replicas: 1
    config:
        matches:
        - excludedNamespaces:
            - ansible-automation-platform
            - hive
            - kube-*
            - local-quay
            - multicluster-engine
            - open-cluster-management
            - open-cluster-management-*
            - openshift-*
            - rhacs-operator
            - stackrox
            processes:
            - '*'
    validatingWebhook: Enabled

