##############################################################################
#                      GENERAL                                               #
##############################################################################

application = "snapit-media-manager"
aws_region  = "us-east-1"

##############################################################################
#                      HELM                                                  #
##############################################################################

ingress_nginx_name = "ingress-nginx-controller"

helm_service_template = [{
  name                = "snapit-media-manager"
  namespaces          = "snapit"
  is_there_config_map = true
  is_there_secret     = true
  secret_type         = "Opaque"

  helm_chart_key_value = {
    "chartName"                                     = "snapit-media-manager"
    "serviceAccount.create"                         = "true"
    "serviceAccount.name"                           = "snapit-media-manager-svc-acc"
    "service.type"                                  = "NodePort"
    "service.port"                                  = "28081"
    "service.targetPort"                            = "28081"
    "ingress.enabled"                               = "true"
    "image.pullPolicy"                              = "Always"
    "resources.requests.cpu"                        = "100m"
    "resources.requests.memory"                     = "256Mi"
    "resources.limits.cpu"                          = "200m"
    "resources.limits.memory"                       = "512Mi"
    "livenessProbe.initialDelaySeconds"             = "90"
    "livenessProbe.periodSeconds"                   = "90"
    "livenessProbe.timeoutSeconds"                  = "30"
    "readinessProbe.initialDelaySeconds"            = "60"
    "readinessProbe.periodSeconds"                  = "90"
    "readinessProbe.timeoutSeconds"                 = "30"
    "autoscaling.enabled"                           = "true"
    "autoscaling.minReplicas"                       = "2"
    "autoscaling.maxReplicas"                       = "2"
    "autoscaling.targetCPUUtilizationPercentage"    = "70"
    "autoscaling.targetMemoryUtilizationPercentage" = "70"
    "configMap.enabled"                             = "false"
    "nameOverride"                                  = "snapit-media-manager"
    "fullnameOverride"                              = "snapit-media-manager-api"
  }

  helm_chart_config_map = {
    "APPLICATION_NAME" = "snapit-media-manager"
    "API_DOCS_PATH"    = "/api-docs"
    "API_PORT"         = "28081"
    "DATABASE_URL"     = "jdbc:postgresql://snapit-db.cq23vjwswp3a.us-east-1.rds.amazonaws.com:5432/postgres"
  }


}]
